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

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.lsp4j.generator.EitherTypeArgument;
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
import org.eclipse.xtend.lib.macro.declaration.CompilationStrategy;
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableFieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableParameterDeclaration;
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
  @Override
  public void doTransform(final MutableClassDeclaration annotatedClass, final TransformationContext context) {
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
    this.generateToString(impl, context);
    Type _type = impl.getExtendedClass().getType();
    Type _type_1 = context.newTypeReference(Object.class).getType();
    final boolean shouldIncludeSuper = (!Objects.equal(_type, _type_1));
    final EqualsHashCodeProcessor.Util equalsHashCodeUtil = new EqualsHashCodeProcessor.Util(context);
    final Function1<MutableFieldDeclaration, Boolean> _function_1 = (MutableFieldDeclaration it) -> {
      boolean _isStatic = it.isStatic();
      return Boolean.valueOf((!_isStatic));
    };
    final Iterable<? extends MutableFieldDeclaration> fields = IterableExtensions.filter(impl.getDeclaredFields(), _function_1);
    equalsHashCodeUtil.addEquals(impl, fields, shouldIncludeSuper);
    equalsHashCodeUtil.addHashCode(impl, fields, shouldIncludeSuper);
    return impl;
  }
  
  protected void generateImplMembers(final MutableClassDeclaration impl, @Extension final JsonRpcDataTransformationContext context) {
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
            final MutableParameterDeclaration parameter = IterableExtensions.head(it.getParameters());
            parameter.addAnnotation(context.newAnnotationReference(NonNull.class));
            StringConcatenationClient _client = new StringConcatenationClient() {
              @Override
              protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                _builder.append("this.");
                String _simpleName = field.getSimpleName();
                _builder.append(_simpleName);
                _builder.append(" = ");
                TypeReference _preconditionsUtil = JsonRpcDataProcessor.this.getPreconditionsUtil(impl, context);
                _builder.append(_preconditionsUtil);
                _builder.append(".checkNotNull(");
                String _simpleName_1 = parameter.getSimpleName();
                _builder.append(_simpleName_1);
                _builder.append(", \"");
                String _simpleName_2 = field.getSimpleName();
                _builder.append(_simpleName_2);
                _builder.append("\");");
                _builder.newLineIfNotEmpty();
              }
            };
            it.setBody(_client);
          }
          if ((deprecated != null)) {
            it.addAnnotation(context.newAnnotationReference(Deprecated.class));
          }
        };
        ObjectExtensions.<MutableMethodDeclaration>operator_doubleArrow(_findDeclaredMethod_1, _function_3);
        final Collection<EitherTypeArgument> childTypes = context.getChildTypes(field.getType());
        boolean _isEmpty = childTypes.isEmpty();
        boolean _not_1 = (!_isEmpty);
        if (_not_1) {
          final Function1<EitherTypeArgument, JsonType> _function_4 = (EitherTypeArgument it) -> {
            return context.getJsonType(it.getType());
          };
          final List<JsonType> jsonTypes = IterableExtensions.<JsonType>toList(IterableExtensions.<EitherTypeArgument, JsonType>map(childTypes, _function_4));
          int _size = jsonTypes.size();
          int _size_1 = IterableExtensions.<JsonType>toSet(jsonTypes).size();
          boolean _tripleNotEquals = (_size != _size_1);
          if (_tripleNotEquals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("The json types of an Either must be distinct.");
            context.addWarning(field, _builder.toString());
          } else {
            for (final EitherTypeArgument childType : childTypes) {
              this.addEitherSetter(field, setterName, childType, context);
            }
          }
        }
      }
    };
    IterableExtensions.filter(impl.getDeclaredFields(), _function).forEach(_function_1);
  }
  
  protected void addEitherSetter(final MutableFieldDeclaration field, final String setterName, final EitherTypeArgument argument, @Extension final JsonRpcDataTransformationContext context) {
    final Procedure1<MutableMethodDeclaration> _function = (MutableMethodDeclaration method) -> {
      context.setPrimarySourceElement(method, context.getPrimarySourceElement(field));
      method.addParameter(field.getSimpleName(), argument.getType());
      method.setStatic(field.isStatic());
      method.setVisibility(Visibility.PUBLIC);
      method.setReturnType(context.getPrimitiveVoid());
      final CompilationStrategy _function_1 = (CompilationStrategy.CompilationContext ctx) -> {
        return this.compileEitherSetterBody(field, argument, field.getSimpleName(), ctx, context);
      };
      method.setBody(_function_1);
    };
    field.getDeclaringType().addMethod(setterName, _function);
  }
  
  protected CharSequence compileEitherSetterBody(final MutableFieldDeclaration field, final EitherTypeArgument argument, final String variableName, @Extension final CompilationStrategy.CompilationContext compilationContext, @Extension final JsonRpcDataTransformationContext context) {
    CharSequence _xblockexpression = null;
    {
      AnnotationReference _findAnnotation = field.findAnnotation(context.newTypeReference(NonNull.class).getType());
      final boolean hasNonNull = (_findAnnotation != null);
      final String newVariableName = ("_" + variableName);
      StringConcatenation _builder = new StringConcatenation();
      String _javaCode = compilationContext.toJavaCode(context.getEitherType());
      _builder.append(_javaCode);
      _builder.append(".for");
      {
        boolean _isRight = argument.isRight();
        if (_isRight) {
          _builder.append("Right");
        } else {
          _builder.append("Left");
        }
      }
      _builder.append("(");
      _builder.append(variableName);
      _builder.append(")");
      final CharSequence compileNewEither = _builder;
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("if (");
      _builder_1.append(variableName);
      _builder_1.append(" == null) {");
      _builder_1.newLineIfNotEmpty();
      {
        if (hasNonNull) {
          _builder_1.append("  ");
          TypeReference _preconditionsUtil = this.getPreconditionsUtil(field.getDeclaringType(), context);
          _builder_1.append(_preconditionsUtil, "  ");
          _builder_1.append(".checkNotNull(");
          _builder_1.append(variableName, "  ");
          _builder_1.append(", \"");
          String _simpleName = field.getSimpleName();
          _builder_1.append(_simpleName, "  ");
          _builder_1.append("\");");
          _builder_1.newLineIfNotEmpty();
        }
      }
      _builder_1.append("  ");
      _builder_1.append("this.");
      String _simpleName_1 = field.getSimpleName();
      _builder_1.append(_simpleName_1, "  ");
      _builder_1.append(" = null;");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("  ");
      _builder_1.append("return;");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      {
        EitherTypeArgument _parent = argument.getParent();
        boolean _tripleNotEquals = (_parent != null);
        if (_tripleNotEquals) {
          _builder_1.append("final ");
          String _javaCode_1 = compilationContext.toJavaCode(argument.getParent().getType());
          _builder_1.append(_javaCode_1);
          _builder_1.append(" ");
          _builder_1.append(newVariableName);
          _builder_1.append(" = ");
          _builder_1.append(compileNewEither);
          _builder_1.append(";");
          _builder_1.newLineIfNotEmpty();
          CharSequence _compileEitherSetterBody = this.compileEitherSetterBody(field, argument.getParent(), newVariableName, compilationContext, context);
          _builder_1.append(_compileEitherSetterBody);
          _builder_1.newLineIfNotEmpty();
        } else {
          _builder_1.append("this.");
          String _simpleName_2 = field.getSimpleName();
          _builder_1.append(_simpleName_2);
          _builder_1.append(" = ");
          _builder_1.append(compileNewEither);
          _builder_1.append(";");
          _builder_1.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }
  
  protected MutableMethodDeclaration generateToString(final MutableClassDeclaration impl, @Extension final TransformationContext context) {
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
  
  private TypeReference getPreconditionsUtil(final Type type, @Extension final TransformationContext context) {
    TypeReference _xifexpression = null;
    boolean _startsWith = type.getQualifiedName().startsWith("org.eclipse.lsp4j.debug");
    if (_startsWith) {
      _xifexpression = context.newTypeReference("org.eclipse.lsp4j.debug.util.Preconditions");
    } else {
      _xifexpression = context.newTypeReference("org.eclipse.lsp4j.util.Preconditions");
    }
    return _xifexpression;
  }
}
