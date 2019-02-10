package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CFileType;
import com.github.aarcangeli.esj.CLanguage;
import com.github.aarcangeli.esj.psi.headers.SeFile;
import com.github.aarcangeli.esj.psi.headers.SeFileMember;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CFile extends PsiFileBase implements SeFile {
    public CFile(FileViewProvider viewProvider) {
        super(viewProvider, CLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return CFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "ES File: " + getName();
    }

    @Override
    public SeFileMember[] getMembers() {
        List<SeFileMember> members = new ArrayList<>();
        for (PsiElement child : getChildren()) {
            if (child instanceof SeFileMember) {
                members.add((SeFileMember) child);
            }
        }
        return members.toArray(SeFileMember.EMPTY_ARRAY);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        for (SeFileMember member : getMembers()) {
            if (member == lastParent) continue;
            if (!processor.execute(member, state)) return false;
        }
        return true;
    }
}
