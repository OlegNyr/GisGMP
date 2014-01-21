package ru.nyrk.gisgmp.web.converters.xstream;

import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.io.IOException;
import java.io.Writer;


public class HeaderXStreamMarshaller extends XStreamMarshaller {
    @Override
    protected void marshalWriter(Object graph, Writer writer) throws XmlMappingException, IOException {
        writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        super.marshalWriter(graph, writer);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
