package org.eclipse.lsp4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Value-object describing what options formatting should use.
 */
@SuppressWarnings("all")
public class FormattingOptions extends LinkedHashMap<String, Object> {
  private final static String TAB_SIZE = "tabSize";
  
  private final static String INSERT_SPACES = "insertSpaces";
  
  public FormattingOptions() {
  }
  
  public FormattingOptions(final int tabSize, final boolean insertSpaces) {
    this.setTabSize(tabSize);
    this.setInsertSpaces(insertSpaces);
  }
  
  /**
   * @deprecated See https://github.com/eclipse/lsp4j/issues/99
   */
  @Deprecated
  public FormattingOptions(final int tabSize, final boolean insertSpaces, final Map<String, String> properties) {
    this(tabSize, insertSpaces);
    this.putAll(properties);
  }
  
  /**
   * Only {@code boolean | number | string} are accepted by formatting options.
   */
  @Override
  public Object put(final String key, final Object value) {
    if (key != null) {
      switch (key) {
        case FormattingOptions.TAB_SIZE:
          if ((value instanceof Integer)) {
            return super.put(key, value);
          } else {
            if ((value instanceof Number)) {
              return super.put(key, Integer.valueOf(((Number)value).intValue()));
            } else {
              if ((value instanceof String)) {
                try {
                  return super.put(key, Integer.valueOf(((String)value)));
                } catch (final Throwable _t) {
                  if (_t instanceof NumberFormatException) {
                    final NumberFormatException e = (NumberFormatException)_t;
                  } else {
                    throw Exceptions.sneakyThrow(_t);
                  }
                }
              }
            }
          }
          break;
        case FormattingOptions.INSERT_SPACES:
          if ((value instanceof Boolean)) {
            return super.put(key, value);
          } else {
            if ((value instanceof String)) {
              return super.put(key, Boolean.valueOf(((String)value)));
            }
          }
          break;
        default:
          if ((((value instanceof Boolean) || (value instanceof Number)) || (value instanceof String))) {
            return super.put(key, value);
          } else {
            return super.put(key, value.toString());
          }
      }
    } else {
      if ((((value instanceof Boolean) || (value instanceof Number)) || (value instanceof String))) {
        return super.put(key, value);
      } else {
        return super.put(key, value.toString());
      }
    }
    return null;
  }
  
  /**
   * Size of a tab in spaces.
   */
  public int getTabSize() {
    final Object value = this.get(FormattingOptions.TAB_SIZE);
    if ((value instanceof Number)) {
      return ((Number)value).intValue();
    } else {
      throw new AssertionError((("Property \'" + FormattingOptions.TAB_SIZE) + "\' must be a number"));
    }
  }
  
  public void setTabSize(final int tabSize) {
    this.put(FormattingOptions.TAB_SIZE, Integer.valueOf(tabSize));
  }
  
  /**
   * Prefer spaces over tabs.
   */
  public boolean isInsertSpaces() {
    final Object value = this.get(FormattingOptions.INSERT_SPACES);
    if ((value instanceof Boolean)) {
      return ((Boolean) value).booleanValue();
    } else {
      throw new AssertionError((("Property \'" + FormattingOptions.INSERT_SPACES) + "\' must be a Boolean"));
    }
  }
  
  public void setInsertSpaces(final boolean insertSpaces) {
    this.put(FormattingOptions.INSERT_SPACES, Boolean.valueOf(insertSpaces));
  }
  
  /**
   * @deprecated See https://github.com/eclipse/lsp4j/issues/99
   */
  @Deprecated
  public Map<String, String> getProperties() {
    final LinkedHashMap<String, String> result = CollectionLiterals.<String, String>newLinkedHashMap();
    Set<Map.Entry<String, Object>> _entrySet = this.entrySet();
    for (final Map.Entry<String, Object> entry : _entrySet) {
      result.put(entry.getKey(), entry.getValue().toString());
    }
    return result;
  }
  
  /**
   * @deprecated See https://github.com/eclipse/lsp4j/issues/99
   */
  @Deprecated
  public void setProperties(final Map<String, String> properties) {
    this.putAll(properties);
  }
}
