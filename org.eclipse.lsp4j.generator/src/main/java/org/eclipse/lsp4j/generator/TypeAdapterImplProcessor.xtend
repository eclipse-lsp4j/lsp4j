/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.generator

import java.io.IOException
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.TypeReference
import org.eclipse.xtend.lib.macro.declaration.Visibility

class TypeAdapterImplProcessor extends AbstractClassProcessor {
	
	override doRegisterGlobals(ClassDeclaration annotatedClass, extension RegisterGlobalsContext context) {
		registerClass(annotatedClass.qualifiedName + '.Factory')
	}

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		val typeAdapterImplAnnotation = annotatedClass.findAnnotation(TypeAdapterImpl.findTypeGlobally)
		val targetType = typeAdapterImplAnnotation.getClassValue('value')
		generateImpl(annotatedClass, targetType, context)
		generateFactory(findClass(annotatedClass.qualifiedName + '.Factory'), annotatedClass, targetType, context)
	}

	protected def generateImpl(MutableClassDeclaration impl, TypeReference targetType, extension TransformationContext context) {
		val targetFields = getTargetFields(targetType, context)
		for (field : targetFields.filter[!type.isPrimitive && !type.actualTypeArguments.empty]) {
			impl.addField(field.simpleName.toUpperCase + '_TYPE_TOKEN') [
				final = true
				static = true
				type = newTypeReference('com.google.gson.reflect.TypeToken', field.type)
				initializer = '''new TypeToken<«field.type»>() {}'''
			]
		}
		
		impl.extendedClass = newTypeReference('com.google.gson.TypeAdapter', targetType)
		impl.addField('gson') [
			type = newTypeReference('com.google.gson.Gson')
			final = true
		]
		impl.addConstructor[
			addParameter('gson', newTypeReference('com.google.gson.Gson'))
			body = '''
				this.gson = gson;
			'''
		]
		
		impl.addMethod('read') [ method |
			method.addParameter('in', newTypeReference('com.google.gson.stream.JsonReader'))
			method.exceptions = newTypeReference(IOException)
			method.returnType = targetType
			method.body = '''
				«newTypeReference('com.google.gson.stream.JsonToken')» nextToken = in.peek();
				if (nextToken == JsonToken.NULL) {
					return null;
				}
				
				«targetType» result = new «targetType»();
				in.beginObject();
				while (in.hasNext()) {
					String name = in.nextName();
					switch (name) {
					«FOR field : targetFields»
						case "«field.simpleName»":
							result.set«field.simpleName.toFirstUpper»(read«field.simpleName.toFirstUpper»(in));
							break;
					«ENDFOR»
					default:
						in.skipValue();
					}
				}
				in.endObject();
				return result;
			'''
		]
		
		for (field : targetFields) {
			val existingMethod = impl.findDeclaredMethod('read' + field.simpleName.toFirstUpper,
					newTypeReference('com.google.gson.stream.JsonReader'))
			if (existingMethod === null) {
				impl.addMethod('read' + field.simpleName.toFirstUpper) [
					visibility = Visibility.PROTECTED
					addParameter('in', newTypeReference('com.google.gson.stream.JsonReader'))
					exceptions = newTypeReference(IOException)
					returnType = field.type
					if (field.type.isPrimitive) {
						switch field.type.simpleName {
							case 'boolean':
								body = '''return in.nextBoolean();'''
							case 'double':
								body = '''return in.nextDouble();'''
							case 'float':
								body = '''return (float) in.nextDouble();'''
							case 'long':
								body = '''return in.nextLong();'''
							case 'int':
								body = '''return in.nextInt();'''
							case 'short', case 'byte', case 'char':
								body = '''return («field.type») in.nextInt();'''
						}
					} else if (!field.type.actualTypeArguments.empty) {
						body = '''return gson.fromJson(in, «field.simpleName.toUpperCase»_TYPE_TOKEN.getType());'''
					} else {
						body = '''return gson.fromJson(in, «field.type».class);'''
					}
				]
			}
		}
		
		impl.addMethod('write') [ method |
			method.addParameter('out', newTypeReference('com.google.gson.stream.JsonWriter'))
			method.addParameter('value', targetType)
			method.exceptions = newTypeReference(IOException)
			method.body = '''
				if (value == null) {
					out.nullValue();
					return;
				}
				
				out.beginObject();
				«FOR field : targetFields»
					out.name("«field.simpleName»");
					write«field.simpleName.toFirstUpper»(out, value.get«field.simpleName.toFirstUpper»());
				«ENDFOR»
				out.endObject();
			'''
		]
		
		for (field : targetFields) {
			val existingMethod = impl.findDeclaredMethod('write' + field.simpleName.toFirstUpper,
					newTypeReference('com.google.gson.stream.JsonWriter'), field.type)
			if (existingMethod === null) {
				impl.addMethod('write' + field.simpleName.toFirstUpper) [
					visibility = Visibility.PROTECTED
					addParameter('out', newTypeReference('com.google.gson.stream.JsonWriter'))
					addParameter('value', field.type)
					exceptions = newTypeReference(IOException)
					if (field.type.isPrimitive) {
						body = '''
							out.value(value);
						'''
					} else if (!field.type.actualTypeArguments.empty) {
						body = '''
							gson.toJson(value, «field.simpleName.toUpperCase»_TYPE_TOKEN.getType(), out);
						'''
					} else {
						body = '''
							gson.toJson(value, «field.type».class, out);
						'''
					}
				]
			}
		}
		
		return impl
	}
	
	protected def generateFactory(MutableClassDeclaration factory, MutableClassDeclaration impl, TypeReference targetType,
			extension TransformationContext context) {
		factory.implementedInterfaces = #[newTypeReference('com.google.gson.TypeAdapterFactory')]
		factory.addMethod('create') [
			val t = addTypeParameter('T')
			addParameter('gson', newTypeReference('com.google.gson.Gson'))
			addParameter('typeToken', newTypeReference('com.google.gson.reflect.TypeToken', newTypeReference(t)))
			returnType = newTypeReference('com.google.gson.TypeAdapter', newTypeReference(t))
			body = '''
				if (!«targetType».class.isAssignableFrom(typeToken.getRawType())) {
					return null;
				}
				return (TypeAdapter<T>) new «impl»(gson);
			'''
		]
	}
	
	private def getTargetFields(TypeReference targetType, extension TransformationContext context) {
		val objectType = Object.newTypeReference.type
		val targetFields = newArrayList
		var typeRef = targetType
		while (typeRef.type != objectType) {
			val clazz = typeRef.type as ClassDeclaration
			targetFields += clazz.declaredFields.filter[!static]
			typeRef = clazz.extendedClass
		}
		return targetFields
	}
	
}