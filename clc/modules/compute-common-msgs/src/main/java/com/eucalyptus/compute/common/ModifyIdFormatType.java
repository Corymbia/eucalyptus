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

import javax.annotation.Nonnull;

public class ModifyIdFormatType extends IdFormatMessage {

  @Nonnull
  private String resource;
  @Nonnull
  private Boolean useLongIds;

  public String getResource( ) {
    return resource;
  }

  public void setResource( String resource ) {
    this.resource = resource;
  }

  public Boolean getUseLongIds( ) {
    return useLongIds;
  }

  public void setUseLongIds( Boolean useLongIds ) {
    this.useLongIds = useLongIds;
  }
}