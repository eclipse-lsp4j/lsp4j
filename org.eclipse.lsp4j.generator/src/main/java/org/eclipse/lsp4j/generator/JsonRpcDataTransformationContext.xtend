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
package org.eclipse.lsp4j.generator

import java.util.Collection
import java.util.List
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.InterfaceDeclaration
import org.eclipse.xtend.lib.macro.declaration.TypeReference

class JsonRpcDataTransformationContext implements TransformationContext {

	@Delegate
	val TransformationContext delegate
	@Accessors(PUBLIC_GETTER)
	val TypeReference eitherType

	new(TransformationContext delegate) {
		this.delegate = delegate
		this.eitherType = Either.newTypeReference
	}

	def boolean isEither(TypeReference typeReference) {
		return typeReference !== null && eitherType.isAssignableFrom(typeReference)
	}

	def TypeReference getLeftType(TypeReference typeReference) {
		val type = typeReference.type
		if (type === eitherType.type) {
			return typeReference.actualTypeArguments.head
		}
		if (type instanceof InterfaceDeclaration) {
			return type.extendedInterfaces.map[leftType].filterNull.head
		}
		return null
	}

	def TypeReference getRightType(TypeReference typeReference) {
		val type = typeReference.type
		if (type === eitherType.type) {
			return typeReference.actualTypeArguments.last
		}
		if (type instanceof InterfaceDeclaration) {
			return type.extendedInterfaces.map[rightType].filterNull.head
		}
		return null
	}

	def Collection<EitherTypeArgument> getChildTypes(TypeReference typeReference) {
		val types = newArrayList
		if (typeReference.either) {
			typeReference.leftType.collectChildTypes(null, false, types)
			typeReference.rightType.collectChildTypes(null, true, types)
		}
		return types
	}

	protected def void collectChildTypes(TypeReference type, EitherTypeArgument parent, boolean right, Collection<EitherTypeArgument> types) {
		val argument = new EitherTypeArgument(type, parent, right)
		if (type.either) {
			type.leftType.collectChildTypes(argument, false, types)
			type.rightType.collectChildTypes(argument, true, types)
		} else if (type !== null) {
			types.add(argument)
		}
	}

	def boolean isJsonNull(TypeReference type) {
		return type.jsonType === JsonType.NULL
	}

	def boolean isJsonString(TypeReference type) {
		return type.jsonType === JsonType.STRING
	}

	def boolean isJsonNumber(TypeReference type) {
		return type.jsonType === JsonType.NUMBER
	}

	def boolean isJsonBoolean(TypeReference type) {
		return type.jsonType === JsonType.BOOLEAN
	}

	def boolean isJsonArray(TypeReference type) {
		return type.jsonType === JsonType.ARRAY
	}

	def boolean isJsonObject(TypeReference type) {
		return type.jsonType === JsonType.OBJECT
	}

	def JsonType getJsonType(TypeReference type) {
		if (type === null) {
			return JsonType.NULL
		}
		if (type.array || List.newTypeReference.isAssignableFrom(type)) {
			return JsonType.ARRAY
		}
		if (Enum.newTypeReference.isAssignableFrom(type) || Number.newTypeReference.isAssignableFrom(type)) {
			return JsonType.NUMBER
		}
		if (Boolean.newTypeReference.isAssignableFrom(type)) {
			return JsonType.BOOLEAN
		}
		if (String.newTypeReference.isAssignableFrom(type) || Character.newTypeReference.isAssignableFrom(type)) {
			return JsonType.STRING
		}
		if (!type.primitive) {
			return JsonType.OBJECT
		}
		throw new IllegalStateException('Unexpected type reference: ' + type)
	}

}

@Accessors
@FinalFieldsConstructor
class EitherTypeArgument {
	val TypeReference type
	val EitherTypeArgument parent
	val boolean right
}

enum JsonType {
	NULL,
	STRING,
	NUMBER,
	BOOLEAN,
	ARRAY,
	OBJECT
}
