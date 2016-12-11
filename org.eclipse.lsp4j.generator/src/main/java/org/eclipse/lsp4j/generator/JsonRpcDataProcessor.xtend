/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.generator

import com.google.gson.JsonObject
import java.util.List
import java.util.Map
import org.eclipse.lsp4j.jsonrpc.json.Either
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonEnum
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty
import org.eclipse.lsp4j.jsonrpc.validation.NonNull
import org.eclipse.xtend.lib.annotations.AccessorsProcessor
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.TypeReference
import org.eclipse.xtend.lib.macro.declaration.Visibility
import org.eclipse.xtend2.lib.StringConcatenation
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter
import com.google.gson.JsonElement

class JsonRpcDataProcessor extends AbstractClassProcessor {

    static val MAX_CONSTRUCTOR_ARGS = 3
    
    override doTransform(List<? extends MutableClassDeclaration> annotatedClasses, extension TransformationContext context) {
        for (impl : annotatedClasses) {
            if (impl.extendedClass === null || impl.extendedClass.type == Object.newTypeReference.type) {
                impl.extendedClass = WrappedJsonObject.newTypeReference
            }
        }
        for (impl : annotatedClasses) {
            generateImpl(impl, context)
        }
    }

    protected def generateImpl(MutableClassDeclaration impl, extension TransformationContext context) {
        impl.removeAnnotation(impl.annotations.findFirst [
            annotationTypeDeclaration == JsonRpcData.findTypeGlobally
        ])
        val fields = impl.declaredFields.filter[!static]
        impl.generateImplMembers(context)
        

        impl.addConstructor [
            body = '''
                super();
            '''
        ]
        impl.addConstructor [
            addParameter("jsonObject", JsonObject.newTypeReference)
            body = '''
                super(jsonObject);
            '''
        ]

        if (!fields.empty) {
            if (fields.size <= MAX_CONSTRUCTOR_ARGS && impl.extendedClass !== object) {
                impl.addConstructor [ constructor |
                    fields.forEach [ field |
                        constructor.addParameter(field.simpleName, field.type)
                    ]
                    constructor.body = '''
                        «FOR field : fields»
                            this.set«field.simpleName.toFirstUpper»(«field.simpleName»);
                        «ENDFOR»
                    '''
                ]
            }
        }
        fields.forEach[ remove ]
        return impl
    }
    
    private def StringConcatenation getConverterExpression(TypeReference ref, extension TransformationContext context) {
        switch (ref.type) {
            case string.type : {
                return '''stringConverter'''
            }
            case primitiveInt.type,
            case Integer.newTypeReference.type: {
                return '''integerConverter'''
            }
            case primitiveBoolean.type,
            case Boolean.newTypeReference.type: {
                return '''booleanConverter'''
            }
            case List.newTypeReference.type : {
                return '''listConverter(WrappedJsonConverter.«getConverterExpression(ref.actualTypeArguments.head, context)»)'''
            }
            case Map.newTypeReference.type : {
                return '''mapConverter(WrappedJsonConverter.«getConverterExpression(ref.actualTypeArguments.get(1), context)»)'''
            }
            case JsonElement.newTypeReference.type : {
                return '''noConverter'''
            }
            case object.type : {
                return '''anyConverter'''
            }
            case Either.newTypeReference.type : {
                return '''eitherConverter(WrappedJsonConverter.«getConverterExpression(ref.actualTypeArguments.get(0), context)», WrappedJsonConverter.«getConverterExpression(ref.actualTypeArguments.get(1), context)»)'''
            }
            case WrappedJsonEnum.newTypeReference.isAssignableFrom(ref) : {
                return '''enumConverter(«ref».class)'''
            }
            case WrappedJsonObject.newTypeReference.isAssignableFrom(ref) : {
                return '''objectConverter(«ref».class)'''
            }
            default : {
                return '''!!!! unhandled type «ref»'''
            }
        }
    }

    private def void generateImplMembers(MutableClassDeclaration impl, extension TransformationContext context) {
        impl.declaredFields.filter [
            !static
        ].forEach [ field |
            val property = field.simpleName+"Property"
            impl.addField(property) [
                static = true
                type = WrappedJsonProperty.newTypeReference(field.type)
                initializer = '''new «WrappedJsonProperty»<>("«field.simpleName»", «WrappedJsonConverter».«field.type.getConverterExpression(context)»)'''
            ]
            val accessorsUtil = new AccessorsProcessor.Util(context)
            val deprecated = field.findAnnotation(Deprecated.findTypeGlobally)
            accessorsUtil.addGetter(field, Visibility.PUBLIC)
            val hasNonNull = field.findAnnotation(NonNull.newTypeReference.type) !== null
            impl.findDeclaredMethod(accessorsUtil.getGetterName(field)) => [
                docComment = field.docComment
                if (hasNonNull) {
                    addAnnotation(newAnnotationReference(NonNull))
                }
                if (deprecated !== null)
                    addAnnotation(newAnnotationReference(Deprecated))
                body = '''
                    return «property».get(jsonObject);
                '''
            ]

            if (!field.type.inferred) {
                accessorsUtil.addSetter(field, Visibility.PUBLIC)
                impl.findDeclaredMethod(accessorsUtil.getSetterName(field), field.type) => [
                    docComment = field.docComment
                    if (hasNonNull) {
                        parameters.head.addAnnotation(newAnnotationReference(NonNull))
                    }
                    if (deprecated !== null)
                        addAnnotation(newAnnotationReference(Deprecated))
                    val paramName = parameters.head.simpleName
                    body = '''
                        «property».set(jsonObject, «paramName»);
                    '''
                ]
                
                impl.addMethod('remove'+field.simpleName.toFirstUpper) [
                    docComment = '''Removes the property «field.simpleName» from the underlying JSON object.'''
                    returnType = field.type
                    if (deprecated !== null)
                        addAnnotation(newAnnotationReference(Deprecated))
                    body = '''
                        return «property».remove(jsonObject);
                    '''
                ]
            }
        ]
    }

    private def Iterable<FieldDeclaration> getAllFields(ClassDeclaration it) {
        declaredFields + ((extendedClass?.type as ClassDeclaration)?.allFields ?: #[])
    }

}
