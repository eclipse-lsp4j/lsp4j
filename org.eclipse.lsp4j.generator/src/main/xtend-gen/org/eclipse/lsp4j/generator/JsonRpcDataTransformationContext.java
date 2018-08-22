/**
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.generator;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.eclipse.lsp4j.generator.EitherTypeArgument;
import org.eclipse.lsp4j.generator.JsonType;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend.lib.annotations.Delegate;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.Element;
import org.eclipse.xtend.lib.macro.declaration.InterfaceDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableAnnotationTypeDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableElement;
import org.eclipse.xtend.lib.macro.declaration.MutableEnumerationTypeDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableInterfaceDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend.lib.macro.file.Path;
import org.eclipse.xtend.lib.macro.services.AnnotationReferenceBuildContext;
import org.eclipse.xtend.lib.macro.services.Problem;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class JsonRpcDataTransformationContext implements TransformationContext {
  @Delegate
  private final TransformationContext delegate;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final TypeReference eitherType;
  
  public JsonRpcDataTransformationContext(final TransformationContext delegate) {
    this.delegate = delegate;
    this.eitherType = this.newTypeReference(Either.class);
  }
  
  public boolean isEither(final TypeReference typeReference) {
    return ((typeReference != null) && this.eitherType.isAssignableFrom(typeReference));
  }
  
  public TypeReference getLeftType(final TypeReference typeReference) {
    final Type type = typeReference.getType();
    Type _type = this.eitherType.getType();
    boolean _tripleEquals = (type == _type);
    if (_tripleEquals) {
      return IterableExtensions.<TypeReference>head(typeReference.getActualTypeArguments());
    }
    if ((type instanceof InterfaceDeclaration)) {
      final Function1<TypeReference, TypeReference> _function = (TypeReference it) -> {
        return this.getLeftType(it);
      };
      return IterableExtensions.<TypeReference>head(IterableExtensions.<TypeReference>filterNull(IterableExtensions.map(((InterfaceDeclaration)type).getExtendedInterfaces(), _function)));
    }
    return null;
  }
  
  public TypeReference getRightType(final TypeReference typeReference) {
    final Type type = typeReference.getType();
    Type _type = this.eitherType.getType();
    boolean _tripleEquals = (type == _type);
    if (_tripleEquals) {
      return IterableExtensions.<TypeReference>last(typeReference.getActualTypeArguments());
    }
    if ((type instanceof InterfaceDeclaration)) {
      final Function1<TypeReference, TypeReference> _function = (TypeReference it) -> {
        return this.getRightType(it);
      };
      return IterableExtensions.<TypeReference>head(IterableExtensions.<TypeReference>filterNull(IterableExtensions.map(((InterfaceDeclaration)type).getExtendedInterfaces(), _function)));
    }
    return null;
  }
  
  public Collection<EitherTypeArgument> getChildTypes(final TypeReference typeReference) {
    final ArrayList<EitherTypeArgument> types = CollectionLiterals.<EitherTypeArgument>newArrayList();
    boolean _isEither = this.isEither(typeReference);
    if (_isEither) {
      this.collectChildTypes(this.getLeftType(typeReference), null, false, types);
      this.collectChildTypes(this.getRightType(typeReference), null, true, types);
    }
    return types;
  }
  
  protected void collectChildTypes(final TypeReference type, final EitherTypeArgument parent, final boolean right, final Collection<EitherTypeArgument> types) {
    final EitherTypeArgument argument = new EitherTypeArgument(type, parent, right);
    boolean _isEither = this.isEither(type);
    if (_isEither) {
      this.collectChildTypes(this.getLeftType(type), argument, false, types);
      this.collectChildTypes(this.getRightType(type), argument, true, types);
    } else {
      if ((type != null)) {
        types.add(argument);
      }
    }
  }
  
  public boolean isJsonNull(final TypeReference type) {
    JsonType _jsonType = this.getJsonType(type);
    return (_jsonType == JsonType.NULL);
  }
  
  public boolean isJsonString(final TypeReference type) {
    JsonType _jsonType = this.getJsonType(type);
    return (_jsonType == JsonType.STRING);
  }
  
  public boolean isJsonNumber(final TypeReference type) {
    JsonType _jsonType = this.getJsonType(type);
    return (_jsonType == JsonType.NUMBER);
  }
  
  public boolean isJsonBoolean(final TypeReference type) {
    JsonType _jsonType = this.getJsonType(type);
    return (_jsonType == JsonType.BOOLEAN);
  }
  
  public boolean isJsonArray(final TypeReference type) {
    JsonType _jsonType = this.getJsonType(type);
    return (_jsonType == JsonType.ARRAY);
  }
  
  public boolean isJsonObject(final TypeReference type) {
    JsonType _jsonType = this.getJsonType(type);
    return (_jsonType == JsonType.OBJECT);
  }
  
  public JsonType getJsonType(final TypeReference type) {
    if ((type == null)) {
      return JsonType.NULL;
    }
    if ((type.isArray() || this.newTypeReference(List.class).isAssignableFrom(type))) {
      return JsonType.ARRAY;
    }
    if ((this.newTypeReference(Enum.class).isAssignableFrom(type) || this.newTypeReference(Number.class).isAssignableFrom(type))) {
      return JsonType.NUMBER;
    }
    boolean _isAssignableFrom = this.newTypeReference(Boolean.class).isAssignableFrom(type);
    if (_isAssignableFrom) {
      return JsonType.BOOLEAN;
    }
    if ((this.newTypeReference(String.class).isAssignableFrom(type) || this.newTypeReference(Character.class).isAssignableFrom(type))) {
      return JsonType.STRING;
    }
    boolean _isPrimitive = type.isPrimitive();
    boolean _not = (!_isPrimitive);
    if (_not) {
      return JsonType.OBJECT;
    }
    throw new IllegalStateException(("Unexpected type reference: " + type));
  }
  
  public Element getPrimaryGeneratedJavaElement(final Element source) {
    return this.delegate.getPrimaryGeneratedJavaElement(source);
  }
  
  public Element getPrimarySourceElement(final Element target) {
    return this.delegate.getPrimarySourceElement(target);
  }
  
  public boolean isExternal(final Element element) {
    return this.delegate.isExternal(element);
  }
  
  public boolean isGenerated(final Element element) {
    return this.delegate.isGenerated(element);
  }
  
  public boolean isSource(final Element element) {
    return this.delegate.isSource(element);
  }
  
  public boolean isThePrimaryGeneratedJavaElement(final Element target) {
    return this.delegate.isThePrimaryGeneratedJavaElement(target);
  }
  
  public void addError(final Element element, final String message) {
    this.delegate.addError(element, message);
  }
  
  public void addWarning(final Element element, final String message) {
    this.delegate.addWarning(element, message);
  }
  
  public List<? extends Problem> getProblems(final Element element) {
    return this.delegate.getProblems(element);
  }
  
  public void validateLater(final Procedure0 validationCallback) {
    this.delegate.validateLater(validationCallback);
  }
  
  public TypeReference getAnyType() {
    return this.delegate.getAnyType();
  }
  
  public TypeReference getList(final TypeReference param) {
    return this.delegate.getList(param);
  }
  
  public TypeReference getObject() {
    return this.delegate.getObject();
  }
  
  public TypeReference getPrimitiveBoolean() {
    return this.delegate.getPrimitiveBoolean();
  }
  
  public TypeReference getPrimitiveByte() {
    return this.delegate.getPrimitiveByte();
  }
  
  public TypeReference getPrimitiveChar() {
    return this.delegate.getPrimitiveChar();
  }
  
  public TypeReference getPrimitiveDouble() {
    return this.delegate.getPrimitiveDouble();
  }
  
  public TypeReference getPrimitiveFloat() {
    return this.delegate.getPrimitiveFloat();
  }
  
  public TypeReference getPrimitiveInt() {
    return this.delegate.getPrimitiveInt();
  }
  
  public TypeReference getPrimitiveLong() {
    return this.delegate.getPrimitiveLong();
  }
  
  public TypeReference getPrimitiveShort() {
    return this.delegate.getPrimitiveShort();
  }
  
  public TypeReference getPrimitiveVoid() {
    return this.delegate.getPrimitiveVoid();
  }
  
  public TypeReference getSet(final TypeReference param) {
    return this.delegate.getSet(param);
  }
  
  public TypeReference getString() {
    return this.delegate.getString();
  }
  
  public TypeReference newArrayTypeReference(final TypeReference componentType) {
    return this.delegate.newArrayTypeReference(componentType);
  }
  
  public TypeReference newSelfTypeReference(final Type typeDeclaration) {
    return this.delegate.newSelfTypeReference(typeDeclaration);
  }
  
  public TypeReference newTypeReference(final String typeName, final TypeReference... typeArguments) {
    return this.delegate.newTypeReference(typeName, typeArguments);
  }
  
  public TypeReference newTypeReference(final Type typeDeclaration, final TypeReference... typeArguments) {
    return this.delegate.newTypeReference(typeDeclaration, typeArguments);
  }
  
  public TypeReference newTypeReference(final Class<?> clazz, final TypeReference... typeArguments) {
    return this.delegate.newTypeReference(clazz, typeArguments);
  }
  
  public TypeReference newWildcardTypeReference() {
    return this.delegate.newWildcardTypeReference();
  }
  
  public TypeReference newWildcardTypeReference(final TypeReference upperBound) {
    return this.delegate.newWildcardTypeReference(upperBound);
  }
  
  public TypeReference newWildcardTypeReferenceWithLowerBound(final TypeReference lowerBound) {
    return this.delegate.newWildcardTypeReferenceWithLowerBound(lowerBound);
  }
  
  public MutableAnnotationTypeDeclaration findAnnotationType(final String qualifiedName) {
    return this.delegate.findAnnotationType(qualifiedName);
  }
  
  public MutableClassDeclaration findClass(final String qualifiedName) {
    return this.delegate.findClass(qualifiedName);
  }
  
  public MutableEnumerationTypeDeclaration findEnumerationType(final String qualifiedName) {
    return this.delegate.findEnumerationType(qualifiedName);
  }
  
  public MutableInterfaceDeclaration findInterface(final String qualifiedName) {
    return this.delegate.findInterface(qualifiedName);
  }
  
  public Type findTypeGlobally(final Class<?> clazz) {
    return this.delegate.findTypeGlobally(clazz);
  }
  
  public Type findTypeGlobally(final String typeName) {
    return this.delegate.findTypeGlobally(typeName);
  }
  
  public boolean exists(final Path path) {
    return this.delegate.exists(path);
  }
  
  public String getCharset(final Path path) {
    return this.delegate.getCharset(path);
  }
  
  public Iterable<? extends Path> getChildren(final Path path) {
    return this.delegate.getChildren(path);
  }
  
  public CharSequence getContents(final Path path) {
    return this.delegate.getContents(path);
  }
  
  public InputStream getContentsAsStream(final Path path) {
    return this.delegate.getContentsAsStream(path);
  }
  
  public long getLastModification(final Path path) {
    return this.delegate.getLastModification(path);
  }
  
  public boolean isFile(final Path path) {
    return this.delegate.isFile(path);
  }
  
  public boolean isFolder(final Path path) {
    return this.delegate.isFolder(path);
  }
  
  public URI toURI(final Path path) {
    return this.delegate.toURI(path);
  }
  
  public Path getProjectFolder(final Path path) {
    return this.delegate.getProjectFolder(path);
  }
  
  public Set<Path> getProjectSourceFolders(final Path path) {
    return this.delegate.getProjectSourceFolders(path);
  }
  
  public Path getSourceFolder(final Path path) {
    return this.delegate.getSourceFolder(path);
  }
  
  public Path getTargetFolder(final Path sourceFolder) {
    return this.delegate.getTargetFolder(sourceFolder);
  }
  
  public AnnotationReference newAnnotationReference(final String annotationTypeName) {
    return this.delegate.newAnnotationReference(annotationTypeName);
  }
  
  public AnnotationReference newAnnotationReference(final Type annotationTypeDelcaration) {
    return this.delegate.newAnnotationReference(annotationTypeDelcaration);
  }
  
  public AnnotationReference newAnnotationReference(final Class<?> annotationClass) {
    return this.delegate.newAnnotationReference(annotationClass);
  }
  
  public AnnotationReference newAnnotationReference(final AnnotationReference annotationReference) {
    return this.delegate.newAnnotationReference(annotationReference);
  }
  
  public AnnotationReference newAnnotationReference(final String annotationTypeName, final Procedure1<AnnotationReferenceBuildContext> initializer) {
    return this.delegate.newAnnotationReference(annotationTypeName, initializer);
  }
  
  public AnnotationReference newAnnotationReference(final Type annotationTypeDelcaration, final Procedure1<AnnotationReferenceBuildContext> initializer) {
    return this.delegate.newAnnotationReference(annotationTypeDelcaration, initializer);
  }
  
  public AnnotationReference newAnnotationReference(final Class<?> annotationClass, final Procedure1<AnnotationReferenceBuildContext> initializer) {
    return this.delegate.newAnnotationReference(annotationClass, initializer);
  }
  
  public AnnotationReference newAnnotationReference(final AnnotationReference annotationReference, final Procedure1<AnnotationReferenceBuildContext> initializer) {
    return this.delegate.newAnnotationReference(annotationReference, initializer);
  }
  
  public void setPrimarySourceElement(final MutableElement javaElement, final Element sourceElement) {
    this.delegate.setPrimarySourceElement(javaElement, sourceElement);
  }
  
  @Pure
  public TypeReference getEitherType() {
    return this.eitherType;
  }
}
