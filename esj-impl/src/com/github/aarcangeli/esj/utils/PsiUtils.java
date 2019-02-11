package com.github.aarcangeli.esj.utils;

import com.github.aarcangeli.esj.CFileType;
import com.github.aarcangeli.esj.index.CNameIndex;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PsiUtils {
    public static final Key<String> PSI_NAME = Key.create("PSI_NAME");

    @NotNull
    public static TextRange getTextRangeInParent(PsiElement element) {
        return TextRange.from(element.getStartOffsetInParent(), element.getTextLength());
    }

    public static boolean processDeclarations(@Nullable PsiElement element, PsiScopeProcessor processor) {
        if (element == null) return true;
        if (!findInside(element, processor)) return false;
        Project project = element.getProject();
        FileBasedIndex.getInstance().getAllKeys(CNameIndex.INDEX_ID, element.getProject());
        String name = processor.getHint(PSI_NAME);
        if (name != null) {
            GlobalSearchScope scope = GlobalSearchScope.allScope(element.getProject());
            for (VirtualFile file : FileBasedIndex.getInstance().getContainingFiles(CNameIndex.INDEX_ID, name, scope)) {
                PsiFile itFile = PsiManager.getInstance(project).findFile(file);
                if (itFile != null) {
                    if (!findInside(itFile, processor)) return false;
                }
            }
            return true;
        }
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        VirtualFile file = element.getContainingFile().getVirtualFile();
        fileIndex.iterateContent(virtualFile -> {
            if (!Objects.equals(file, virtualFile) && virtualFile.getFileType() == CFileType.INSTANCE) {
                PsiFile itFile = PsiManager.getInstance(project).findFile(virtualFile);
                if (itFile != null) {
                    return findInside(itFile, processor);
                }
            }
            return true;
        });
        return true;
    }

    private static boolean findInside(PsiElement element, PsiScopeProcessor processor) {
        PsiFile file = element.getContainingFile();
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
}
