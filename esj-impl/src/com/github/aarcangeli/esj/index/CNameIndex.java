package com.github.aarcangeli.esj.index;

import com.github.aarcangeli.esj.CFileType;
import com.github.aarcangeli.esj.psi.SeFile;
import com.github.aarcangeli.esj.psi.SeFileMember;
import com.intellij.util.indexing.*;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CNameIndex extends ScalarIndexExtension<String> {
    public static final ID<String, Void> INDEX_ID = ID.create("es.names.index");

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return file -> file.getFileType() == CFileType.INSTANCE;
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    @NotNull
    @Override
    public ID<String, Void> getName() {
        return INDEX_ID;
    }

    @NotNull
    @Override
    public DataIndexer<String, Void, FileContent> getIndexer() {
        return content -> {
            Map<String, Void> result = new THashMap<>();
            for (SeFileMember member : ((SeFile) content.getPsiFile()).getMembers()) {
                result.put(member.getName(), null);
            }
            return result;
        };
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return new EnumeratorStringDescriptor();
    }

    @Override
    public int getVersion() {
        return 0;
    }
}
