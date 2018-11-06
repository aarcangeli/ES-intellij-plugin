package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CLanguage;
import com.intellij.psi.tree.IFileElementType;

public interface CElementType {
    IFileElementType SE_FILE = new IFileElementType("SE_FILE", CLanguage.INSTANCE);
}
