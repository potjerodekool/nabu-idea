// This is a generated file. Not intended for manual editing.
package io.github.potjerodekool.nabuidea.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static io.github.potjerodekool.nabuidea.language.psi.NabuTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import io.github.potjerodekool.nabuidea.language.psi.*;

public class NabuPropertyImpl extends ASTWrapperPsiElement implements NabuProperty {

  public NabuPropertyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NabuVisitor visitor) {
    visitor.visitProperty(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NabuVisitor) accept((NabuVisitor)visitor);
    else super.accept(visitor);
  }

}
