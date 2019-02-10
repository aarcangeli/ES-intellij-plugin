package com.github.aarcangeli.esj.cidr;

import com.github.aarcangeli.esj.CFileType;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.cidr.execution.debugger.breakpoints.CidrLineBreakpointFileTypesProvider;

import java.util.Set;

public class CLineBreakpointFileTypesProvider implements CidrLineBreakpointFileTypesProvider {
    private static final Set<FileType> TYPES = ContainerUtil.immutableSet(new FileType[]{CFileType.INSTANCE});

    @Override
    public Set<FileType> getFileTypes() {
        return TYPES;
    }
}
