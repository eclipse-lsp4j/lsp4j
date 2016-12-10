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
 * Contains additional diagnostic information about the context in which a code action is run.
 */
@SuppressWarnings("all")
public class CodeActionContext extends WrappedJsonObject {
  private static WrappedJsonProperty<List<Diagnostic>> diagnosticsProperty = new WrappedJsonProperty<>("diagnostics", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(Diagnostic.class)));
  
  /**
   * An array of diagnostics.
   */
  @Pure
  @NonNull
  public List<Diagnostic> getDiagnostics() {
    return diagnosticsProperty.get(jsonObject);
  }
  
  /**
   * An array of diagnostics.
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
  
  public CodeActionContext() {
    super();
  }
  
  public CodeActionContext(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public CodeActionContext(final List<Diagnostic> diagnostics) {
    this.setDiagnostics(diagnostics);
  }
}
