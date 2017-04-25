package org.eclipse.lsp4j;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Diagnostics notification are sent from the server to the client to signal results of validation runs.
 */
@SuppressWarnings("all")
public class PublishDiagnosticsParams {
  /**
   * The URI for which diagnostic information is reported.
   */
  @NonNull
  private String uri;
  
  /**
   * An array of diagnostic information items.
   */
  @NonNull
  private List<Diagnostic> diagnostics;
  
  public PublishDiagnosticsParams() {
    ArrayList<Diagnostic> _arrayList = new ArrayList<Diagnostic>();
    this.diagnostics = _arrayList;
  }
  
  public PublishDiagnosticsParams(final String uri, final List<Diagnostic> diagnostics) {
    this.uri = uri;
    this.diagnostics = diagnostics;
  }
  
  /**
   * The URI for which diagnostic information is reported.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * The URI for which diagnostic information is reported.
   */
  public void setUri(@NonNull final String uri) {
    this.uri = uri;
  }
  
  /**
   * An array of diagnostic information items.
   */
  @Pure
  @NonNull
  public List<Diagnostic> getDiagnostics() {
    return this.diagnostics;
  }
  
  /**
   * An array of diagnostic information items.
   */
  public void setDiagnostics(@NonNull final List<Diagnostic> diagnostics) {
    this.diagnostics = diagnostics;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
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
    PublishDiagnosticsParams other = (PublishDiagnosticsParams) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
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
    int result = 1;
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    result = prime * result + ((this.diagnostics== null) ? 0 : this.diagnostics.hashCode());
    return result;
  }
}
