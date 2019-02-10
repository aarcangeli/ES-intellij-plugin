package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;

public class CProcedure extends CAbstractMember implements PsiNameIdentifierOwner {
    public CProcedure() {
        super(CElementTypes.SE_PROCEDURE);
    }

    CEventSpecification getEventSpecification() {
        ASTNode list = findChildByType(CElementTypes.SE_EVENT_SPECIFICATION);
        if (list != null) {
            return (CEventSpecification) list.getPsi();
        }
        return null;
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        CEventSpecification eventSpecification = getEventSpecification();
        if (eventSpecification != null && !processor.execute(eventSpecification, state)) return false;
        return true;
    }
}
