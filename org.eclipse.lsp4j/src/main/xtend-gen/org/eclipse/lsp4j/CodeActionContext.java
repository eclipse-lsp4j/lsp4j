package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Contains additional diagnostic information about the context in which a code action is run.
 */
@SuppressWarnings("all")
public class CodeActionContext {
  /**
   * An array of diagnostics.
   */
  @NonNull
  private List<Diagnostic> diagnostics = CollectionLiterals.<Diagnostic>newArrayList();
  
  /**
   * An array of diagnostics.
   */
  @Pure
  public List<Diagnostic> getDiagnostics() {
    return this.diagnostics;
  }
  
  /**
   * An array of diagnostics.
   */
  public void setDiagnostics(final List<Diagnostic> diagnostics) {
    this.diagnostics = diagnostics;
  }
  
  public CodeActionContext() {
    
  }
  
  public CodeActionContext(final List<Diagnostic> diagnostics) {
    this.diagnostics = diagnostics;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("diagnostics", this.diagnostics);
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
    if (!super.equals(obj))
      return false;
    CodeActionContext other = (CodeActionContext) obj;
    if (this.diagnostics == null) {
      if (other.diagnostics != null)
        return false;
    } else if (!this.diagnostics.equals(other.diagnostics))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.diagnostics== null) ? 0 : this.diagnostics.hashCode());
    return result;
  }
}
