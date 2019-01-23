package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CFileType;
import com.github.aarcangeli.esj.lexer.CTokens;
import com.github.aarcangeli.esj.psi.composite.CAbstractNamedIdentifier;
import com.github.aarcangeli.esj.psi.composite.CClassStatement;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.github.aarcangeli.esj.psi.CElementTypes.*;

public class CGenericReference extends PsiReferenceBase<PsiElement> {
    private final String name;
    private PsiElement resolved;
    private final TokenSet SET_TO_GO_INSIDE = TokenSet.create(SE_CLASS_STATEMENT, SE_PROPERTIES_BLOCK, SE_COMPONENTS_BLOCK,
            SE_FUNCTIONS_BLOCK, SE_PROCEDURES_BLOCK, SE_EVENT_STATEMENT, SE_ENUM_STATEMENT);

    public CGenericReference(@NotNull PsiElement element, @NotNull TextRange textRangeInElement, String name) {
        super(element, true);
        this.name = name;
        setRangeInElement(textRangeInElement);
    }

    @Override
    @Nullable
    public PsiElement resolve() {
        PsiFile file = getElement().getContainingFile();
        PsiElement it = getElement();
        // find named psi inside every parent until file
        while (it != file) {
            PsiElement lastChild = it;
            it = it.getParent();
            if (!findInside(it, lastChild)) return resolved;
        }
        //FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project))
        Project project = getElement().getProject();
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        fileIndex.iterateContent(virtualFile -> {
            if (virtualFile.getFileType() == CFileType.INSTANCE) {
                PsiFile itFile = PsiManager.getInstance(project).findFile(virtualFile);
                if (itFile != null) {
                    return findInside(itFile, null);
                }
            }
            return true;
        });
        return resolved;
    }

    private boolean findInside(PsiElement element, PsiElement lastChild) {
        for (PsiElement child : element.getChildren()) {
            if (lastChild != child) {
                if (child instanceof CAbstractNamedIdentifier) {
                    CAbstractNamedIdentifier statement = (CAbstractNamedIdentifier) child;
                    if (Objects.equals(statement.getName(), name)) {
                        resolved = child;
                        return false;
                    }
                }
                if (SET_TO_GO_INSIDE.contains(child.getNode().getElementType())) {
                    if (!findInside(child, null)) return false;
                }
            }
        }
        return true;
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
