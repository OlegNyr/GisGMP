package ru.nyrk.gisgmp.web.converters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.springframework.format.Formatter;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: oanyrkov
 * Date: 12.05.13
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class FormatterDateTimeRus implements Formatter<DateTime> {
    public static String[] DEFAULT_PATTERNS = new String[]{"dd.MM.yy", "dd.MM.yy HH:mm:ss", "dd.MM.yy HH:mm", "yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ssZZ", "yyyy-MM-dd", "dd.MM.yyyy"};

    private String[] patterns = DEFAULT_PATTERNS;
    private DateTimeFormatter formatterPrintDate = DateTimeFormat.forPattern("dd.MM.yy");
    private DateTimeFormatter formatterPrintDateTime = DateTimeFormat.forPattern("dd.MM.yy HH:mm:ss");
    private DateTimeFormatter dateTimeFormatter;

    @PostConstruct
    public void init() {
        DateTimeParser[] parsers = new DateTimeParser[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            parsers[i] = DateTimeFormat.forPattern(patterns[i]).getParser();
        }
        dateTimeFormatter = new DateTimeFormatterBuilder().append(null, parsers).toFormatter();
    }

    @Override
    public DateTime parse(String text, Locale locale) throws ParseException {
        return dateTimeFormatter.parseDateTime(text);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String print(DateTime object, Locale locale) {

        if(object == null) return null;
        if(object.millisOfDay().get() == 0)  return  object.toString(formatterPrintDate);
        return object.toString(formatterPrintDateTime);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
