package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 20.12.13
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public class Ticket {
    @XStreamAlias("PostBlock")
    PostBlock postBlock;
    @XStreamAlias("RequestProcessResult")
    RequestProcessResult requestProcessResult;

    public Ticket() {
        postBlock = new PostBlock();

    }

    public PostBlock getPostBlock() {
        return postBlock;
    }

    public RequestProcessResult getRequestProcessResult() {
        return requestProcessResult;
    }

    public void setRequestProcessResult(RequestProcessResult requestProcessResult) {
        this.requestProcessResult = requestProcessResult;
    }
}
