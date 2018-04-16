/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.generator

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.Target
import org.eclipse.xtend.lib.macro.Active

/**
 * Generates getters and setters for all fields as well as {@code equals} and {@code hashCode} implementations.
 * All JSON-RPC protocol classes that are written in Xtend should be annotated with this.
 */
@Target(ElementType.TYPE)
@Active(JsonRpcDataProcessor)
@Retention(SOURCE)
annotation JsonRpcData {
    
}