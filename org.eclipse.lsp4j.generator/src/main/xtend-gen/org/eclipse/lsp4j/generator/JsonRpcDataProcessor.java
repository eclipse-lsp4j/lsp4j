/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.generator;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.eclipse.lsp4j.generator.JsonRpcData;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtend.lib.annotations.AccessorsProcessor;
import org.eclipse.xtend.lib.annotations.EqualsHashCodeProcessor;
import org.eclipse.xtend.lib.macro.AbstractClassProcessor;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.AnnotationTypeDeclaration;
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableConstructorDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableFieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableParameterDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeDeclaration;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend.lib.macro.declaration.Visibility;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class JsonRpcDataProcessor extends AbstractClassProcessor {
  private final static int MAX_CONSTRUCTOR_ARGS = 3;
  
  @Override
  public void doTransform(final MutableClassDeclaration annotatedClass, @Extension final TransformationContext context) {
    this.generateImpl(annotatedClass, context);
  }
  
  protected MutableClassDeclaration generateImpl(final MutableClassDeclaration impl, @Extension final TransformationContext context) {
    Iterable<? extends AnnotationReference> _annotations = impl.getAnnotations();
    final Function1<AnnotationReference, Boolean> _function = (AnnotationReference it) -> {
      AnnotationTypeDeclaration _annotationTypeDeclaration = it.getAnnotationTypeDeclaration();
      Type _findTypeGlobally = context.findTypeGlobally(JsonRpcData.class);
      return Boolean.valueOf(Objects.equal(_annotationTypeDeclaration, _findTypeGlobally));
    };
    AnnotationReference _findFirst = IterableExtensions.findFirst(_annotations, _function);
    impl.removeAnnotation(_findFirst);
    this.generateImplMembers(impl, context);
    Iterable<? extends MutableFieldDeclaration> _declaredFields = impl.getDeclaredFields();
    final Function1<MutableFieldDeclaration, Boolean> _function_1 = (MutableFieldDeclaration it) -> {
      boolean _isStatic = it.isStatic();
      return Boolean.valueOf((!_isStatic));
    };
    final Iterable<? extends MutableFieldDeclaration> fields = IterableExtensions.filter(_declaredFields, _function_1);
    boolean _isEmpty = IterableExtensions.isEmpty(fields);
    boolean _not = (!_isEmpty);
    if (_not) {
      final Procedure1<MutableConstructorDeclaration> _function_2 = (MutableConstructorDeclaration it) -> {
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
          }
        };
        it.setBody(_client);
      };
      impl.addConstructor(_function_2);
      if (((IterableExtensions.size(fields) <= JsonRpcDataProcessor.MAX_CONSTRUCTOR_ARGS) && (impl.getExtendedClass() != context.getObject()))) {
        final Procedure1<MutableConstructorDeclaration> _function_3 = (MutableConstructorDeclaration constructor) -> {
          final Consumer<MutableFieldDeclaration> _function_4 = (MutableFieldDeclaration field) -> {
            String _simpleName = field.getSimpleName();
            TypeReference _type = field.getType();
            constructor.addParameter(_simpleName, _type);
          };
          fields.forEach(_function_4);
          StringConcatenationClient _client = new StringConcatenationClient() {
            @Override
            protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
              {
                for(final MutableFieldDeclaration field : fields) {
                  _builder.append("this.");
                  String _simpleName = field.getSimpleName();
                  _builder.append(_simpleName, "");
                  _builder.append(" = ");
                  String _simpleName_1 = field.getSimpleName();
                  _builder.append(_simpleName_1, "");
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          };
          constructor.setBody(_client);
        };
        impl.addConstructor(_function_3);
      }
    }
    this.generateToString(impl, context);
    TypeReference _extendedClass = impl.getExtendedClass();
    Type _type = _extendedClass.getType();
    TypeReference _newTypeReference = context.newTypeReference(Object.class);
    Type _type_1 = _newTypeReference.getType();
    final boolean shouldIncludeSuper = (!Objects.equal(_type, _type_1));
    final EqualsHashCodeProcessor.Util equalsHashCodeUtil = new EqualsHashCodeProcessor.Util(context);
    equalsHashCodeUtil.addEquals(impl, fields, shouldIncludeSuper);
    equalsHashCodeUtil.addHashCode(impl, fields, shouldIncludeSuper);
    return impl;
  }
  
  private void generateImplMembers(final MutableClassDeclaration impl, @Extension final TransformationContext context) {
    Iterable<? extends MutableFieldDeclaration> _declaredFields = impl.getDeclaredFields();
    final Function1<MutableFieldDeclaration, Boolean> _function = (MutableFieldDeclaration it) -> {
      boolean _isStatic = it.isStatic();
      return Boolean.valueOf((!_isStatic));
    };
    Iterable<? extends MutableFieldDeclaration> _filter = IterableExtensions.filter(_declaredFields, _function);
    final Consumer<MutableFieldDeclaration> _function_1 = (MutableFieldDeclaration field) -> {
      final AccessorsProcessor.Util accessorsUtil = new AccessorsProcessor.Util(context);
      Type _findTypeGlobally = context.findTypeGlobally(Deprecated.class);
      final AnnotationReference deprecated = field.findAnnotation(_findTypeGlobally);
      accessorsUtil.addGetter(field, Visibility.PUBLIC);
      TypeReference _newTypeReference = context.newTypeReference(NonNull.class);
      Type _type = _newTypeReference.getType();
      AnnotationReference _findAnnotation = field.findAnnotation(_type);
      final boolean hasNonNull = (_findAnnotation != null);
      String _getterName = accessorsUtil.getGetterName(field);
      MutableMethodDeclaration _findDeclaredMethod = impl.findDeclaredMethod(_getterName);
      final Procedure1<MutableMethodDeclaration> _function_2 = (MutableMethodDeclaration it) -> {
        String _docComment = field.getDocComment();
        it.setDocComment(_docComment);
        if (hasNonNull) {
          AnnotationReference _newAnnotationReference = context.newAnnotationReference(NonNull.class);
          it.addAnnotation(_newAnnotationReference);
        }
        if ((deprecated != null)) {
          AnnotationReference _newAnnotationReference_1 = context.newAnnotationReference(Deprecated.class);
          it.addAnnotation(_newAnnotationReference_1);
        }
      };
      ObjectExtensions.<MutableMethodDeclaration>operator_doubleArrow(_findDeclaredMethod, _function_2);
      TypeReference _type_1 = field.getType();
      boolean _isInferred = _type_1.isInferred();
      boolean _not = (!_isInferred);
      if (_not) {
        accessorsUtil.addSetter(field, Visibility.PUBLIC);
        String _setterName = accessorsUtil.getSetterName(field);
        TypeReference _type_2 = field.getType();
        MutableMethodDeclaration _findDeclaredMethod_1 = impl.findDeclaredMethod(_setterName, _type_2);
        final Procedure1<MutableMethodDeclaration> _function_3 = (MutableMethodDeclaration it) -> {
          String _docComment = field.getDocComment();
          it.setDocComment(_docComment);
          if (hasNonNull) {
            Iterable<? extends MutableParameterDeclaration> _parameters = it.getParameters();
            MutableParameterDeclaration _head = IterableExtensions.head(_parameters);
            AnnotationReference _newAnnotationReference = context.newAnnotationReference(NonNull.class);
            _head.addAnnotation(_newAnnotationReference);
          }
          if ((deprecated != null)) {
            AnnotationReference _newAnnotationReference_1 = context.newAnnotationReference(Deprecated.class);
            it.addAnnotation(_newAnnotationReference_1);
          }
        };
        ObjectExtensions.<MutableMethodDeclaration>operator_doubleArrow(_findDeclaredMethod_1, _function_3);
      }
    };
    _filter.forEach(_function_1);
  }
  
  private MutableMethodDeclaration generateToString(final MutableClassDeclaration impl, @Extension final TransformationContext context) {
    MutableMethodDeclaration _xblockexpression = null;
    {
      final ArrayList<FieldDeclaration> toStringFields = CollectionLiterals.<FieldDeclaration>newArrayList();
      ClassDeclaration c = impl;
      do {
        {
          Iterable<? extends FieldDeclaration> _declaredFields = c.getDeclaredFields();
          Iterables.<FieldDeclaration>addAll(toStringFields, _declaredFields);
          TypeReference _extendedClass = c.getExtendedClass();
          Type _type = null;
          if (_extendedClass!=null) {
            _type=_extendedClass.getType();
          }
          c = ((ClassDeclaration) _type);
        }
      } while(((c != null) && (!Objects.equal(c, context.getObject()))));
      final Procedure1<MutableMethodDeclaration> _function = (MutableMethodDeclaration it) -> {
        TypeReference _string = context.getString();
        it.setReturnType(_string);
        AnnotationReference _newAnnotationReference = context.newAnnotationReference(Override.class);
        it.addAnnotation(_newAnnotationReference);
        AnnotationReference _newAnnotationReference_1 = context.newAnnotationReference(Pure.class);
        it.addAnnotation(_newAnnotationReference_1);
        final AccessorsProcessor.Util accessorsUtil = new AccessorsProcessor.Util(context);
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append(ToStringBuilder.class, "");
            _builder.append(" b = new ");
            _builder.append(ToStringBuilder.class, "");
            _builder.append("(this);");
            _builder.newLineIfNotEmpty();
            {
              for(final FieldDeclaration field : toStringFields) {
                _builder.append("b.add(\"");
                String _simpleName = field.getSimpleName();
                _builder.append(_simpleName, "");
                _builder.append("\", ");
                {
                  TypeDeclaration _declaringType = field.getDeclaringType();
                  boolean _equals = Objects.equal(_declaringType, impl);
                  if (_equals) {
                    _builder.append("this.");
                    String _simpleName_1 = field.getSimpleName();
                    _builder.append(_simpleName_1, "");
                  } else {
                    String _getterName = accessorsUtil.getGetterName(field);
                    _builder.append(_getterName, "");
                    _builder.append("()");
                  }
                }
                _builder.append(");");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("return b.toString();");
            _builder.newLine();
          }
        };
        it.setBody(_client);
      };
      _xblockexpression = impl.addMethod("toString", _function);
    }
    return _xblockexpression;
  }
}
