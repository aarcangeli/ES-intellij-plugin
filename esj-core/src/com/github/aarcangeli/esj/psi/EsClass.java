package com.github.aarcangeli.esj.psi;

/**
 * Represents a class
 */
public interface EsClass extends EsFileMember {
    EsClass[] EMPTY_ARRAY = new EsClass[0];

    EsMember[] getAllMembers();
}
