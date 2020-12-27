/**
 * Copyright (c) 2020 Eric Dallo.
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
 * Since 3.16.0
 */
public final class SemanticTokenModifiers {
    private SemanticTokenModifiers() {
    }

    public static final String Declaration = "declaration";

    public static final String Definition = "definition";

    public static final String Readonly = "readonly";

    public static final String Static = "static";

    public static final String Deprecated = "deprecated";

    public static final String Abstract = "abstract";

    public static final String Async = "async";

    public static final String Modification = "modification";

    public static final String Documentation = "documentation";

    public static final String DefaultLibrary = "defaultLibrary";

}
