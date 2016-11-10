/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.annotations.util

import org.eclipse.xtend.lib.annotations.EqualsHashCode
import org.eclipse.xtend.lib.annotations.ToString

@ToString
@EqualsHashCode
class Wrapper<T> {
	
	T value
	
	def static <T> Wrapper<T> wrap(T value) {
		new Wrapper<T>(value)
	}
	
	def static <T> Wrapper<T> forType(Class<T> type) {
		new Wrapper<T>
	}
	
	new() {
		this(null)
	}
	
	new(T value) {
		this.value = value
	}
	
	def T get() {
		value
	}
	
	def void set(T value) {
		this.value = value
	}
	
	def boolean isEmpty() {
		return value === null
	}
	
}
