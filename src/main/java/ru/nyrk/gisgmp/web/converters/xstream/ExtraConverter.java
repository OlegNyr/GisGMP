package ru.nyrk.gisgmp.web.converters.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import ru.nyrk.util.StrTry;

public class ExtraConverter implements Converter {
    @SuppressWarnings("unchecked")
    public boolean canConvert(final Class clazz) {
        return false;// clazz.equals(FilesTag.class);
    }


    public void marshal(final Object value,
                        final HierarchicalStreamWriter writer,
                        final MarshallingContext context) {
//        final FilesTag extra = (FilesTag) value;
//        if (extra != null) {
//            writer.addAttribute("name", extra.getFilesTagId().getTagNm());
//            writer.setValue(StrTry.normalStr(extra.getTagVl()));
//        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return null;
    }

}
