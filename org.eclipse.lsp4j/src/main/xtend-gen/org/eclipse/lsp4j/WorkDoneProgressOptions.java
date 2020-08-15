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

/**
 * Options to signal work done progress support in server capabilities.
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public interface WorkDoneProgressOptions {
  public abstract Boolean getWorkDoneProgress();
  
  public abstract void setWorkDoneProgress(final Boolean workDoneProgress);
}
