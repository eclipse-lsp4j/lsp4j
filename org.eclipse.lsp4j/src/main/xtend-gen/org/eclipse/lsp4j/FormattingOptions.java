package org.eclipse.lsp4j;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * Value-object describing what options formatting should use.
 */
@SuppressWarnings("all")
public class FormattingOptions extends LinkedHashMap<String, Either<String, Either<Number, Boolean>>> {
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
    this.setProperties(properties);
  }
  
  public String getString(final String key) {
    return this.get(key).getLeft();
  }
  
  public void putString(final String key, final String value) {
    this.put(key, Either.<String, Either<Number, Boolean>>forLeft(value));
  }
  
  public Number getNumber(final String key) {
    Either<Number, Boolean> _right = this.get(key).getRight();
    Number _left = null;
    if (_right!=null) {
      _left=_right.getLeft();
    }
    return _left;
  }
  
  public void putNumber(final String key, final Number value) {
    this.put(key, Either.<String, Either<Number, Boolean>>forRight(Either.<Number, Boolean>forLeft(value)));
  }
  
  public Boolean getBoolean(final String key) {
    Either<Number, Boolean> _right = this.get(key).getRight();
    Boolean _right_1 = null;
    if (_right!=null) {
      _right_1=_right.getRight();
    }
    return _right_1;
  }
  
  public void putBoolean(final String key, final Boolean value) {
    this.put(key, Either.<String, Either<Number, Boolean>>forRight(Either.<Number, Boolean>forRight(value)));
  }
  
  /**
   * Size of a tab in spaces.
   */
  public int getTabSize() {
    final Number value = this.getNumber(FormattingOptions.TAB_SIZE);
    if ((value != null)) {
      return value.intValue();
    } else {
      return 0;
    }
  }
  
  public void setTabSize(final int tabSize) {
    this.putNumber(FormattingOptions.TAB_SIZE, Integer.valueOf(tabSize));
  }
  
  /**
   * Prefer spaces over tabs.
   */
  public boolean isInsertSpaces() {
    final Boolean value = this.getBoolean(FormattingOptions.INSERT_SPACES);
    if ((value != null)) {
      return (value).booleanValue();
    } else {
      return false;
    }
  }
  
  public void setInsertSpaces(final boolean insertSpaces) {
    this.putBoolean(FormattingOptions.INSERT_SPACES, Boolean.valueOf(insertSpaces));
  }
  
  /**
   * @deprecated See https://github.com/eclipse/lsp4j/issues/99
   */
  @Deprecated
  public Map<String, String> getProperties() {
    final LinkedHashMap<String, String> properties = CollectionLiterals.<String, String>newLinkedHashMap();
    Set<Map.Entry<String, Either<String, Either<Number, Boolean>>>> _entrySet = this.entrySet();
    for (final Map.Entry<String, Either<String, Either<Number, Boolean>>> entry : _entrySet) {
      {
        Serializable _xifexpression = null;
        boolean _isLeft = entry.getValue().isLeft();
        if (_isLeft) {
          _xifexpression = entry.getValue().getLeft();
        } else {
          Serializable _xifexpression_1 = null;
          if ((entry.getValue().isRight() && entry.getValue().getRight().isLeft())) {
            _xifexpression_1 = entry.getValue().getRight().getLeft();
          } else {
            Boolean _xifexpression_2 = null;
            if ((entry.getValue().isRight() && entry.getValue().getRight().isRight())) {
              _xifexpression_2 = entry.getValue().getRight().getRight();
            }
            _xifexpression_1 = _xifexpression_2;
          }
          _xifexpression = _xifexpression_1;
        }
        final Serializable value = _xifexpression;
        if ((value != null)) {
          properties.put(entry.getKey(), value.toString());
        }
      }
    }
    return Collections.<String, String>unmodifiableMap(properties);
  }
  
  /**
   * @deprecated See https://github.com/eclipse/lsp4j/issues/99
   */
  @Deprecated
  public void setProperties(final Map<String, String> properties) {
    Set<Map.Entry<String, String>> _entrySet = properties.entrySet();
    for (final Map.Entry<String, String> entry : _entrySet) {
      this.putString(entry.getKey(), entry.getValue());
    }
  }
}
