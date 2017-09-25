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

import edu.ucsb.eucalyptus.msgs.EucalyptusData;

public class Tag extends EucalyptusData {

  private String key;
  private String value;

  public String getKey( ) {
    return key;
  }

  public void setKey( String key ) {
    this.key = key;
  }

  public String getValue( ) {
    return value;
  }

  public void setValue( String value ) {
    this.value = value;
  }
}
