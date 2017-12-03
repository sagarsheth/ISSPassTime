package isspasstime.com.isspasstime.datacontainer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sagar on 12/3/2017.
 */

public class PassTime {
    @SerializedName("duration")
    @Expose
    private Integer duration;

    @SerializedName("risetime")
    @Expose
    private Long risetime;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer message) {
        this.duration = duration;
    }

    public Long getRisetime() {
        return risetime;
    }

    public void setRisetime(Long risetime) {
        this.risetime = risetime;
    }
}
