package com.github.aarcangeli.esj;

import com.github.aarcangeli.esj.lexer.CLexerAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.github.aarcangeli.esj.lexer.CTokens.*;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class CHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey KEY_IDENTIFIER = createTextAttributesKey("ES_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey KEY_SEPARATOR = createTextAttributesKey("ES_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey KEY_KEYWORD = createTextAttributesKey("ES_KEY", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey KEY_COMMENT = createTextAttributesKey("ES_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey KEY_STRING = TextAttributesKey.createTextAttributesKey("ES_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey KEY_BAD_CHARACTER = createTextAttributesKey("ES_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
    public static final TextAttributesKey KEY_NUMBER = createTextAttributesKey("ES_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey KEY_CPP_BLOCK = TextAttributesKey.createTextAttributesKey("CG_CPP_BLOCK", EditorColors.INJECTED_LANGUAGE_FRAGMENT);

    private static final Map<IElementType, TextAttributesKey> ourMap = new HashMap<>();

    public final static TokenSet allKeywords = TokenSet.create(K_WHILE, K_FOR, K_IF, K_ELSE, K_ENUM, K_SWITCH, K_CASE,
            K_CLASS, K_BREAK, K_DO, K_VOID, K_CONST, K_INLINE, K_STATIC, K_VIRTUAL, K_RETURN, K_AUTOWAIT, K_AUTOCALL,
            K_DEFAULT, K_WAITEVENT, K_EVENT, K_NAME, K_THUMBNAIL, K_FEATURES, K_USES, K_EXPORT, K_TEXTURE, K_SOUND, K_MODEL,
            K_PROPERTIES, K_COMPONENTS, K_FUNCTIONS, K_PROCEDURES, K_WAIT, K_ON, K_OTHERWISE, K_CALL, K_JUMP, K_STOP,
            K_RESUME, K_PASS, K_CTSTRING, K_CTSTRINGTRANS, K_CTFILENAME, K_CTFILENAMENODEP, K_BOOL, K_COLOR, K_FLOAT,
            K_INDEX, K_RANGE, K_CENTITYPOINTER, K_CMODELOBJECT, K_CMODELINSTANCE, K_CANIMOBJECT, K_CSOUNDOBJECT,
            K_CPLACEMENT3D, K_FLOATAABBOX3D, K_FLOATMATRIX3D, K_FLOATQUAT3D, K_ANGLE, K_FLOAT3D, K_ANGLE3D,
            K_FLOATPLANE3D, K_ANIMATION, K_ILLUMINATIONTYPE, K_FLAGS);

    static {
        ourMap.put(IDENTIFIER, KEY_IDENTIFIER);
        ourMap.put(COMMENT, KEY_COMMENT);

        ourMap.put(CPP_BLOCK, KEY_CPP_BLOCK);
        ourMap.put(CPP_BLOCK_BEGIN, KEY_CPP_BLOCK);
        ourMap.put(CPP_BLOCK_END, KEY_CPP_BLOCK);

        ourMap.put(C_CHAR, KEY_STRING);
        ourMap.put(C_INT, KEY_NUMBER);
        ourMap.put(C_STRING, KEY_STRING);

        fillMap(ourMap, allKeywords, KEY_KEYWORD);

        ourMap.put(TokenType.BAD_CHARACTER, KEY_BAD_CHARACTER);
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new CLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(ourMap.get(tokenType));
    }

    public static class Factory extends SyntaxHighlighterFactory {
        @NotNull
        @Override
        public SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
            return new CHighlighter();
        }
    }
}
