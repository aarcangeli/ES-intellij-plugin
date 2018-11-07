package com.github.aarcangeli.esj.lexer;

import com.github.aarcangeli.esj.CLanguage;
import com.intellij.psi.tree.IElementType;

public interface CTokens {
    // basic tokens
    IElementType IDENTIFIER = new IElementType("ES.IDENTIFIER", CLanguage.INSTANCE);
    IElementType COMMENT = new IElementType("ES.COMMENT", CLanguage.INSTANCE);

    // inline C++ blocks
    IElementType CPP_BLOCK = new IElementType("ES.CPP_BLOCK", CLanguage.INSTANCE);
    IElementType CPP_BLOCK_BEGIN = new IElementType("ES.CPP_BLOCK_BEGIN", CLanguage.INSTANCE);
    IElementType CPP_BLOCK_END = new IElementType("ES.CPP_BLOCK_END", CLanguage.INSTANCE);

    // different type of constants
    IElementType C_CHAR = new IElementType("ES.C_CHAR", CLanguage.INSTANCE);
    IElementType C_INT = new IElementType("ES.C_INT", CLanguage.INSTANCE);
    IElementType C_STRING = new IElementType("ES.C_STRING", CLanguage.INSTANCE);

    // standard cpp-keywords
    IElementType K_WHILE = new IElementType("ES.K_WHILE", CLanguage.INSTANCE);
    IElementType K_FOR = new IElementType("ES.K_FOR", CLanguage.INSTANCE);
    IElementType K_IF = new IElementType("ES.K_IF", CLanguage.INSTANCE);
    IElementType K_ELSE = new IElementType("ES.K_ELSE", CLanguage.INSTANCE);
    IElementType K_ENUM = new IElementType("ES.K_ENUM", CLanguage.INSTANCE);
    IElementType K_SWITCH = new IElementType("ES.K_SWITCH", CLanguage.INSTANCE);
    IElementType K_CASE = new IElementType("ES.K_CASE", CLanguage.INSTANCE);
    IElementType K_DEFAULT = new IElementType("ES.K_DEFAULT", CLanguage.INSTANCE);
    IElementType K_BREAK = new IElementType("ES.K_BREAK", CLanguage.INSTANCE);
    IElementType K_CLASS = new IElementType("ES.K_CLASS", CLanguage.INSTANCE);
    IElementType K_DO = new IElementType("ES.K_DO", CLanguage.INSTANCE);
    IElementType K_VOID = new IElementType("ES.K_VOID", CLanguage.INSTANCE);
    IElementType K_CONST = new IElementType("ES.K_CONST", CLanguage.INSTANCE);
    IElementType K_INLINE = new IElementType("ES.K_INLINE", CLanguage.INSTANCE);
    IElementType K_STATIC = new IElementType("ES.K_STATIC", CLanguage.INSTANCE);
    IElementType K_VIRTUAL = new IElementType("ES.K_VIRTUAL", CLanguage.INSTANCE);
    IElementType K_RETURN = new IElementType("ES.K_RETURN", CLanguage.INSTANCE);
    IElementType K_AUTOWAIT = new IElementType("ES.K_AUTOWAIT", CLanguage.INSTANCE);
    IElementType K_AUTOCALL = new IElementType("ES.K_AUTOCALL", CLanguage.INSTANCE);
    IElementType K_WAITEVENT = new IElementType("ES.K_WAITEVENT", CLanguage.INSTANCE);

    // aditional keywords
    IElementType K_EVENT = new IElementType("ES.K_EVENT", CLanguage.INSTANCE);
    IElementType K_NAME = new IElementType("ES.K_NAME", CLanguage.INSTANCE);
    IElementType K_THUMBNAIL = new IElementType("ES.K_THUMBNAIL", CLanguage.INSTANCE);
    IElementType K_FEATURES = new IElementType("ES.K_FEATURES", CLanguage.INSTANCE);
    IElementType K_USES = new IElementType("ES.K_USES", CLanguage.INSTANCE);
    IElementType K_EXPORT = new IElementType("ES.K_EXPORT", CLanguage.INSTANCE);

    IElementType K_TEXTURE = new IElementType("ES.K_TEXTURE", CLanguage.INSTANCE);
    IElementType K_SOUND = new IElementType("ES.K_SOUND", CLanguage.INSTANCE);
    IElementType K_MODEL = new IElementType("ES.K_MODEL", CLanguage.INSTANCE);

    IElementType K_PROPERTIES = new IElementType("ES.K_PROPERTIES", CLanguage.INSTANCE);
    IElementType K_COMPONENTS = new IElementType("ES.K_COMPONENTS", CLanguage.INSTANCE);
    IElementType K_FUNCTIONS = new IElementType("ES.K_FUNCTIONS", CLanguage.INSTANCE);
    IElementType K_PROCEDURES = new IElementType("ES.K_PROCEDURES", CLanguage.INSTANCE);

    IElementType K_WAIT = new IElementType("ES.K_WAIT", CLanguage.INSTANCE);
    IElementType K_ON = new IElementType("ES.K_ON", CLanguage.INSTANCE);
    IElementType K_OTHERWISE = new IElementType("ES.K_OTHERWISE", CLanguage.INSTANCE);

    IElementType K_CALL = new IElementType("ES.K_CALL", CLanguage.INSTANCE);
    IElementType K_JUMP = new IElementType("ES.K_JUMP", CLanguage.INSTANCE);
    IElementType K_STOP = new IElementType("ES.K_STOP", CLanguage.INSTANCE);
    IElementType K_RESUME = new IElementType("ES.K_RESUME", CLanguage.INSTANCE);
    IElementType K_PASS = new IElementType("ES.K_PASS", CLanguage.INSTANCE);

    // special data types
    IElementType K_CTSTRING = new IElementType("ES.K_CTSTRING", CLanguage.INSTANCE);
    IElementType K_CTSTRINGTRANS = new IElementType("ES.K_CTSTRINGTRANS", CLanguage.INSTANCE);
    IElementType K_CTFILENAME = new IElementType("ES.K_CTFILENAME", CLanguage.INSTANCE);
    IElementType K_CTFILENAMENODEP = new IElementType("ES.K_CTFILENAMENODEP", CLanguage.INSTANCE);
    IElementType K_BOOL = new IElementType("ES.K_BOOL", CLanguage.INSTANCE);
    IElementType K_COLOR = new IElementType("ES.K_COLOR", CLanguage.INSTANCE);
    IElementType K_FLOAT = new IElementType("ES.K_FLOAT", CLanguage.INSTANCE);
    IElementType K_INDEX = new IElementType("ES.K_INDEX", CLanguage.INSTANCE);
    IElementType K_RANGE = new IElementType("ES.K_RANGE", CLanguage.INSTANCE);
    IElementType K_CENTITYPOINTER = new IElementType("ES.K_CENTITYPOINTER", CLanguage.INSTANCE);
    IElementType K_CMODELOBJECT = new IElementType("ES.K_CMODELOBJECT", CLanguage.INSTANCE);
    IElementType K_CMODELINSTANCE = new IElementType("ES.K_CMODELINSTANCE", CLanguage.INSTANCE);
    IElementType K_CANIMOBJECT = new IElementType("ES.K_CANIMOBJECT", CLanguage.INSTANCE);
    IElementType K_CSOUNDOBJECT = new IElementType("ES.K_CSOUNDOBJECT", CLanguage.INSTANCE);
    IElementType K_CPLACEMENT3D = new IElementType("ES.K_CPLACEMENT3D", CLanguage.INSTANCE);
    IElementType K_FLOATAABBOX3D = new IElementType("ES.K_FLOATAABBOX3D", CLanguage.INSTANCE);
    IElementType K_FLOATMATRIX3D = new IElementType("ES.K_FLOATMATRIX3D", CLanguage.INSTANCE);
    IElementType K_FLOATQUAT3D = new IElementType("ES.K_FLOATQUAT3D", CLanguage.INSTANCE);
    IElementType K_ANGLE = new IElementType("ES.K_ANGLE", CLanguage.INSTANCE);
    IElementType K_FLOAT3D = new IElementType("ES.K_FLOAT3D", CLanguage.INSTANCE);
    IElementType K_ANGLE3D = new IElementType("ES.K_ANGLE3D", CLanguage.INSTANCE);
    IElementType K_FLOATPLANE3D = new IElementType("ES.K_FLOATPLANE3D", CLanguage.INSTANCE);
    IElementType K_ANIMATION = new IElementType("ES.K_ANIMATION", CLanguage.INSTANCE);
    IElementType K_ILLUMINATIONTYPE = new IElementType("ES.K_ILLUMINATIONTYPE", CLanguage.INSTANCE);
    IElementType K_FLAGS = new IElementType("ES.K_FLAGS", CLanguage.INSTANCE);

    // Operators
    IElementType SEMICOLON = new IElementType("ES.SEMICOLON", CLanguage.INSTANCE);
    IElementType LPARENTH = new IElementType("ES.LPARENTH", CLanguage.INSTANCE);
    IElementType RPARENTH = new IElementType("ES.RPARENTH", CLanguage.INSTANCE);
    IElementType LBRACE = new IElementType("ES.LBRACE", CLanguage.INSTANCE);
    IElementType RBRACE = new IElementType("ES.RBRACE", CLanguage.INSTANCE);
    IElementType EQ = new IElementType("ES.EQ", CLanguage.INSTANCE);
    IElementType PLUS = new IElementType("ES.PLUS", CLanguage.INSTANCE);
    IElementType MINUS = new IElementType("ES.MINUS", CLanguage.INSTANCE);
    IElementType LT = new IElementType("ES.LT", CLanguage.INSTANCE);
    IElementType GT = new IElementType("ES.GT", CLanguage.INSTANCE);
    IElementType EXCL = new IElementType("ES.EXCL", CLanguage.INSTANCE);
    IElementType OR = new IElementType("ES.OR", CLanguage.INSTANCE);
    IElementType AND = new IElementType("ES.AND", CLanguage.INSTANCE);
    IElementType ASTERISK = new IElementType("ES.ASTERISK", CLanguage.INSTANCE);
    IElementType DIV = new IElementType("ES.DIV", CLanguage.INSTANCE);
    IElementType PERC = new IElementType("ES.PERC", CLanguage.INSTANCE);
    IElementType XOR = new IElementType("ES.XOR", CLanguage.INSTANCE);
    IElementType LBRACKET = new IElementType("ES.LBRACKET", CLanguage.INSTANCE);
    IElementType RBRACKET = new IElementType("ES.RBRACKET", CLanguage.INSTANCE);
    IElementType COLON = new IElementType("ES.COLON", CLanguage.INSTANCE);
    IElementType COMMA = new IElementType("ES.COMMA", CLanguage.INSTANCE);
    IElementType DOT = new IElementType("ES.DOT", CLanguage.INSTANCE);
    IElementType QUEST = new IElementType("ES.QUEST", CLanguage.INSTANCE);
    IElementType TILDE = new IElementType("ES.TILDE", CLanguage.INSTANCE);
}
