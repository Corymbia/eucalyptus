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
import java.util.List;
import com.eucalyptus.binding.HttpEmbedded;
import com.eucalyptus.binding.HttpParameterMapping;
import com.google.common.collect.Lists;

public class DescribeDhcpOptionsType extends VpcMessage {

  @HttpEmbedded
  private DhcpOptionsIdSetType dhcpOptionsSet;
  @HttpParameterMapping( parameter = "Filter" )
  @HttpEmbedded( multiple = true )
  private ArrayList<Filter> filterSet = new ArrayList<Filter>( );

  public Collection<String> dhcpOptionsIds( ) {
    List<String> dhcpOptionsIds = Lists.newArrayList( );
    if ( dhcpOptionsSet != null && dhcpOptionsSet.getItem( ) != null ) {
      for ( DhcpOptionsIdSetItemType item : dhcpOptionsSet.getItem( ) ) {
        if ( item != null ) {
          dhcpOptionsIds.add( item.getDhcpOptionsId( ) );
        }

      }

    }

    return dhcpOptionsIds;
  }

  public DhcpOptionsIdSetType getDhcpOptionsSet( ) {
    return dhcpOptionsSet;
  }

  public void setDhcpOptionsSet( DhcpOptionsIdSetType dhcpOptionsSet ) {
    this.dhcpOptionsSet = dhcpOptionsSet;
  }

  public ArrayList<Filter> getFilterSet( ) {
    return filterSet;
  }

  public void setFilterSet( ArrayList<Filter> filterSet ) {
    this.filterSet = filterSet;
  }
}