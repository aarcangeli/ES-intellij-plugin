package com.github.aarcangeli.esj.lexer;

import com.github.aarcangeli.esj.CLanguage;
import com.intellij.psi.tree.IElementType;

public class CTokens {
    // basic tokens
    public static final IElementType IDENTIFIER = new IElementType("ES.IDENTIFIER", CLanguage.INSTANCE);
    public static final IElementType COMMENT = new IElementType("ES.COMMENT", CLanguage.INSTANCE);

    // inline C++ blocks
    public static final IElementType CPP_BLOCK = new IElementType("ES.CPP_BLOCK", CLanguage.INSTANCE);
    public static final IElementType CPP_BLOCK_BEGIN = new IElementType("ES.CPP_BLOCK_BEGIN", CLanguage.INSTANCE);
    public static final IElementType CPP_BLOCK_END = new IElementType("ES.CPP_BLOCK_END", CLanguage.INSTANCE);

    // different type of constants
    public static final IElementType C_CHAR = new IElementType("ES.C_CHAR", CLanguage.INSTANCE);
    public static final IElementType C_INT = new IElementType("ES.C_INT", CLanguage.INSTANCE);
    public static final IElementType C_STRING = new IElementType("ES.C_STRING", CLanguage.INSTANCE);

    // standard cpp-keywords
    public static final IElementType K_WHILE = new IElementType("ES.K_WHILE", CLanguage.INSTANCE);
    public static final IElementType K_FOR = new IElementType("ES.K_FOR", CLanguage.INSTANCE);
    public static final IElementType K_IF = new IElementType("ES.K_IF", CLanguage.INSTANCE);
    public static final IElementType K_ELSE = new IElementType("ES.K_ELSE", CLanguage.INSTANCE);
    public static final IElementType K_ENUM = new IElementType("ES.K_ENUM", CLanguage.INSTANCE);
    public static final IElementType K_SWITCH = new IElementType("ES.K_SWITCH", CLanguage.INSTANCE);
    public static final IElementType K_CASE = new IElementType("ES.K_CASE", CLanguage.INSTANCE);
    public static final IElementType K_CLASS = new IElementType("ES.K_CLASS", CLanguage.INSTANCE);
    public static final IElementType K_DO = new IElementType("ES.K_DO", CLanguage.INSTANCE);
    public static final IElementType K_VOID = new IElementType("ES.K_VOID", CLanguage.INSTANCE);
    public static final IElementType K_CONST = new IElementType("ES.K_CONST", CLanguage.INSTANCE);
    public static final IElementType K_INLINE = new IElementType("ES.K_INLINE", CLanguage.INSTANCE);
    public static final IElementType K_STATIC = new IElementType("ES.K_STATIC", CLanguage.INSTANCE);
    public static final IElementType K_VIRTUAL = new IElementType("ES.K_VIRTUAL", CLanguage.INSTANCE);
    public static final IElementType K_RETURN = new IElementType("ES.K_RETURN", CLanguage.INSTANCE);
    public static final IElementType K_AUTOWAIT = new IElementType("ES.K_AUTOWAIT", CLanguage.INSTANCE);
    public static final IElementType K_AUTOCALL = new IElementType("ES.K_AUTOCALL", CLanguage.INSTANCE);
    public static final IElementType K_WAITEVENT = new IElementType("ES.K_WAITEVENT", CLanguage.INSTANCE);

    // aditional keywords
    public static final IElementType K_EVENT = new IElementType("ES.K_EVENT", CLanguage.INSTANCE);
    public static final IElementType K_NAME = new IElementType("ES.K_NAME", CLanguage.INSTANCE);
    public static final IElementType K_THUMBNAIL = new IElementType("ES.K_THUMBNAIL", CLanguage.INSTANCE);
    public static final IElementType K_FEATURES = new IElementType("ES.K_FEATURES", CLanguage.INSTANCE);
    public static final IElementType K_USES = new IElementType("ES.K_USES", CLanguage.INSTANCE);
    public static final IElementType K_EXPORT = new IElementType("ES.K_EXPORT", CLanguage.INSTANCE);

    public static final IElementType K_TEXTURE = new IElementType("ES.K_TEXTURE", CLanguage.INSTANCE);
    public static final IElementType K_SOUND = new IElementType("ES.K_SOUND", CLanguage.INSTANCE);
    public static final IElementType K_MODEL = new IElementType("ES.K_MODEL", CLanguage.INSTANCE);

    public static final IElementType K_PROPERTIES = new IElementType("ES.K_PROPERTIES", CLanguage.INSTANCE);
    public static final IElementType K_COMPONENTS = new IElementType("ES.K_COMPONENTS", CLanguage.INSTANCE);
    public static final IElementType K_FUNCTIONS = new IElementType("ES.K_FUNCTIONS", CLanguage.INSTANCE);
    public static final IElementType K_PROCEDURES = new IElementType("ES.K_PROCEDURES", CLanguage.INSTANCE);

    public static final IElementType K_WAIT = new IElementType("ES.K_WAIT", CLanguage.INSTANCE);
    public static final IElementType K_ON = new IElementType("ES.K_ON", CLanguage.INSTANCE);
    public static final IElementType K_OTHERWISE = new IElementType("ES.K_OTHERWISE", CLanguage.INSTANCE);

    public static final IElementType K_CALL = new IElementType("ES.K_CALL", CLanguage.INSTANCE);
    public static final IElementType K_JUMP = new IElementType("ES.K_JUMP", CLanguage.INSTANCE);
    public static final IElementType K_STOP = new IElementType("ES.K_STOP", CLanguage.INSTANCE);
    public static final IElementType K_RESUME = new IElementType("ES.K_RESUME", CLanguage.INSTANCE);
    public static final IElementType K_PASS = new IElementType("ES.K_PASS", CLanguage.INSTANCE);

    // special data types
    public static final IElementType K_CTSTRING = new IElementType("ES.K_CTSTRING", CLanguage.INSTANCE);
    public static final IElementType K_CTSTRINGTRANS = new IElementType("ES.K_CTSTRINGTRANS", CLanguage.INSTANCE);
    public static final IElementType K_CTFILENAME = new IElementType("ES.K_CTFILENAME", CLanguage.INSTANCE);
    public static final IElementType K_CTFILENAMENODEP = new IElementType("ES.K_CTFILENAMENODEP", CLanguage.INSTANCE);
    public static final IElementType K_BOOL = new IElementType("ES.K_BOOL", CLanguage.INSTANCE);
    public static final IElementType K_COLOR = new IElementType("ES.K_COLOR", CLanguage.INSTANCE);
    public static final IElementType K_FLOAT = new IElementType("ES.K_FLOAT", CLanguage.INSTANCE);
    public static final IElementType K_INDEX = new IElementType("ES.K_INDEX", CLanguage.INSTANCE);
    public static final IElementType K_RANGE = new IElementType("ES.K_RANGE", CLanguage.INSTANCE);
    public static final IElementType K_CENTITYPOINTER = new IElementType("ES.K_CENTITYPOINTER", CLanguage.INSTANCE);
    public static final IElementType K_CMODELOBJECT = new IElementType("ES.K_CMODELOBJECT", CLanguage.INSTANCE);
    public static final IElementType K_CMODELINSTANCE = new IElementType("ES.K_CMODELINSTANCE", CLanguage.INSTANCE);
    public static final IElementType K_CANIMOBJECT = new IElementType("ES.K_CANIMOBJECT", CLanguage.INSTANCE);
    public static final IElementType K_CSOUNDOBJECT = new IElementType("ES.K_CSOUNDOBJECT", CLanguage.INSTANCE);
    public static final IElementType K_CPLACEMENT3D = new IElementType("ES.K_CPLACEMENT3D", CLanguage.INSTANCE);
    public static final IElementType K_FLOATAABBOX3D = new IElementType("ES.K_FLOATAABBOX3D", CLanguage.INSTANCE);
    public static final IElementType K_FLOATMATRIX3D = new IElementType("ES.K_FLOATMATRIX3D", CLanguage.INSTANCE);
    public static final IElementType K_FLOATQUAT3D = new IElementType("ES.K_FLOATQUAT3D", CLanguage.INSTANCE);
    public static final IElementType K_ANGLE = new IElementType("ES.K_ANGLE", CLanguage.INSTANCE);
    public static final IElementType K_FLOAT3D = new IElementType("ES.K_FLOAT3D", CLanguage.INSTANCE);
    public static final IElementType K_ANGLE3D = new IElementType("ES.K_ANGLE3D", CLanguage.INSTANCE);
    public static final IElementType K_FLOATPLANE3D = new IElementType("ES.K_FLOATPLANE3D", CLanguage.INSTANCE);
    public static final IElementType K_ANIMATION = new IElementType("ES.K_ANIMATION", CLanguage.INSTANCE);
    public static final IElementType K_ILLUMINATIONTYPE = new IElementType("ES.K_ILLUMINATIONTYPE", CLanguage.INSTANCE);
    public static final IElementType K_FLAGS = new IElementType("ES.K_FLAGS", CLanguage.INSTANCE);

    // Operators
    public static final IElementType SEMICOLON = new IElementType("ES.SEMICOLON", CLanguage.INSTANCE);
    public static final IElementType LPARENTH = new IElementType("ES.LPARENTH", CLanguage.INSTANCE);
    public static final IElementType RPARENTH = new IElementType("ES.RPARENTH", CLanguage.INSTANCE);
    public static final IElementType LBRACE = new IElementType("ES.LBRACE", CLanguage.INSTANCE);
    public static final IElementType RBRACE = new IElementType("ES.RBRACE", CLanguage.INSTANCE);
    public static final IElementType EQ = new IElementType("ES.EQ", CLanguage.INSTANCE);
    public static final IElementType PLUS = new IElementType("ES.PLUS", CLanguage.INSTANCE);
    public static final IElementType MINUS = new IElementType("ES.MINUS", CLanguage.INSTANCE);
    public static final IElementType LT = new IElementType("ES.LT", CLanguage.INSTANCE);
    public static final IElementType GT = new IElementType("ES.GT", CLanguage.INSTANCE);
    public static final IElementType EXCL = new IElementType("ES.EXCL", CLanguage.INSTANCE);
    public static final IElementType OR = new IElementType("ES.OR", CLanguage.INSTANCE);
    public static final IElementType AND = new IElementType("ES.AND", CLanguage.INSTANCE);
    public static final IElementType ASTERISK = new IElementType("ES.ASTERISK", CLanguage.INSTANCE);
    public static final IElementType DIV = new IElementType("ES.DIV", CLanguage.INSTANCE);
    public static final IElementType PERC = new IElementType("ES.PERC", CLanguage.INSTANCE);
    public static final IElementType XOR = new IElementType("ES.XOR", CLanguage.INSTANCE);
    public static final IElementType LBRACKET = new IElementType("ES.LBRACKET", CLanguage.INSTANCE);
    public static final IElementType RBRACKET = new IElementType("ES.RBRACKET", CLanguage.INSTANCE);
    public static final IElementType COLON = new IElementType("ES.COLON", CLanguage.INSTANCE);
    public static final IElementType COMMA = new IElementType("ES.COMMA", CLanguage.INSTANCE);
    public static final IElementType DOT = new IElementType("ES.DOT", CLanguage.INSTANCE);
    public static final IElementType QUEST = new IElementType("ES.QUEST", CLanguage.INSTANCE);
    public static final IElementType TILDE = new IElementType("ES.TILDE", CLanguage.INSTANCE);
}
