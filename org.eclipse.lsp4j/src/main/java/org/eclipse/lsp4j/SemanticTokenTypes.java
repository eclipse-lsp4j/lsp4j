/**
 * Copyright (c) 2016-2018 TypeFox and others.
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

import com.google.common.annotations.Beta;

/**
 * @since 3.16.0
 */
@Beta
public final class SemanticTokenTypes {
    private SemanticTokenTypes() {
    }

    public static final String Namespace = "namespace";

    public static final String Type = "type";

    public static final String Class = "class";

    public static final String Enum = "enum";

    public static final String Interface = "interface";

    public static final String Struct = "struct";

    public static final String TypeParameter = "typeParameter";

    public static final String Parameter = "parameter";

    public static final String Variable = "variable";

    public static final String Property = "property";

    public static final String EnumMember = "enumMember";

    public static final String Event = "event";

    public static final String Function = "function";

    public static final String Member = "member";

    public static final String Macro = "macro";

    public static final String Keyword = "keyword";

    public static final String Modifier = "modifier";

    public static final String Comment = "comment";

    public static final String String = "string";

    public static final String Number = "number";

    public static final String Regexp = "regexp";

    public static final String Operator = "operator";

}
