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
  
  public boolean isSource(final Element arg0) {
    return this.delegate.isSource(arg0);
  }
  
  public boolean isGenerated(final Element arg0) {
    return this.delegate.isGenerated(arg0);
  }
  
  public boolean isExternal(final Element arg0) {
    return this.delegate.isExternal(arg0);
  }
  
  public Element getPrimaryGeneratedJavaElement(final Element arg0) {
    return this.delegate.getPrimaryGeneratedJavaElement(arg0);
  }
  
  public boolean isThePrimaryGeneratedJavaElement(final Element arg0) {
    return this.delegate.isThePrimaryGeneratedJavaElement(arg0);
  }
  
  public Element getPrimarySourceElement(final Element arg0) {
    return this.delegate.getPrimarySourceElement(arg0);
  }
  
  public List<? extends Problem> getProblems(final Element arg0) {
    return this.delegate.getProblems(arg0);
  }
  
  public void addError(final Element arg0, final String arg1) {
    this.delegate.addError(arg0, arg1);
  }
  
  public void addWarning(final Element arg0, final String arg1) {
    this.delegate.addWarning(arg0, arg1);
  }
  
  public void validateLater(final Procedure0 arg0) {
    this.delegate.validateLater(arg0);
  }
  
  public TypeReference newArrayTypeReference(final TypeReference arg0) {
    return this.delegate.newArrayTypeReference(arg0);
  }
  
  public TypeReference newTypeReference(final String arg0, final TypeReference... arg1) {
    return this.delegate.newTypeReference(arg0, arg1);
  }
  
  public TypeReference newTypeReference(final Type arg0, final TypeReference... arg1) {
    return this.delegate.newTypeReference(arg0, arg1);
  }
  
  public TypeReference newSelfTypeReference(final Type arg0) {
    return this.delegate.newSelfTypeReference(arg0);
  }
  
  public TypeReference newTypeReference(final Class<?> arg0, final TypeReference... arg1) {
    return this.delegate.newTypeReference(arg0, arg1);
  }
  
  public TypeReference newWildcardTypeReference() {
    return this.delegate.newWildcardTypeReference();
  }
  
  public TypeReference newWildcardTypeReference(final TypeReference arg0) {
    return this.delegate.newWildcardTypeReference(arg0);
  }
  
  public TypeReference newWildcardTypeReferenceWithLowerBound(final TypeReference arg0) {
    return this.delegate.newWildcardTypeReferenceWithLowerBound(arg0);
  }
  
  public TypeReference getObject() {
    return this.delegate.getObject();
  }
  
  public TypeReference getString() {
    return this.delegate.getString();
  }
  
  public TypeReference getList(final TypeReference arg0) {
    return this.delegate.getList(arg0);
  }
  
  public TypeReference getSet(final TypeReference arg0) {
    return this.delegate.getSet(arg0);
  }
  
  public TypeReference getAnyType() {
    return this.delegate.getAnyType();
  }
  
  public TypeReference getPrimitiveVoid() {
    return this.delegate.getPrimitiveVoid();
  }
  
  public TypeReference getPrimitiveBoolean() {
    return this.delegate.getPrimitiveBoolean();
  }
  
  public TypeReference getPrimitiveShort() {
    return this.delegate.getPrimitiveShort();
  }
  
  public TypeReference getPrimitiveInt() {
    return this.delegate.getPrimitiveInt();
  }
  
  public TypeReference getPrimitiveLong() {
    return this.delegate.getPrimitiveLong();
  }
  
  public TypeReference getPrimitiveFloat() {
    return this.delegate.getPrimitiveFloat();
  }
  
  public TypeReference getPrimitiveDouble() {
    return this.delegate.getPrimitiveDouble();
  }
  
  public TypeReference getPrimitiveChar() {
    return this.delegate.getPrimitiveChar();
  }
  
  public TypeReference getPrimitiveByte() {
    return this.delegate.getPrimitiveByte();
  }
  
  public MutableClassDeclaration findClass(final String arg0) {
    return this.delegate.findClass(arg0);
  }
  
  public MutableInterfaceDeclaration findInterface(final String arg0) {
    return this.delegate.findInterface(arg0);
  }
  
  public MutableEnumerationTypeDeclaration findEnumerationType(final String arg0) {
    return this.delegate.findEnumerationType(arg0);
  }
  
  public MutableAnnotationTypeDeclaration findAnnotationType(final String arg0) {
    return this.delegate.findAnnotationType(arg0);
  }
  
  public Type findTypeGlobally(final Class<?> arg0) {
    return this.delegate.findTypeGlobally(arg0);
  }
  
  public Type findTypeGlobally(final String arg0) {
    return this.delegate.findTypeGlobally(arg0);
  }
  
  public Iterable<? extends Path> getChildren(final Path arg0) {
    return this.delegate.getChildren(arg0);
  }
  
  public boolean exists(final Path arg0) {
    return this.delegate.exists(arg0);
  }
  
  public boolean isFolder(final Path arg0) {
    return this.delegate.isFolder(arg0);
  }
  
  public boolean isFile(final Path arg0) {
    return this.delegate.isFile(arg0);
  }
  
  public long getLastModification(final Path arg0) {
    return this.delegate.getLastModification(arg0);
  }
  
  public String getCharset(final Path arg0) {
    return this.delegate.getCharset(arg0);
  }
  
  public CharSequence getContents(final Path arg0) {
    return this.delegate.getContents(arg0);
  }
  
  public InputStream getContentsAsStream(final Path arg0) {
    return this.delegate.getContentsAsStream(arg0);
  }
  
  public URI toURI(final Path arg0) {
    return this.delegate.toURI(arg0);
  }
  
  public Path getSourceFolder(final Path arg0) {
    return this.delegate.getSourceFolder(arg0);
  }
  
  public Path getTargetFolder(final Path arg0) {
    return this.delegate.getTargetFolder(arg0);
  }
  
  public Path getProjectFolder(final Path arg0) {
    return this.delegate.getProjectFolder(arg0);
  }
  
  public Set<Path> getProjectSourceFolders(final Path arg0) {
    return this.delegate.getProjectSourceFolders(arg0);
  }
  
  public AnnotationReference newAnnotationReference(final String arg0) {
    return this.delegate.newAnnotationReference(arg0);
  }
  
  public AnnotationReference newAnnotationReference(final Type arg0) {
    return this.delegate.newAnnotationReference(arg0);
  }
  
  public AnnotationReference newAnnotationReference(final Class<?> arg0) {
    return this.delegate.newAnnotationReference(arg0);
  }
  
  public AnnotationReference newAnnotationReference(final AnnotationReference arg0) {
    return this.delegate.newAnnotationReference(arg0);
  }
  
  public AnnotationReference newAnnotationReference(final String arg0, final Procedure1<AnnotationReferenceBuildContext> arg1) {
    return this.delegate.newAnnotationReference(arg0, arg1);
  }
  
  public AnnotationReference newAnnotationReference(final Type arg0, final Procedure1<AnnotationReferenceBuildContext> arg1) {
    return this.delegate.newAnnotationReference(arg0, arg1);
  }
  
  public AnnotationReference newAnnotationReference(final Class<?> arg0, final Procedure1<AnnotationReferenceBuildContext> arg1) {
    return this.delegate.newAnnotationReference(arg0, arg1);
  }
  
  public AnnotationReference newAnnotationReference(final AnnotationReference arg0, final Procedure1<AnnotationReferenceBuildContext> arg1) {
    return this.delegate.newAnnotationReference(arg0, arg1);
  }
  
  public void setPrimarySourceElement(final MutableElement arg0, final Element arg1) {
    this.delegate.setPrimarySourceElement(arg0, arg1);
  }
  
  @Pure
  public TypeReference getEitherType() {
    return this.eitherType;
  }
}
