package isspasstime.com.isspasstime.datacontainer;

/**
 * Created by Sagar on 12/3/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ISSPassDetails {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("request")
    @Expose
    private ISSRequest request;

    @SerializedName("response")
    @Expose
    private List<PassTime> passTime = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ISSRequest getRequest() {
        return request;
    }

    public void setRequestf(ISSRequest request) {
        this.request = request;
    }

    public List<PassTime> getPassTimes() {
        return passTime;
    }

    public void setPassTimes(List<PassTime> passTime) {
        this.passTime = passTime;
    }
}
