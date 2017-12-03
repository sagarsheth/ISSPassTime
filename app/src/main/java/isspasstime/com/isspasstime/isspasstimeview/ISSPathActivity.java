package isspasstime.com.isspasstime.isspasstimeview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;

import isspasstime.com.isspasstime.R;
import isspasstime.com.isspasstime.fetchpasstime.ApiUtils;
import isspasstime.com.isspasstime.fetchpasstime.ISSPassTimeService;
import isspasstime.com.isspasstime.locationutils.LocationHelper;
import isspasstime.com.isspasstime.datacontainer.ISSPassDetails;
import isspasstime.com.isspasstime.datacontainer.PassTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sagar on 12/13/17.
 */

public class ISSPathActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, OnRequestPermissionsResultCallback {

    private EditText current_lattitude;
    private EditText current_longitude;
    private ISSPassTimeAdapter mAdapter;
    private RecyclerView mRecyclerView;

    // Location Controller
    private LocationHelper locationHelper;
    private Location mLastLocation;
    private double latitude;
    private double longitude;
    private ProgressDialog progress;

    //Api Controller
    private ISSPassTimeService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isspath);
        current_lattitude = (EditText) findViewById(R.id.current_lattitude);
        current_longitude = (EditText) findViewById(R.id.current_longitude);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        locationHelper = new LocationHelper(this);
        locationHelper.checkpermission();
        mService = ApiUtils.getPassTimeService();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new ISSPassTimeAdapter(this, new ArrayList<PassTime>(0),
                new ISSPassTimeAdapter.PostItemListener() {
                    @Override
                    public void onPostClick(long id) {
                        showToast("Post id is");
                    }
                });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        retryLocationFetch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();
    }

    public void onFetchPassTimeClicked(View view) {
        getCurrentLocation();
        if (mLastLocation != null) {
            getPassTimeAsPerLocation(latitude, longitude);
        }
    }

    public void onFetchLocationClicked(View view) {
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        mLastLocation = locationHelper.getLocation();
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            current_lattitude.setText(String.valueOf(latitude));
            current_longitude.setText(String.valueOf(longitude));
            Log.i("My Location", "Latitude is " + latitude + " and long is " + longitude);
        } else {
            showToast("Couldn't get the location. Make sure location is enabled on the device");
        }
    }

    private void retryLocationFetch() {
        // check availability of play services
        locationHelper.checkpermission();
        if (locationHelper.checkPlayServices()) {
            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient().setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

                    final Status status = locationSettingsResult.getStatus();

                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location requests here
                            getCurrentLocation();
                            getPassTimeAsPerLocation(latitude, longitude);
                            break;
                    }
                }
            });
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void getPassTimeAsPerLocation(Double latitude, Double longitude) {
        progress.show();
        mService.getPassTimes(latitude, longitude).enqueue(new Callback<ISSPassDetails>() {
            @Override
            public void onResponse(Call<ISSPassDetails> call, Response<ISSPassDetails> response) {

                progress.dismiss();
                if (response.isSuccessful()) {
                    mAdapter.updateAnswers(response.body().getPassTimes());
                } else {
                    // handle request errors depending on status code
                    int statusCode = response.code();
                    if (statusCode == 400) {
                        showToast("Unable to fetch Pass Timings.");
                    }
                }
            }

            @Override
            public void onFailure(Call<ISSPassDetails> call, Throwable t) {
                progress.dismiss();
                showToast("Unable to fetch Pass Timings.");
            }
        });
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation = locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }


    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}