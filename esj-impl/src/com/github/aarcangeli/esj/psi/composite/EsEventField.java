package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.psi.PsiNameIdentifierOwner;

public class EsEventField extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner {
    public static final EsEventField[] EMPTY_ARRAY = new EsEventField[0];

    public EsEventField() {
        super(CElementTypes.SE_EVENT_FIELD);
    }
}
