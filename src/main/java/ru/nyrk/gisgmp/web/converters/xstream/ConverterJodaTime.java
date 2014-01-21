package ru.nyrk.gisgmp.web.converters.xstream;

import com.thoughtworks.xstream.converters.ErrorReporter;
import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ConverterJodaTime extends AbstractSingleValueConverter implements ErrorReporter{

    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    public ConverterJodaTime() {
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(DateTime.class);
    }

    @Override
    public String toString(Object obj) {
       return ((DateTime)obj).toString(dateTimeFormatter);
    }

    @Override
    public Object fromString(String str) {
        return  DateTime.parse(str);
    }

    @Override
    public void appendErrors(ErrorWriter errorWriter) {

    }
}
