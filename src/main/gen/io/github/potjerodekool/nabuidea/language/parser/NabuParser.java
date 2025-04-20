// This is a generated file. Not intended for manual editing.
package io.github.potjerodekool.nabuidea.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static io.github.potjerodekool.nabuidea.language.psi.NabuTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class NabuParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return nabuFile(b, l + 1);
  }

  /* ********************************************************** */
  // LBRACE
  // RBRACE
  public static boolean classBody(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classBody")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LBRACE, RBRACE);
    exit_section_(b, m, CLASS_BODY, r);
    return r;
  }

  /* ********************************************************** */
  // PUBLIC
  // | PROTECTED
  // | PRIVATE
  // | ABSTRACT
  // | STATIC
  // | FINAL
  // | SEALED
  // | NON_SEALED
  // | STRICTFP
  public static boolean classModifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "classModifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CLASS_MODIFIER, "<class modifier>");
    r = consumeToken(b, PUBLIC);
    if (!r) r = consumeToken(b, PROTECTED);
    if (!r) r = consumeToken(b, PRIVATE);
    if (!r) r = consumeToken(b, ABSTRACT);
    if (!r) r = consumeToken(b, STATIC);
    if (!r) r = consumeToken(b, FINAL);
    if (!r) r = consumeToken(b, SEALED);
    if (!r) r = consumeToken(b, NON_SEALED);
    if (!r) r = consumeToken(b, STRICTFP);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ordinaryCompilationUnit
  public static boolean compilationUnit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilationUnit")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPILATION_UNIT, "<compilation unit>");
    r = ordinaryCompilationUnit(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // compilationUnit
  static boolean nabuFile(PsiBuilder b, int l) {
    return compilationUnit(b, l + 1);
  }

  /* ********************************************************** */
  // classModifier* CLASS typeIdentifier classBody
  public static boolean normalClassDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normalClassDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NORMAL_CLASS_DECLARATION, "<normal class declaration>");
    r = normalClassDeclaration_0(b, l + 1);
    r = r && consumeToken(b, CLASS);
    r = r && typeIdentifier(b, l + 1);
    r = r && classBody(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // classModifier*
  private static boolean normalClassDeclaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normalClassDeclaration_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!classModifier(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "normalClassDeclaration_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // packageDeclaration?
  // topLevelClassOrInterfaceDeclaration*
  public static boolean ordinaryCompilationUnit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ordinaryCompilationUnit")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ORDINARY_COMPILATION_UNIT, "<ordinary compilation unit>");
    r = ordinaryCompilationUnit_0(b, l + 1);
    r = r && ordinaryCompilationUnit_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // packageDeclaration?
  private static boolean ordinaryCompilationUnit_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ordinaryCompilationUnit_0")) return false;
    packageDeclaration(b, l + 1);
    return true;
  }

  // topLevelClassOrInterfaceDeclaration*
  private static boolean ordinaryCompilationUnit_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ordinaryCompilationUnit_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!topLevelClassOrInterfaceDeclaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ordinaryCompilationUnit_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // PACKAGE IDENTIFIER (DOT IDENTIFIER)* SEMI_COLON COMMENT?
  public static boolean packageDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "packageDeclaration")) return false;
    if (!nextTokenIs(b, PACKAGE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PACKAGE, IDENTIFIER);
    r = r && packageDeclaration_2(b, l + 1);
    r = r && consumeToken(b, SEMI_COLON);
    r = r && packageDeclaration_4(b, l + 1);
    exit_section_(b, m, PACKAGE_DECLARATION, r);
    return r;
  }

  // (DOT IDENTIFIER)*
  private static boolean packageDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "packageDeclaration_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!packageDeclaration_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "packageDeclaration_2", c)) break;
    }
    return true;
  }

  // DOT IDENTIFIER
  private static boolean packageDeclaration_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "packageDeclaration_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  // COMMENT?
  private static boolean packageDeclaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "packageDeclaration_4")) return false;
    consumeToken(b, COMMENT);
    return true;
  }

  /* ********************************************************** */
  // normalClassDeclaration
  public static boolean topLevelClassOrInterfaceDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "topLevelClassOrInterfaceDeclaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TOP_LEVEL_CLASS_OR_INTERFACE_DECLARATION, "<top level class or interface declaration>");
    r = normalClassDeclaration(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean typeIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "typeIdentifier")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, TYPE_IDENTIFIER, r);
    return r;
  }

}
