package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.icons.CIcons;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.EsEvent;
import com.github.aarcangeli.esj.psi.EsFile;
import com.github.aarcangeli.esj.psi.EsFileMember;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EsEventImpl extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner, EsFileMember, EsEvent {
    public EsEventImpl() {
        super(CElementTypes.SE_EVENT_STATEMENT);
    }

    @Override
    public @Nullable
    Icon getIcon(int flags) {
        return CIcons.ES_FILE;
    }

    @Override
    public EsFile getContainingFile() {
        return (EsFile) super.getContainingFile();
    }

    EsEventField[] getFields() {
        List<EsEventField> members = new ArrayList<>();
        for (PsiElement child : getChildren()) {
            for (PsiElement child2 : child.getChildren()) {
                if (child2 instanceof EsEventField) {
                    members.add((EsEventField) child2);
                }
            }
        }
        return members.toArray(EsEventField.EMPTY_ARRAY);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        for (EsEventField field : getFields()) {
            if (field == lastParent) continue;
            if (!processor.execute(field, state)) return false;
        }
        return true;
    }
}
