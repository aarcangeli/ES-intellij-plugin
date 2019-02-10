package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CFileType;
import com.github.aarcangeli.esj.psi.composite.CAbstractNamedIdentifier;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.scope.BaseScopeProcessor;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.github.aarcangeli.esj.psi.CElementTypes.*;

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
        resolved = null;
        if (!findInside(getElement())) return resolved;
        // todo: resolve in parent class
        if ("".isEmpty()) return null;
        //FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project))
        Project project = getElement().getProject();
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        fileIndex.iterateContent(virtualFile -> {
            if (virtualFile.getFileType() == CFileType.INSTANCE) {
                PsiFile itFile = PsiManager.getInstance(project).findFile(virtualFile);
                if (itFile != null) {
                    return findInside(itFile);
                }
            }
            return true;
        });
        return resolved;
    }

    private boolean findInside(PsiElement element) {
        PsiScopeProcessor processor = new BaseScopeProcessor() {
            @Override
            public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
                if (element instanceof CAbstractNamedIdentifier) {
                    if (Objects.equals(((CAbstractNamedIdentifier) element).getName(), name)) {
                        resolved = element;
                        return false;
                    }
                }
                return true;
            }
        };
        PsiFile file = getElement().getContainingFile();
        PsiElement lastParent = null;
        PsiElement it = element;
        while (it != null && lastParent != file) {
            if (!processor.execute(it, ResolveState.initial())) return false;
            if (!it.processDeclarations(processor, ResolveState.initial(), lastParent, element)) return false;
            lastParent = it;
            it = it.getContext();
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
