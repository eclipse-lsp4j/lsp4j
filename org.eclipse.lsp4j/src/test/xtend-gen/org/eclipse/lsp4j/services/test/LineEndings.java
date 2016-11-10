/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.services.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public final class LineEndings {
  private final static Pattern LINE_ENDING_PAT = Pattern.compile("\\r?\\n");
  
  private LineEndings() {
  }
  
  public static String toSystemLineEndings(final CharSequence s) {
    Matcher _matcher = LineEndings.LINE_ENDING_PAT.matcher(s);
    return _matcher.replaceAll(StringConcatenation.DEFAULT_LINE_DELIMITER);
  }
}
