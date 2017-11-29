/*******************************************************************************
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.debug.test.json;

import com.google.gson.annotations.SerializedName;

/**
 * DSP, unlike LSP enums are passed as strings, not numbers. That means a
 * different style.
 */
public enum MyDebugEnum {

	/**
	 * An enum that simply needs case conversion
	 */
	ENUM1,

	/**
	 * An enum with _ case to camel case
	 */
	ANOTHER_ENUM,

	/**
	 * An enum with no fixed rule
	 */
	@SerializedName("aStrangeEnumUTC")
	A_STRANGE_ENUM_UTC;

}
