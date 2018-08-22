/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.test

import org.eclipse.lsp4j.CodeLens
import org.eclipse.lsp4j.generator.JsonRpcData
import org.junit.Assert
import org.junit.Test

class NoAnnotationTest {
    
    @Test def void testNoAnnotation() {
        Assert.assertFalse(CodeLens.annotations.exists[annotationType == JsonRpcData])
    }
    
}