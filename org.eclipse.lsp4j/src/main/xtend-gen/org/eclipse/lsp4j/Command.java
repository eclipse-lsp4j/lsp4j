package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Represents a reference to a command. Provides a title which will be used to represent a command in the UI and,
 * optionally, an array of arguments which will be passed to the command handler function when invoked.
 */
@SuppressWarnings("all")
public class Command extends WrappedJsonObject {
  private static WrappedJsonProperty<String> titleProperty = new WrappedJsonProperty<>("title", WrappedJsonConverter.stringConverter);
  
  /**
   * Title of the command, like `save`.
   */
  @Pure
  @NonNull
  public String getTitle() {
    return titleProperty.get(jsonObject);
  }
  
  /**
   * Title of the command, like `save`.
   */
  public void setTitle(@NonNull final String title) {
    titleProperty.set(jsonObject, title);
  }
  
  /**
   * Removes the property title from the underlying JSON object.
   */
  public String removeTitle() {
    return titleProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> commandProperty = new WrappedJsonProperty<>("command", WrappedJsonConverter.stringConverter);
  
  /**
   * The identifier of the actual command handler.
   */
  @Pure
  @NonNull
  public String getCommand() {
    return commandProperty.get(jsonObject);
  }
  
  /**
   * The identifier of the actual command handler.
   */
  public void setCommand(@NonNull final String command) {
    commandProperty.set(jsonObject, command);
  }
  
  /**
   * Removes the property command from the underlying JSON object.
   */
  public String removeCommand() {
    return commandProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<List<Object>> argumentsProperty = new WrappedJsonProperty<>("arguments", WrappedJsonConverter.listConverter(WrappedJsonConverter.anyConverter));
  
  /**
   * Arguments that the command handler should be invoked with.
   */
  @Pure
  public List<Object> getArguments() {
    return argumentsProperty.get(jsonObject);
  }
  
  /**
   * Arguments that the command handler should be invoked with.
   */
  public void setArguments(final List<Object> arguments) {
    argumentsProperty.set(jsonObject, arguments);
  }
  
  /**
   * Removes the property arguments from the underlying JSON object.
   */
  public List<Object> removeArguments() {
    return argumentsProperty.remove(jsonObject);
  }
  
  public Command() {
    super();
  }
  
  public Command(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public Command(final String title, final String command, final List<Object> arguments) {
    this.setTitle(title);
    this.setCommand(command);
    this.setArguments(arguments);
  }
}
