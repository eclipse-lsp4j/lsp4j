/**
 * Copyright (c) 2025 Avaloq Group AG.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.jsonrpc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An API using this annotation is part of an upcoming Protocol Specification and in a draft state.
 * Therefore it is subject to incompatible changes (including even removal) in a future release.
 */
@Retention(RetentionPolicy.CLASS)
@Target({
ElementType.CONSTRUCTOR,
ElementType.FIELD,
ElementType.METHOD,
ElementType.TYPE
})
@Documented
public @interface ProtocolDraft {}
