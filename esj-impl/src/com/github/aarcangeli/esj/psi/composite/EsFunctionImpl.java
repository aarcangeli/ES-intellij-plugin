package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.EsFunction;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EsFunctionImpl extends CAbstractMember implements PsiNameIdentifierOwner, EsFunction {
    private TokenSet PARAMETER_SET = TokenSet.create(CElementTypes.SE_PARAMETER);

    public EsFunctionImpl() {
        super(CElementTypes.SE_FUNCTION);
    }

    EsParameter[] getParameters() {
        List<EsParameter> parameters = new ArrayList<>();
        ASTNode list = findChildByType(CElementTypes.SE_PARAMETER_LIST);
        if (list != null) {
            for (ASTNode child : list.getChildren(PARAMETER_SET)) {
                parameters.add((EsParameter) child.getPsi());
            }
        }
        return parameters.toArray(EsParameter.EMPTY);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        for (EsParameter parameter : getParameters()) {
            if (parameter == lastParent) continue;
            if (!processor.execute(parameter, state)) return false;
        }
        return true;
    }
}
