package isspasstime.com.isspasstime.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Sagar on 12/3/2017.
 */

public class Utils {

    public static String getCurrentTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(tz);
        String localTime = sdf.format(new Date(timestamp * 1000));
        return localTime;
    }
}
