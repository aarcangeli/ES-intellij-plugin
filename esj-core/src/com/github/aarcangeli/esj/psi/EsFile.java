package com.github.aarcangeli.esj.psi;

import com.intellij.psi.PsiFile;

/**
 * Represents a class
 */
public interface EsFile extends PsiFile {
    EsFile[] EMPTY_ARRAY = new EsFile[0];

    EsFileMember[] getMembers();
}
