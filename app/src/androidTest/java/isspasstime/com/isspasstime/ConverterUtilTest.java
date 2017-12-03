package isspasstime.com.isspasstime;

import android.util.Log;

import org.junit.Test;

import isspasstime.com.isspasstime.utils.Utils;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sagar on 12/3/2017.
 */

public class ConverterUtilTest {

    @Test
    public void testConvertCelsiusToFahrenheit() {
        String actual = Utils.getCurrentTime(1512344316);
        Log.e("vaaaa", "" + actual);
        String expected = "03/12/2017 17:38:36";
        assertEquals("Conversion from celsius to fahrenheit failed", expected, actual.trim());
    }
}
