package ru.nyrk.gisgmp.web.converters;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.springframework.core.convert.converter.Converter;
import ru.nyrk.util.StrTry;

import javax.annotation.PostConstruct;


public class StringToDateTimeConverter implements Converter<String, DateTime> {

    public static String[] DEFAULT_PATTERNS = new String[]{"dd.MM.yy", "dd.MM.yy HH:mm:ss", "yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ssZZ", "yyyy-MM-dd", "dd.MM.yyyy"};

    private String[] patterns = DEFAULT_PATTERNS;

    private DateTimeFormatter dateTimeFormatter;

    public String[] getPatterns() {
        return patterns;
    }

    @PostConstruct
    public void init() {
        DateTimeParser[] parsers = new DateTimeParser[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            parsers[i] = DateTimeFormat.forPattern(patterns[i]).getParser();
        }
        dateTimeFormatter = new DateTimeFormatterBuilder().append(null, parsers).toFormatter();
    }

    @Override
    public DateTime convert(String source) {
        if (StrTry.isEmpty(source)) return null;
        return dateTimeFormatter.parseDateTime(source);
    }
}
