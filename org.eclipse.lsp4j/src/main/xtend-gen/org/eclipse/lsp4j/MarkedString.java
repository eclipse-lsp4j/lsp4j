package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * MarkedString can be used to render human readable text. It is either a markdown string
 * or a code-block that provides a language and a code snippet. The language identifier
 * is sematically equal to the optional language identifier in fenced code blocks in GitHub
 * issues. See https://help.github.com/articles/creating-and-highlighting-code-blocks/#syntax-highlighting
 * 
 * The pair of a language and a value is an equivalent to markdown:
 * ```${language}
 * ${value}
 * ```
 * 
 * Note that markdown strings will be sanitized - that means html will be escaped.
 */
@SuppressWarnings("all")
public class MarkedString {
  @NonNull
  private String language;
  
  @NonNull
  private String value;
  
  @Pure
  @NonNull
  public String getLanguage() {
    return this.language;
  }
  
  public void setLanguage(@NonNull final String language) {
    this.language = language;
  }
  
  @Pure
  @NonNull
  public String getValue() {
    return this.value;
  }
  
  public void setValue(@NonNull final String value) {
    this.value = value;
  }
  
  public MarkedString() {
    
  }
  
  public MarkedString(final String language, final String value) {
    this.language = language;
    this.value = value;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("language", this.language);
    b.add("value", this.value);
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
    MarkedString other = (MarkedString) obj;
    if (this.language == null) {
      if (other.language != null)
        return false;
    } else if (!this.language.equals(other.language))
      return false;
    if (this.value == null) {
      if (other.value != null)
        return false;
    } else if (!this.value.equals(other.value))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.language== null) ? 0 : this.language.hashCode());
    result = prime * result + ((this.value== null) ? 0 : this.value.hashCode());
    return result;
  }
}
