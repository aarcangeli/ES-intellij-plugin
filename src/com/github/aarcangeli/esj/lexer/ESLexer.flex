package com.github.aarcangeli.esj.lexer;

/*
 * This file is compiled with JFlex into gen/com/github/aarcangeli/esj/lexer/CLexer.java
 * If you are using Intellij You can install Grammar-Kit plugin and click ctrl+shift+G
 *
 * This file is based on Scanner.l from Serious Engine
 */

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

@SuppressWarnings({"ALL"})
%%

%class CLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType

WHITE_SPACE_CHAR = [\ \t\n\r]

DIGIT           = [0-9]
HEXDIGIT        = [0-9A-Fa-f]
IDENTIFIERFIRST = [A-Za-z_]
IDENTIFIEROTHER = [A-Za-z0-9_]
NONEXP_FLT      = {DIGIT}+"."{DIGIT}*
EXP_FLT         = ({DIGIT}+("."({DIGIT}*)?)?)("E"|"e")("+"|"-")?{DIGIT}+

IDENTIFIER      = {IDENTIFIERFIRST}{IDENTIFIEROTHER}*

C_STYLE_COMMENT = ("/*"{COMMENT_TAIL})|"/*"
COMMENT_TAIL = ([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?
END_OF_LINE_COMMENT = "//" [^\r\n]*

// NB: escape characters are intentionally ignored since the reference compiler does not support it
STRING_LITERAL = \"[^\n\"]*(\")?
// NB: same as above
CHARACTER_LITERAL = \'[^\n']*(\')?

%state COMMENT
%state CPPBLOCK

%%

<YYINITIAL> {
    // Spaces and comments
    {WHITE_SPACE_CHAR}+     { return TokenType.WHITE_SPACE; }
    {C_STYLE_COMMENT}       { return CTokens.COMMENT; }
    {END_OF_LINE_COMMENT}   { return CTokens.COMMENT; }

    // Start and end of C++ code
    "%{"                    { yybegin(CPPBLOCK); return CTokens.CPP_BLOCK_BEGIN; }
    "%}"                    { return CTokens.CPP_BLOCK_END; }

    {DIGIT}+                { return CTokens.C_INT; }
    "0x"{HEXDIGIT}+         { return CTokens.C_INT; }
    {NONEXP_FLT}("f"|"F")?  { return CTokens.C_INT; }
    {EXP_FLT}("f"|"F")?     { return CTokens.C_INT; }
    {STRING_LITERAL}        { return CTokens.C_STRING; }
    {CHARACTER_LITERAL}     { return CTokens.C_CHAR; }

    // standard c++ keywords
    "while"     { return CTokens.K_WHILE; }
    "switch"    { return CTokens.K_SWITCH; }
    "case"      { return CTokens.K_CASE; }
    "for"       { return CTokens.K_FOR; }
    "if"        { return CTokens.K_IF; }
    "else"      { return CTokens.K_ELSE; }
    "enum"      { return CTokens.K_ENUM; }
    "class"     { return CTokens.K_CLASS; }
    "do"        { return CTokens.K_DO; }
    "void"      { return CTokens.K_VOID; }
    "const"     { return CTokens.K_CONST; }
    "inline"    { return CTokens.K_INLINE; }
    "static"    { return CTokens.K_STATIC; }
    "virtual"   { return CTokens.K_VIRTUAL; }
    "return"    { return CTokens.K_RETURN; }
    "autowait"  { return CTokens.K_AUTOWAIT; }
    "autocall"  { return CTokens.K_AUTOCALL; }
    "waitevent" { return CTokens.K_WAITEVENT; }

    // aditional keywords
    "event"     { return CTokens.K_EVENT; }
    "name"      { return CTokens.K_NAME; }
    "thumbnail" { return CTokens.K_THUMBNAIL; }
    "features"  { return CTokens.K_FEATURES; }
    "uses"      { return CTokens.K_USES; }
    "export"    { return CTokens.K_EXPORT; }

    "texture" { return CTokens.K_TEXTURE; }
    "sound"   { return CTokens.K_SOUND; }
    "model"   { return CTokens.K_MODEL; }

    "properties"  { return CTokens.K_PROPERTIES; }
    "components"  { return CTokens.K_COMPONENTS; }
    "functions"   { return CTokens.K_FUNCTIONS; }
    "procedures"  { return CTokens.K_PROCEDURES; }

    "wait"      { return CTokens.K_WAIT;}
    "on"        { return CTokens.K_ON;}
    "otherwise" { return CTokens.K_OTHERWISE; }

    "call"      { return CTokens.K_CALL;}
    "jump"      { return CTokens.K_JUMP;}
    "stop"      { return CTokens.K_STOP;}
    "resume"    { return CTokens.K_RESUME;}
    "pass"      { return CTokens.K_PASS;}

    // special data types
    "CTString"          { return CTokens.K_CTSTRING; }
    "CTStringTrans"     { return CTokens.K_CTSTRINGTRANS; }
    "CTFileName"        { return CTokens.K_CTFILENAME; }
    "CTFileNameNoDep"   { return CTokens.K_CTFILENAMENODEP; }
    "BOOL"              { return CTokens.K_BOOL; }
    "COLOR"             { return CTokens.K_COLOR; }
    "FLOAT"             { return CTokens.K_FLOAT; }
    "INDEX"             { return CTokens.K_INDEX; }
    "RANGE"             { return CTokens.K_RANGE; }
    "CEntityPointer"    { return CTokens.K_CENTITYPOINTER; }
    "CModelObject"      { return CTokens.K_CMODELOBJECT; }
    "CModelInstance"    { return CTokens.K_CMODELINSTANCE; }
    "CAnimObject"       { return CTokens.K_CANIMOBJECT; }
    "CSoundObject"      { return CTokens.K_CSOUNDOBJECT; }
    "CPlacement3D"      { return CTokens.K_CPLACEMENT3D; }
    "FLOATaabbox3D"     { return CTokens.K_FLOATAABBOX3D; }
    "FLOATmatrix3D"     { return CTokens.K_FLOATMATRIX3D; }
    "FLOATquat3D"       { return CTokens.K_FLOATQUAT3D; }
    "ANGLE"             { return CTokens.K_ANGLE; }
    "ANIMATION"         { return CTokens.K_ANIMATION; }
    "ILLUMINATIONTYPE"  { return CTokens.K_ILLUMINATIONTYPE; }
    "FLOAT3D"           { return CTokens.K_FLOAT3D; }
    "ANGLE3D"           { return CTokens.K_ANGLE3D; }
    "FLOATplane3D"      { return CTokens.K_FLOATPLANE3D; }
    "flags"             { return CTokens.K_FLAGS; }

    // operators
    ";" { return CTokens.SEMICOLON; }
    "(" { return CTokens.LPARENTH; }
    ")" { return CTokens.RPARENTH; }
    "{" { return CTokens.LBRACE; }
    "}" { return CTokens.RBRACE; }
    "=" { return CTokens.EQ; }
    "+" { return CTokens.PLUS; }
    "-" { return CTokens.MINUS; }
    "<" { return CTokens.LT; }
    ">" { return CTokens.GT; }
    "!" { return CTokens.EXCL; }
    "|" { return CTokens.OR; }
    "&" { return CTokens.AND; }
    "*" { return CTokens.ASTERISK; }
    "/" { return CTokens.DIV; }
    "%" { return CTokens.PERC; }
    "^" { return CTokens.XOR; }
    "[" { return CTokens.LBRACKET; }
    "]" { return CTokens.RBRACKET; }
    ":" { return CTokens.COLON; }
    "," { return CTokens.COMMA; }
    "." { return CTokens.DOT; }
    "?" { return CTokens.QUEST; }
    "~" { return CTokens.TILDE; }

    {IDENTIFIER} { return CTokens.IDENTIFIER; }
}

<CPPBLOCK> {
    // Eat up to %} and return a single token
    "%}" { yybegin(YYINITIAL); yypushback(yylength()); return CTokens.CPP_BLOCK; }
    {IDENTIFIER} {}
    [^] {}

    // If we missed the last %} just return it
    <<EOF>> { yybegin(YYINITIAL); return CTokens.CPP_BLOCK; }
}

[^]  { return TokenType.BAD_CHARACTER; }
