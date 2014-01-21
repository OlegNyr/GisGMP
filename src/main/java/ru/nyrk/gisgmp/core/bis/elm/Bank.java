package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class Bank {
    @XStreamAlias("BIK")
    String BIK;

    public String getBIK() {
        return BIK;
    }
}
