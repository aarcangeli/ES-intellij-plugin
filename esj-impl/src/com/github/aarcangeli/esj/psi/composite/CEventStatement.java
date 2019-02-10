package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.icons.CIcons;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.SeFile;
import com.github.aarcangeli.esj.psi.SeFileMember;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CEventStatement extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner, SeFileMember {
    public CEventStatement() {
        super(CElementTypes.SE_EVENT_STATEMENT);
    }

    @Override
    public @Nullable
    Icon getIcon(int flags) {
        return CIcons.ES_FILE;
    }

    @Override
    public SeFile getContainingFile() {
        return (SeFile) super.getContainingFile();
    }

    CEventField[] getFields() {
        List<CEventField> members = new ArrayList<>();
        for (PsiElement child : getChildren()) {
            for (PsiElement child2 : child.getChildren()) {
                if (child2 instanceof CEventField) {
                    members.add((CEventField) child2);
                }
            }
        }
        return members.toArray(CEventField.EMPTY_ARRAY);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        for (CEventField field : getFields()) {
            if (field == lastParent) continue;
            if (!processor.execute(field, state)) return false;
        }
        return true;
    }
}
