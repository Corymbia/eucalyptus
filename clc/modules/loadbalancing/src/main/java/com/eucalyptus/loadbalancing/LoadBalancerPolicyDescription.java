/*************************************************************************
 * Copyright 2009-2014 Ent. Services Development Corporation LP
 *
 * Redistribution and use of this software in source and binary forms,
 * with or without modification, are permitted provided that the
 * following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer
 *   in the documentation and/or other materials provided with the
 *   distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ************************************************************************/

package com.eucalyptus.loadbalancing;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import com.eucalyptus.crypto.util.PEMFiles;
import com.eucalyptus.entities.AbstractPersistent;
import com.eucalyptus.entities.Entities;
import com.eucalyptus.entities.TransactionResource;
import com.eucalyptus.loadbalancing.LoadBalancerBackendServerDescription.LoadBalancerBackendServerDescriptionCoreView;
import com.eucalyptus.loadbalancing.LoadBalancerBackendServerDescription.LoadBalancerBackendServerDescriptionCoreViewTransform;
import com.eucalyptus.loadbalancing.LoadBalancerListener.LoadBalancerListenerCoreView;
import com.eucalyptus.loadbalancing.LoadBalancerListener.LoadBalancerListenerCoreViewTransform;
import com.eucalyptus.loadbalancing.LoadBalancerPolicyAttributeDescription.LoadBalancerPolicyAttributeDescriptionCoreView;
import com.eucalyptus.loadbalancing.LoadBalancerPolicyAttributeDescription.LoadBalancerPolicyAttributeDescriptionCoreViewTransform;
import com.eucalyptus.loadbalancing.LoadBalancerPolicyAttributeDescription.LoadBalancerPolicyAtttributeDescriptionEntityTransform;
import com.eucalyptus.loadbalancing.LoadBalancerPolicyAttributeTypeDescription.LoadBalancerPolicyAttributeTypeDescriptionCoreView;
import com.eucalyptus.loadbalancing.service.InvalidConfigurationRequestException;
import com.eucalyptus.util.EucalyptusCloudException;
import com.eucalyptus.util.Exceptions;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Entity
@PersistenceContext( name = "eucalyptus_loadbalancing" )
@Table( name = "metadata_policy_description" )
public class LoadBalancerPolicyDescription extends AbstractPersistent {
  private static Logger    LOG     = Logger.getLogger( LoadBalancerPolicyDescription.class );

  private static final long serialVersionUID = 1L;
  
  @Transient
  private LoadBalancerPolicyDescriptionRelationView view = null;
  
  @ManyToOne
  @JoinColumn( name = "metadata_loadbalancer_fk", nullable=false )
  private LoadBalancer loadbalancer = null;
  
  @ManyToMany( fetch = FetchType.LAZY, mappedBy="policies")
  private List<LoadBalancerListener> listeners = null;

  @ManyToMany( fetch = FetchType.LAZY, mappedBy="policyDescriptions" )
  private List<LoadBalancerBackendServerDescription> backendServers = null;
  
  @Column( name = "policy_name", nullable=false)
  private String policyName = null;
  
  @Column( name = "policy_type_name", nullable=true)
  private String policyTypeName = null;

  @Column( name = "unique_name", unique=true, nullable=false)
  private String uniqueName = null;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "policyDescription")
  private List<LoadBalancerPolicyAttributeDescription> policyAttrDescription = null;
  
  @Transient
  private LoadBalancerPolicyTypeDescription policyType = null;
  
  private LoadBalancerPolicyDescription(){}
  
  private LoadBalancerPolicyDescription(final LoadBalancer lb, final String policyName){
    this.loadbalancer = lb;
    this.policyName = policyName;
  }
  
  public LoadBalancerPolicyDescription(final LoadBalancer lb, final String policyName, final String policyTypeName){
    this(lb, policyName);
    this.policyTypeName = policyTypeName;
  }
  
  public LoadBalancerPolicyDescription(final LoadBalancer lb, final String policyName, 
      final String policyTypeName, final List<LoadBalancerPolicyAttributeDescription> descs){
    this(lb, policyName, policyTypeName);
    this.policyAttrDescription = descs;
  } 
  
  public static LoadBalancerPolicyDescription named(final LoadBalancer lb, final String policyName){
    final LoadBalancerPolicyDescription instance = new LoadBalancerPolicyDescription(lb, policyName);
    instance.uniqueName = instance.createUniqueName();
    return instance;
  }
  
  public String getPolicyName(){
    return this.policyName;
  }
  
  public String getPolicyTypeName(){
    return this.policyTypeName;
  }
  
  public LoadBalancerPolicyTypeDescription getPolicyTypeDescription(){
    if(this.policyType == null){
      this.policyType = LoadBalancerPolicies.findLoadBalancerPolicyTypeDescription(this.policyTypeName);
    }
    return this.policyType;
  }
  
  public void addPolicyAttributeDescription(final String attrName, String attrValue) 
      throws InvalidConfigurationRequestException {
    if(this.getPolicyTypeDescription() != null){
      LoadBalancerPolicyAttributeTypeDescriptionCoreView attrType = null;
      for(final LoadBalancerPolicyAttributeTypeDescriptionCoreView type: this.policyType.getPolicyAttributeTypeDescriptions()){
        if(attrName.equals(type.getAttributeName())){
          attrType = type;
          break;
        }
      }
      if(attrType==null)
         throw new InvalidConfigurationRequestException(String.format("Attribute %s is not defined in the policy type", attrName));
      if(!LoadBalancerPolicies.isAttributeValueValid(attrType.getAttributeType(), attrType.getCardinality(), attrValue))
        throw new InvalidConfigurationRequestException(String.format("Attribute value %s is not valid", attrValue));
     
      /* check for cardinality
       * ONE(1) : Single value required
        ZERO_OR_ONE(0..1) : Up to one value can be supplied
        ZERO_OR_MORE(0..*) : Optional. Multiple values are allowed
        ONE_OR_MORE(1..*0) : Required. Multiple values are allowed
       */
      final String cardinality = attrType.getCardinality();
      if(this.policyAttrDescription != null && ("ONE".equals(cardinality) || "ZERO_OR_ONE".equals(cardinality))) {
        for(final LoadBalancerPolicyAttributeDescription existing : this.policyAttrDescription) {
          if(attrName.equals(existing.getAttributeName()))
            throw new InvalidConfigurationRequestException(String.format("More than one attribute(%s) is found (Cardinality: %s)", attrName, cardinality));
        }
      }
      
      if ("PublicKeyPolicyType".equals(this.policyType.getPolicyTypeName()) &&
          "PublicKey".equals(attrName)) {
        try{
          String certString = attrValue.trim();
          if(! certString.startsWith("-----BEGIN CERTIFICATE-----"))
            certString = String.format("-----BEGIN CERTIFICATE-----\n%s", certString);
          if(! certString.endsWith("-----END CERTIFICATE-----"))
            certString = String.format("%s\n-----END CERTIFICATE-----", certString);
          final X509Certificate cert = PEMFiles.getCert(certString.getBytes( Charsets.UTF_8 ));
          if(cert==null)
            throw new EucalyptusCloudException("Malformed cert");
          attrValue = certString;
        }catch(final Exception ex){
          throw new InvalidConfigurationRequestException("PublicKey is invalid");
        }
      }
    }
    
    final LoadBalancerPolicyAttributeDescription attr = new LoadBalancerPolicyAttributeDescription(this, attrName, attrValue);
    if(this.policyAttrDescription == null)
      this.policyAttrDescription = Lists.newArrayList();
    this.policyAttrDescription.add(attr);
  }
  
  public void removePolicyAttributeDescription(final LoadBalancerPolicyAttributeDescription desc){
    if(this.policyAttrDescription == null)
      return;
    this.policyAttrDescription.remove(desc);
  }
  
  public List<LoadBalancerPolicyAttributeDescriptionCoreView> getPolicyAttributeDescription(){
    return this.view.getPolicyAttributeDescription();
  }
  
  public List<LoadBalancerPolicyAttributeDescription> findAttributeDescription(final String attrName) throws NoSuchElementException{
    final List<LoadBalancerPolicyAttributeDescription> attributes = Lists.newArrayList();
    for (final LoadBalancerPolicyAttributeDescriptionCoreView attrView : this.getPolicyAttributeDescription()){
      if(attrView.getAttributeName().equals(attrName)) {
        attributes.add(LoadBalancerPolicyAtttributeDescriptionEntityTransform.INSTANCE.apply(attrView));
      }
    }
    return attributes;
  }
  
  public List<LoadBalancerListenerCoreView> getListeners(){
    return this.view.getListeners();
  }
  
  public List<LoadBalancerBackendServerDescriptionCoreView> getBackendServers(){
    return this.view.getBackendServers();
  }
  
  @PostLoad
  private void onLoad(){
    if(this.view==null)
      this.view = new LoadBalancerPolicyDescriptionRelationView(this);
  }
  
 @PrePersist
  private void generateOnCommit( ) {
    if(this.uniqueName==null)
      this.uniqueName = createUniqueName( );
  }

  protected String createUniqueName( ) {
    return String.format("policy-%s-%s-%s", this.loadbalancer.getOwnerAccountNumber(), this.loadbalancer.getDisplayName(), this.policyName);
  }
  
  public String getUniqueName(){
    return this.uniqueName;
  }
  
  @Override
  public boolean equals(final Object obj){
    if ( this == obj ) {
      return true;
    }
    if ( obj == null ) {
      return false;
    }
    if ( getClass( ) != obj.getClass( ) ) {
      return false;
    }
    final LoadBalancerPolicyDescription other = (LoadBalancerPolicyDescription) obj;
    if(this.loadbalancer==null){
      if( other.loadbalancer!=null){
        return false;
      }
    }else if(!this.loadbalancer.equals(other.loadbalancer)){
      return false;
    }
    
    if ( this.policyName == null ) {
      if ( other.policyName != null ) {
        return false;
      }
    } else if ( !this.policyName.equals( other.policyName ) ) {
      return false;
    }
    
    return true;
  }
  
  public final String getRecordId(){
    return this.getId();
  }
  
  @Override
  public int hashCode(){
    final int prime = 31;
    int result = 1;

    result = prime * result +  ( ( this.loadbalancer == null )
      ? 0
      : this.loadbalancer.hashCode( ) );
    
    result = prime * result + ( ( this.policyName == null )
      ? 0
      : this.policyName.hashCode( ) );
    return result;
  }
  
  @Override
  public String toString(){
    return String.format("LoadBalancer Policy Description for (%s):%s-%s", this.loadbalancer, this.policyName, this.policyTypeName);
  }
  
  public static class LoadBalancerPolicyDescriptionRelationView{
    private LoadBalancerPolicyDescription policyDesc = null;
    private ImmutableList<LoadBalancerPolicyAttributeDescriptionCoreView> policyAttrDesc = null;
    private ImmutableList<LoadBalancerListenerCoreView> listeners = null;
    private ImmutableList<LoadBalancerBackendServerDescriptionCoreView> backendServers = null;
    LoadBalancerPolicyDescriptionRelationView(final LoadBalancerPolicyDescription desc){
      this.policyDesc = desc;
      if(desc.policyAttrDescription != null)
        this.policyAttrDesc = ImmutableList.copyOf(Collections2.transform(desc.policyAttrDescription,
            LoadBalancerPolicyAttributeDescriptionCoreViewTransform.INSTANCE));
      if(desc.listeners!= null)
        this.listeners = ImmutableList.copyOf(Collections2.transform(desc.listeners, 
            LoadBalancerListenerCoreViewTransform.INSTANCE));
      
      if(desc.backendServers!=null)
        this.backendServers = ImmutableList.copyOf(Collections2.transform(desc.backendServers,
            LoadBalancerBackendServerDescriptionCoreViewTransform.INSTANCE));
    }
    
    public ImmutableList<LoadBalancerPolicyAttributeDescriptionCoreView> getPolicyAttributeDescription(){
      return this.policyAttrDesc;
    }
    
    public ImmutableList<LoadBalancerListenerCoreView> getListeners(){
      return this.listeners;
    }
    
    public ImmutableList<LoadBalancerBackendServerDescriptionCoreView> getBackendServers(){
      return this.backendServers;
    }
  }
  
  public static class LoadBalancerPolicyDescriptionCoreView {
    private LoadBalancerPolicyDescription policyDesc = null;
    LoadBalancerPolicyDescriptionCoreView(final LoadBalancerPolicyDescription desc){
      this.policyDesc = desc;
    }
    
    public String getPolicyName(){
      return this.policyDesc.policyName;
    }
    
    public String getPolicyTypeName(){
      return this.policyDesc.policyTypeName;
    }
  }
  
  public enum LoadBalancerPolicyDescriptionCoreViewTransform implements 
    Function<LoadBalancerPolicyDescription, LoadBalancerPolicyDescriptionCoreView>{
    INSTANCE;

    @Override
    public LoadBalancerPolicyDescriptionCoreView apply(
        LoadBalancerPolicyDescription arg0) {
      return new LoadBalancerPolicyDescriptionCoreView(arg0);
    }
  }
  
  public enum LoadBalancerPolicyDescriptionEntityTransform implements
    Function<LoadBalancerPolicyDescriptionCoreView, LoadBalancerPolicyDescription>{
    INSTANCE;

    @Override
    public LoadBalancerPolicyDescription apply(
        LoadBalancerPolicyDescriptionCoreView arg0) {
      try ( final TransactionResource db = Entities.transactionFor( LoadBalancerPolicyDescription.class ) ) {
        return Entities.uniqueResult(arg0.policyDesc);
      }catch(final NoSuchElementException ex){
        throw ex;
      }catch (final Exception ex) {
        throw Exceptions.toUndeclared(ex);
      }
    }
  }
}
