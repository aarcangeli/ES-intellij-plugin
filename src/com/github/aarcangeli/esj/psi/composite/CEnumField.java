package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CEnumField extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public CEnumField() {
        super(CElementTypes.SE_ENUM_FIELD);
    }
}
