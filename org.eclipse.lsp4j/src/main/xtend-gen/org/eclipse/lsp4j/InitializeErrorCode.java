package org.eclipse.lsp4j;

/**
 * Known error codes for an `InitializeError`
 */
@SuppressWarnings("all")
public interface InitializeErrorCode {
  /**
   * If the protocol version provided by the client can't be handled by the server.
   * 
   * @deprecated This initialize error got replaced by client capabilities.
   * There is no version handshake in version 3.0x
   */
  @Deprecated
  public final static int unknownProtocolVersion = 1;
}
