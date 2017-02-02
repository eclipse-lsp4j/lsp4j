/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.test;

import com.google.common.base.Objects;
import java.lang.annotation.Annotation;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.generator.JsonRpcData;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class NoAnnotationTest {
  @Test
  public void testNoAnnotation() {
    final Function1<Annotation, Boolean> _function = (Annotation it) -> {
      Class<? extends Annotation> _annotationType = it.annotationType();
      return Boolean.valueOf(Objects.equal(_annotationType, JsonRpcData.class));
    };
    Assert.assertFalse(IterableExtensions.<Annotation>exists(((Iterable<Annotation>)Conversions.doWrapArray(CodeLens.class.getAnnotations())), _function));
  }
}
