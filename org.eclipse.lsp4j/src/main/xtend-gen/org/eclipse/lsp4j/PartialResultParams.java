/**
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.messages.Either;

/**
 * A parameter literal used to pass a partial result token.
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public interface PartialResultParams {
  /**
   * An optional token that a server can use to report partial results (e.g. streaming) to
   * the client.
   */
  public abstract Either<String, Number> getPartialResultToken();
  
  public abstract void setPartialResultToken(final Either<String, Number> token);
}
