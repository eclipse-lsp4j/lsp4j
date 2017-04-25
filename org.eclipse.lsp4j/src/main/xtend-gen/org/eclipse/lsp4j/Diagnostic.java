package org.eclipse.lsp4j;

import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a diagnostic, such as a compiler error or warning. Diagnostic objects are only valid in the scope of a resource.
 */
@SuppressWarnings("all")
public class Diagnostic {
  /**
   * The range at which the message applies
   */
  @NonNull
  private Range range;
  
  /**
   * The diagnostic's severity. Can be omitted. If omitted it is up to the client to interpret diagnostics as error,
   * warning, info or hint.
   */
  private DiagnosticSeverity severity;
  
  /**
   * The diagnostic's code. Can be omitted.
   */
  private String code;
  
  /**
   * A human-readable string describing the source of this diagnostic, e.g. 'typescript' or 'super lint'.
   */
  private String source;
  
  /**
   * The diagnostic's message.
   */
  @NonNull
  private String message;
  
  /**
   * The range at which the message applies
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range at which the message applies
   */
  public void setRange(@NonNull final Range range) {
    this.range = range;
  }
  
  /**
   * The diagnostic's severity. Can be omitted. If omitted it is up to the client to interpret diagnostics as error,
   * warning, info or hint.
   */
  @Pure
  public DiagnosticSeverity getSeverity() {
    return this.severity;
  }
  
  /**
   * The diagnostic's severity. Can be omitted. If omitted it is up to the client to interpret diagnostics as error,
   * warning, info or hint.
   */
  public void setSeverity(final DiagnosticSeverity severity) {
    this.severity = severity;
  }
  
  /**
   * The diagnostic's code. Can be omitted.
   */
  @Pure
  public String getCode() {
    return this.code;
  }
  
  /**
   * The diagnostic's code. Can be omitted.
   */
  public void setCode(final String code) {
    this.code = code;
  }
  
  /**
   * A human-readable string describing the source of this diagnostic, e.g. 'typescript' or 'super lint'.
   */
  @Pure
  public String getSource() {
    return this.source;
  }
  
  /**
   * A human-readable string describing the source of this diagnostic, e.g. 'typescript' or 'super lint'.
   */
  public void setSource(final String source) {
    this.source = source;
  }
  
  /**
   * The diagnostic's message.
   */
  @Pure
  @NonNull
  public String getMessage() {
    return this.message;
  }
  
  /**
   * The diagnostic's message.
   */
  public void setMessage(@NonNull final String message) {
    this.message = message;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("severity", this.severity);
    b.add("code", this.code);
    b.add("source", this.source);
    b.add("message", this.message);
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
    Diagnostic other = (Diagnostic) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.severity == null) {
      if (other.severity != null)
        return false;
    } else if (!this.severity.equals(other.severity))
      return false;
    if (this.code == null) {
      if (other.code != null)
        return false;
    } else if (!this.code.equals(other.code))
      return false;
    if (this.source == null) {
      if (other.source != null)
        return false;
    } else if (!this.source.equals(other.source))
      return false;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.severity== null) ? 0 : this.severity.hashCode());
    result = prime * result + ((this.code== null) ? 0 : this.code.hashCode());
    result = prime * result + ((this.source== null) ? 0 : this.source.hashCode());
    result = prime * result + ((this.message== null) ? 0 : this.message.hashCode());
    return result;
  }
}
