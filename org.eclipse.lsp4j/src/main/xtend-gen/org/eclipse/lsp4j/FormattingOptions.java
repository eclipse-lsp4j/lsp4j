package org.eclipse.lsp4j;

import java.util.Map;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Value-object describing what options formatting should use.
 */
@SuppressWarnings("all")
public class FormattingOptions {
  /**
   * Size of a tab in spaces.
   */
  private int tabSize;
  
  /**
   * Prefer spaces over tabs.
   */
  private boolean insertSpaces;
  
  /**
   * Signature for further properties.
   */
  private Map<String, String> properties;
  
  /**
   * Size of a tab in spaces.
   */
  @Pure
  public int getTabSize() {
    return this.tabSize;
  }
  
  /**
   * Size of a tab in spaces.
   */
  public void setTabSize(final int tabSize) {
    this.tabSize = tabSize;
  }
  
  /**
   * Prefer spaces over tabs.
   */
  @Pure
  public boolean isInsertSpaces() {
    return this.insertSpaces;
  }
  
  /**
   * Prefer spaces over tabs.
   */
  public void setInsertSpaces(final boolean insertSpaces) {
    this.insertSpaces = insertSpaces;
  }
  
  /**
   * Signature for further properties.
   */
  @Pure
  public Map<String, String> getProperties() {
    return this.properties;
  }
  
  /**
   * Signature for further properties.
   */
  public void setProperties(final Map<String, String> properties) {
    this.properties = properties;
  }
  
  public FormattingOptions() {
    
  }
  
  public FormattingOptions(final int tabSize, final boolean insertSpaces, final Map<String, String> properties) {
    this.tabSize = tabSize;
    this.insertSpaces = insertSpaces;
    this.properties = properties;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("tabSize", this.tabSize);
    b.add("insertSpaces", this.insertSpaces);
    b.add("properties", this.properties);
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
    FormattingOptions other = (FormattingOptions) obj;
    if (other.tabSize != this.tabSize)
      return false;
    if (other.insertSpaces != this.insertSpaces)
      return false;
    if (this.properties == null) {
      if (other.properties != null)
        return false;
    } else if (!this.properties.equals(other.properties))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.tabSize;
    result = prime * result + (this.insertSpaces ? 1231 : 1237);
    result = prime * result + ((this.properties== null) ? 0 : this.properties.hashCode());
    return result;
  }
}
