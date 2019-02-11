package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class EsEnumField extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public EsEnumField() {
        super(CElementTypes.SE_ENUM_FIELD);
    }
}
