/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.services;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CompletableFuture;

/**
 * Annotation to mark a notification method on an interface or class.
 * A request method must have the return type {@link CompletableFuture} with an object parameter type or Void and have zero or one argument.
 * According to jsonrpc an argument must be an 'object' (a java bean, not e,g. String).
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonRequest {
	String value() default "";
}
