package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CFunction extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public CFunction() {
        super(CElementTypes.SE_FUNCTION);
    }
}
