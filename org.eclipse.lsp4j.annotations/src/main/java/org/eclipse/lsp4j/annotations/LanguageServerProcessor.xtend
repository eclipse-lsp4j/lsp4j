/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.annotations

import org.eclipse.xtend.lib.annotations.AccessorsProcessor
import org.eclipse.xtend.lib.annotations.EqualsHashCodeProcessor
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.Visibility
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder

class LanguageServerProcessor extends AbstractClassProcessor {

	static val MAX_CONSTRUCTOR_ARGS = 3

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		generateImpl(annotatedClass, context)
	}

	protected def generateImpl(MutableClassDeclaration impl, extension TransformationContext context) {
		impl.removeAnnotation(impl.annotations.findFirst [
			annotationTypeDeclaration == LanguageServerAPI.findTypeGlobally
		])
		impl.generateImplMembers(context)
		val fields = impl.declaredFields.filter [ !static ]

		if (!fields.empty) {
			impl.addConstructor [
				body = ''''''
			]
			if (fields.size <= MAX_CONSTRUCTOR_ARGS && impl.extendedClass !== object) {
				impl.addConstructor [ constructor |
					fields.forEach [ field |
						constructor.addParameter(field.simpleName, field.type)
					]
					constructor.body = '''
						«FOR field : fields»
							this.«field.simpleName» = «field.simpleName»;
						«ENDFOR»
					'''
				]
			}
		}

		generateToString(impl, context)

		val equalsHashCodeUtil = new EqualsHashCodeProcessor.Util(context)
		equalsHashCodeUtil.addEquals(impl, fields, true)
		equalsHashCodeUtil.addHashCode(impl, fields, true)

		return impl
	}

	private def void generateImplMembers(MutableClassDeclaration impl, extension TransformationContext context) {
		impl.declaredFields.filter [
			!static
		].forEach [ field |
			val accessorsUtil = new AccessorsProcessor.Util(context)
			val deprecated = field.findAnnotation(Deprecated.findTypeGlobally)
			accessorsUtil.addGetter(field, Visibility.PUBLIC)
			impl.findDeclaredMethod(accessorsUtil.getGetterName(field)) => [
				docComment = field.docComment
				if (deprecated !== null)
					addAnnotation(newAnnotationReference(Deprecated))
			]

			if (!field.type.inferred) {
				accessorsUtil.addSetter(field, Visibility.PUBLIC)
				impl.findDeclaredMethod(accessorsUtil.getSetterName(field), field.type) => [
					docComment = field.docComment
					if (deprecated !== null)
						addAnnotation(newAnnotationReference(Deprecated))
				]
			}
		]
	}

	private def generateToString(MutableClassDeclaration impl, extension TransformationContext context) {
		val toStringFields = newArrayList
		var ClassDeclaration c = impl
		do {
			toStringFields += c.declaredFields
			c = c.extendedClass?.type as ClassDeclaration
		} while (c !== null && c != object)
		impl.addMethod("toString") [
			returnType = string
			addAnnotation(newAnnotationReference(Override))
			addAnnotation(newAnnotationReference(Pure))
			val accessorsUtil = new AccessorsProcessor.Util(context)
			body = '''
				«ToStringBuilder» b = new «ToStringBuilder»(this);
				«FOR field : toStringFields»
					b.add("«field.simpleName»", «IF field.declaringType == impl»this.«field.simpleName»«ELSE»«
						accessorsUtil.getGetterName(field)»()«ENDIF»);
				«ENDFOR»
				return b.toString();
			'''
		]
	}

	private def Iterable<FieldDeclaration> getAllFields(ClassDeclaration it) {
		declaredFields + ((extendedClass?.type as ClassDeclaration)?.allFields ?: #[])
	}

}
