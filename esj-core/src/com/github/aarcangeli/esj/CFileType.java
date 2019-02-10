package com.github.aarcangeli.esj;

import com.github.aarcangeli.esj.icons.CIcons;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CFileType extends LanguageFileType {
    public static CFileType INSTANCE = new CFileType();

    private CFileType() {
        super(CLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "ES File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Serious Engine ES file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "es";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return CIcons.ES_FILE;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    public static class Factory extends FileTypeFactory {
        @Override
        public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
            fileTypeConsumer.consume(INSTANCE, "es");
            fileTypeConsumer.consume(INSTANCE, new FileNameMatcher() {
                @Override
                public boolean accept(@NotNull String filename) {
                    return filename.toLowerCase().endsWith(".es");
                }

                @Override
                public @NotNull String getPresentableString() {
                    return "matcher";
                }
            });
        }
    }
}
