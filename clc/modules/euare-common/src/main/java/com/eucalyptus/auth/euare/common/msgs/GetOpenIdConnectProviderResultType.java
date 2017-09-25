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
package com.eucalyptus.auth.euare.common.msgs;

import java.util.ArrayList;
import java.util.Date;
import edu.ucsb.eucalyptus.msgs.EucalyptusData;

public class GetOpenIdConnectProviderResultType extends EucalyptusData {

  private String url;
  private Date createDate;
  private ArrayList<String> clientIDList = new ArrayList<String>( );
  private ArrayList<String> thumbprintList = new ArrayList<String>( );

  public String getUrl( ) {
    return url;
  }

  public void setUrl( String url ) {
    this.url = url;
  }

  public Date getCreateDate( ) {
    return createDate;
  }

  public void setCreateDate( Date createDate ) {
    this.createDate = createDate;
  }

  public ArrayList<String> getClientIDList( ) {
    return clientIDList;
  }

  public void setClientIDList( ArrayList<String> clientIDList ) {
    this.clientIDList = clientIDList;
  }

  public ArrayList<String> getThumbprintList( ) {
    return thumbprintList;
  }

  public void setThumbprintList( ArrayList<String> thumbprintList ) {
    this.thumbprintList = thumbprintList;
  }
}