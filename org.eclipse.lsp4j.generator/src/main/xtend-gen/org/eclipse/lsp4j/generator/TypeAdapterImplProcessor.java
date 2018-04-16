/**
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.generator;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.eclipse.lsp4j.generator.TypeAdapterImpl;
import org.eclipse.xtend.lib.macro.AbstractClassProcessor;
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableConstructorDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableFieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableTypeParameterDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend.lib.macro.declaration.Visibility;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class TypeAdapterImplProcessor extends AbstractClassProcessor {
  @Override
  public void doRegisterGlobals(final ClassDeclaration annotatedClass, @Extension final RegisterGlobalsContext context) {
    String _qualifiedName = annotatedClass.getQualifiedName();
    String _plus = (_qualifiedName + ".Factory");
    context.registerClass(_plus);
  }
  
  @Override
  public void doTransform(final MutableClassDeclaration annotatedClass, @Extension final TransformationContext context) {
    final AnnotationReference typeAdapterImplAnnotation = annotatedClass.findAnnotation(context.findTypeGlobally(TypeAdapterImpl.class));
    final TypeReference targetType = typeAdapterImplAnnotation.getClassValue("value");
    this.generateImpl(annotatedClass, targetType, context);
    String _qualifiedName = annotatedClass.getQualifiedName();
    String _plus = (_qualifiedName + ".Factory");
    this.generateFactory(context.findClass(_plus), annotatedClass, targetType, context);
  }
  
  protected MutableClassDeclaration generateImpl(final MutableClassDeclaration impl, final TypeReference targetType, @Extension final TransformationContext context) {
    final ArrayList<FieldDeclaration> targetFields = this.getTargetFields(targetType, context);
    final Function1<FieldDeclaration, Boolean> _function = (FieldDeclaration it) -> {
      return Boolean.valueOf(((!it.getType().isPrimitive()) && (!it.getType().getActualTypeArguments().isEmpty())));
    };
    Iterable<FieldDeclaration> _filter = IterableExtensions.<FieldDeclaration>filter(targetFields, _function);
    for (final FieldDeclaration field : _filter) {
      String _upperCase = field.getSimpleName().toUpperCase();
      String _plus = (_upperCase + "_TYPE_TOKEN");
      final Procedure1<MutableFieldDeclaration> _function_1 = (MutableFieldDeclaration it) -> {
        it.setFinal(true);
        it.setStatic(true);
        it.setType(context.newTypeReference("com.google.gson.reflect.TypeToken", field.getType()));
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append("new TypeToken<");
            TypeReference _type = field.getType();
            _builder.append(_type);
            _builder.append(">() {}");
          }
        };
        it.setInitializer(_client);
      };
      impl.addField(_plus, _function_1);
    }
    impl.setExtendedClass(context.newTypeReference("com.google.gson.TypeAdapter", targetType));
    final Procedure1<MutableFieldDeclaration> _function_2 = (MutableFieldDeclaration it) -> {
      it.setType(context.newTypeReference("com.google.gson.Gson"));
      it.setFinal(true);
    };
    impl.addField("gson", _function_2);
    final Procedure1<MutableConstructorDeclaration> _function_3 = (MutableConstructorDeclaration it) -> {
      it.addParameter("gson", context.newTypeReference("com.google.gson.Gson"));
      StringConcatenationClient _client = new StringConcatenationClient() {
        @Override
        protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
          _builder.append("this.gson = gson;");
          _builder.newLine();
        }
      };
      it.setBody(_client);
    };
    impl.addConstructor(_function_3);
    final Procedure1<MutableMethodDeclaration> _function_4 = (MutableMethodDeclaration method) -> {
      method.addParameter("in", context.newTypeReference("com.google.gson.stream.JsonReader"));
      method.setExceptions(context.newTypeReference(IOException.class));
      method.setReturnType(targetType);
      StringConcatenationClient _client = new StringConcatenationClient() {
        @Override
        protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
          TypeReference _newTypeReference = context.newTypeReference("com.google.gson.stream.JsonToken");
          _builder.append(_newTypeReference);
          _builder.append(" nextToken = in.peek();");
          _builder.newLineIfNotEmpty();
          _builder.append("if (nextToken == JsonToken.NULL) {");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("return null;");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
          _builder.newLine();
          _builder.append(targetType);
          _builder.append(" result = new ");
          _builder.append(targetType);
          _builder.append("();");
          _builder.newLineIfNotEmpty();
          _builder.append("in.beginObject();");
          _builder.newLine();
          _builder.append("while (in.hasNext()) {");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("String name = in.nextName();");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("switch (name) {");
          _builder.newLine();
          {
            for(final FieldDeclaration field : targetFields) {
              _builder.append("\t");
              _builder.append("case \"");
              String _simpleName = field.getSimpleName();
              _builder.append(_simpleName, "\t");
              _builder.append("\":");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("result.set");
              String _firstUpper = StringExtensions.toFirstUpper(field.getSimpleName());
              _builder.append(_firstUpper, "\t\t");
              _builder.append("(read");
              String _firstUpper_1 = StringExtensions.toFirstUpper(field.getSimpleName());
              _builder.append(_firstUpper_1, "\t\t");
              _builder.append("(in));");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("break;");
              _builder.newLine();
            }
          }
          _builder.append("\t");
          _builder.append("default:");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("in.skipValue();");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
          _builder.append("in.endObject();");
          _builder.newLine();
          _builder.append("return result;");
          _builder.newLine();
        }
      };
      method.setBody(_client);
    };
    impl.addMethod("read", _function_4);
    for (final FieldDeclaration field_1 : targetFields) {
      {
        String _firstUpper = StringExtensions.toFirstUpper(field_1.getSimpleName());
        String _plus_1 = ("read" + _firstUpper);
        final MutableMethodDeclaration existingMethod = impl.findDeclaredMethod(_plus_1, 
          context.newTypeReference("com.google.gson.stream.JsonReader"));
        if ((existingMethod == null)) {
          String _firstUpper_1 = StringExtensions.toFirstUpper(field_1.getSimpleName());
          String _plus_2 = ("read" + _firstUpper_1);
          final Procedure1<MutableMethodDeclaration> _function_5 = (MutableMethodDeclaration it) -> {
            it.setVisibility(Visibility.PROTECTED);
            it.addParameter("in", context.newTypeReference("com.google.gson.stream.JsonReader"));
            it.setExceptions(context.newTypeReference(IOException.class));
            it.setReturnType(field_1.getType());
            boolean _isPrimitive = field_1.getType().isPrimitive();
            if (_isPrimitive) {
              String _simpleName = field_1.getType().getSimpleName();
              if (_simpleName != null) {
                switch (_simpleName) {
                  case "boolean":
                    StringConcatenationClient _client = new StringConcatenationClient() {
                      @Override
                      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                        _builder.append("return in.nextBoolean();");
                      }
                    };
                    it.setBody(_client);
                    break;
                  case "double":
                    StringConcatenationClient _client_1 = new StringConcatenationClient() {
                      @Override
                      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                        _builder.append("return in.nextDouble();");
                      }
                    };
                    it.setBody(_client_1);
                    break;
                  case "float":
                    StringConcatenationClient _client_2 = new StringConcatenationClient() {
                      @Override
                      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                        _builder.append("return (float) in.nextDouble();");
                      }
                    };
                    it.setBody(_client_2);
                    break;
                  case "long":
                    StringConcatenationClient _client_3 = new StringConcatenationClient() {
                      @Override
                      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                        _builder.append("return in.nextLong();");
                      }
                    };
                    it.setBody(_client_3);
                    break;
                  case "int":
                    StringConcatenationClient _client_4 = new StringConcatenationClient() {
                      @Override
                      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                        _builder.append("return in.nextInt();");
                      }
                    };
                    it.setBody(_client_4);
                    break;
                  case "short":
                  case "byte":
                  case "char":
                    StringConcatenationClient _client_5 = new StringConcatenationClient() {
                      @Override
                      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                        _builder.append("return (");
                        TypeReference _type = field_1.getType();
                        _builder.append(_type);
                        _builder.append(") in.nextInt();");
                      }
                    };
                    it.setBody(_client_5);
                    break;
                }
              }
            } else {
              boolean _isEmpty = field_1.getType().getActualTypeArguments().isEmpty();
              boolean _not = (!_isEmpty);
              if (_not) {
                StringConcatenationClient _client_6 = new StringConcatenationClient() {
                  @Override
                  protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                    _builder.append("return gson.fromJson(in, ");
                    String _upperCase = field_1.getSimpleName().toUpperCase();
                    _builder.append(_upperCase);
                    _builder.append("_TYPE_TOKEN.getType());");
                  }
                };
                it.setBody(_client_6);
              } else {
                StringConcatenationClient _client_7 = new StringConcatenationClient() {
                  @Override
                  protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                    _builder.append("return gson.fromJson(in, ");
                    TypeReference _type = field_1.getType();
                    _builder.append(_type);
                    _builder.append(".class);");
                  }
                };
                it.setBody(_client_7);
              }
            }
          };
          impl.addMethod(_plus_2, _function_5);
        }
      }
    }
    final Procedure1<MutableMethodDeclaration> _function_5 = (MutableMethodDeclaration method) -> {
      method.addParameter("out", context.newTypeReference("com.google.gson.stream.JsonWriter"));
      method.addParameter("value", targetType);
      method.setExceptions(context.newTypeReference(IOException.class));
      StringConcatenationClient _client = new StringConcatenationClient() {
        @Override
        protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
          _builder.append("if (value == null) {");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("out.nullValue();");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("return;");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
          _builder.newLine();
          _builder.append("out.beginObject();");
          _builder.newLine();
          {
            for(final FieldDeclaration field : targetFields) {
              _builder.append("out.name(\"");
              String _simpleName = field.getSimpleName();
              _builder.append(_simpleName);
              _builder.append("\");");
              _builder.newLineIfNotEmpty();
              _builder.append("write");
              String _firstUpper = StringExtensions.toFirstUpper(field.getSimpleName());
              _builder.append(_firstUpper);
              _builder.append("(out, value.get");
              String _firstUpper_1 = StringExtensions.toFirstUpper(field.getSimpleName());
              _builder.append(_firstUpper_1);
              _builder.append("());");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("out.endObject();");
          _builder.newLine();
        }
      };
      method.setBody(_client);
    };
    impl.addMethod("write", _function_5);
    for (final FieldDeclaration field_2 : targetFields) {
      {
        String _firstUpper = StringExtensions.toFirstUpper(field_2.getSimpleName());
        String _plus_1 = ("write" + _firstUpper);
        final MutableMethodDeclaration existingMethod = impl.findDeclaredMethod(_plus_1, 
          context.newTypeReference("com.google.gson.stream.JsonWriter"), field_2.getType());
        if ((existingMethod == null)) {
          String _firstUpper_1 = StringExtensions.toFirstUpper(field_2.getSimpleName());
          String _plus_2 = ("write" + _firstUpper_1);
          final Procedure1<MutableMethodDeclaration> _function_6 = (MutableMethodDeclaration it) -> {
            it.setVisibility(Visibility.PROTECTED);
            it.addParameter("out", context.newTypeReference("com.google.gson.stream.JsonWriter"));
            it.addParameter("value", field_2.getType());
            it.setExceptions(context.newTypeReference(IOException.class));
            boolean _isPrimitive = field_2.getType().isPrimitive();
            if (_isPrimitive) {
              StringConcatenationClient _client = new StringConcatenationClient() {
                @Override
                protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                  _builder.append("out.value(value);");
                  _builder.newLine();
                }
              };
              it.setBody(_client);
            } else {
              boolean _isEmpty = field_2.getType().getActualTypeArguments().isEmpty();
              boolean _not = (!_isEmpty);
              if (_not) {
                StringConcatenationClient _client_1 = new StringConcatenationClient() {
                  @Override
                  protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                    _builder.append("gson.toJson(value, ");
                    String _upperCase = field_2.getSimpleName().toUpperCase();
                    _builder.append(_upperCase);
                    _builder.append("_TYPE_TOKEN.getType(), out);");
                    _builder.newLineIfNotEmpty();
                  }
                };
                it.setBody(_client_1);
              } else {
                TypeReference _type = field_2.getType();
                TypeReference _newTypeReference = context.newTypeReference(Object.class);
                boolean _equals = Objects.equal(_type, _newTypeReference);
                if (_equals) {
                  StringConcatenationClient _client_2 = new StringConcatenationClient() {
                    @Override
                    protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                      _builder.append("if (value == null)");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.append("out.nullValue();");
                      _builder.newLine();
                      _builder.append("else");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.append("gson.toJson(value, value.getClass(), out);");
                      _builder.newLine();
                    }
                  };
                  it.setBody(_client_2);
                } else {
                  StringConcatenationClient _client_3 = new StringConcatenationClient() {
                    @Override
                    protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                      _builder.append("gson.toJson(value, ");
                      TypeReference _type = field_2.getType();
                      _builder.append(_type);
                      _builder.append(".class, out);");
                      _builder.newLineIfNotEmpty();
                    }
                  };
                  it.setBody(_client_3);
                }
              }
            }
          };
          impl.addMethod(_plus_2, _function_6);
        }
      }
    }
    return impl;
  }
  
  protected MutableMethodDeclaration generateFactory(final MutableClassDeclaration factory, final MutableClassDeclaration impl, final TypeReference targetType, @Extension final TransformationContext context) {
    MutableMethodDeclaration _xblockexpression = null;
    {
      TypeReference _newTypeReference = context.newTypeReference("com.google.gson.TypeAdapterFactory");
      factory.setImplementedInterfaces(Collections.<TypeReference>unmodifiableList(CollectionLiterals.<TypeReference>newArrayList(_newTypeReference)));
      final Procedure1<MutableMethodDeclaration> _function = (MutableMethodDeclaration it) -> {
        final MutableTypeParameterDeclaration t = it.addTypeParameter("T");
        it.addParameter("gson", context.newTypeReference("com.google.gson.Gson"));
        it.addParameter("typeToken", context.newTypeReference("com.google.gson.reflect.TypeToken", context.newTypeReference(t)));
        it.setReturnType(context.newTypeReference("com.google.gson.TypeAdapter", context.newTypeReference(t)));
        StringConcatenationClient _client = new StringConcatenationClient() {
          @Override
          protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
            _builder.append("if (!");
            _builder.append(targetType);
            _builder.append(".class.isAssignableFrom(typeToken.getRawType())) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("return null;");
            _builder.newLine();
            _builder.append("}");
            _builder.newLine();
            _builder.append("return (TypeAdapter<T>) new ");
            _builder.append(impl);
            _builder.append("(gson);");
            _builder.newLineIfNotEmpty();
          }
        };
        it.setBody(_client);
      };
      _xblockexpression = factory.addMethod("create", _function);
    }
    return _xblockexpression;
  }
  
  private ArrayList<FieldDeclaration> getTargetFields(final TypeReference targetType, @Extension final TransformationContext context) {
    final Type objectType = context.newTypeReference(Object.class).getType();
    final ArrayList<FieldDeclaration> targetFields = CollectionLiterals.<FieldDeclaration>newArrayList();
    TypeReference typeRef = targetType;
    while ((!Objects.equal(typeRef.getType(), objectType))) {
      {
        Type _type = typeRef.getType();
        final ClassDeclaration clazz = ((ClassDeclaration) _type);
        final Function1<FieldDeclaration, Boolean> _function = (FieldDeclaration it) -> {
          boolean _isStatic = it.isStatic();
          return Boolean.valueOf((!_isStatic));
        };
        Iterable<? extends FieldDeclaration> _filter = IterableExtensions.filter(clazz.getDeclaredFields(), _function);
        Iterables.<FieldDeclaration>addAll(targetFields, _filter);
        typeRef = clazz.getExtendedClass();
      }
    }
    return targetFields;
  }
}
