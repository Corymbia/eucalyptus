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

import java.util.ArrayList;
import java.util.Collection;
import com.google.common.collect.Lists;
import edu.ucsb.eucalyptus.msgs.EucalyptusData;

public class NetworkAclEntrySetType extends EucalyptusData {

  private ArrayList<NetworkAclEntryType> item = new ArrayList<NetworkAclEntryType>( );

  public NetworkAclEntrySetType( ) {
  }

  public NetworkAclEntrySetType( Collection<NetworkAclEntryType> values ) {
    this.item = Lists.newArrayList( values );
  }

  public ArrayList<NetworkAclEntryType> getItem( ) {
    return item;
  }

  public void setItem( ArrayList<NetworkAclEntryType> item ) {
    this.item = item;
  }
}