package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CLanguage;
import com.github.aarcangeli.esj.lexer.CTokens;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;

public interface CElementTypes extends CTokens {
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

    // class
    IElementType SE_CLASS_ATTRIBUTE = new CCompositeElementType("SE_CLASS_ATTRIBUTE");

    IElementType SE_PROPERTIES_BLOCK = new CCompositeElementType("SE_PROPERTIES_BLOCK");
    IElementType SE_COMPONENTS_BLOCK = new CCompositeElementType("SE_COMPONENTS_BLOCK");
    IElementType SE_FUNCTIONS_BLOCK = new CCompositeElementType("SE_FUNCTIONS_BLOCK");
    IElementType SE_PROCEDURES_BLOCK = new CCompositeElementType("SE_PROCEDURES_BLOCK");

    IElementType SE_PROPERTY_FIELD = new CCompositeElementType("SE_PROPERTY_FIELD");
    IElementType SE_PROPERTY_WED = new CCompositeElementType("SE_PROPERTY_FIELD");
    IElementType SE_PROPERTY_SHORTCUT = new CCompositeElementType("SE_PROPERTY_SHORTCUT");
    IElementType SE_PROPERTY_COLOR = new CCompositeElementType("SE_PROPERTY_COLOR");
    IElementType SE_PROPERTY_DEFAULT_VALUE = new CCompositeElementType("SE_PROPERTY_DEFAULT_VALUE");
    IElementType SE_INTERNAL_PROPERTY_BLOCK = new CCompositeElementType("SE_INTERNAL_PROPERTY_BLOCK");
    IElementType SE_INTERNAL_PROPERTY = new CCompositeElementType("SE_INTERNAL_PROPERTY");

    IElementType SE_COMPONENT_FIELD = new CCompositeElementType("SE_COMPONENT_FIELD");

    IElementType SE_FUNCTION = new CCompositeElementType("SE_FUNCTION");
    IElementType SE_FUNCTION_MODIFIERS = new CCompositeElementType("SE_FUNCTION_MODIFIERS");
    IElementType SE_PARAMETER_LIST = new CCompositeElementType("SE_PARAMETER_LIST");
    IElementType SE_PARAMETER = new CCompositeElementType("SE_PARAMETER");

    IElementType SE_CODE_BLOCK = new CCompositeElementType("SE_CODE_BLOCK");

    IElementType SE_BLOCK_STATEMENT = new CCompositeElementType("SE_BLOCK_STATEMENT");
    IElementType SE_IF_STATEMENT = new CCompositeElementType("SE_IF_STATEMENT");
}
