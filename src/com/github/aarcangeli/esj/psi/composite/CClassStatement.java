package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CClassStatement extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public CClassStatement() {
        super(CElementTypes.SE_CLASS_STATEMENT);
    }
}
