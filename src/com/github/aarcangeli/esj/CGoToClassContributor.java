package com.github.aarcangeli.esj;

import com.github.aarcangeli.esj.psi.composite.CClassStatement;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CGoToClassContributor implements ChooseByNameContributor {

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        List<String> all = new ArrayList<>();
        fileIndex.iterateContent(virtualFile -> {
            if (virtualFile.getFileType() == CFileType.INSTANCE) {
                PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
                if (file != null) {
                    for (PsiElement child : file.getChildren()) {
                        if (child instanceof CClassStatement) {
                            all.add(((CClassStatement) child).getName());
                        }
                    }
                }
            }
            return true;
        });
        return all.toArray(new String[0]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        List<NavigationItem> all = new ArrayList<>();
        fileIndex.iterateContent(virtualFile -> {
            if (virtualFile.getFileType() == CFileType.INSTANCE) {
                PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
                if (file != null) {
                    for (PsiElement child : file.getChildren()) {
                        if (child instanceof CClassStatement && Objects.equals(((CClassStatement) child).getName(), name)) {
                            all.add((NavigationItem) child);
                        }
                    }
                }
            }
            return true;
        });
        return all.toArray(new NavigationItem[0]);
    }
}
