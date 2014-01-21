package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ImportRequest {
    @XStreamAlias("PostBlock")
    private PostBlock postBlock;

    @XStreamAlias("FinalPayment")
    private FinalPayment finalPayment;

    public PostBlock getPostBlock() {
        return postBlock;
    }

    public FinalPayment getFinalPayment() {
        return finalPayment;
    }
}
