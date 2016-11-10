/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.annotations.chedto

import org.eclipse.xtend.lib.macro.AbstractInterfaceProcessor
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.InterfaceDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableInterfaceDeclaration
import org.eclipse.xtend.lib.macro.declaration.Type
import org.eclipse.xtend.lib.macro.declaration.TypeReference

class CheDTOGenerator extends AbstractInterfaceProcessor {
	
	override doRegisterGlobals(InterfaceDeclaration annotatedClass, extension RegisterGlobalsContext context) {
		context.registerInterface(annotatedClass.getDTOName)
	}
	
	def String getDTOName(Type declaration) {
		return 'org.eclipse.che.plugin.languageserver.shared.lsapi.'+declaration.simpleName+"DTO"
	}
	
	override doTransform(MutableInterfaceDeclaration annotatedClass, extension TransformationContext context) {
		val dto = context.findInterface(annotatedClass.getDTOName)
		dto.addAnnotation(newAnnotationReference("org.eclipse.che.dto.shared.DTO"))
		dto.extendedInterfaces = #[annotatedClass.newTypeReference()]
		
		dto.overrideDTOGetters(annotatedClass, context)
	}
	
	def void overrideDTOGetters(MutableInterfaceDeclaration declaration, InterfaceDeclaration superType, extension TransformationContext context) {
		for (m : superType.declaredMethods) {
			if (m.returnType.isDTO) {
				declaration.addMethod(m.simpleName) [
					docComment = '''
						«m.docComment»
						Overridden to return the DTO type.
					'''
					returnType = m.returnType.convertToDTOTypeReference(context)
				]
			}
			declaration.addMethod(m.simpleName.toSetterName) [
				docComment = '''
					«m.docComment»
				'''
				addParameter(m.simpleName.toSetterName.substring(3).toFirstLower, m.returnType.convertToDTOTypeReference(context))
			]
		}
		for (extendedInterface : superType.extendedInterfaces) {
			declaration.overrideDTOGetters(extendedInterface.type as InterfaceDeclaration, context)
		}
	}
	
	def String toSetterName(String getterName) {
	    if (getterName.startsWith('get')) {
	        return 'set'+getterName.substring(3)
	    } else { // assume 'is'
	        return 'set'+getterName.substring(2)
	    }
	}
	
	def TypeReference convertToDTOTypeReference(TypeReference reference, extension TransformationContext context) {
		if (reference.actualTypeArguments.size == 1) {
			val param = convertToDTOTypeReference(reference.actualTypeArguments.head.upperBound, context)
			return reference.type.newTypeReference(param)
		}
		if (reference.actualTypeArguments.size == 2) {
			val param = convertToDTOTypeReference(reference.actualTypeArguments.get(1).upperBound, context)
			return reference.type.newTypeReference(reference.actualTypeArguments.head, param)
		}
		if (reference.isDTO)
		  return reference.type.getDTOName.newTypeReference
		else 
		  return reference
	}
	
	def boolean isDTO(TypeReference reference) {
		reference.name.startsWith("io.typefox") 
		|| reference.name.startsWith("java.util.List") && reference.actualTypeArguments.head.upperBound.isDTO
		|| reference.name.startsWith("java.util.Map") && reference.actualTypeArguments.get(1).upperBound.isDTO
	}
	
}
