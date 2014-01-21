package ru.nyrk.gisgmp.util.soap;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

@Component
public class SOAPFactory {

    MessageFactory messageFactory;

    @PostConstruct
    public void init() throws Exception {
        messageFactory = MessageFactory.newInstance();
    }

    public MessageFactory getMessageFactory() {
        return messageFactory;
    }

    public SOAPMessage createMessage() throws Exception {
        return messageFactory.createMessage();
    }
}


