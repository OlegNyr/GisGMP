package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 20.12.13
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */
public class RequestProcessResult {
    @XStreamAlias("ErrorCode")
    String errorCode;
    @XStreamAlias("ErrorDescription")
    String errorDescription;
    @XStreamAlias("ErrorData")
    String errorData;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorData() {
        return errorData;
    }

    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }
}
