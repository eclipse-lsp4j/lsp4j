/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

/**
 * Known error codes for an `InitializeError`
 */
@SuppressWarnings("all")
public interface InitializeErrorCode {
  /**
   * If the protocol version provided by the client can't be handled by the server.
   * 
   * @deprecated This initialize error got replaced by client capabilities.
   * There is no version handshake in version 3.0x
   */
  @Deprecated
  public final static int unknownProtocolVersion = 1;
}
