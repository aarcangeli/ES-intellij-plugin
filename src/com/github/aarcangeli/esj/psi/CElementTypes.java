package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CLanguage;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;

public interface CElementTypes {
    IFileElementType SE_FILE = new IFileElementType("SE_FILE", CLanguage.INSTANCE);

    // commons
    IElementType SE_CLASS_ID = new CCompositeElementType("SE_CLASS_ID");
    IElementType SE_CPP_BLOCK = new CCompositeElementType("SE_CPP_BLOCK");

    // types
    IElementType SE_TYPE = new CCompositeElementType("SE_TYPE");
    IElementType SE_TYPE_MODIFIERS = new CCompositeElementType("SE_TYPE_MODIFIERS");

    // statements
    IElementType SE_USE_STATEMENT = new CCompositeElementType("SE_USE_STATEMENT");
    IElementType SE_ENUM_STATEMENT = new CCompositeElementType("SE_ENUM_STATEMENT");
    IElementType SE_EVENT_STATEMENT = new CCompositeElementType("SE_EVENT_STATEMENT");
    IElementType SE_CLASS_STATEMENT = new CCompositeElementType("SE_CLASS_STATEMENT");

    // fields
    IElementType SE_ENUM_FIELD = new CCompositeElementType("SE_ENUM_FIELD");
    IElementType SE_EVENT_FIELD = new CCompositeElementType("SE_EVENT_FIELD");
}
