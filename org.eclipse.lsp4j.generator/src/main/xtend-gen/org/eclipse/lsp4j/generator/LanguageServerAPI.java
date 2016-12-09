/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.eclipse.lsp4j.generator.JsonRpcDataProcessor;
import org.eclipse.xtend.lib.macro.Active;

/**
 * @deprecated use JsonRpcData instead
 */
@Deprecated
@Target(ElementType.TYPE)
@Active(JsonRpcDataProcessor.class)
@Retention(RetentionPolicy.SOURCE)
public @interface LanguageServerAPI {
}
