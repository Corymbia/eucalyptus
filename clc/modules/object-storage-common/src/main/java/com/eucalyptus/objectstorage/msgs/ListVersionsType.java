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
package com.eucalyptus.objectstorage.msgs;

import com.eucalyptus.objectstorage.policy.AdminOverrideAllowed;
import com.eucalyptus.objectstorage.policy.RequiresACLPermission;
import com.eucalyptus.objectstorage.policy.RequiresPermission;
import com.eucalyptus.objectstorage.policy.ResourceType;
import com.eucalyptus.objectstorage.policy.S3PolicySpec;
import com.eucalyptus.objectstorage.util.ObjectStorageProperties;

@AdminOverrideAllowed
@RequiresPermission( standard = S3PolicySpec.S3_LISTBUCKETVERSIONS )
@ResourceType( S3PolicySpec.S3_RESOURCE_BUCKET )
@RequiresACLPermission( object = {}, bucket = { ObjectStorageProperties.Permission.READ } )
public class ListVersionsType extends ObjectStorageRequestType {

  private String prefix;
  private String keyMarker;
  private String versionIdMarker;
  private String maxKeys;
  private String delimiter;

  public ListVersionsType( ) {
    prefix = "";
  }

  public String getPrefix( ) {
    return prefix;
  }

  public void setPrefix( String prefix ) {
    this.prefix = prefix;
  }

  public String getKeyMarker( ) {
    return keyMarker;
  }

  public void setKeyMarker( String keyMarker ) {
    this.keyMarker = keyMarker;
  }

  public String getVersionIdMarker( ) {
    return versionIdMarker;
  }

  public void setVersionIdMarker( String versionIdMarker ) {
    this.versionIdMarker = versionIdMarker;
  }

  public String getMaxKeys( ) {
    return maxKeys;
  }

  public void setMaxKeys( String maxKeys ) {
    this.maxKeys = maxKeys;
  }

  public String getDelimiter( ) {
    return delimiter;
  }

  public void setDelimiter( String delimiter ) {
    this.delimiter = delimiter;
  }
}