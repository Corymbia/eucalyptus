/*************************************************************************
 * (c) Copyright 2017 Hewlett Packard Enterprise Development Company LP
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 ************************************************************************/
package com.eucalyptus.cloudformation.common.msgs;

import java.util.Date;
import edu.ucsb.eucalyptus.msgs.EucalyptusData;

public class StackEvent extends EucalyptusData {

  private String eventId;
  private String logicalResourceId;
  private String physicalResourceId;
  private String resourceProperties;
  private String resourceStatus;
  private String resourceStatusReason;
  private String resourceType;
  private String stackId;
  private String stackName;
  private Date timestamp;

  public String getEventId( ) {
    return eventId;
  }

  public void setEventId( String eventId ) {
    this.eventId = eventId;
  }

  public String getLogicalResourceId( ) {
    return logicalResourceId;
  }

  public void setLogicalResourceId( String logicalResourceId ) {
    this.logicalResourceId = logicalResourceId;
  }

  public String getPhysicalResourceId( ) {
    return physicalResourceId;
  }

  public void setPhysicalResourceId( String physicalResourceId ) {
    this.physicalResourceId = physicalResourceId;
  }

  public String getResourceProperties( ) {
    return resourceProperties;
  }

  public void setResourceProperties( String resourceProperties ) {
    this.resourceProperties = resourceProperties;
  }

  public String getResourceStatus( ) {
    return resourceStatus;
  }

  public void setResourceStatus( String resourceStatus ) {
    this.resourceStatus = resourceStatus;
  }

  public String getResourceStatusReason( ) {
    return resourceStatusReason;
  }

  public void setResourceStatusReason( String resourceStatusReason ) {
    this.resourceStatusReason = resourceStatusReason;
  }

  public String getResourceType( ) {
    return resourceType;
  }

  public void setResourceType( String resourceType ) {
    this.resourceType = resourceType;
  }

  public String getStackId( ) {
    return stackId;
  }

  public void setStackId( String stackId ) {
    this.stackId = stackId;
  }

  public String getStackName( ) {
    return stackName;
  }

  public void setStackName( String stackName ) {
    this.stackName = stackName;
  }

  public Date getTimestamp( ) {
    return timestamp;
  }

  public void setTimestamp( Date timestamp ) {
    this.timestamp = timestamp;
  }
}
