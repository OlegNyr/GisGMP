package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 19.12.13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class ImportData {
    @XStreamAsAttribute
    @XStreamAlias("ref")
    String ref;

    public String getRef() {
        return ref;
    }
    @XStreamAlias("ImportRequest")
    private ImportRequest importRequest;

    public ImportRequest getImportRequest() {
        return importRequest;
    }
}
