/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.annotations

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Set
import javax.annotation.Nullable
import org.eclipse.lsp4j.annotations.util.Wrapper
import org.eclipse.xtend.lib.annotations.AccessorsProcessor
import org.eclipse.xtend.lib.annotations.EqualsHashCodeProcessor
import org.eclipse.xtend.lib.macro.AbstractInterfaceProcessor
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration
import org.eclipse.xtend.lib.macro.declaration.InterfaceDeclaration
import org.eclipse.xtend.lib.macro.declaration.MethodDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableInterfaceDeclaration
import org.eclipse.xtend.lib.macro.declaration.Type
import org.eclipse.xtend.lib.macro.declaration.TypeReference
import org.eclipse.xtend.lib.macro.declaration.Visibility
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder

class LanguageServerProcessor extends AbstractInterfaceProcessor {
	
	static val NAMESPACE = 'org.eclipse.lsp4j'
	static val BUILDER_INTERFACE = NAMESPACE + '.builders.IBuilder'
	static val MAX_CONSTRUCTOR_ARGS = 3
	
	override doRegisterGlobals(InterfaceDeclaration annotatedInterface, RegisterGlobalsContext context) {
		context.registerClass(annotatedInterface.implName)
		if (annotatedInterface.doGenerateBuilder)
			context.registerClass(annotatedInterface.builderName)
	}
	
	override doTransform(MutableInterfaceDeclaration annotatedInterface, extension TransformationContext context) {
		val impl = generateImpl(annotatedInterface, context)
		if (impl.doGenerateBuilder(null))
			generateBuilder(impl, annotatedInterface, context)
	}
	
	private def boolean doGenerateBuilder(InterfaceDeclaration decl) {
		decl.declaredMethods.exists[
			!static && parameters.empty && returnType !== null && (returnType.inferred || !returnType.isVoid)
		] || decl.extendedInterfaces.exists[(type as InterfaceDeclaration).doGenerateBuilder]
	}
	
	private def boolean doGenerateBuilder(Type type, extension TransformationContext context) {
		if (type instanceof ClassDeclaration) {
			if (context === null)
				!type.declaredFields.empty
			else {
				val interfaceType = type.getInterfaceType(context)
				if (interfaceType instanceof InterfaceDeclaration)
					interfaceType.doGenerateBuilder
				else
					false
			}
		} else if (type instanceof InterfaceDeclaration)
			doGenerateBuilder(type)
		else
			false
	}
	
	protected def generateImpl(MutableInterfaceDeclaration annotatedInterface, extension TransformationContext context) {
		val impl = annotatedInterface.implName.findClass
		impl.primarySourceElement = annotatedInterface
		
    	annotatedInterface.removeAnnotation(annotatedInterface.annotations.findFirst[annotationTypeDeclaration == LanguageServerAPI.findTypeGlobally])
		impl.implementedInterfaces = #[annotatedInterface.newTypeReference]
		impl.docComment = annotatedInterface.docComment
		
		val superApiInterfaces = annotatedInterface.getSuperApiInterfaces(context).toList
		val visitedInterfaces = newHashSet
		if (!superApiInterfaces.empty) {
			impl.extendedClass = superApiInterfaces.head.implName.newTypeReference
			val interfaceQueue = new LinkedList
			interfaceQueue += superApiInterfaces.head
			do {
				val i = interfaceQueue.removeFirst
				if (visitedInterfaces += i)
					interfaceQueue.addAll(i.getSuperApiInterfaces(context))
			} while (!interfaceQueue.empty)
		}
		
		impl.generateImplMembers(annotatedInterface, visitedInterfaces, context)
		
		if (!impl.declaredFields.empty) {
			impl.addConstructor[
				body = ''''''
			]
			impl.addConstructor[ constructor |
				constructor.addParameter('other', impl.newTypeReference)
				constructor.body = '''
					«IF !superApiInterfaces.empty»
						super(other);
					«ENDIF»
					«FOR field : impl.declaredFields»
						this.«field.simpleName» = other.«field.simpleName»;
					«ENDFOR»
				'''
			]
			if (impl.declaredFields.size <= MAX_CONSTRUCTOR_ARGS && superApiInterfaces.empty) {
				impl.addConstructor[ constructor |
					impl.declaredFields.forEach [ field |
						constructor.addParameter(field.simpleName, field.type)
					]
					constructor.body = '''
						«FOR field : impl.declaredFields»
							this.«field.simpleName» = «field.simpleName»;
						«ENDFOR»
					'''
				]
			}
		}
		
		generateToString(impl, annotatedInterface, context)
		
		val equalsHashCodeUtil = new EqualsHashCodeProcessor.Util(context)
		equalsHashCodeUtil.addEquals(impl, impl.declaredFields, !superApiInterfaces.empty)
		equalsHashCodeUtil.addHashCode(impl, impl.declaredFields, !superApiInterfaces.empty)
		
		return impl
	}
	
	private def getSuperApiInterfaces(InterfaceDeclaration it, extension TransformationContext context) {
		extendedInterfaces.map[type].filter(InterfaceDeclaration).filter[qualifiedName.startsWith(NAMESPACE)]
	}
	
	private def void generateImplMembers(MutableClassDeclaration impl, InterfaceDeclaration source,
			Set<InterfaceDeclaration> visitedInterfaces, extension TransformationContext context) {
		source.declaredMethods.filter[
			!static && thePrimaryGeneratedJavaElement && parameters.empty && returnType !== null
				&& (returnType.inferred || !returnType.isVoid)
		].forEach[ method |
			impl.addField(method.fieldName) [ field |
				field.primarySourceElement = method
				field.docComment = method.docComment
				field.type = method.getFieldType(context)
				val nullable = method.findAnnotation(Nullable.findTypeGlobally)
				if (nullable !== null)
					field.addAnnotation(newAnnotationReference(Nullable))
			    
				val accessorsUtil = new AccessorsProcessor.Util(context) {
					override getGetterName(FieldDeclaration it) {
						method.simpleName
					}
				}
				
				accessorsUtil.addGetter(field, Visibility.PUBLIC)
				impl.findDeclaredMethod(method.simpleName) => [
					addAnnotation(newAnnotationReference(Override))
					if (method.findAnnotation(Deprecated.findTypeGlobally) !== null)
						addAnnotation(newAnnotationReference(Deprecated))
					if (nullable !== null)
						addAnnotation(newAnnotationReference(Nullable))
				]
				
				if (!field.type.inferred) {
					accessorsUtil.addSetter(field, Visibility.PUBLIC)
					if (nullable !== null) {
						impl.findDeclaredMethod(accessorsUtil.getSetterName(field), field.type) => [
						    if (method.findAnnotation(Deprecated.findTypeGlobally) !== null)
                                addAnnotation(newAnnotationReference(Deprecated))
							parameters.head?.addAnnotation(newAnnotationReference(Nullable))
						]
					}
				}
			]
		]
		visitedInterfaces += source
		source.getSuperApiInterfaces(context).filter[!visitedInterfaces.contains(it)].forEach[
			impl.generateImplMembers(it, visitedInterfaces, context)
		]
	}
	
	private def getFieldType(MethodDeclaration method, extension TransformationContext context) {
		val returnType = method.returnType
		if (returnType.isLanguageServerAPI) {
			return returnType.type.implName.findTypeGlobally.newTypeReference
		}
		val typeArguments = returnType.actualTypeArguments
		val globalListType = List.findTypeGlobally
		if (returnType.type == globalListType && typeArguments.size == 1) {
			var contentType = typeArguments.get(0)
			if (contentType.isWildCard)
				contentType = contentType.upperBound
			if (contentType.isLanguageServerAPI)
				return List.newTypeReference(contentType.type.implName.findTypeGlobally.newTypeReference)
		}
		val globalMapType = Map.findTypeGlobally
		if (returnType.type == globalMapType && typeArguments.size == 2) {
			var contentType = typeArguments.get(1)
			if (contentType.isWildCard)
				contentType = contentType.upperBound
			if (contentType.type == globalListType && contentType.actualTypeArguments.size == 1) {
				contentType = contentType.actualTypeArguments.get(0)
				if (contentType.isWildCard)
					contentType = contentType.upperBound
				if (contentType.isLanguageServerAPI) {
					return Map.newTypeReference(
						typeArguments.get(0),
						List.newTypeReference(contentType.type.implName.findTypeGlobally.newTypeReference)
					)
				}
			} else if (contentType.isLanguageServerAPI)
				return Map.newTypeReference(typeArguments.get(0), contentType.type.implName.findTypeGlobally.newTypeReference)
		}
		return returnType
	}
	
	private def isLanguageServerAPI(TypeReference typeRef) {
	    typeRef != null && typeRef.type != null && typeRef.type instanceof InterfaceDeclaration
	    	&& typeRef.type.qualifiedName.startsWith(NAMESPACE)
	}
	
	private def getFieldName(MethodDeclaration method) {
		val name = method.simpleName
	    if (method.annotations.exists[annotationTypeDeclaration.simpleName == NoPrefix.simpleName])
	        return name
		else if (name.startsWith('get') && name.length > 3)
			return name.substring(3).toFirstLower
		else if (name.startsWith('is') && name.length > 2)
			return name.substring(2).toFirstLower
		else
			return name
	}
	
	private def generateToString(MutableClassDeclaration impl, InterfaceDeclaration source, extension TransformationContext context) {
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
	
	protected def generateBuilder(MutableClassDeclaration impl, MutableInterfaceDeclaration annotatedInterface,
			extension TransformationContext context) {
		val builder = annotatedInterface.builderName.findClass
		builder.primarySourceElement = annotatedInterface
		builder.docComment = '''Builder for instances of {@link «annotatedInterface.simpleName»}.'''
        builder.implementedInterfaces = #[BUILDER_INTERFACE.newTypeReference(annotatedInterface.newTypeReference)]
		builder.addConstructor[
			body = ''''''
		]
		builder.addConstructor[ constructor |
			constructor.addParameter('initializer', Procedures.Procedure1.newTypeReference(builder.newTypeReference))
			constructor.body = '''
				initializer.apply(this);
			'''
		]
		
		generateBuilderMembers(builder, impl, context)
		
		builder.addMethod('build') [  method |
			method.docComment = '''Build the configured instance.'''
			method.addAnnotation(newAnnotationReference(Override))
			method.returnType = annotatedInterface.newTypeReference
			method.body = '''
				«impl.newTypeReference» result = new «impl.newTypeReference»();
				internalBuild(result);
				return result;
			'''
		]
		
		val accessorsUtil = new AccessorsProcessor.Util(context)
		builder.addMethod('internalBuild') [  method |
			method.visibility = Visibility.PROTECTED
			method.addParameter('result', impl.newTypeReference)
			method.body = '''
				«FOR implField : impl.allFields»
					result.«accessorsUtil.getSetterName(implField)»(this.«implField.simpleName»);
				«ENDFOR»
			'''
		]
		
		return builder
	}
	
	private def Iterable<FieldDeclaration> getAllFields(ClassDeclaration it) {
	    declaredFields + ((extendedClass?.type as ClassDeclaration)?.allFields ?: #[])
	}
	
	private def generateBuilderMembers(MutableClassDeclaration builder, MutableClassDeclaration impl,
			extension TransformationContext context) {
		val globalListType = List.findTypeGlobally
		val globalMapType = Map.findTypeGlobally
		impl.allFields.forEach[ implField |
			val fieldType = implField.type
			builder.addField(implField.simpleName) [ field |
				field.primarySourceElement = implField.primarySourceElement
				field.docComment = implField.docComment
				field.type = fieldType
				if (fieldType.type == globalListType) {
				    field.initializer = '''«Lists.newTypeReference».newArrayList()'''
				} else if (fieldType.type == globalMapType) {
				    field.initializer = '''«Maps.newTypeReference».newLinkedHashMap()'''
				}
			]
			
			val methodName = new Wrapper
			val paramType = new Wrapper
			val contentType = new Wrapper
			val keyType = new Wrapper
			if (fieldType.type == globalListType) {
				// Fields with list type
				methodName.set = implField.singularName
				builder.addMethod(methodName.get) [ method |
					method.primarySourceElement = implField.primarySourceElement
					method.returnType = builder.newTypeReference
					contentType.set = fieldType.actualTypeArguments.get(0)
					paramType.set = contentType.get.getInterfaceType(context) ?: contentType.get
					method.addParameter(methodName.get, paramType.get)
					method.body = '''
						«IF paramType == contentType»
							this.«implField.simpleName».add(«methodName.get»);
						«ELSE»
							if («methodName.get» != null && !(«methodName.get» instanceof «contentType.get»))
								throw new «IllegalArgumentException.newTypeReference»("Implementation not supported: " + «methodName.get».getClass().getSimpleName());
							this.«implField.simpleName».add((«contentType.get») «methodName.get»);
						«ENDIF»
						return this;
					'''
				]
				
			} else if (fieldType.type == globalMapType) {
				methodName.set = implField.singularName
				builder.addMethod(methodName.get) [ method |
					method.primarySourceElement = implField.primarySourceElement
					method.returnType = builder.newTypeReference
					keyType.set = fieldType.actualTypeArguments.get(0)
					method.addParameter('key', keyType.get)
					contentType.set = fieldType.actualTypeArguments.get(1)
					if (contentType.get.type == globalListType) {
						// Fields with multi-map type
						contentType.set = contentType.get.actualTypeArguments.get(0)
						paramType.set = contentType.get.getInterfaceType(context) ?: contentType.get
						method.addParameter(methodName.get, paramType.get)
						method.body = '''
							«fieldType.actualTypeArguments.get(1)» list = this.«implField.simpleName».get(key);
							if (list == null) {
								list = «Lists.newTypeReference».newArrayList();
								this.«implField.simpleName».put(key, list);
							}
							«IF paramType == contentType»
								list.add(«methodName.get»);
							«ELSE»
								if («methodName.get» != null && !(«methodName.get» instanceof «contentType.get»))
									throw new «IllegalArgumentException.newTypeReference»("Implementation not supported: " + «methodName.get».getClass().getSimpleName());
								list.add((«contentType.get») «methodName.get»);
							«ENDIF»
							return this;
						'''
					} else {
						// Fields with simple map type
						paramType.set = contentType.get.getInterfaceType(context) ?: contentType.get
						method.addParameter(methodName.get, paramType.get)
						method.body = '''
							if (this.«implField.simpleName» == null)
								this.«implField.simpleName» = «Maps.newTypeReference».newLinkedHashMap();
							«IF paramType == contentType»
								this.«implField.simpleName».put(key, «methodName.get»);
							«ELSE»
								if («methodName.get» != null && !(«methodName.get» instanceof «contentType.get»))
									throw new «IllegalArgumentException.newTypeReference»("Implementation not supported: " + «methodName.get».getClass().getSimpleName());
								this.«implField.simpleName».put(key, («contentType.get») «methodName.get»);
							«ENDIF»
							return this;
						'''
					}
				]
				
			} else {
				// Fields with single value type
				methodName.set = implField.simpleName
				paramType.set = fieldType.getInterfaceType(context) ?: fieldType
				contentType.set = fieldType
				builder.addMethod(methodName.get) [ method |
					method.primarySourceElement = implField.primarySourceElement
					method.returnType = builder.newTypeReference
					method.addParameter(methodName.get, paramType.get)
					method.body = '''
						«IF paramType == contentType»
							this.«methodName.get» = «methodName.get»;
						«ELSE»
							if («methodName.get» != null && !(«methodName.get» instanceof «contentType.get»))
								throw new «IllegalArgumentException.newTypeReference»("Implementation not supported: " + «methodName.get».getClass().getSimpleName());
							this.«methodName.get» = («contentType.get») «methodName.get»;
						«ENDIF»
						return this;
					'''
				]
			}
			
			if (paramType != contentType) {
				val contentInterface = contentType.get.type.getInterfaceType(context)
				if (contentInterface instanceof InterfaceDeclaration) {
					val contentMethods = contentInterface.declaredMethods.filter[
						!static && parameters.empty && returnType !== null && (returnType.inferred || !returnType.isVoid)
					].toList
					if (!contentMethods.empty || contentInterface.extendedInterfaces.exists[
						(type as InterfaceDeclaration).doGenerateBuilder
					]) {
						// Add a method that accepts an initializer function
						builder.addMethod(methodName.get) [ method |
							method.primarySourceElement = implField.primarySourceElement
							method.returnType = builder.newTypeReference
							val builderType = paramType.get.type.builderName.newTypeReference
							if (!keyType.empty)
								method.addParameter('key', keyType.get)
							method.addParameter('initializer', Procedures.Procedure1.newTypeReference(builderType))
							method.body = '''
								«builderType» builder = new «builderType»();
								initializer.apply(builder);
								return «methodName.get»(«IF !keyType.empty»key, «ENDIF»builder.build());
							'''
						]
					}
					if (!contentMethods.empty && contentMethods.size <= MAX_CONSTRUCTOR_ARGS
							&& contentInterface.extendedInterfaces.empty
							&& contentMethods.forall[returnType.actualTypeArguments.empty]) {
						// Add a method that accepts constructor arguments for the respective type
						builder.addMethod(methodName.get) [ method |
							method.primarySourceElement = implField.primarySourceElement
							method.returnType = builder.newTypeReference
							if (!keyType.empty)
								method.addParameter('key', keyType.get)
							contentMethods.forEach[ contentMethod |
								method.addParameter(contentMethod.fieldName, contentMethod.returnType)
							]
							method.body = '''
								«FOR m : contentMethods»
									«IF m.returnType.isLanguageServerAPI»
										if («m.fieldName» != null && !(«m.fieldName» instanceof «m.getFieldType(context)»))
											throw new «IllegalArgumentException.newTypeReference»("Implementation not supported: " + «m.fieldName».getClass().getSimpleName());
									«ENDIF»
								«ENDFOR»
								«contentInterface.newTypeReference» instance = new «contentType.get»(«
									FOR m : contentMethods SEPARATOR ', '»«
										IF m.returnType.isLanguageServerAPI»(«m.getFieldType(context)») «ENDIF»«
										m.fieldName»«
									ENDFOR»);
								return «methodName.get»(«IF !keyType.empty»key, «ENDIF»instance);
							'''
						]
					}
				}
			}
		]
	}
	
	private def getSingularName(FieldDeclaration field) {
		val name = field.simpleName
		if (name.endsWith('ies'))
			name.substring(0, name.length - 3) + 'y'
		else if (name.endsWith('s'))
			name.substring(0, name.length - 1)
		else
			name
	}
	
	private def getInterfaceType(TypeReference typeRef, extension TransformationContext context) {
		typeRef?.type.getInterfaceType(context)?.newTypeReference
	}
	
	private def getInterfaceType(Type type, extension TransformationContext context) {
		val name = type?.qualifiedName
		if (name !== null && name.endsWith('Impl') && name.startsWith(NAMESPACE))
			return name.substring(0, name.length - 4).replace('impl.', '').findTypeGlobally
		else if (name !== null && name.endsWith('Builder') && name.startsWith(NAMESPACE))
			return name.substring(0, name.length - 7).replace('builders.', '').findTypeGlobally
	}
	
	private def getImplName(Type t) {
		if (t !== null)
			t.qualifiedName.substring(0, t.qualifiedName.length - t.simpleName.length) + 'impl.' + t.simpleName + 'Impl'
	}
	
	private def getBuilderName(Type t) {
		if (t !== null)
			t.qualifiedName.substring(0, t.qualifiedName.length - t.simpleName.length) + 'builders.' + t.simpleName + 'Builder'
	}

}