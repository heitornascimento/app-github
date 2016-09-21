package cl.com.br.github.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by heitornascimento on 8/21/16.
 */
public class DateUtils {

    public static String formatDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
            String formatDate = simpleDateFormat.format(date);
            return formatDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
