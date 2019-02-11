package com.github.aarcangeli.esj;

import com.github.aarcangeli.esj.psi.EsFile;
import com.github.aarcangeli.esj.psi.EsFileMember;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CGoToClassContributor implements ChooseByNameContributor {

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        return getAllSymbolsInProgect(project)
                .stream()
                .map(EsFileMember::getName)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        return getAllSymbolsInProgect(project)
                .stream()
                .filter(symbol -> Objects.equals(symbol.getName(), name))
                .toArray(NavigationItem[]::new);
    }

    List<EsFileMember> getAllSymbolsInProgect(Project project) {
        List<EsFileMember> result = new ArrayList<>();
        ProjectRootManager.getInstance(project).getFileIndex().iterateContent(virtualFile -> {
            if (virtualFile.getFileType() == CFileType.INSTANCE) {
                EsFile file = (EsFile) PsiManager.getInstance(project).findFile(virtualFile);
                if (file != null) {
                    result.addAll(Arrays.asList(file.getMembers()));
                }
            }
            return true;
        });
        return result;
    }
}
