package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CLanguage;
import com.github.aarcangeli.esj.lexer.CTokens;
import com.github.aarcangeli.esj.psi.composite.*;
import com.github.aarcangeli.esj.psi.composites.CUseStatement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CElementTypes extends CTokens {
    IFileElementType SE_FILE = new IFileElementType("SE_FILE", CLanguage.INSTANCE) {
        @Override
        @Nullable
        public ASTNode parseContents(@NotNull ASTNode chameleon) {
            VirtualFile virtualFile = chameleon.getPsi().getContainingFile().getVirtualFile();
            if (virtualFile != null) {
                System.out.println("Parsing " + virtualFile.getName());
            } else {
                System.out.println("Parsing unnamed file");
            }
            return super.parseContents(chameleon);
        }
    };

    // commons
    IElementType SE_CLASS_ID = new CCompositeElementType("SE_CLASS_ID");
    IElementType SE_CPP_BLOCK = new CCompositeElementType("SE_CPP_BLOCK");

    // types
    IElementType SE_TYPE = new CCompositeElementType("SE_TYPE", CType.class);
    IElementType SE_TYPE_MODIFIERS = new CCompositeElementType("SE_TYPE_MODIFIERS");

    // use statement
    IElementType SE_USE_STATEMENT = new CCompositeElementType("SE_USE_STATEMENT", CUseStatement.class);

    // event statement
    IElementType SE_EVENT_STATEMENT = new CCompositeElementType("SE_EVENT_STATEMENT", CEventStatement.class);
    IElementType SE_EVENT_FIELD = new CCompositeElementType("SE_EVENT_FIELD", CEventField.class);

    // enums
    IElementType SE_ENUM_STATEMENT = new CCompositeElementType("SE_ENUM_STATEMENT", CEnumStatement.class);
    IElementType SE_ENUM_FIELD = new CCompositeElementType("SE_ENUM_FIELD", CEnumField.class);

    // class
    IElementType SE_CLASS_STATEMENT = new CCompositeElementType("SE_CLASS_STATEMENT", SeClassImpl.class);
    IElementType SE_CLASS_ATTRIBUTE = new CCompositeElementType("SE_CLASS_ATTRIBUTE");

    IElementType SE_PROPERTIES_BLOCK = new CCompositeElementType("SE_PROPERTIES_BLOCK");
    IElementType SE_COMPONENTS_BLOCK = new CCompositeElementType("SE_COMPONENTS_BLOCK");
    IElementType SE_FUNCTIONS_BLOCK = new CCompositeElementType("SE_FUNCTIONS_BLOCK");
    IElementType SE_PROCEDURES_BLOCK = new CCompositeElementType("SE_PROCEDURES_BLOCK");

    IElementType SE_PROPERTY_FIELD = new CCompositeElementType("SE_PROPERTY_FIELD", CPropertyField.class);
    IElementType SE_PROPERTY_WED = new CCompositeElementType("SE_PROPERTY_WED");
    IElementType SE_PROPERTY_SHORTCUT = new CCompositeElementType("SE_PROPERTY_SHORTCUT");
    IElementType SE_PROPERTY_COLOR = new CCompositeElementType("SE_PROPERTY_COLOR");
    IElementType SE_PROPERTY_DEFAULT_VALUE = new CCompositeElementType("SE_PROPERTY_DEFAULT_VALUE");
    IElementType SE_INTERNAL_PROPERTY_BLOCK = new CCompositeElementType("SE_INTERNAL_PROPERTY_BLOCK");
    IElementType SE_INTERNAL_PROPERTY = new CCompositeElementType("SE_INTERNAL_PROPERTY");

    IElementType SE_COMPONENT_FIELD = new CCompositeElementType("SE_COMPONENT_FIELD", CComponentField.class);

    IElementType SE_FUNCTION = new CCompositeElementType("SE_FUNCTION", CFunction.class);
    IElementType SE_FUNCTION_MODIFIERS = new CCompositeElementType("SE_FUNCTION_MODIFIERS");
    IElementType SE_PARAMETER_LIST = new CCompositeElementType("SE_PARAMETER_LIST");
    IElementType SE_PARAMETER = new CCompositeElementType("SE_PARAMETER", CParameter.class);

    IElementType SE_PROCEDURE = new CCompositeElementType("SE_PROCEDURE", CProcedure.class);
    IElementType SE_PROCEDURE_OVERRIDE = new CCompositeElementType("SE_PROCEDURE_OVERRIDE");
    IElementType SE_EVENT_SPECIFICATION = new CCompositeElementType("SE_EVENT_SPECIFICATION", CEventSpecification.class);

    IElementType SE_CODE_BLOCK = new CCompositeElementType("SE_CODE_BLOCK");

    // statements
    IElementType SE_BLOCK_STATEMENT = new CCompositeElementType("SE_BLOCK_STATEMENT");
    IElementType SE_IF_STATEMENT = new CCompositeElementType("SE_IF_STATEMENT");
    IElementType SE_TRY_STATEMENT = new CCompositeElementType("SE_IF_STATEMENT");
    IElementType SE_SWITCH_STATEMENT = new CCompositeElementType("SE_IF_STATEMENT");
    IElementType SE_CASE_STATEMENT = new CCompositeElementType("SE_CASE_STATEMENT");
    IElementType SE_DEFAULT_STATEMENT = new CCompositeElementType("SE_DEFAULT_STATEMENT");
    IElementType SE_BREAK_STATEMENT = new CCompositeElementType("SE_BREAK_STATEMENT");
    IElementType SE_WHILE_STATEMENT = new CCompositeElementType("SE_WHILE_STATEMENT");
    IElementType SE_DO_WHILE_STATEMENT = new CCompositeElementType("SE_DO_WHILE_STATEMENT");
    IElementType SE_FOR_STATEMENT = new CCompositeElementType("SE_DO_WHILE_STATEMENT");
    IElementType SE_EXPRESSION_STATEMENT = new CCompositeElementType("SE_EXPRESSION_STATEMENT");
    IElementType SE_RETURN_STATEMENT = new CCompositeElementType("SE_RETURN_STATEMENT");
    IElementType SE_WAIT_STATEMENT = new CCompositeElementType("SE_WAIT_STATEMENT");
    IElementType SE_AUTOWAIT_STATEMENT = new CCompositeElementType("SE_AUTOWAIT_STATEMENT");
    IElementType SE_WAITEVENT_STATEMENT = new CCompositeElementType("SE_WAITEVENT_STATEMENT");
    IElementType SE_ON_STATEMENT = new CCompositeElementType("SE_ON_STATEMENT");
    IElementType SE_OTHERWISE_STATEMENT = new CCompositeElementType("SE_OTHERWISE_STATEMENT");
    IElementType SE_JUMP_STATEMENT = new CCompositeElementType("SE_JUMP_STATEMENT");
    IElementType SE_CALL_STATEMENT = new CCompositeElementType("SE_CALL_STATEMENT");
    IElementType SE_AUTOCALL_STATEMENT = new CCompositeElementType("SE_AUTOCALL_STATEMENT");
    IElementType SE_STOP_STATEMENT = new CCompositeElementType("SE_STOP_STATEMENT");
    IElementType SE_RESUME_STATEMENT = new CCompositeElementType("SE_RESUME_STATEMENT");
    IElementType SE_PASS_STATEMENT = new CCompositeElementType("SE_PASS_STATEMENT");
    IElementType SE_EMPTY_STATEMENT = new CCompositeElementType("SE_EMPTY_STATEMENT");
    IElementType SE_FOREACHINLIST_MACRO = new CCompositeElementType("SE_FOREACHINLIST_MACRO");
    IElementType SE_FOREACHINSTATICARRAY_MACRO = new CCompositeElementType("SE_FOREACHINSTATICARRAY_MACRO");
    IElementType SE_FOREACHINDYNAMICARRAY_MACRO = new CCompositeElementType("SE_FOREACHINDYNAMICARRAY_MACRO");
    IElementType SE_FOREACHINDYNAMICCONTAINER_MACRO = new CCompositeElementType("SE_FOREACHINDYNAMICCONTAINER_MACRO");
    IElementType SE_FOREVER_MACRO = new CCompositeElementType("SE_FOREVER_MACRO");

    // expressions
    IElementType SE_EXPRESSION = new CCompositeElementType("SE_EXPRESSION");
    IElementType SE_REFERENCE_EXPRESSION = new CCompositeElementType("SE_REFERENCE_EXPRESSION", CGenericReferenceExpression.class);
}
