package org.eclipse.lsp4j;

/**
 * Known error codes for an `InitializeError`
 */
@SuppressWarnings("all")
public interface InitializeErrorCode {
  /**
   * If the protocol version provided by the client can't be handled by the server.
   */
  public final static int unknownProtocolVersion = 1;
}
