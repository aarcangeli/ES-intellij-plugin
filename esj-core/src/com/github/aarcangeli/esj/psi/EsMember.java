package com.github.aarcangeli.esj.psi;

import com.intellij.psi.PsiElement;

/**
 * Represents a member of a class (for example, a property, a component, a function or a procedure).
 */
public interface EsMember extends PsiElement {
    EsMember[] EMPTY_ARRAY = new EsMember[0];

    EsClass getContainingClass();
}
