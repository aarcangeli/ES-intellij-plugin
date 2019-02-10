package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.icons.CIcons;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.SeFile;
import com.github.aarcangeli.esj.psi.SeFileMember;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CEnumStatement extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner, SeFileMember {
    public CEnumStatement() {
        super(CElementTypes.SE_ENUM_STATEMENT);
    }

    @Override
    public @Nullable Icon getIcon(int flags) {
        return CIcons.ES_FILE;
    }

    @Override
    public SeFile getContainingFile() {
        return (SeFile) super.getContainingFile();
    }
}
