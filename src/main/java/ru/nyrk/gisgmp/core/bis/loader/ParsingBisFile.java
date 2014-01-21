package ru.nyrk.gisgmp.core.bis.loader;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;
import ru.nyrk.gisgmp.core.bis.elm.AppData;
import ru.nyrk.gisgmp.util.soap.SOAPUtility;

import java.io.InputStream;

@Component
public class ParsingBisFile {

    @Autowired
    @Qualifier("xStreamMarshaller")
    private XStreamMarshaller xStreamMarshaller;

    public AppData parsingBisSource(InputStream is) throws Exception {
        xStreamMarshaller.setAnnotatedClass(AppData.class);
        return (AppData) xStreamMarshaller.unmarshal(SOAPUtility.getDOMSourceFromStream(is));
    }
}
