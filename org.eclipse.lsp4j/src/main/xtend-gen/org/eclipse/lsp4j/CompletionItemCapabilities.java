package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class CompletionItemCapabilities {
  /**
   * Client supports snippets as insert text.
   * 
   * A snippet can define tab stops and placeholders with `$1`, `$2`
   * and `${3:foo}`. `$0` defines the final tab stop, it defaults to
   * the end of the snippet. Placeholders with equal identifiers are linked,
   * that is typing in one will update others too.
   */
  private Boolean snippetSupport;
  
  public CompletionItemCapabilities() {
  }
  
  public CompletionItemCapabilities(final Boolean snippetSupport) {
    this.snippetSupport = snippetSupport;
  }
  
  /**
   * Client supports snippets as insert text.
   * 
   * A snippet can define tab stops and placeholders with `$1`, `$2`
   * and `${3:foo}`. `$0` defines the final tab stop, it defaults to
   * the end of the snippet. Placeholders with equal identifiers are linked,
   * that is typing in one will update others too.
   */
  @Pure
  public Boolean getSnippetSupport() {
    return this.snippetSupport;
  }
  
  /**
   * Client supports snippets as insert text.
   * 
   * A snippet can define tab stops and placeholders with `$1`, `$2`
   * and `${3:foo}`. `$0` defines the final tab stop, it defaults to
   * the end of the snippet. Placeholders with equal identifiers are linked,
   * that is typing in one will update others too.
   */
  public void setSnippetSupport(final Boolean snippetSupport) {
    this.snippetSupport = snippetSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("snippetSupport", this.snippetSupport);
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
    CompletionItemCapabilities other = (CompletionItemCapabilities) obj;
    if (this.snippetSupport == null) {
      if (other.snippetSupport != null)
        return false;
    } else if (!this.snippetSupport.equals(other.snippetSupport))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.snippetSupport== null) ? 0 : this.snippetSupport.hashCode());
    return result;
  }
}
