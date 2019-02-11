package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.EsComponent;
import com.intellij.psi.PsiNameIdentifierOwner;

public class EsComponentImpl extends CAbstractMember implements PsiNameIdentifierOwner, EsComponent {
    public EsComponentImpl() {
        super(CElementTypes.SE_COMPONENT_FIELD);
    }
}
