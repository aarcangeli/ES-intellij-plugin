package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CFileType;
import com.github.aarcangeli.esj.CLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class CFile extends PsiFileBase {
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
}
