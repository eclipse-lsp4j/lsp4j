/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI
import java.util.List
import java.util.Map
import org.eclipse.lsp4j.jsonrpc.validation.NonNull
import java.util.LinkedHashMap

/**
 * A workspace edit represents changes to many resources managed in the workspace.
 */
@LanguageServerAPI
class WorkspaceEdit {
	
	/**
	 * Holds changes to existing resources.
	 */
	@NonNull
	Map<String, List<TextEdit>> changes = new LinkedHashMap
	
}