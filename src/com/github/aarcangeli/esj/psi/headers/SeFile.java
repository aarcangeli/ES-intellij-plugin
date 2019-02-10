package com.github.aarcangeli.esj.psi.headers;

import com.intellij.psi.PsiFile;

/**
 * Represents a class
 */
public interface SeFile extends PsiFile {
    SeFile[] EMPTY_ARRAY = new SeFile[0];

    SeFileMember[] getMembers();
}
