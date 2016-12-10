package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class InitializeError extends WrappedJsonObject {
  private static WrappedJsonProperty<Boolean> retryProperty = new WrappedJsonProperty<>("retry", WrappedJsonConverter.booleanConverter);
  
  /**
   * Indicates whether the client should retry to send the initialize request after showing the message provided
   * in the ResponseError.
   */
  @Pure
  public boolean isRetry() {
    return retryProperty.get(jsonObject);
  }
  
  /**
   * Indicates whether the client should retry to send the initialize request after showing the message provided
   * in the ResponseError.
   */
  public void setRetry(final boolean retry) {
    retryProperty.set(jsonObject, retry);
  }
  
  /**
   * Removes the property retry from the underlying JSON object.
   */
  public boolean removeRetry() {
    return retryProperty.remove(jsonObject);
  }
  
  public InitializeError() {
    super();
  }
  
  public InitializeError(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public InitializeError(final boolean retry) {
    this.setRetry(retry);
  }
}
