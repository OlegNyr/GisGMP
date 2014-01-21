package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 19.12.13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public class PayeeBankAcc {
    @XStreamAlias("Account")
    String account;
    @XStreamAlias("Bank")
    Bank bank;
}
