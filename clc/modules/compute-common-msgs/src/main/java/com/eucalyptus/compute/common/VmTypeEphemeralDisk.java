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

import edu.ucsb.eucalyptus.msgs.EucalyptusData;

public class VmTypeEphemeralDisk extends EucalyptusData {

  private String virtualDeviceName;
  private String deviceName;
  private Integer size;
  private String format;

  public VmTypeEphemeralDisk( ) {
  }

  public VmTypeEphemeralDisk( String virtualDeviceName, String deviceName, Integer size, String format ) {
    super( );
    this.virtualDeviceName = virtualDeviceName;
    this.deviceName = deviceName;
    this.size = size;
    this.format = format;
  }

  public String getVirtualDeviceName( ) {
    return virtualDeviceName;
  }

  public void setVirtualDeviceName( String virtualDeviceName ) {
    this.virtualDeviceName = virtualDeviceName;
  }

  public String getDeviceName( ) {
    return deviceName;
  }

  public void setDeviceName( String deviceName ) {
    this.deviceName = deviceName;
  }

  public Integer getSize( ) {
    return size;
  }

  public void setSize( Integer size ) {
    this.size = size;
  }

  public String getFormat( ) {
    return format;
  }

  public void setFormat( String format ) {
    this.format = format;
  }
}