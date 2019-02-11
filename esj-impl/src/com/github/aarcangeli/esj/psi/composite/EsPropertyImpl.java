package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.EsProperty;
import com.intellij.psi.PsiNameIdentifierOwner;

public class EsPropertyImpl extends CAbstractMember implements PsiNameIdentifierOwner, EsProperty {
    public EsPropertyImpl() {
        super(CElementTypes.SE_PROPERTY_FIELD);
    }
}
