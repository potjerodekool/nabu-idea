package io.github.potjerodekool.nabuidea.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import io.github.potjerodekool.nabuidea.language.psi.NabuTypes;
import com.intellij.psi.TokenType;

%%

%class NabuLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=\R
WHITE_SPACE=[\ \n\t\f]
END_OF_LINE_COMMENT=("#"|"!")[^\r\n]*
Identifier = [:jletter:] [:jletterdigit:]*

%state WAITING_VALUE

%%

<YYINITIAL> "package"           { yybegin(YYINITIAL); return NabuTypes.PACKAGE; }
<YYINITIAL> "class"           { yybegin(YYINITIAL); return NabuTypes.CLASS; }

<YYINITIAL> "public"           { yybegin(YYINITIAL); return NabuTypes.PUBLIC; }
<YYINITIAL> "protected"           { yybegin(YYINITIAL); return NabuTypes.PROTECTED; }
<YYINITIAL> "private"           { yybegin(YYINITIAL); return NabuTypes.PRIVATE; }
<YYINITIAL> "abstract"           { yybegin(YYINITIAL); return NabuTypes.ABSTRACT; }
<YYINITIAL> "static"           { yybegin(YYINITIAL); return NabuTypes.STATIC; }
<YYINITIAL> "final"           { yybegin(YYINITIAL); return NabuTypes.FINAL; }
<YYINITIAL> "sealed"           { yybegin(YYINITIAL); return NabuTypes.SEALED; }
<YYINITIAL> "non-sealed"           { yybegin(YYINITIAL); return NabuTypes.NON_SEALED; }
<YYINITIAL> "strictfp"           { yybegin(YYINITIAL); return NabuTypes.STRICTFP; }

<YYINITIAL> {
      {Identifier}                   { yybegin(YYINITIAL); return NabuTypes.IDENTIFIER; }
}

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return NabuTypes.COMMENT; }

<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<YYINITIAL> "."           { yybegin(YYINITIAL); return NabuTypes.DOT; }
<YYINITIAL> ";"           { yybegin(YYINITIAL); return NabuTypes.SEMI_COLON; }
<YYINITIAL> "{"           { yybegin(YYINITIAL); return NabuTypes.LBRACE; }
<YYINITIAL> "}"           { yybegin(YYINITIAL); return NabuTypes.RBRACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }

