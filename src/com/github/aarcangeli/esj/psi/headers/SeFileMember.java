package com.github.aarcangeli.esj.psi.headers;

import com.intellij.psi.PsiElement;

/**
 * Represents a class
 */
public interface SeFileMember extends PsiElement {
    SeFileMember[] EMPTY_ARRAY = new SeFileMember[0];

    SeFile getContainingFile();
}
