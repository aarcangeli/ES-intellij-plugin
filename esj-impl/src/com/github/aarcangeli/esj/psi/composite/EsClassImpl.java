package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.icons.CIcons;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.EsClass;
import com.github.aarcangeli.esj.psi.EsFile;
import com.github.aarcangeli.esj.psi.EsMember;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EsClassImpl extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner, EsClass {
    public EsClassImpl() {
        super(CElementTypes.SE_CLASS_STATEMENT);
    }

    @Override
    public @Nullable
    Icon getIcon(int flags) {
        return CIcons.ES_FILE;
    }

    @Override
    public EsMember[] getAllMembers() {
        List<EsMember> members = new ArrayList<>();
        for (PsiElement child : getChildren()) {
            for (PsiElement child2 : child.getChildren()) {
                if (child2 instanceof EsMember) {
                    members.add((EsMember) child2);
                }
            }
        }
        return members.toArray(EsMember.EMPTY_ARRAY);
    }

    @Override
    public EsFile getContainingFile() {
        return (EsFile) super.getContainingFile();
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        for (EsMember member : getAllMembers()) {
            if (member == lastParent) continue;
            if (!processor.execute(member, state)) return false;
        }
        return true;
    }
}
