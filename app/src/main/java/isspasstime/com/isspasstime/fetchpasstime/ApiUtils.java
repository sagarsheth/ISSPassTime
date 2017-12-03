package isspasstime.com.isspasstime.fetchpasstime;

/**
 * Created by Sagar on 12/3/2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://api.open-notify.org/";

    public static ISSPassTimeService getPassTimeService() {
        return RetrofitClient.getClient(BASE_URL).create(ISSPassTimeService.class);
    }
}