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

public class GetTemplateSummaryType extends CloudFormationMessage {

  private String stackName;
  private String templateBody;
  private String templateURL;

  public String getStackName( ) {
    return stackName;
  }

  public void setStackName( String stackName ) {
    this.stackName = stackName;
  }

  public String getTemplateBody( ) {
    return templateBody;
  }

  public void setTemplateBody( String templateBody ) {
    this.templateBody = templateBody;
  }

  public String getTemplateURL( ) {
    return templateURL;
  }

  public void setTemplateURL( String templateURL ) {
    this.templateURL = templateURL;
  }
}