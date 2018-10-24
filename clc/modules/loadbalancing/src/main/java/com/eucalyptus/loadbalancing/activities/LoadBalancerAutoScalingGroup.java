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
package com.eucalyptus.loadbalancing.activities;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import com.eucalyptus.entities.AbstractPersistent;
import com.eucalyptus.entities.Entities;
import com.eucalyptus.entities.TransactionResource;
import com.eucalyptus.loadbalancing.LoadBalancer;
import com.eucalyptus.loadbalancing.LoadBalancer.LoadBalancerCoreView;
import com.eucalyptus.loadbalancing.activities.LoadBalancerServoInstance.LoadBalancerServoInstanceCoreView;
import com.eucalyptus.loadbalancing.activities.LoadBalancerServoInstance.LoadBalancerServoInstanceCoreViewTransform;
import com.eucalyptus.util.Exceptions;
import com.eucalyptus.util.NonNullFunction;
import com.eucalyptus.util.TypeMapper;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;

/**
 * @author Sang-Min Park
 *
 */
@Entity
@PersistenceContext( name = "eucalyptus_loadbalancing" )
@Table( name = "metadata_autoscale_group" )
public class LoadBalancerAutoScalingGroup extends AbstractPersistent {
	private static Logger    LOG     = Logger.getLogger( LoadBalancerAutoScalingGroup.class );

	private static final long serialVersionUID = 1L;

	@Transient
	private LoadBalancerAutoScalingGroupRelationView view = null;

	@PostLoad
	private void onLoad(){
		if(this.view==null)
			this.view = new LoadBalancerAutoScalingGroupRelationView(this);
	}
	

  @ManyToOne
  @JoinColumn( name = "metadata_loadbalancer_fk", nullable=false )
  private LoadBalancer loadbalancer = null;
  
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "autoscaling_group")
	private Collection<LoadBalancerServoInstance> servos = null;

	@Column(name="metadata_group_name", nullable=false)
	private String groupName = null;
	
	@Column(name="metadata_capacity", nullable=true)
	private Integer capacity = null;
	
	/// NOTE: Post 3.4, launch_config_name is not used; left here for upgrade
	/// To reference the latest launch config name associated with scaling group, use 'describe-autoscaling-group'
	@Column(name="metadata_launch_config_name", nullable=true)
	private String launchConfig = null; // not used post 3.4.
	
  @Column(name="metadata_availability_zone", nullable=true)
  private String availabilityZone = null;

	@Column(name="metadata_user_subnet_id", nullable=true)
	private String userSubnetId = null;

	@Column(name="metadata_system_subnet_id", nullable=true)
	private String systemSubnetId = null;


	@Column(name="unique_name", nullable=false, unique=true)
	private String uniqueName = null;
	
	private LoadBalancerAutoScalingGroup(){}
	private LoadBalancerAutoScalingGroup(final LoadBalancer lb, final String availabilityZone, final String groupName, final String launchConfig){
		this.loadbalancer = lb;
		this.availabilityZone = availabilityZone;
		this.groupName = groupName;		
		this.launchConfig = launchConfig;
		this.uniqueName = this.createUniqueName();
		view = new LoadBalancerAutoScalingGroupRelationView(this);
	}

	private LoadBalancerAutoScalingGroup(final LoadBalancer lb, final String availabilityZone,
										 final String userSubnetId, final String systemSubnetId, final String groupName, final String launchConfig){
		this.loadbalancer = lb;
		this.availabilityZone = availabilityZone;
		this.userSubnetId =  userSubnetId;
		this.systemSubnetId = systemSubnetId;
		this.groupName = groupName;
		this.launchConfig = launchConfig;
		this.uniqueName = this.createUniqueName();
		view = new LoadBalancerAutoScalingGroupRelationView(this);
	}

	public static LoadBalancerAutoScalingGroup newInstance(final LoadBalancer lb, final String availabilityZone,
														   final String userSubnetId, final String systemSubnetId, final String groupName, final String launchConfig) {
		final LoadBalancerAutoScalingGroup instance = new LoadBalancerAutoScalingGroup(lb, availabilityZone,
				userSubnetId, systemSubnetId, groupName, launchConfig);
		return instance;
	}

	public static LoadBalancerAutoScalingGroup newInstance(final LoadBalancer lb, final String availabilityZone, final String groupName, final String launchConfig){
		final LoadBalancerAutoScalingGroup instance = new LoadBalancerAutoScalingGroup(lb, availabilityZone, groupName, launchConfig);
		return instance;
	}
	
	public static LoadBalancerAutoScalingGroup named(final LoadBalancer lb, final String availabilityZone){
		LoadBalancerAutoScalingGroup instance = new LoadBalancerAutoScalingGroup();
		instance.loadbalancer = lb;
		instance.availabilityZone = availabilityZone;
		instance.uniqueName = instance.createUniqueName();
		return instance;
	}
		
	public static LoadBalancerAutoScalingGroup named(){
		return new LoadBalancerAutoScalingGroup();
	}
	
	public String getName(){
		return this.groupName;
	}
	
	public void setCapacity(int capacity){
		this.capacity = capacity;
	}
	
	public int getCapacity(){
		return this.capacity;
	}
	
	public String getAvailabilityZone() {
	  return this.availabilityZone;
	}
	
	public void setAvailabilityZone(final String zone) {
	  this.availabilityZone = zone;
	}

	public String getUserSubnetId() { return this.userSubnetId; }

	public String getSystemSubnetId() { return this.systemSubnetId; }

	public void setUserSubnetId(final String subnetId) { this.userSubnetId = subnetId; }

	public void setSystemSubnetId(final String subnetId) { this.systemSubnetId = subnetId; }
	
	public List<LoadBalancerServoInstanceCoreView> getServos(){
		return view.getServos();
	}
	
    public void setLoadBalancer(LoadBalancer lb){
    	this.loadbalancer = lb;
    }
    
    public LoadBalancerCoreView getLoadBalancer(){
    	return this.view.getLoadBalancer();
    }
    
    @PrePersist
    private void generateOnCommit( ) {
    	if(this.uniqueName==null)
    		this.uniqueName = createUniqueName( );
    }

    protected String createUniqueName( ) {
    	return String.format("autoscale-group-%s-%s-%s", 
    	    this.loadbalancer.getOwnerAccountNumber(), 
    	    this.loadbalancer.getDisplayName(),
    	    this.availabilityZone);
    }
    
    public static class LoadBalancerAutoScalingGroupCoreView {
    	private LoadBalancerAutoScalingGroup group = null;
    	
    	LoadBalancerAutoScalingGroupCoreView(LoadBalancerAutoScalingGroup group){
    		this.group = group;
    	}
    	
    	public String getName(){
    		return this.group.getName();
    	}
    	
    	public int getCapacity(){
    		return this.group.getCapacity();
    	}    	
    	
    	public String getAvailabilityZone() {
    	  return this.group.getAvailabilityZone();
    	}

		public String getUserSubnetId() { return this.group.getUserSubnetId(); }

		public String getSystemSubnetId() { return this.group.getSystemSubnetId(); }
    }
    
	@TypeMapper
    public enum LoadBalancerAutoScalingGroupCoreViewTransform implements Function<LoadBalancerAutoScalingGroup, LoadBalancerAutoScalingGroupCoreView>{
    	INSTANCE;

		@Override
		@Nullable
		public LoadBalancerAutoScalingGroupCoreView apply(
				@Nullable LoadBalancerAutoScalingGroup arg0) {
			return new LoadBalancerAutoScalingGroupCoreView(arg0);
		}
    }

	public enum LoadBalancerAutoScalingGroupEntityTransform implements NonNullFunction<LoadBalancerAutoScalingGroupCoreView, LoadBalancerAutoScalingGroup> {
		INSTANCE;

		@Nonnull
    @Override
		public LoadBalancerAutoScalingGroup apply(
				@Nullable LoadBalancerAutoScalingGroupCoreView arg0) {
			try ( final TransactionResource db = Entities.transactionFor( LoadBalancerAutoScalingGroup.class ) ) {
				return Entities.uniqueResult(arg0.group);
			}catch(final Exception ex){
				throw Exceptions.toUndeclared(ex);
			}
		}
	}
	
	public static class LoadBalancerAutoScalingGroupRelationView {
		private ImmutableList<LoadBalancerServoInstanceCoreView> servos = null;
		private LoadBalancer loadbalancer = null;

		LoadBalancerAutoScalingGroupRelationView(
				LoadBalancerAutoScalingGroup group) {
			if(group.servos!=null)
				servos = ImmutableList.copyOf(Collections2.transform(group.servos, LoadBalancerServoInstanceCoreViewTransform.INSTANCE));
			if(group.loadbalancer!=null) {
				Entities.initialize( group.loadbalancer );
				loadbalancer = group.loadbalancer;
			}
		}
		
		public ImmutableList<LoadBalancerServoInstanceCoreView> getServos(){
			return servos;
		}
		
		public LoadBalancerCoreView getLoadBalancer(){
			return loadbalancer.getCoreView( );
		}
	}
}
