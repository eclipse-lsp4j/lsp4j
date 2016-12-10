package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Diagnostics notification are sent from the server to the client to signal results of validation runs.
 */
@SuppressWarnings("all")
public class PublishDiagnosticsParams extends WrappedJsonObject {
  private static WrappedJsonProperty<String> uriProperty = new WrappedJsonProperty<>("uri", WrappedJsonConverter.stringConverter);
  
  /**
   * The URI for which diagnostic information is reported.
   */
  @Pure
  @NonNull
  public String getUri() {
    return uriProperty.get(jsonObject);
  }
  
  /**
   * The URI for which diagnostic information is reported.
   */
  public void setUri(@NonNull final String uri) {
    uriProperty.set(jsonObject, uri);
  }
  
  /**
   * Removes the property uri from the underlying JSON object.
   */
  public String removeUri() {
    return uriProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<List<Diagnostic>> diagnosticsProperty = new WrappedJsonProperty<>("diagnostics", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(Diagnostic.class)));
  
  /**
   * An array of diagnostic information items.
   */
  @Pure
  @NonNull
  public List<Diagnostic> getDiagnostics() {
    return diagnosticsProperty.get(jsonObject);
  }
  
  /**
   * An array of diagnostic information items.
   */
  public void setDiagnostics(@NonNull final List<Diagnostic> diagnostics) {
    diagnosticsProperty.set(jsonObject, diagnostics);
  }
  
  /**
   * Removes the property diagnostics from the underlying JSON object.
   */
  public List<Diagnostic> removeDiagnostics() {
    return diagnosticsProperty.remove(jsonObject);
  }
  
  public PublishDiagnosticsParams() {
    super();
  }
  
  public PublishDiagnosticsParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public PublishDiagnosticsParams(final String uri, final List<Diagnostic> diagnostics) {
    this.setUri(uri);
    this.setDiagnostics(diagnostics);
  }
}
