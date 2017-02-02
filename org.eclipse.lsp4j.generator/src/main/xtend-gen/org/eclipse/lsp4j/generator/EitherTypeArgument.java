package org.eclipse.lsp4j.generator;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtext.xbase.lib.Pure;

@Accessors
@FinalFieldsConstructor
@SuppressWarnings("all")
public class EitherTypeArgument {
  private final TypeReference type;
  
  private final EitherTypeArgument parent;
  
  private final boolean right;
  
  public EitherTypeArgument(final TypeReference type, final EitherTypeArgument parent, final boolean right) {
    super();
    this.type = type;
    this.parent = parent;
    this.right = right;
  }
  
  @Pure
  public TypeReference getType() {
    return this.type;
  }
  
  @Pure
  public EitherTypeArgument getParent() {
    return this.parent;
  }
  
  @Pure
  public boolean isRight() {
    return this.right;
  }
}
