package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CEventStatement extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public CEventStatement() {
        super(CElementTypes.SE_EVENT_STATEMENT);
    }
}
