package com.github.aarcangeli.esj.psi.headers;

import com.intellij.psi.PsiElement;

/**
 * Represents a class
 */
public interface SeClass extends SeFileMember {
    SeClass[] EMPTY_ARRAY = new SeClass[0];

    SeMember[] getAllMembers();
}
