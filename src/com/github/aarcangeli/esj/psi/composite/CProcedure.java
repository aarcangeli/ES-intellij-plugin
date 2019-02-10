package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.headers.SeClass;
import com.github.aarcangeli.esj.psi.headers.SeMember;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;

public class CProcedure extends CAbstractMember implements PsiNameIdentifierOwner {
    public CProcedure() {
        super(CElementTypes.SE_PROCEDURE);
    }

}
