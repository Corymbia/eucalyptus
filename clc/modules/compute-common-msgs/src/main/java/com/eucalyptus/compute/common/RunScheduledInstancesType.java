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
package com.eucalyptus.compute.common;

public class RunScheduledInstancesType extends ScheduledInstanceComputeMessage {

  private String clientToken;
  private Integer instanceCount;
  private ScheduledInstancesLaunchSpecification launchSpecification;
  private String scheduledInstanceId;

  public String getClientToken( ) {
    return clientToken;
  }

  public void setClientToken( String clientToken ) {
    this.clientToken = clientToken;
  }

  public Integer getInstanceCount( ) {
    return instanceCount;
  }

  public void setInstanceCount( Integer instanceCount ) {
    this.instanceCount = instanceCount;
  }

  public ScheduledInstancesLaunchSpecification getLaunchSpecification( ) {
    return launchSpecification;
  }

  public void setLaunchSpecification( ScheduledInstancesLaunchSpecification launchSpecification ) {
    this.launchSpecification = launchSpecification;
  }

  public String getScheduledInstanceId( ) {
    return scheduledInstanceId;
  }

  public void setScheduledInstanceId( String scheduledInstanceId ) {
    this.scheduledInstanceId = scheduledInstanceId;
  }
}