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

public class ParameterDeclaration extends EucalyptusData {

  private String defaultValue;
  private String description;
  private Boolean noEcho;
  private ParameterConstraints parameterConstraints;
  private String parameterKey;
  private String parameterType;

  public String getDefaultValue( ) {
    return defaultValue;
  }

  public void setDefaultValue( String defaultValue ) {
    this.defaultValue = defaultValue;
  }

  public String getDescription( ) {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public Boolean getNoEcho( ) {
    return noEcho;
  }

  public void setNoEcho( Boolean noEcho ) {
    this.noEcho = noEcho;
  }

  public ParameterConstraints getParameterConstraints( ) {
    return parameterConstraints;
  }

  public void setParameterConstraints( ParameterConstraints parameterConstraints ) {
    this.parameterConstraints = parameterConstraints;
  }

  public String getParameterKey( ) {
    return parameterKey;
  }

  public void setParameterKey( String parameterKey ) {
    this.parameterKey = parameterKey;
  }

  public String getParameterType( ) {
    return parameterType;
  }

  public void setParameterType( String parameterType ) {
    this.parameterType = parameterType;
  }
}