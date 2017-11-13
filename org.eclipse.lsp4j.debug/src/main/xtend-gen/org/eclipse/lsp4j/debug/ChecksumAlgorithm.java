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
 * Names of checksum algorithms that may be supported by a debug adapter.
 */
@SuppressWarnings("all")
public enum ChecksumAlgorithm {
  @SerializedName("MD5")
  MD5,
  
  @SerializedName("SHA1")
  SHA1,
  
  @SerializedName("SHA256")
  SHA256,
  
  TIMESTAMP;
}
