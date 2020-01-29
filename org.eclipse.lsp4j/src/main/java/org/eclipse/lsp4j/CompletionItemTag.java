/******************************************************************************
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/

package org.eclipse.lsp4j;

/**
 * Completion item tags are extra annotations that tweak the rendering of a completion
 * item.
 * 
 * Since 3.15.0
 */
public enum CompletionItemTag {

    /**
     * Render a completion as obsolete, usually using a strike-out.
     */
    Deprecated(1);

    private final int value;

    CompletionItemTag(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CompletionItemTag forValue(int value) {
        CompletionItemTag[] allValues = CompletionItemTag.values();
        if (value < 1 || value > allValues.length)
            throw new IllegalArgumentException("Illegal enum value: " + value);
        return allValues[value - 1];
    }
}