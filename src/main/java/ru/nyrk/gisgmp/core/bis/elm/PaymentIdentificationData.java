package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PaymentIdentificationData {
    @XStreamAlias("Bank")
    Bank bank;
    @XStreamAlias("SystemIdentifier")
    String systemIdentifier;

    public Bank getBank() {
        return bank;
    }

    public String getSystemIdentifier() {
        return systemIdentifier;
    }
}
