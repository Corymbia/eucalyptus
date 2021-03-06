/*
 * Copyright 2020 AppScale Systems, Inc
 *
 * Use of this source code is governed by a BSD-2-Clause
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/BSD-2-Clause
 */
package com.eucalyptus.cloudformation.common.msgs;

import com.eucalyptus.cloudformation.common.CloudFormationMessageValidation.FieldRange;
import edu.ucsb.eucalyptus.msgs.EucalyptusData;


public class ParameterDeclaration extends EucalyptusData {

  private String defaultValue;

  @FieldRange(min = 1, max = 1024)
  private String description;

  private Boolean noEcho;

  private ParameterConstraints parameterConstraints;

  private String parameterKey;

  private String parameterType;

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(final String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public Boolean getNoEcho() {
    return noEcho;
  }

  public void setNoEcho(final Boolean noEcho) {
    this.noEcho = noEcho;
  }

  public ParameterConstraints getParameterConstraints() {
    return parameterConstraints;
  }

  public void setParameterConstraints(final ParameterConstraints parameterConstraints) {
    this.parameterConstraints = parameterConstraints;
  }

  public String getParameterKey() {
    return parameterKey;
  }

  public void setParameterKey(final String parameterKey) {
    this.parameterKey = parameterKey;
  }

  public String getParameterType() {
    return parameterType;
  }

  public void setParameterType(final String parameterType) {
    this.parameterType = parameterType;
  }

}
