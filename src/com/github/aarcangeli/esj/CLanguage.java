package com.github.aarcangeli.esj;

import com.intellij.lang.Language;

public class CLanguage extends Language {
    public static final CLanguage INSTANCE = new CLanguage();

    private CLanguage() {
        super("SE1-ES");
    }
}
