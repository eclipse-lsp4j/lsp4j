/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import com.google.gson.annotations.SerializedName;

/**
 * Datatype of values in this column.  Defaults to 'string' if not specified.
 */
@SuppressWarnings("all")
public enum ColumnDescriptorType {
  STRING,
  
  NUMBER,
  
  BOOLEAN,
  
  @SerializedName("unixTimestampUTC")
  UNIX_TIMESTAMP_UTC;
}
