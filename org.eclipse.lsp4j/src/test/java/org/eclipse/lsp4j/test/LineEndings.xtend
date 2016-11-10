/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.test

import java.util.regex.Pattern
import org.eclipse.xtend2.lib.StringConcatenation

final class LineEndings {
	
	static val LINE_ENDING_PAT = Pattern.compile('\\r?\\n')
	
	private new() {}
	
	static def toSystemLineEndings(CharSequence s) {
		LINE_ENDING_PAT.matcher(s).replaceAll(StringConcatenation.DEFAULT_LINE_DELIMITER)
	}
	
}
