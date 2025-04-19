// This is a generated file. Not intended for manual editing.
package io.github.potjerodekool.nabuidea.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import io.github.potjerodekool.nabuidea.language.psi.impl.*;

public interface NabuTypes {

  IElementType PROPERTY = new NabuElementType("PROPERTY");

  IElementType COMMENT = new NabuTokenType("COMMENT");
  IElementType CRLF = new NabuTokenType("CRLF");
  IElementType KEY = new NabuTokenType("KEY");
  IElementType SEPARATOR = new NabuTokenType("SEPARATOR");
  IElementType VALUE = new NabuTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == PROPERTY) {
        return new NabuPropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
