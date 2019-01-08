package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CEnumStatement extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public CEnumStatement() {
        super(CElementTypes.SE_ENUM_STATEMENT);
    }
}
