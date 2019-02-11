package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CFileType;
import com.github.aarcangeli.esj.psi.composite.EsClassImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;

public class SePsiUtils {
    public static void replaceWithIdentifier(PsiElement oldElement, String newName) {
        if (!newName.equals(oldElement.getText())) {
            PsiFileFactory factory = PsiFileFactory.getInstance(oldElement.getProject());
            PsiFile file = factory.createFileFromText("dummy.es", CFileType.INSTANCE, "class " + newName);
            EsClassImpl childClass = ((CFile) file).findChildByClass(EsClassImpl.class);
            if (childClass != null) {
                PsiElement nameIdentifier = childClass.getNameIdentifier();
                if (nameIdentifier != null) {
                    ASTNode from = oldElement.getNode();
                    ASTNode parent = from.getTreeParent();
                    parent.replaceChild(from, nameIdentifier.getNode());
                }
            }
        }
    }
}
