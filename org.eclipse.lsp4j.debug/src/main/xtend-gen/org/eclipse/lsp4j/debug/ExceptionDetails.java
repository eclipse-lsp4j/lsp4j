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

import java.util.Arrays;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Detailed information about an exception that has occurred.
 */
@SuppressWarnings("all")
public class ExceptionDetails {
  /**
   * Message contained in the exception.
   * <p>
   * This is an optional property.
   */
  private String message;
  
  /**
   * Short type name of the exception object.
   * <p>
   * This is an optional property.
   */
  private String typeName;
  
  /**
   * Fully-qualified type name of the exception object.
   * <p>
   * This is an optional property.
   */
  private String fullTypeName;
  
  /**
   * Optional expression that can be evaluated in the current scope to obtain the exception object.
   * <p>
   * This is an optional property.
   */
  private String evaluateName;
  
  /**
   * Stack trace at the time the exception was thrown.
   * <p>
   * This is an optional property.
   */
  private String stackTrace;
  
  /**
   * Details of the exception contained by this exception, if any.
   * <p>
   * This is an optional property.
   */
  private ExceptionDetails[] innerException;
  
  /**
   * Message contained in the exception.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getMessage() {
    return this.message;
  }
  
  /**
   * Message contained in the exception.
   * <p>
   * This is an optional property.
   */
  public void setMessage(final String message) {
    this.message = message;
  }
  
  /**
   * Short type name of the exception object.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getTypeName() {
    return this.typeName;
  }
  
  /**
   * Short type name of the exception object.
   * <p>
   * This is an optional property.
   */
  public void setTypeName(final String typeName) {
    this.typeName = typeName;
  }
  
  /**
   * Fully-qualified type name of the exception object.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getFullTypeName() {
    return this.fullTypeName;
  }
  
  /**
   * Fully-qualified type name of the exception object.
   * <p>
   * This is an optional property.
   */
  public void setFullTypeName(final String fullTypeName) {
    this.fullTypeName = fullTypeName;
  }
  
  /**
   * Optional expression that can be evaluated in the current scope to obtain the exception object.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getEvaluateName() {
    return this.evaluateName;
  }
  
  /**
   * Optional expression that can be evaluated in the current scope to obtain the exception object.
   * <p>
   * This is an optional property.
   */
  public void setEvaluateName(final String evaluateName) {
    this.evaluateName = evaluateName;
  }
  
  /**
   * Stack trace at the time the exception was thrown.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getStackTrace() {
    return this.stackTrace;
  }
  
  /**
   * Stack trace at the time the exception was thrown.
   * <p>
   * This is an optional property.
   */
  public void setStackTrace(final String stackTrace) {
    this.stackTrace = stackTrace;
  }
  
  /**
   * Details of the exception contained by this exception, if any.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ExceptionDetails[] getInnerException() {
    return this.innerException;
  }
  
  /**
   * Details of the exception contained by this exception, if any.
   * <p>
   * This is an optional property.
   */
  public void setInnerException(final ExceptionDetails[] innerException) {
    this.innerException = innerException;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("message", this.message);
    b.add("typeName", this.typeName);
    b.add("fullTypeName", this.fullTypeName);
    b.add("evaluateName", this.evaluateName);
    b.add("stackTrace", this.stackTrace);
    b.add("innerException", this.innerException);
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
    ExceptionDetails other = (ExceptionDetails) obj;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
      return false;
    if (this.typeName == null) {
      if (other.typeName != null)
        return false;
    } else if (!this.typeName.equals(other.typeName))
      return false;
    if (this.fullTypeName == null) {
      if (other.fullTypeName != null)
        return false;
    } else if (!this.fullTypeName.equals(other.fullTypeName))
      return false;
    if (this.evaluateName == null) {
      if (other.evaluateName != null)
        return false;
    } else if (!this.evaluateName.equals(other.evaluateName))
      return false;
    if (this.stackTrace == null) {
      if (other.stackTrace != null)
        return false;
    } else if (!this.stackTrace.equals(other.stackTrace))
      return false;
    if (this.innerException == null) {
      if (other.innerException != null)
        return false;
    } else if (!Arrays.deepEquals(this.innerException, other.innerException))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.message== null) ? 0 : this.message.hashCode());
    result = prime * result + ((this.typeName== null) ? 0 : this.typeName.hashCode());
    result = prime * result + ((this.fullTypeName== null) ? 0 : this.fullTypeName.hashCode());
    result = prime * result + ((this.evaluateName== null) ? 0 : this.evaluateName.hashCode());
    result = prime * result + ((this.stackTrace== null) ? 0 : this.stackTrace.hashCode());
    return prime * result + ((this.innerException== null) ? 0 : Arrays.deepHashCode(this.innerException));
  }
}
