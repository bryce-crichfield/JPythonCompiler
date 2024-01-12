package org.ksu.jpython.lang;

public interface TranslationBuilder {
    void enterScope();
    void exitScope();
    void outputNoTab(String s);
    void outputWithTab(String s);

    String show();
}
