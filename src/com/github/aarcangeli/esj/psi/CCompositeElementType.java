package com.github.aarcangeli.esj.psi;

import com.github.aarcangeli.esj.CLanguage;
import com.intellij.lang.ASTNode;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.tree.ICompositeElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public class CCompositeElementType extends IElementType implements ICompositeElementType {
    private final Constructor<? extends ASTNode> myConstructor;

    public CCompositeElementType(String debugName, Class<? extends ASTNode> nodeClass) {
        super(debugName, CLanguage.INSTANCE);
        myConstructor = ReflectionUtil.getDefaultConstructor(nodeClass);
    }

    public CCompositeElementType(String debugName) {
        super(debugName, CLanguage.INSTANCE);
        myConstructor = null;
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        if (myConstructor != null) {
            return ReflectionUtil.createInstance(myConstructor);
        } else {
            // inline empty composition
            return new CompositePsiElement(this) {
            };
        }
    }
}
