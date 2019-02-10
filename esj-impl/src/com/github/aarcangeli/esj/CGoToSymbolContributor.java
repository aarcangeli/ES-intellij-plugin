package com.github.aarcangeli.esj;

import com.github.aarcangeli.esj.CFileType;
import com.github.aarcangeli.esj.psi.composite.CAbstractNamedIdentifier;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.aarcangeli.esj.psi.CElementTypes.*;

public class CGoToSymbolContributor implements ChooseByNameContributor {
    TokenSet SYMBOL_CONTAINERS = TokenSet.create(
            SE_CLASS_STATEMENT, SE_EVENT_STATEMENT, SE_ENUM_STATEMENT,
            SE_PROPERTIES_BLOCK, SE_COMPONENTS_BLOCK, SE_FUNCTIONS_BLOCK, SE_PROCEDURES_BLOCK
    );

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        return getAllSymbolsInProgect(project)
                .stream()
                .map(CAbstractNamedIdentifier::getName)
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

    List<CAbstractNamedIdentifier> getAllSymbolsInProgect(Project project) {
        List<CAbstractNamedIdentifier> result = new ArrayList<>();
        ProjectRootManager.getInstance(project).getFileIndex().iterateContent(virtualFile -> {
            if (virtualFile.getFileType() == CFileType.INSTANCE) {
                PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
                if (file != null) {
                    collectItemsInside(result, file);
                }
            }
            return true;
        });
        return result;
    }

    private void collectItemsInside(List<CAbstractNamedIdentifier> result, PsiElement element) {
        for (PsiElement child : element.getChildren()) {
            if (SYMBOL_CONTAINERS.contains(child.getNode().getElementType())) {
                collectItemsInside(result, child);
            }
            if (child instanceof CAbstractNamedIdentifier) {
                result.add((CAbstractNamedIdentifier) child);
            }
        }
    }
}
