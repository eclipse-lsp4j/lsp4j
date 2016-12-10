package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.Either;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Represents a diagnostic, such as a compiler error or warning. Diagnostic objects are only valid in the scope of a resource.
 */
@SuppressWarnings("all")
public class Diagnostic extends WrappedJsonObject {
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * The range at which the message applies
   */
  @Pure
  @NonNull
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * The range at which the message applies
   */
  public void setRange(@NonNull final Range range) {
    rangeProperty.set(jsonObject, range);
  }
  
  /**
   * Removes the property range from the underlying JSON object.
   */
  public Range removeRange() {
    return rangeProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<DiagnosticSeverity> severityProperty = new WrappedJsonProperty<>("severity", WrappedJsonConverter.enumConverter(DiagnosticSeverity.class));
  
  /**
   * The diagnostic's severity. Can be omitted. If omitted it is up to the client to interpret diagnostics as error,
   * warning, info or hint.
   */
  @Pure
  public DiagnosticSeverity getSeverity() {
    return severityProperty.get(jsonObject);
  }
  
  /**
   * The diagnostic's severity. Can be omitted. If omitted it is up to the client to interpret diagnostics as error,
   * warning, info or hint.
   */
  public void setSeverity(final DiagnosticSeverity severity) {
    severityProperty.set(jsonObject, severity);
  }
  
  /**
   * Removes the property severity from the underlying JSON object.
   */
  public DiagnosticSeverity removeSeverity() {
    return severityProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Either<String, Integer>> codeProperty = new WrappedJsonProperty<>("code", WrappedJsonConverter.eitherConverter(WrappedJsonConverter.stringConverter, WrappedJsonConverter.integerConverter));
  
  /**
   * The diagnostic's code. Can be omitted.
   */
  @Pure
  public Either<String, Integer> getCode() {
    return codeProperty.get(jsonObject);
  }
  
  /**
   * The diagnostic's code. Can be omitted.
   */
  public void setCode(final Either<String, Integer> code) {
    codeProperty.set(jsonObject, code);
  }
  
  /**
   * Removes the property code from the underlying JSON object.
   */
  public Either<String, Integer> removeCode() {
    return codeProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> sourceProperty = new WrappedJsonProperty<>("source", WrappedJsonConverter.stringConverter);
  
  /**
   * A human-readable string describing the source of this diagnostic, e.g. 'typescript' or 'super lint'.
   */
  @Pure
  public String getSource() {
    return sourceProperty.get(jsonObject);
  }
  
  /**
   * A human-readable string describing the source of this diagnostic, e.g. 'typescript' or 'super lint'.
   */
  public void setSource(final String source) {
    sourceProperty.set(jsonObject, source);
  }
  
  /**
   * Removes the property source from the underlying JSON object.
   */
  public String removeSource() {
    return sourceProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> messageProperty = new WrappedJsonProperty<>("message", WrappedJsonConverter.stringConverter);
  
  /**
   * The diagnostic's message.
   */
  @Pure
  @NonNull
  public String getMessage() {
    return messageProperty.get(jsonObject);
  }
  
  /**
   * The diagnostic's message.
   */
  public void setMessage(@NonNull final String message) {
    messageProperty.set(jsonObject, message);
  }
  
  /**
   * Removes the property message from the underlying JSON object.
   */
  public String removeMessage() {
    return messageProperty.remove(jsonObject);
  }
  
  public Diagnostic() {
    super();
  }
  
  public Diagnostic(final JsonObject jsonObject) {
    super(jsonObject);
  }
}
