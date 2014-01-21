package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 19.12.13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class BudgetIndex {
    @XStreamAlias("Status")
    String status;
    @XStreamAlias("PaymentType")
    String paymentType;
    @XStreamAlias("Purpose")
    String purpose;
    @XStreamAlias("TaxPeriod")
    String taxPeriod;
    @XStreamAlias("TaxDocNumber")
    String taxDocNumber;
    @XStreamAlias("TaxDocDate")
    String taxDocDate;

    public String getStatus() {
        return status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getTaxPeriod() {
        return taxPeriod;
    }

    public String getTaxDocNumber() {
        return taxDocNumber;
    }

    public String getTaxDocDate() {
        return taxDocDate;
    }
}
