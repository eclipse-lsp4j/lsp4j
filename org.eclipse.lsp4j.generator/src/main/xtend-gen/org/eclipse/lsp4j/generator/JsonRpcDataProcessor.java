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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.lsp4j.generator.JsonRpcData;
import org.eclipse.lsp4j.jsonrpc.json.Either;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonEnum;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtend.lib.annotations.AccessorsProcessor;
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
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class JsonRpcDataProcessor extends AbstractClassProcessor {
  private final static int MAX_CONSTRUCTOR_ARGS = 3;
  
  @Override
  public void doTransform(final List<? extends MutableClassDeclaration> annotatedClasses, @Extension final TransformationContext context) {
    for (final MutableClassDeclaration impl : annotatedClasses) {
      if (((impl.getExtendedClass() == null) || Objects.equal(impl.getExtendedClass().getType(), context.newTypeReference(Object.class).getType()))) {
        TypeReference _newTypeReference = context.newTypeReference(WrappedJsonObject.class);
        impl.setExtendedClass(_newTypeReference);
      }
    }
    for (final MutableClassDeclaration impl_1 : annotatedClasses) {
      this.generateImpl(impl_1, context);
    }
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
    Iterable<? extends MutableFieldDeclaration> _declaredFields = impl.getDeclaredFields();
    final Function1<MutableFieldDeclaration, Boolean> _function_1 = (MutableFieldDeclaration it) -> {
      boolean _isStatic = it.isStatic();
      return Boolean.valueOf((!_isStatic));
    };
    final Iterable<? extends MutableFieldDeclaration> fields = IterableExtensions.filter(_declaredFields, _function_1);
    this.generateImplMembers(impl, context);
    final Procedure1<MutableConstructorDeclaration> _function_2 = (MutableConstructorDeclaration it) -> {
      StringConcatenationClient _client = new StringConcatenationClient() {
        @Override
        protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
          _builder.append("super();");
          _builder.newLine();
        }
      };
      it.setBody(_client);
    };
    impl.addConstructor(_function_2);
    final Procedure1<MutableConstructorDeclaration> _function_3 = (MutableConstructorDeclaration it) -> {
      TypeReference _newTypeReference = context.newTypeReference(JsonObject.class);
      it.addParameter("jsonObject", _newTypeReference);
      StringConcatenationClient _client = new StringConcatenationClient() {
        @Override
        protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
          _builder.append("super(jsonObject);");
          _builder.newLine();
        }
      };
      it.setBody(_client);
    };
    impl.addConstructor(_function_3);
    boolean _isEmpty = IterableExtensions.isEmpty(fields);
    boolean _not = (!_isEmpty);
    if (_not) {
      if (((IterableExtensions.size(fields) <= JsonRpcDataProcessor.MAX_CONSTRUCTOR_ARGS) && (impl.getExtendedClass() != context.getObject()))) {
        final Procedure1<MutableConstructorDeclaration> _function_4 = (MutableConstructorDeclaration constructor) -> {
          final Consumer<MutableFieldDeclaration> _function_5 = (MutableFieldDeclaration field) -> {
            String _simpleName = field.getSimpleName();
            TypeReference _type = field.getType();
            constructor.addParameter(_simpleName, _type);
          };
          fields.forEach(_function_5);
          StringConcatenationClient _client = new StringConcatenationClient() {
            @Override
            protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
              {
                for(final MutableFieldDeclaration field : fields) {
                  _builder.append("this.set");
                  String _simpleName = field.getSimpleName();
                  String _firstUpper = StringExtensions.toFirstUpper(_simpleName);
                  _builder.append(_firstUpper, "");
                  _builder.append("(");
                  String _simpleName_1 = field.getSimpleName();
                  _builder.append(_simpleName_1, "");
                  _builder.append(");");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          };
          constructor.setBody(_client);
        };
        impl.addConstructor(_function_4);
      }
    }
    final Consumer<MutableFieldDeclaration> _function_5 = (MutableFieldDeclaration it) -> {
      it.remove();
    };
    fields.forEach(_function_5);
    return impl;
  }
  
  private StringConcatenation getConverterExpression(final TypeReference ref, @Extension final TransformationContext context) {
    Type _type = ref.getType();
    boolean _matched = false;
    TypeReference _string = context.getString();
    Type _type_1 = _string.getType();
    if (Objects.equal(_type, _type_1)) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("stringConverter");
      return _builder;
    }
    if (!_matched) {
      TypeReference _primitiveInt = context.getPrimitiveInt();
      Type _type_2 = _primitiveInt.getType();
      if (Objects.equal(_type, _type_2)) {
        _matched=true;
      }
      if (!_matched) {
        TypeReference _newTypeReference = context.newTypeReference(Integer.class);
        Type _type_3 = _newTypeReference.getType();
        if (Objects.equal(_type, _type_3)) {
          _matched=true;
        }
      }
      if (_matched) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("integerConverter");
        return _builder_1;
      }
    }
    if (!_matched) {
      TypeReference _primitiveBoolean = context.getPrimitiveBoolean();
      Type _type_4 = _primitiveBoolean.getType();
      if (Objects.equal(_type, _type_4)) {
        _matched=true;
      }
      if (!_matched) {
        TypeReference _newTypeReference_1 = context.newTypeReference(Boolean.class);
        Type _type_5 = _newTypeReference_1.getType();
        if (Objects.equal(_type, _type_5)) {
          _matched=true;
        }
      }
      if (_matched) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("booleanConverter");
        return _builder_2;
      }
    }
    if (!_matched) {
      TypeReference _newTypeReference_2 = context.newTypeReference(List.class);
      Type _type_6 = _newTypeReference_2.getType();
      if (Objects.equal(_type, _type_6)) {
        _matched=true;
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("listConverter(WrappedJsonConverter.");
        List<TypeReference> _actualTypeArguments = ref.getActualTypeArguments();
        TypeReference _head = IterableExtensions.<TypeReference>head(_actualTypeArguments);
        StringConcatenation _converterExpression = this.getConverterExpression(_head, context);
        _builder_3.append(_converterExpression, "");
        _builder_3.append(")");
        return _builder_3;
      }
    }
    if (!_matched) {
      TypeReference _newTypeReference_3 = context.newTypeReference(Map.class);
      Type _type_7 = _newTypeReference_3.getType();
      if (Objects.equal(_type, _type_7)) {
        _matched=true;
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("mapConverter(WrappedJsonConverter.");
        List<TypeReference> _actualTypeArguments_1 = ref.getActualTypeArguments();
        TypeReference _get = _actualTypeArguments_1.get(1);
        StringConcatenation _converterExpression_1 = this.getConverterExpression(_get, context);
        _builder_4.append(_converterExpression_1, "");
        _builder_4.append(")");
        return _builder_4;
      }
    }
    if (!_matched) {
      TypeReference _newTypeReference_4 = context.newTypeReference(JsonElement.class);
      Type _type_8 = _newTypeReference_4.getType();
      if (Objects.equal(_type, _type_8)) {
        _matched=true;
        StringConcatenation _builder_5 = new StringConcatenation();
        _builder_5.append("noConverter");
        return _builder_5;
      }
    }
    if (!_matched) {
      TypeReference _object = context.getObject();
      Type _type_9 = _object.getType();
      if (Objects.equal(_type, _type_9)) {
        _matched=true;
        StringConcatenation _builder_6 = new StringConcatenation();
        _builder_6.append("anyConverter");
        return _builder_6;
      }
    }
    if (!_matched) {
      TypeReference _newTypeReference_5 = context.newTypeReference(Either.class);
      Type _type_10 = _newTypeReference_5.getType();
      if (Objects.equal(_type, _type_10)) {
        _matched=true;
        StringConcatenation _builder_7 = new StringConcatenation();
        _builder_7.append("eitherConverter(WrappedJsonConverter.");
        List<TypeReference> _actualTypeArguments_2 = ref.getActualTypeArguments();
        TypeReference _get_1 = _actualTypeArguments_2.get(0);
        StringConcatenation _converterExpression_2 = this.getConverterExpression(_get_1, context);
        _builder_7.append(_converterExpression_2, "");
        _builder_7.append(", WrappedJsonConverter.");
        List<TypeReference> _actualTypeArguments_3 = ref.getActualTypeArguments();
        TypeReference _get_2 = _actualTypeArguments_3.get(1);
        StringConcatenation _converterExpression_3 = this.getConverterExpression(_get_2, context);
        _builder_7.append(_converterExpression_3, "");
        _builder_7.append(")");
        return _builder_7;
      }
    }
    if (!_matched) {
      TypeReference _newTypeReference_6 = context.newTypeReference(WrappedJsonEnum.class);
      boolean _isAssignableFrom = _newTypeReference_6.isAssignableFrom(ref);
      if (_isAssignableFrom) {
        _matched=true;
        StringConcatenation _builder_8 = new StringConcatenation();
        _builder_8.append("enumConverter(");
        _builder_8.append(ref, "");
        _builder_8.append(".class)");
        return _builder_8;
      }
    }
    if (!_matched) {
      TypeReference _newTypeReference_7 = context.newTypeReference(WrappedJsonObject.class);
      boolean _isAssignableFrom_1 = _newTypeReference_7.isAssignableFrom(ref);
      if (_isAssignableFrom_1) {
        _matched=true;
        StringConcatenation _builder_9 = new StringConcatenation();
        _builder_9.append("objectConverter(");
        _builder_9.append(ref, "");
        _builder_9.append(".class)");
        return _builder_9;
      }
    }
    StringConcatenation _builder_10 = new StringConcatenation();
    _builder_10.append("!!!! unhandled type ");
    _builder_10.append(ref, "");
    return _builder_10;
  }
  
  private void generateImplMembers(final MutableClassDeclaration impl, @Extension final TransformationContext context) {
    Iterable<? extends MutableFieldDeclaration> _declaredFields = impl.getDeclaredFields();
    final Function1<MutableFieldDeclaration, Boolean> _function = (MutableFieldDeclaration it) -> {
      boolean _isStatic = it.isStatic();
      return Boolean.valueOf((!_isStatic));
    };
    Iterable<? extends MutableFieldDeclaration> _filter = IterableExtensions.filter(_declaredFields, _function);
    final Consumer<MutableFieldDeclaration> _function_1 = (MutableFieldDeclaration field) -> {
      String _simpleName = field.getSimpleName();
      final String property = (_simpleName + "Property");
      final Procedure1<MutableFieldDeclaration> _function_2 = (MutableFieldDeclaration it) -> {
        it.setStatic(true);
        TypeReference _type = field.getType();
        TypeReference _newTypeReference = context.newTypeReference(WrappedJsonProperty.class, _type);
        it.setType(_newTypeReference);
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append("new ");
            _builder.append(WrappedJsonProperty.class, "");
            _builder.append("<>(\"");
            String _simpleName = field.getSimpleName();
            _builder.append(_simpleName, "");
            _builder.append("\", ");
            _builder.append(WrappedJsonConverter.class, "");
            _builder.append(".");
            TypeReference _type = field.getType();
            StringConcatenation _converterExpression = JsonRpcDataProcessor.this.getConverterExpression(_type, context);
            _builder.append(_converterExpression, "");
            _builder.append(")");
          }
        };
        it.setInitializer(_client);
      };
      impl.addField(property, _function_2);
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
      final Procedure1<MutableMethodDeclaration> _function_3 = (MutableMethodDeclaration it) -> {
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
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append("return ");
            _builder.append(property, "");
            _builder.append(".get(jsonObject);");
            _builder.newLineIfNotEmpty();
          }
        };
        it.setBody(_client);
      };
      ObjectExtensions.<MutableMethodDeclaration>operator_doubleArrow(_findDeclaredMethod, _function_3);
      TypeReference _type_1 = field.getType();
      boolean _isInferred = _type_1.isInferred();
      boolean _not = (!_isInferred);
      if (_not) {
        accessorsUtil.addSetter(field, Visibility.PUBLIC);
        String _setterName = accessorsUtil.getSetterName(field);
        TypeReference _type_2 = field.getType();
        MutableMethodDeclaration _findDeclaredMethod_1 = impl.findDeclaredMethod(_setterName, _type_2);
        final Procedure1<MutableMethodDeclaration> _function_4 = (MutableMethodDeclaration it) -> {
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
          Iterable<? extends MutableParameterDeclaration> _parameters_1 = it.getParameters();
          MutableParameterDeclaration _head_1 = IterableExtensions.head(_parameters_1);
          final String paramName = _head_1.getSimpleName();
          StringConcatenationClient _client = new StringConcatenationClient() {
            @Override
            protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
              _builder.append(property, "");
              _builder.append(".set(jsonObject, ");
              _builder.append(paramName, "");
              _builder.append(");");
              _builder.newLineIfNotEmpty();
            }
          };
          it.setBody(_client);
        };
        ObjectExtensions.<MutableMethodDeclaration>operator_doubleArrow(_findDeclaredMethod_1, _function_4);
        String _simpleName_1 = field.getSimpleName();
        String _firstUpper = StringExtensions.toFirstUpper(_simpleName_1);
        String _plus = ("remove" + _firstUpper);
        final Procedure1<MutableMethodDeclaration> _function_5 = (MutableMethodDeclaration it) -> {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("Removes the property ");
          String _simpleName_2 = field.getSimpleName();
          _builder.append(_simpleName_2, "");
          _builder.append(" from the underlying JSON object.");
          it.setDocComment(_builder.toString());
          TypeReference _type_3 = field.getType();
          it.setReturnType(_type_3);
          if ((deprecated != null)) {
            AnnotationReference _newAnnotationReference = context.newAnnotationReference(Deprecated.class);
            it.addAnnotation(_newAnnotationReference);
          }
          StringConcatenationClient _client = new StringConcatenationClient() {
            @Override
            protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
              _builder.append("return ");
              _builder.append(property, "");
              _builder.append(".remove(jsonObject);");
              _builder.newLineIfNotEmpty();
            }
          };
          it.setBody(_client);
        };
        impl.addMethod(_plus, _function_5);
      }
    };
    _filter.forEach(_function_1);
  }
  
  private Iterable<FieldDeclaration> getAllFields(final ClassDeclaration it) {
    Iterable<? extends FieldDeclaration> _declaredFields = it.getDeclaredFields();
    Iterable<FieldDeclaration> _elvis = null;
    TypeReference _extendedClass = it.getExtendedClass();
    Type _type = null;
    if (_extendedClass!=null) {
      _type=_extendedClass.getType();
    }
    Iterable<FieldDeclaration> _allFields = null;
    if (((ClassDeclaration) _type)!=null) {
      Type _type_1 = null;
      if (_extendedClass!=null) {
        _type_1=_extendedClass.getType();
      }
      _allFields=this.getAllFields(((ClassDeclaration) _type_1));
    }
    if (_allFields != null) {
      _elvis = _allFields;
    } else {
      _elvis = Collections.<FieldDeclaration>unmodifiableList(CollectionLiterals.<FieldDeclaration>newArrayList());
    }
    return Iterables.<FieldDeclaration>concat(_declaredFields, _elvis);
  }
}
