/*
 * Copyright 2021 AppScale Systems, Inc
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.eucalyptus.rds.service.persist.views;

import org.immutables.value.Value.Immutable;
import com.eucalyptus.rds.common.policy.RdsResourceName;

/**
 *
 */
@Immutable
public interface DBSubnetGroupView {

  String getNaturalId();

  String getDisplayName();

  String getOwnerAccountNumber();

  String getDescription();

  String getVpcId();

  default String getArn() {
    return new RdsResourceName(
        "",
        getOwnerAccountNumber(),
        "subgrp",
        getDisplayName()
    ).toString();
  }
}
