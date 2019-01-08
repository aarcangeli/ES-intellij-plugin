package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CProcedure extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public CProcedure() {
        super(CElementTypes.SE_PROCEDURE);
    }
}
