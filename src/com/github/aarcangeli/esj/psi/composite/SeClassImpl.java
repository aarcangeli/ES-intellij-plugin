package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.icons.CIcons;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.headers.SeClass;
import com.github.aarcangeli.esj.psi.headers.SeFile;
import com.github.aarcangeli.esj.psi.headers.SeFileMember;
import com.github.aarcangeli.esj.psi.headers.SeMember;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SeClassImpl extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner, SeClass {
    public SeClassImpl() {
        super(CElementTypes.SE_CLASS_STATEMENT);
    }

    @Override
    public @Nullable
    Icon getIcon(int flags) {
        return CIcons.ES_FILE;
    }

    @Override
    public SeMember[] getAllMembers() {
        List<SeMember> members = new ArrayList<>();
        for (PsiElement child : getChildren()) {
            for (PsiElement child2 : child.getChildren()) {
                if (child2 instanceof SeMember) {
                    members.add((SeMember) child2);
                }
            }
        }
        return members.toArray(SeMember.EMPTY_ARRAY);
    }

    @Override
    public SeFile getContainingFile() {
        return (SeFile) super.getContainingFile();
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        for (SeMember member : getAllMembers()) {
            if (member == lastParent) continue;
            if (!processor.execute(member, state)) return false;
        }
        return true;
    }
}
