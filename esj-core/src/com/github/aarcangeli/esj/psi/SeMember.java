package com.github.aarcangeli.esj.psi;

import com.intellij.psi.PsiElement;

/**
 * Represents a member of a class (for example, a property, a component, a function or a procedure).
 */
public interface SeMember extends PsiElement {
    SeMember[] EMPTY_ARRAY = new SeMember[0];

    SeClass getContainingClass();
}
