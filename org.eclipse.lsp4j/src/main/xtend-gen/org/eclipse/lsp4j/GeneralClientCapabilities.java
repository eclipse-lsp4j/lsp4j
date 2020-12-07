/**
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j;

import com.google.common.annotations.Beta;
import org.eclipse.lsp4j.MarkdownCapabilities;
import org.eclipse.lsp4j.RegularExpressionsCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * General client capabilities.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class GeneralClientCapabilities {
  /**
   * Client capabilities specific to regular expressions.
   * 
   * Since 3.16.0
   */
  private RegularExpressionsCapabilities regularExpressions;
  
  /**
   * Client capabilities specific to the client's markdown parser.
   * 
   * Since 3.16.0
   */
  private MarkdownCapabilities markdown;
  
  /**
   * Client capabilities specific to regular expressions.
   * 
   * Since 3.16.0
   */
  @Pure
  public RegularExpressionsCapabilities getRegularExpressions() {
    return this.regularExpressions;
  }
  
  /**
   * Client capabilities specific to regular expressions.
   * 
   * Since 3.16.0
   */
  public void setRegularExpressions(final RegularExpressionsCapabilities regularExpressions) {
    this.regularExpressions = regularExpressions;
  }
  
  /**
   * Client capabilities specific to the client's markdown parser.
   * 
   * Since 3.16.0
   */
  @Pure
  public MarkdownCapabilities getMarkdown() {
    return this.markdown;
  }
  
  /**
   * Client capabilities specific to the client's markdown parser.
   * 
   * Since 3.16.0
   */
  public void setMarkdown(final MarkdownCapabilities markdown) {
    this.markdown = markdown;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("regularExpressions", this.regularExpressions);
    b.add("markdown", this.markdown);
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
    GeneralClientCapabilities other = (GeneralClientCapabilities) obj;
    if (this.regularExpressions == null) {
      if (other.regularExpressions != null)
        return false;
    } else if (!this.regularExpressions.equals(other.regularExpressions))
      return false;
    if (this.markdown == null) {
      if (other.markdown != null)
        return false;
    } else if (!this.markdown.equals(other.markdown))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.regularExpressions== null) ? 0 : this.regularExpressions.hashCode());
    return prime * result + ((this.markdown== null) ? 0 : this.markdown.hashCode());
  }
}
