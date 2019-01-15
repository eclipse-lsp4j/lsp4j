/**
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.test.services;

import java.util.regex.Pattern;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public final class LineEndings {
  private static final Pattern LINE_ENDING_PAT = Pattern.compile("\\r?\\n");
  
  private LineEndings() {
  }
  
  public static String toSystemLineEndings(final CharSequence s) {
    return LineEndings.LINE_ENDING_PAT.matcher(s).replaceAll(StringConcatenation.DEFAULT_LINE_DELIMITER);
  }
}
