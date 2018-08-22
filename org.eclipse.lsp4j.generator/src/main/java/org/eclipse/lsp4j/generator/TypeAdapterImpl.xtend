/******************************************************************************
 * Copyright (c) 2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.generator

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.Target
import org.eclipse.xtend.lib.macro.Active

/**
 * Used to simplify the implementation of Gson TypeAdapters.
 */
@Target(ElementType.TYPE)
@Active(TypeAdapterImplProcessor)
@Retention(SOURCE)
annotation TypeAdapterImpl {
	
	Class<?> value
	
}