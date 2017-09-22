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
package com.eucalyptus.ws.server;

import java.util.EnumSet;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import com.eucalyptus.auth.principal.TemporaryAccessKey;
import com.eucalyptus.component.annotation.ComponentPart;
import com.eucalyptus.empyrean.Empyrean;
import com.eucalyptus.ws.protocol.BaseQueryBinding;
import com.eucalyptus.ws.protocol.OperationParameter;

/**
 *
 */
@ComponentPart(Empyrean.class)
public class EmpyreanPipeline extends QueryPipeline {

  public EmpyreanPipeline() {
    super(
        "empyrean-query",
        "/services/Empyrean",
        EnumSet.allOf( TemporaryAccessKey.TemporaryKeyType.class ) );
  }

  @Override
  public ChannelPipeline addHandlers( final ChannelPipeline pipeline ) {
    super.addHandlers( pipeline );
    pipeline.addLast( "empyrean-query-binding", new EmpyreanQueryBinding( ) );
    return pipeline;
  }

  @ComponentPart(Empyrean.class)
  private static class EmpyreanQueryBinding extends BaseQueryBinding<OperationParameter> implements ChannelHandler {
    private EmpyreanQueryBinding( ) {
      super( "http://www.eucalyptus.com/ns/bootstrap/%s/", "2017-05-04", OperationParameter.Action, OperationParameter.Operation );
    }
  }
}