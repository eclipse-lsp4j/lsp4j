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

import org.eclipse.lsp4j.debug.VariablePresentationHint;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A Variable is a name/value pair.
 * <p>
 * Optionally a variable can have a 'type' that is shown if space permits or when hovering over the variable's
 * name.
 * <p>
 * An optional 'kind' is used to render additional properties of the variable, e.g. different icons can be used to
 * indicate that a variable is public or private.
 * <p>
 * If the value is structured (has children), a handle is provided to retrieve the children with the
 * VariablesRequest.
 * <p>
 * If the number of named or indexed children is large, the numbers should be returned via the optional
 * 'namedVariables' and 'indexedVariables' attributes.
 * <p>
 * The client can use this optional information to present the children in a paged UI and fetch them in chunks.
 */
@SuppressWarnings("all")
public class Variable {
  /**
   * The variable's name.
   */
  @NonNull
  private String name;
  
  /**
   * The variable's value. This can be a multi-line text, e.g. for a function the body of a function.
   */
  @NonNull
  private String value;
  
  /**
   * The type of the variable's value. Typically shown in the UI when hovering over the value.
   * <p>
   * This attribute should only be returned by a debug adapter if the client has passed the value true for the
   * 'supportsVariableType' capability of the 'initialize' request.
   * <p>
   * This is an optional property.
   */
  private String type;
  
  /**
   * Properties of a variable that can be used to determine how to render the variable in the UI.
   * <p>
   * This is an optional property.
   */
  private VariablePresentationHint presentationHint;
  
  /**
   * Optional evaluatable name of this variable which can be passed to the 'EvaluateRequest' to fetch the variable's
   * value.
   * <p>
   * This is an optional property.
   */
  private String evaluateName;
  
  /**
   * If variablesReference is > 0, the variable is structured and its children can be retrieved by passing
   * variablesReference to the VariablesRequest.
   */
  private int variablesReference;
  
  /**
   * The number of named child variables.
   * <p>
   * The client can use this optional information to present the children in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  private Integer namedVariables;
  
  /**
   * The number of indexed child variables.
   * <p>
   * The client can use this optional information to present the children in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  private Integer indexedVariables;
  
  /**
   * Optional memory reference for the variable if the variable represents executable code, such as a function
   * pointer.
   * <p>
   * This attribute is only required if the client has passed the value true for the 'supportsMemoryReferences'
   * capability of the 'initialize' request.
   * <p>
   * This is an optional property.
   */
  private String memoryReference;
  
  /**
   * The variable's name.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * The variable's name.
   */
  public void setName(@NonNull final String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }
  
  /**
   * The variable's value. This can be a multi-line text, e.g. for a function the body of a function.
   */
  @Pure
  @NonNull
  public String getValue() {
    return this.value;
  }
  
  /**
   * The variable's value. This can be a multi-line text, e.g. for a function the body of a function.
   */
  public void setValue(@NonNull final String value) {
    this.value = Preconditions.checkNotNull(value, "value");
  }
  
  /**
   * The type of the variable's value. Typically shown in the UI when hovering over the value.
   * <p>
   * This attribute should only be returned by a debug adapter if the client has passed the value true for the
   * 'supportsVariableType' capability of the 'initialize' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getType() {
    return this.type;
  }
  
  /**
   * The type of the variable's value. Typically shown in the UI when hovering over the value.
   * <p>
   * This attribute should only be returned by a debug adapter if the client has passed the value true for the
   * 'supportsVariableType' capability of the 'initialize' request.
   * <p>
   * This is an optional property.
   */
  public void setType(final String type) {
    this.type = type;
  }
  
  /**
   * Properties of a variable that can be used to determine how to render the variable in the UI.
   * <p>
   * This is an optional property.
   */
  @Pure
  public VariablePresentationHint getPresentationHint() {
    return this.presentationHint;
  }
  
  /**
   * Properties of a variable that can be used to determine how to render the variable in the UI.
   * <p>
   * This is an optional property.
   */
  public void setPresentationHint(final VariablePresentationHint presentationHint) {
    this.presentationHint = presentationHint;
  }
  
  /**
   * Optional evaluatable name of this variable which can be passed to the 'EvaluateRequest' to fetch the variable's
   * value.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getEvaluateName() {
    return this.evaluateName;
  }
  
  /**
   * Optional evaluatable name of this variable which can be passed to the 'EvaluateRequest' to fetch the variable's
   * value.
   * <p>
   * This is an optional property.
   */
  public void setEvaluateName(final String evaluateName) {
    this.evaluateName = evaluateName;
  }
  
  /**
   * If variablesReference is > 0, the variable is structured and its children can be retrieved by passing
   * variablesReference to the VariablesRequest.
   */
  @Pure
  public int getVariablesReference() {
    return this.variablesReference;
  }
  
  /**
   * If variablesReference is > 0, the variable is structured and its children can be retrieved by passing
   * variablesReference to the VariablesRequest.
   */
  public void setVariablesReference(final int variablesReference) {
    this.variablesReference = variablesReference;
  }
  
  /**
   * The number of named child variables.
   * <p>
   * The client can use this optional information to present the children in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getNamedVariables() {
    return this.namedVariables;
  }
  
  /**
   * The number of named child variables.
   * <p>
   * The client can use this optional information to present the children in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  public void setNamedVariables(final Integer namedVariables) {
    this.namedVariables = namedVariables;
  }
  
  /**
   * The number of indexed child variables.
   * <p>
   * The client can use this optional information to present the children in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getIndexedVariables() {
    return this.indexedVariables;
  }
  
  /**
   * The number of indexed child variables.
   * <p>
   * The client can use this optional information to present the children in a paged UI and fetch them in chunks.
   * <p>
   * This is an optional property.
   */
  public void setIndexedVariables(final Integer indexedVariables) {
    this.indexedVariables = indexedVariables;
  }
  
  /**
   * Optional memory reference for the variable if the variable represents executable code, such as a function
   * pointer.
   * <p>
   * This attribute is only required if the client has passed the value true for the 'supportsMemoryReferences'
   * capability of the 'initialize' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getMemoryReference() {
    return this.memoryReference;
  }
  
  /**
   * Optional memory reference for the variable if the variable represents executable code, such as a function
   * pointer.
   * <p>
   * This attribute is only required if the client has passed the value true for the 'supportsMemoryReferences'
   * capability of the 'initialize' request.
   * <p>
   * This is an optional property.
   */
  public void setMemoryReference(final String memoryReference) {
    this.memoryReference = memoryReference;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("value", this.value);
    b.add("type", this.type);
    b.add("presentationHint", this.presentationHint);
    b.add("evaluateName", this.evaluateName);
    b.add("variablesReference", this.variablesReference);
    b.add("namedVariables", this.namedVariables);
    b.add("indexedVariables", this.indexedVariables);
    b.add("memoryReference", this.memoryReference);
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
    Variable other = (Variable) obj;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.value == null) {
      if (other.value != null)
        return false;
    } else if (!this.value.equals(other.value))
      return false;
    if (this.type == null) {
      if (other.type != null)
        return false;
    } else if (!this.type.equals(other.type))
      return false;
    if (this.presentationHint == null) {
      if (other.presentationHint != null)
        return false;
    } else if (!this.presentationHint.equals(other.presentationHint))
      return false;
    if (this.evaluateName == null) {
      if (other.evaluateName != null)
        return false;
    } else if (!this.evaluateName.equals(other.evaluateName))
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
    if (this.memoryReference == null) {
      if (other.memoryReference != null)
        return false;
    } else if (!this.memoryReference.equals(other.memoryReference))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.value== null) ? 0 : this.value.hashCode());
    result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
    result = prime * result + ((this.presentationHint== null) ? 0 : this.presentationHint.hashCode());
    result = prime * result + ((this.evaluateName== null) ? 0 : this.evaluateName.hashCode());
    result = prime * result + this.variablesReference;
    result = prime * result + ((this.namedVariables== null) ? 0 : this.namedVariables.hashCode());
    result = prime * result + ((this.indexedVariables== null) ? 0 : this.indexedVariables.hashCode());
    return prime * result + ((this.memoryReference== null) ? 0 : this.memoryReference.hashCode());
  }
}
