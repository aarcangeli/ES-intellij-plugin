package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CPropertyField extends CAbstractMember implements PsiNameIdentifierOwner {
    public CPropertyField() {
        super(CElementTypes.SE_PROPERTY_FIELD);
    }
}
