/**
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.debug.Source;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A Scope is a named container for variables. Optionally a scope can map to a source or a range within a source.
 */
@SuppressWarnings("all")
public class Scope {
  /**
   * Name of the scope such as 'Arguments', 'Locals', or 'Registers'. This string is shown in the UI as is and can
   * be translated.
   */
  @NonNull
  private String name;
  
  /**
   * An optional hint for how to present this scope in the UI. If this attribute is missing, the scope is shown with
   * a generic UI.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link ScopePresentationHint}
   */
  private String presentationHint;
  
  /**
   * The variables of this scope can be retrieved by passing the value of variablesReference to the
   * VariablesRequest.
   */
  private int variablesReference;
  
  /**
   * The number of named variables in this scope.
   * <p>
   * The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  private Integer namedVariables;
  
  /**
   * The number of indexed variables in this scope.
   * <p>
   * The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  private Integer indexedVariables;
  
  /**
   * If true, the number of variables in this scope is large or expensive to retrieve.
   */
  private boolean expensive;
  
  /**
   * Optional source for this scope.
   * <p>
   * This is an optional property.
   */
  private Source source;
  
  /**
   * Optional start line of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  private Integer line;
  
  /**
   * Optional start column of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  private Integer column;
  
  /**
   * Optional end line of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  private Integer endLine;
  
  /**
   * Optional end column of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  private Integer endColumn;
  
  /**
   * Name of the scope such as 'Arguments', 'Locals', or 'Registers'. This string is shown in the UI as is and can
   * be translated.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * Name of the scope such as 'Arguments', 'Locals', or 'Registers'. This string is shown in the UI as is and can
   * be translated.
   */
  public void setName(@NonNull final String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }
  
  /**
   * An optional hint for how to present this scope in the UI. If this attribute is missing, the scope is shown with
   * a generic UI.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link ScopePresentationHint}
   */
  @Pure
  public String getPresentationHint() {
    return this.presentationHint;
  }
  
  /**
   * An optional hint for how to present this scope in the UI. If this attribute is missing, the scope is shown with
   * a generic UI.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link ScopePresentationHint}
   */
  public void setPresentationHint(final String presentationHint) {
    this.presentationHint = presentationHint;
  }
  
  /**
   * The variables of this scope can be retrieved by passing the value of variablesReference to the
   * VariablesRequest.
   */
  @Pure
  public int getVariablesReference() {
    return this.variablesReference;
  }
  
  /**
   * The variables of this scope can be retrieved by passing the value of variablesReference to the
   * VariablesRequest.
   */
  public void setVariablesReference(final int variablesReference) {
    this.variablesReference = variablesReference;
  }
  
  /**
   * The number of named variables in this scope.
   * <p>
   * The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getNamedVariables() {
    return this.namedVariables;
  }
  
  /**
   * The number of named variables in this scope.
   * <p>
   * The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  public void setNamedVariables(final Integer namedVariables) {
    this.namedVariables = namedVariables;
  }
  
  /**
   * The number of indexed variables in this scope.
   * <p>
   * The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getIndexedVariables() {
    return this.indexedVariables;
  }
  
  /**
   * The number of indexed variables in this scope.
   * <p>
   * The client can use this optional information to present the variables in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  public void setIndexedVariables(final Integer indexedVariables) {
    this.indexedVariables = indexedVariables;
  }
  
  /**
   * If true, the number of variables in this scope is large or expensive to retrieve.
   */
  @Pure
  public boolean isExpensive() {
    return this.expensive;
  }
  
  /**
   * If true, the number of variables in this scope is large or expensive to retrieve.
   */
  public void setExpensive(final boolean expensive) {
    this.expensive = expensive;
  }
  
  /**
   * Optional source for this scope.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Source getSource() {
    return this.source;
  }
  
  /**
   * Optional source for this scope.
   * <p>
   * This is an optional property.
   */
  public void setSource(final Source source) {
    this.source = source;
  }
  
  /**
   * Optional start line of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getLine() {
    return this.line;
  }
  
  /**
   * Optional start line of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  public void setLine(final Integer line) {
    this.line = line;
  }
  
  /**
   * Optional start column of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getColumn() {
    return this.column;
  }
  
  /**
   * Optional start column of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  public void setColumn(final Integer column) {
    this.column = column;
  }
  
  /**
   * Optional end line of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndLine() {
    return this.endLine;
  }
  
  /**
   * Optional end line of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  public void setEndLine(final Integer endLine) {
    this.endLine = endLine;
  }
  
  /**
   * Optional end column of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndColumn() {
    return this.endColumn;
  }
  
  /**
   * Optional end column of the range covered by this scope.
   * <p>
   * This is an optional property.
   */
  public void setEndColumn(final Integer endColumn) {
    this.endColumn = endColumn;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("presentationHint", this.presentationHint);
    b.add("variablesReference", this.variablesReference);
    b.add("namedVariables", this.namedVariables);
    b.add("indexedVariables", this.indexedVariables);
    b.add("expensive", this.expensive);
    b.add("source", this.source);
    b.add("line", this.line);
    b.add("column", this.column);
    b.add("endLine", this.endLine);
    b.add("endColumn", this.endColumn);
    return b.toString();
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Scope other = (Scope) obj;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.presentationHint == null) {
      if (other.presentationHint != null)
        return false;
    } else if (!this.presentationHint.equals(other.presentationHint))
      return false;
    if (other.variablesReference != this.variablesReference)
      return false;
    if (this.namedVariables == null) {
      if (other.namedVariables != null)
        return false;
    } else if (!this.namedVariables.equals(other.namedVariables))
      return false;
    if (this.indexedVariables == null) {
      if (other.indexedVariables != null)
        return false;
    } else if (!this.indexedVariables.equals(other.indexedVariables))
      return false;
    if (other.expensive != this.expensive)
      return false;
    if (this.source == null) {
      if (other.source != null)
        return false;
    } else if (!this.source.equals(other.source))
      return false;
    if (this.line == null) {
      if (other.line != null)
        return false;
    } else if (!this.line.equals(other.line))
      return false;
    if (this.column == null) {
      if (other.column != null)
        return false;
    } else if (!this.column.equals(other.column))
      return false;
    if (this.endLine == null) {
      if (other.endLine != null)
        return false;
    } else if (!this.endLine.equals(other.endLine))
      return false;
    if (this.endColumn == null) {
      if (other.endColumn != null)
        return false;
    } else if (!this.endColumn.equals(other.endColumn))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.presentationHint== null) ? 0 : this.presentationHint.hashCode());
    result = prime * result + this.variablesReference;
    result = prime * result + ((this.namedVariables== null) ? 0 : this.namedVariables.hashCode());
    result = prime * result + ((this.indexedVariables== null) ? 0 : this.indexedVariables.hashCode());
    result = prime * result + (this.expensive ? 1231 : 1237);
    result = prime * result + ((this.source== null) ? 0 : this.source.hashCode());
    result = prime * result + ((this.line== null) ? 0 : this.line.hashCode());
    result = prime * result + ((this.column== null) ? 0 : this.column.hashCode());
    result = prime * result + ((this.endLine== null) ? 0 : this.endLine.hashCode());
    return prime * result + ((this.endColumn== null) ? 0 : this.endColumn.hashCode());
  }
}
