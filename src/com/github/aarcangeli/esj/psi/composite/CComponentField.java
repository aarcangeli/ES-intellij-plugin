package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CComponentField extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public CComponentField() {
        super(CElementTypes.SE_COMPONENT_FIELD);
    }
}
