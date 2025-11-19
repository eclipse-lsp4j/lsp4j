/**
 * Copyright (c) 2025 Kichwa Coders Canada, Inc.
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
 * An API using this annotation is part of a Protocol Specification and is deprecated in the protocol.
 * Therefore it is subject to removal in a future release.
 * <p>
 * This is synonymous with Java's standard {@link Deprecated} annotation, however the LSP4J project
 * does not use standard Deprecated annotation when implementing LSP/DAP specification as it leads
 * to unresolvable deprecation warnings. The standard Deprecated annotation should be used in LSP4J
 * for LSP4J's own API that is not directly implementing the protocol specification.
 */
@Retention(RetentionPolicy.CLASS)
@Target({
ElementType.CONSTRUCTOR,
ElementType.FIELD,
ElementType.METHOD,
ElementType.TYPE
})
@Documented
public @interface ProtocolDeprecated {}
