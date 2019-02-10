package com.github.aarcangeli.esj.psi.composites;

import com.github.aarcangeli.esj.lexer.CTokens;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.text.CharArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.aarcangeli.esj.psi.CElementTypes.SE_USE_STATEMENT;

public class CUseStatement extends CompositePsiElement {
    public CUseStatement() {
        super(SE_USE_STATEMENT);
    }

    public String getLinkedFile() {
        PsiElement stringElement = findPsiChildByType(CTokens.C_STRING);
        if (stringElement != null) {
            String result = stringElement.getText();
            result = StringUtil.trimStart(result, "\"");
            result = StringUtil.trimEnd(result, "\"");
            return result;
        }
        return null;
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        CUseStatement element = this;
        String str = getText();
        PsiElement string = findPsiChildByType(CTokens.C_STRING);
        int offset = string.getStartOffsetInParent();
        offset = CharArrayUtil.shiftForward(str, offset, "\"");
        int length = string.getStartOffsetInParent() + string.getTextLength();
        length = CharArrayUtil.shiftBackward(str, length - 1, "\"");
        TextRange range = new TextRange(offset, length);

        PsiReference ref = new PsiReference() {
            @Override
            public PsiElement getElement() {
                return element;
            }

            @Override
            public TextRange getRangeInElement() {
                return range;
            }

            @Nullable
            @Override
            public PsiElement resolve() {
                return null;
            }

            @NotNull
            @Override
            public String getCanonicalText() {
                return str;
            }

            @Override
            public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
                return null;
            }

            @Override
            public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
                return null;
            }

            @Override
            public boolean isReferenceTo(PsiElement element) {
                return false;
            }

            @NotNull
            @Override
            public Object[] getVariants() {
                return new Object[0];
            }

            @Override
            public boolean isSoft() {
                return false;
            }
        };

        return new PsiReference[]{ref};
//        String str = getText();
//        PsiElement string = findPsiChildByType(CTokens.C_STRING);
//        int offset = string.getStartOffsetInParent();
//        offset = CharArrayUtil.shiftForward(str, offset, "\"");
//        return new FileReferenceSet(str, this, offset, null, true).getAllReferences();
    }

    @Override
    public String toString() {
        return "CUseStatement:" + getLinkedFile();
    }
}
