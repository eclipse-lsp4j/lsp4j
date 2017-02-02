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
import org.eclipse.lsp4j.generator.JsonRpcDataTransformationContext;
import org.eclipse.lsp4j.generator.JsonType;
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
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeDeclaration;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend.lib.macro.declaration.Visibility;
import org.eclipse.xtend2.lib.StringConcatenation;
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
    final Function1<AnnotationReference, Boolean> _function = (AnnotationReference it) -> {
      AnnotationTypeDeclaration _annotationTypeDeclaration = it.getAnnotationTypeDeclaration();
      Type _findTypeGlobally = context.findTypeGlobally(JsonRpcData.class);
      return Boolean.valueOf(Objects.equal(_annotationTypeDeclaration, _findTypeGlobally));
    };
    impl.removeAnnotation(IterableExtensions.findFirst(impl.getAnnotations(), _function));
    JsonRpcDataTransformationContext _jsonRpcDataTransformationContext = new JsonRpcDataTransformationContext(context);
    this.generateImplMembers(impl, _jsonRpcDataTransformationContext);
    final Function1<MutableFieldDeclaration, Boolean> _function_1 = (MutableFieldDeclaration it) -> {
      boolean _isStatic = it.isStatic();
      return Boolean.valueOf((!_isStatic));
    };
    final Iterable<? extends MutableFieldDeclaration> fields = IterableExtensions.filter(impl.getDeclaredFields(), _function_1);
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
            constructor.addParameter(field.getSimpleName(), field.getType());
          };
          fields.forEach(_function_4);
          StringConcatenationClient _client = new StringConcatenationClient() {
            @Override
            protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
              {
                for(final MutableFieldDeclaration field : fields) {
                  _builder.append("this.");
                  String _simpleName = field.getSimpleName();
                  _builder.append(_simpleName);
                  _builder.append(" = ");
                  String _simpleName_1 = field.getSimpleName();
                  _builder.append(_simpleName_1);
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
    Type _type = impl.getExtendedClass().getType();
    Type _type_1 = context.newTypeReference(Object.class).getType();
    final boolean shouldIncludeSuper = (!Objects.equal(_type, _type_1));
    final EqualsHashCodeProcessor.Util equalsHashCodeUtil = new EqualsHashCodeProcessor.Util(context);
    equalsHashCodeUtil.addEquals(impl, fields, shouldIncludeSuper);
    equalsHashCodeUtil.addHashCode(impl, fields, shouldIncludeSuper);
    return impl;
  }
  
  private void generateImplMembers(final MutableClassDeclaration impl, @Extension final JsonRpcDataTransformationContext context) {
    final Function1<MutableFieldDeclaration, Boolean> _function = (MutableFieldDeclaration it) -> {
      boolean _isStatic = it.isStatic();
      return Boolean.valueOf((!_isStatic));
    };
    final Consumer<MutableFieldDeclaration> _function_1 = (MutableFieldDeclaration field) -> {
      final AccessorsProcessor.Util accessorsUtil = new AccessorsProcessor.Util(context);
      final AnnotationReference deprecated = field.findAnnotation(context.findTypeGlobally(Deprecated.class));
      accessorsUtil.addGetter(field, Visibility.PUBLIC);
      AnnotationReference _findAnnotation = field.findAnnotation(context.newTypeReference(NonNull.class).getType());
      final boolean hasNonNull = (_findAnnotation != null);
      MutableMethodDeclaration _findDeclaredMethod = impl.findDeclaredMethod(accessorsUtil.getGetterName(field));
      final Procedure1<MutableMethodDeclaration> _function_2 = (MutableMethodDeclaration it) -> {
        it.setDocComment(field.getDocComment());
        if (hasNonNull) {
          it.addAnnotation(context.newAnnotationReference(NonNull.class));
        }
        if ((deprecated != null)) {
          it.addAnnotation(context.newAnnotationReference(Deprecated.class));
        }
      };
      ObjectExtensions.<MutableMethodDeclaration>operator_doubleArrow(_findDeclaredMethod, _function_2);
      boolean _isInferred = field.getType().isInferred();
      boolean _not = (!_isInferred);
      if (_not) {
        accessorsUtil.addSetter(field, Visibility.PUBLIC);
        final String setterName = accessorsUtil.getSetterName(field);
        MutableMethodDeclaration _findDeclaredMethod_1 = impl.findDeclaredMethod(setterName, field.getType());
        final Procedure1<MutableMethodDeclaration> _function_3 = (MutableMethodDeclaration it) -> {
          it.setDocComment(field.getDocComment());
          if (hasNonNull) {
            IterableExtensions.head(it.getParameters()).addAnnotation(context.newAnnotationReference(NonNull.class));
          }
          if ((deprecated != null)) {
            it.addAnnotation(context.newAnnotationReference(Deprecated.class));
          }
        };
        ObjectExtensions.<MutableMethodDeclaration>operator_doubleArrow(_findDeclaredMethod_1, _function_3);
        boolean _isEither = context.isEither(field.getType());
        if (_isEither) {
          this.addEitherSetter(field, setterName, field.getType(), context);
        }
      }
    };
    IterableExtensions.filter(impl.getDeclaredFields(), _function).forEach(_function_1);
  }
  
  protected void addEitherSetter(final MutableFieldDeclaration field, final String setterName, final TypeReference type, @Extension final JsonRpcDataTransformationContext context) {
    final TypeReference leftType = context.getLeftType(type);
    final TypeReference rightType = context.getRightType(type);
    JsonType _jsonType = context.getJsonType(leftType);
    JsonType _jsonType_1 = context.getJsonType(rightType);
    boolean _tripleEquals = (_jsonType == _jsonType_1);
    if (_tripleEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("The json types of an Either must be distinct.");
      context.addError(field, _builder.toString());
      return;
    }
    boolean _isEither = context.isEither(leftType);
    if (_isEither) {
    } else {
      this.addEitherSetter(field, setterName, leftType, false, context);
    }
    boolean _isEither_1 = context.isEither(rightType);
    if (_isEither_1) {
    } else {
      this.addEitherSetter(field, setterName, rightType, true, context);
    }
  }
  
  protected void addEitherSetter(final MutableFieldDeclaration field, final String setterName, final TypeReference type, final boolean right, @Extension final JsonRpcDataTransformationContext context) {
    final Procedure1<MutableMethodDeclaration> _function = (MutableMethodDeclaration method) -> {
      context.setPrimarySourceElement(method, context.getPrimarySourceElement(field));
      method.addParameter(field.getSimpleName(), type);
      method.setStatic(field.isStatic());
      method.setVisibility(Visibility.PUBLIC);
      method.setReturnType(context.getPrimitiveVoid());
      StringConcatenationClient _client = new StringConcatenationClient() {
        @Override
        protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
          _builder.append("this.");
          String _simpleName = field.getSimpleName();
          _builder.append(_simpleName);
          _builder.append(" = ");
          TypeReference _eitherType = context.getEitherType();
          _builder.append(_eitherType);
          _builder.append(".for");
          {
            if (right) {
              _builder.append("Right");
            } else {
              _builder.append("Left");
            }
          }
          _builder.append("(");
          String _simpleName_1 = field.getSimpleName();
          _builder.append(_simpleName_1);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
        }
      };
      method.setBody(_client);
    };
    field.getDeclaringType().addMethod(setterName, _function);
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
        it.setReturnType(context.getString());
        it.addAnnotation(context.newAnnotationReference(Override.class));
        it.addAnnotation(context.newAnnotationReference(Pure.class));
        final AccessorsProcessor.Util accessorsUtil = new AccessorsProcessor.Util(context);
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append(ToStringBuilder.class);
            _builder.append(" b = new ");
            _builder.append(ToStringBuilder.class);
            _builder.append("(this);");
            _builder.newLineIfNotEmpty();
            {
              for(final FieldDeclaration field : toStringFields) {
                _builder.append("b.add(\"");
                String _simpleName = field.getSimpleName();
                _builder.append(_simpleName);
                _builder.append("\", ");
                {
                  TypeDeclaration _declaringType = field.getDeclaringType();
                  boolean _equals = Objects.equal(_declaringType, impl);
                  if (_equals) {
                    _builder.append("this.");
                    String _simpleName_1 = field.getSimpleName();
                    _builder.append(_simpleName_1);
                  } else {
                    String _getterName = accessorsUtil.getGetterName(field);
                    _builder.append(_getterName);
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