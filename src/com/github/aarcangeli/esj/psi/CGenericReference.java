package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CFileType;
import com.github.aarcangeli.esj.psi.composite.CEventStatement;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CGenericReference extends PsiReferenceBase<PsiElement> {
    private final String name;
    private PsiElement resolved;

    public CGenericReference(@NotNull PsiElement element, @NotNull TextRange textRangeInElement, String name) {
        super(element, true);
        this.name = name;
        setRangeInElement(textRangeInElement);
    }

    @Override
    @Nullable
    public PsiElement resolve() {

        //FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project))
        Project project = getElement().getProject();
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        fileIndex.iterateContent(virtualFile -> {
            if (virtualFile.getFileType() == CFileType.INSTANCE) {
                PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
                if (file != null) {
                    for (PsiElement child : file.getChildren()) {
                        if (child.getNode().getElementType() == CElementTypes.SE_EVENT_STATEMENT) {
                            CEventStatement statement = (CEventStatement) child;
                            if (Objects.equals(statement.getName(), name)) {
                                resolved = child;
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        });
        return resolved;
    }

    @Override
    public boolean isReferenceTo(@NotNull PsiElement element) {
        return super.isReferenceTo(element);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[]{};
    }
}
