package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CEventField extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public CEventField() {
        super(CElementTypes.SE_EVENT_FIELD);
    }
}
