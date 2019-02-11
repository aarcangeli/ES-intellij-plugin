package com.github.aarcangeli.esj.psi.composite;

import com.github.aarcangeli.esj.icons.CIcons;
import com.github.aarcangeli.esj.psi.CElementTypes;
import com.github.aarcangeli.esj.psi.EsEnum;
import com.github.aarcangeli.esj.psi.EsFile;
import com.github.aarcangeli.esj.psi.EsFileMember;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class EsEnumImpl extends CAbstractNamedIdentifier implements PsiNameIdentifierOwner, EsFileMember, EsEnum {
    public EsEnumImpl() {
        super(CElementTypes.SE_ENUM_STATEMENT);
    }

    @Override
    public @Nullable Icon getIcon(int flags) {
        return CIcons.ES_FILE;
    }

    @Override
    public EsFile getContainingFile() {
        return (EsFile) super.getContainingFile();
    }
}
