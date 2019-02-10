package com.github.aarcangeli.esj.utils;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class PsiUtils {
    @NotNull
    public static TextRange getTextRangeInParent(PsiElement element) {
        return TextRange.from(element.getStartOffsetInParent(), element.getTextLength());
    }
}
