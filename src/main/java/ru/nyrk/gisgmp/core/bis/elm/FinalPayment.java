package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.joda.time.LocalDate;

@XStreamAlias("FinalPayment")
public class FinalPayment {
    @XStreamAlias("Version")
    @XStreamAsAttribute
    String version;


    @XStreamAlias("SupplierBillID")
    String supplierBillID;
    @XStreamAlias("Narrative")
    String narrative;
    @XStreamAlias("Amount")
    String amount;

    @XStreamAlias("PaymentDate")
    LocalDate paymentDate;

    @XStreamAlias("BudgetIndex")
    BudgetIndex budgetIndex;

    @XStreamAlias("PaymentIdentificationData")
    PaymentIdentificationData paymentIdentificationData;

    @XStreamAlias("PayerIdentifier")
    String payerIdentifier;

    @XStreamAlias("PayerPA")
    String payerPA;

    @XStreamAlias("PayeeBankAcc")
    PayeeBankAcc payeeBankAcc;

    @XStreamAlias("ChangeStatus")
    int changeStatus;

    @XStreamAlias("payeeINN")
    String payeeINN;

    @XStreamAlias("payeeKPP")
    String payeeKPP;
    @XStreamAlias("KBK")
    String KBK;

    @XStreamAlias("OKATO")
    String OKATO;

    public String getVersion() {
        return version;
    }

    public String getSupplierBillID() {
        return supplierBillID;
    }

    public String getNarrative() {
        return narrative;
    }

    public String getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public BudgetIndex getBudgetIndex() {
        return budgetIndex;
    }

    public PaymentIdentificationData getPaymentIdentificationData() {
        return paymentIdentificationData;
    }

    public String getPayerIdentifier() {
        return payerIdentifier;
    }

    public String getPayerPA() {
        return payerPA;
    }

    public PayeeBankAcc getPayeeBankAcc() {
        return payeeBankAcc;
    }

    public int getChangeStatus() {
        return changeStatus;
    }

    public String getPayeeINN() {
        return payeeINN;
    }

    public String getPayeeKPP() {
        return payeeKPP;
    }

    public String getKBK() {
        return KBK;
    }

    public String getOKATO() {
        return OKATO;
    }
}
