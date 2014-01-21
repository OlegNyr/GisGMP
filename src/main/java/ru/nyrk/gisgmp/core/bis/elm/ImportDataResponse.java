package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 20.12.13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class ImportDataResponse {
    @XStreamAlias("ref")
    @XStreamAsAttribute
    String ref;

    @XStreamAlias("Ticket")
    Ticket ticket;


    public ImportDataResponse() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
