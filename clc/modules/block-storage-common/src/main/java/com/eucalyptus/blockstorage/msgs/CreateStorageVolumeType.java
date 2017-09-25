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
package com.eucalyptus.blockstorage.msgs;

import java.util.Objects;

public class CreateStorageVolumeType extends StorageRequestType {

  private String volumeId;
  private String size;
  private String snapshotId;
  private String parentVolumeId;

  public CreateStorageVolumeType( ) { }

  public CreateStorageVolumeType( final String volumeId, final Integer size, final String snapshotId, final String parentVolumeId ) {
    this.volumeId = volumeId;
    this.size = Objects.toString( size, null );
    this.snapshotId = snapshotId;
    this.parentVolumeId = parentVolumeId;
  }

  public String getVolumeId( ) {
    return volumeId;
  }

  public void setVolumeId( String volumeId ) {
    this.volumeId = volumeId;
  }

  public String getSize( ) {
    return size;
  }

  public void setSize( String size ) {
    this.size = size;
  }

  public String getSnapshotId( ) {
    return snapshotId;
  }

  public void setSnapshotId( String snapshotId ) {
    this.snapshotId = snapshotId;
  }

  public String getParentVolumeId( ) {
    return parentVolumeId;
  }

  public void setParentVolumeId( String parentVolumeId ) {
    this.parentVolumeId = parentVolumeId;
  }
}