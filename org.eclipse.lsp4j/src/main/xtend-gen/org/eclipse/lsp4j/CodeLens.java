package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * A code lens represents a command that should be shown along with source text, like the number of references,
 * a way to run tests, etc.
 * 
 * A code lens is <em>unresolved</em> when no command is associated to it. For performance reasons the creation of a
 * code lens and resolving should be done to two stages.
 */
@SuppressWarnings("all")
public class CodeLens extends WrappedJsonObject {
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * The range in which this code lens is valid. Should only span a single line.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * The range in which this code lens is valid. Should only span a single line.
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
  
  private static WrappedJsonProperty<Command> commandProperty = new WrappedJsonProperty<>("command", WrappedJsonConverter.objectConverter(Command.class));
  
  /**
   * The command this code lens represents.
   */
  @Pure
  public Command getCommand() {
    return commandProperty.get(jsonObject);
  }
  
  /**
   * The command this code lens represents.
   */
  public void setCommand(final Command command) {
    commandProperty.set(jsonObject, command);
  }
  
  /**
   * Removes the property command from the underlying JSON object.
   */
  public Command removeCommand() {
    return commandProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Object> dataProperty = new WrappedJsonProperty<>("data", WrappedJsonConverter.anyConverter);
  
  /**
   * An data entry field that is preserved on a code lens item between a code lens and a code lens resolve request.
   */
  @Pure
  public Object getData() {
    return dataProperty.get(jsonObject);
  }
  
  /**
   * An data entry field that is preserved on a code lens item between a code lens and a code lens resolve request.
   */
  public void setData(final Object data) {
    dataProperty.set(jsonObject, data);
  }
  
  /**
   * Removes the property data from the underlying JSON object.
   */
  public Object removeData() {
    return dataProperty.remove(jsonObject);
  }
  
  public CodeLens() {
    super();
  }
  
  public CodeLens(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public CodeLens(final Range range, final Command command, final Object data) {
    this.setRange(range);
    this.setCommand(command);
    this.setData(data);
  }
}
