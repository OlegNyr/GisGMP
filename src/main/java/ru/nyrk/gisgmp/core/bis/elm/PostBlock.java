package ru.nyrk.gisgmp.core.bis.elm;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.joda.time.DateTime;

@XStreamAlias("PostBlock")
public class PostBlock {

    @XStreamAlias("ID")
    String id;
    @XStreamAlias("TimeStamp")
    DateTime timeStamp;

    @XStreamAlias("SenderIdentifier")
    String senderIdentifier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSenderIdentifier() {
        return senderIdentifier;
    }

    public void setSenderIdentifier(String senderIdentifier) {
        this.senderIdentifier = senderIdentifier;
    }
}
