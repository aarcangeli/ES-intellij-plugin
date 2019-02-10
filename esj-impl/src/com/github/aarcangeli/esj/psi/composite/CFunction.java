package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CFunction extends CAbstractMember implements PsiNameIdentifierOwner {
    private TokenSet PARAMETER_SET = TokenSet.create(CElementTypes.SE_PARAMETER);

    public CFunction() {
        super(CElementTypes.SE_FUNCTION);
    }

    CParameter[] getParameters() {
        List<CParameter> parameters = new ArrayList<>();
        ASTNode list = findChildByType(CElementTypes.SE_PARAMETER_LIST);
        if (list != null) {
            for (ASTNode child : list.getChildren(PARAMETER_SET)) {
                parameters.add((CParameter) child.getPsi());
            }
        }
        return parameters.toArray(CParameter.EMPTY);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        for (CParameter parameter : getParameters()) {
            if (parameter == lastParent) continue;
            if (!processor.execute(parameter, state)) return false;
        }
        return true;
    }
}
