package ru.nyrk.gisgmp.util.smev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParamSmev {
    @Value("${smev.recipient.code}")
    String recipient_code;
    @Value("${smev.recipient.name}")
    String recipient_name;
    @Value("${smev.sender.code}")
    String sender_code;
    @Value("${smev.sender.name}")
    String sender_name;
    @Value("${smev.typecode}")
    String typecode;
    @Value("${smev.exchangeType}")
    String exchangeType;

    public String getRecipient_code() {
        return recipient_code;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public String getSender_code() {
        return sender_code;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getTypecode() {
        return typecode;
    }

    public String getExchangeType() {
        return exchangeType;
    }
}
