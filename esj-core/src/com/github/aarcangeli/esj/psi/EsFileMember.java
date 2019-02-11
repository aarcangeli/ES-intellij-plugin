package com.github.aarcangeli.esj.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;

/**
 * Represents an entity (for example, a class, en event, a struct)
 */
public interface EsFileMember extends PsiElement, NavigationItem {
    EsFileMember[] EMPTY_ARRAY = new EsFileMember[0];

    EsFile getContainingFile();

    String getName();
}
