package isspasstime.com.isspasstime.fetchpasstime;

import isspasstime.com.isspasstime.datacontainer.ISSPassDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sagar on 12/3/2017.
 */

public interface ISSPassTimeService {

    @GET("iss-pass.json")
    Call<ISSPassDetails> getPassTimes(@Query("lat") Double latitude, @Query("lon") Double longitude);
}
