package com.example.android.mylocationdev;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
// next removed to remove error for having two. this one has different abstract methods
//import android.location.LocationListener;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
// is the next the correct version to use? one above has different abstract methods.
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import junit.framework.Test;

import org.w3c.dom.Text;

import java.text.DateFormat;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    int locationCount = 0;
    long timeDiff = 0;
    long lastTime = 0;
    double lastLong = 0;
    double lastLat = 0;
    int autoUpdateCount = 0;
    double savedLongitude = 0;
    double savedLatitude = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    /**
     * GoogleApiClient.ConnectionCallbacks abstact method
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i("MainActivity", "Connection established");
        localGetLocationOld();
    }

    public void localGetLocation (View view) {
        Log.i("MainActivity", "localGetLocation");
        view.setVisibility(View.GONE);
        Button cancelGetLocation = (Button) findViewById(R.id.cancel_get_location_button);
        cancelGetLocation.setVisibility(View.VISIBLE);
        createLocationRequest();
        startLocationUpdates();
    }

    public void localSaveLocation (View view){
        if (mLastLocation != null) {
            savedLongitude = mLastLocation.getLongitude();
            savedLatitude = mLastLocation.getLatitude();
        }
    }

    public void cancelGetLocation (View view) {
        Log.i("MainActivity", "cancelGetLocation");
        view.setVisibility(View.GONE);
        Button getLocation = (Button) findViewById(R.id.get_location_button);
        getLocation.setVisibility(View.VISIBLE);
        stopLocationUpdates();
    }

    public void localGetLocationOld() {
        Log.i("MainActivity", "localGetLocationOld");
        //String messageText = "";
        //String subMessage = "";
        //double longitude = 0;
        //double latitude = 0;
        //float accuracy = -1;
        //long thisTime = 0;

        //DateFormat myFormat = DateFormat.getDateTimeInstance();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //messageText += "Access denied";
            Log.i("MainActivity", "Access denied.");
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //messageText += "mLastLocation is NOT null";
            Log.i("MainActivity", "mLastLocation is NOT null");
            updateUI();
            //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
/*            thisTime = mLastLocation.getTime();
            locationCount++;
            timeDiff = thisTime - lastTime;
            lastTime = thisTime;
            longitude = mLastLocation.getLongitude();
            latitude = mLastLocation.getLatitude();

            subMessage = "Location count: " + locationCount + " Time since last location: " + timeDiff;
            messageText += System.getProperty("line.separator") + subMessage;
            //Log.i("MainActivity", subMessage);

            subMessage = "Latitude: " + String.valueOf(mLastLocation.getLatitude()) + " Longitude: " + String.valueOf(mLastLocation.getLongitude());
            messageText += System.getProperty("line.separator") + subMessage;
            //Log.i("MainActivity", subMessage);

            subMessage = mLastLocation.toString();
            messageText += System.getProperty("line.separator") + subMessage;
           // Log.i("MainActivity", subMessage);

            subMessage = "Provider: " + mLastLocation.getProvider();
            messageText += System.getProperty("line.separator") + subMessage;
            //Log.i("MainActivity", subMessage);

            //subMessage = "getTime: " + String.valueOf(thisTime);
            //Date myDate = new Date();

            subMessage = "getTime: " + myFormat.format(thisTime);
            messageText += System.getProperty("line.separator") + subMessage;
            //Log.i("MainActivity", subMessage);

            subMessage = "hasAccuracy: " + String.valueOf(mLastLocation.hasAccuracy());
            messageText += System.getProperty("line.separator") + subMessage;
            //Log.i("MainActivity", subMessage);

            accuracy = mLastLocation.getAccuracy();
            subMessage = "getAccuracy: " + String.valueOf(mLastLocation.getAccuracy());
            messageText += System.getProperty("line.separator") + subMessage;
            //Log.i("MainActivity", subMessage);

            subMessage = "hasAltitude: " + String.valueOf(mLastLocation.hasAltitude());
            messageText += System.getProperty("line.separator") + subMessage;
            //Log.i("MainActivity", subMessage);
*/
        } else {
            //subMessage = "mLastLocation is null";
            //messageText += System.getProperty("line.separator") + subMessage;
            Log.i("MainActivity", "mLastLocation is null");
        }
/*        // full text
        TextView locationTextView = (TextView) findViewById(R.id.location_text_view);
        locationTextView.setText(messageText);

        // longitude text
        TextView longTextView = (TextView) findViewById(R.id.long_text_view);
        longTextView.setText(String.valueOf(longitude));
        TextView lastLongTextView = (TextView) findViewById(R.id.last_long_text_view);
        subMessage = "Last Longitude: " + String.valueOf(lastLong) + " dif: " + String.valueOf(longitude-lastLong);
        lastLongTextView.setText(String.valueOf(subMessage));

        // latitude text
        TextView latTextView = (TextView) findViewById(R.id.lat_text_view);
        latTextView.setText(String.valueOf(latitude));
        TextView lastLatTextView = (TextView) findViewById(R.id.last_lat_text_view);
        subMessage = "Last Latitude: " + String.valueOf(lastLat) + " dif: " + String.valueOf(latitude-lastLat);
        lastLatTextView.setText(String.valueOf(subMessage));

        // time change?
        TextView timeChangeTextView = (TextView) findViewById(R.id.time_change_text_view);
        if (timeDiff != 0) {
            timeChangeTextView.setText("");
        } else {
            timeChangeTextView.setText("Last updated: " + myFormat.format(thisTime));
            lastLong = longitude;
            lastLat = latitude;
        }

        // accuracy
        TextView accuracyTextView = (TextView) findViewById(R.id.accuracy_text_view);
        subMessage = "Accuracy: " + accuracy + " meters";
        subMessage += System.getProperty("line.separator") + myFormat.format(thisTime);
        accuracyTextView.setText(subMessage);

        Log.i("MainActivity", messageText);
*/
    }

    public void updateUI(){
        Log.i("MainActivity", "updateUI");
        String messageText = "";
        String subMessage = "";
        double longitude = 0;
        double latitude = 0;
        float accuracy = -1;
        long thisTime = 0;

        DateFormat myFormat = DateFormat.getDateTimeInstance();

        thisTime = mLastLocation.getTime();
        locationCount++;
        timeDiff = thisTime - lastTime;
        lastTime = thisTime;
        longitude = mLastLocation.getLongitude();
        latitude = mLastLocation.getLatitude();

        subMessage = "Location count: " + locationCount + " Time since last location: " + timeDiff;
        messageText += System.getProperty("line.separator") + subMessage;
        //Log.i("MainActivity", subMessage);

        subMessage = "Latitude: " + String.valueOf(mLastLocation.getLatitude()) + " Longitude: " + String.valueOf(mLastLocation.getLongitude());
        messageText += System.getProperty("line.separator") + subMessage;
        //Log.i("MainActivity", subMessage);

        subMessage = mLastLocation.toString();
        messageText += System.getProperty("line.separator") + subMessage;
        // Log.i("MainActivity", subMessage);

        subMessage = "Provider: " + mLastLocation.getProvider();
        messageText += System.getProperty("line.separator") + subMessage;
        //Log.i("MainActivity", subMessage);

        //subMessage = "getTime: " + String.valueOf(thisTime);
        //Date myDate = new Date();

        subMessage = "getTime: " + myFormat.format(thisTime);
        messageText += System.getProperty("line.separator") + subMessage;
        //Log.i("MainActivity", subMessage);

        subMessage = "hasAccuracy: " + String.valueOf(mLastLocation.hasAccuracy());
        messageText += System.getProperty("line.separator") + subMessage;
        //Log.i("MainActivity", subMessage);

        accuracy = mLastLocation.getAccuracy();
        subMessage = "getAccuracy: " + String.valueOf(mLastLocation.getAccuracy());
        messageText += System.getProperty("line.separator") + subMessage;
        //Log.i("MainActivity", subMessage);

        subMessage = "hasAltitude: " + String.valueOf(mLastLocation.hasAltitude());
        messageText += System.getProperty("line.separator") + subMessage;
        //Log.i("MainActivity", subMessage);

        subMessage = "getAltitude: " + String.valueOf(mLastLocation.getAltitude());
        messageText += System.getProperty("line.separator") + subMessage;
        //Log.i("MainActivity", subMessage);

        // *************************************************************************************
        // Udate the views
        // *************************************************************************************

        // time change?
        TextView timeChangeTextView = (TextView) findViewById(R.id.time_change_text_view);
        if (timeDiff != 0) {
            // full text
            TextView locationTextView = (TextView) findViewById(R.id.location_text_view);
            locationTextView.setText(messageText);
            Log.i("MainActivity", messageText);

            // longitude text
            TextView longTextView = (TextView) findViewById(R.id.long_text_view);
            longTextView.setText(String.valueOf(longitude));
            TextView lastLongTextView = (TextView) findViewById(R.id.last_long_text_view);
            subMessage = "Last Longitude: " + String.valueOf(lastLong) + " dif: " + String.valueOf(longitude-lastLong);
            lastLongTextView.setText(String.valueOf(subMessage));

            // latitude text
            TextView latTextView = (TextView) findViewById(R.id.lat_text_view);
            latTextView.setText(String.valueOf(latitude));
            TextView lastLatTextView = (TextView) findViewById(R.id.last_lat_text_view);
            subMessage = "Last Latitude: " + String.valueOf(lastLat) + " dif: " + String.valueOf(latitude-lastLat);
            lastLatTextView.setText(String.valueOf(subMessage));

            // Top time message
            timeChangeTextView.setText("");

            // accuracy
            TextView accuracyTextView = (TextView) findViewById(R.id.accuracy_text_view);
            subMessage = "Accuracy: " + accuracy + " meters";
            subMessage += System.getProperty("line.separator") + myFormat.format(thisTime);
            accuracyTextView.setText(subMessage);

        } else {
            String sTemp = "Last updated: " + myFormat.format(thisTime);
            timeChangeTextView.setText(sTemp);
        }
        lastLong = longitude;
        lastLat = latitude;
    }
    /**
     * GoogleApiClient.ConnectionCallbacks abstact method
     * Runs when a GoogleApiClient object is temporarily in a disconnected state.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.i("MainActivity", "Connection suspended");
    }

    /**
     * GoogleApiClient.OnConnectionFailedListener abstact method
     * Runs when there is an error connection the client to the service.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("MainActivity", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    // setup location request
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    // Location updates started
    protected void startLocationUpdates() {
        Log.i("MainActivity", "startLocationUpdates");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("MainActivity", "Access denied to call");
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }
    // location updates stopped
    protected void stopLocationUpdates() {
        Log.i("MainActivity", "stopLocationUpdates");
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }
    // On location change
    @Override
    public void onLocationChanged(Location location) {
        Log.i("MainActivity", "onLocationChanged");
        String message;
        //stopLocationUpdates();

        // Fix buttons
        //Button cancelGetLocation = (Button) findViewById(R.id.cancel_get_location_button);
        //cancelGetLocation.setVisibility(View.GONE);
        //Button getLocation = (Button) findViewById(R.id.get_location_button);
        //getLocation.setVisibility(View.VISIBLE);

        // populate the table
        TextView col1 = (TextView) findViewById(R.id.table_count);
        TextView col2 = (TextView) findViewById(R.id.table_lat);
        TextView col3 = (TextView) findViewById(R.id.table_long);
        TextView col4 = (TextView) findViewById(R.id.table_time);

        autoUpdateCount++;

        message = System.getProperty("line.separator") + String.valueOf(autoUpdateCount);
        col1.append(message);
        message = System.getProperty("line.separator") + String.valueOf(location.getLatitude());
        col2.append(message);
        message = System.getProperty("line.separator") + String.valueOf(location.getLongitude());
        col3.append(message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            message = System.getProperty("line.separator") + String.valueOf(location.getElapsedRealtimeNanos());
        } else {
            message = System.getProperty("line.separator") + String.valueOf(location.getTime());
        }
        col4.append(message);

        mLastLocation = location;
        updateUI();
//        mCurrentLocation = location;
//        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//        updateUI();
        float[] results = {0,0,0};
        // AJC note the capital L because this is the class not the instance of the class.
        // since this is math it really does not have to do with the instance of the class but the class itself.
        Location.distanceBetween(savedLatitude, savedLongitude, location.getLatitude(), location.getLongitude(), results);
        message = System.getProperty("line.separator");
        message += "count: " + autoUpdateCount;
        message += ": distance: " + String.valueOf(results[0]);
        message += " intial bearing: " + String.valueOf(results[1]);
        message += " final bearing: " +  String.valueOf(results[2]);

        TextView distance = (TextView) findViewById(R.id.distance_text_view);
        distance.append(message);

        //Location.distanceBetween(41.1801005, -73.8690129, 41.1801005, -73.8690129, results);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
            mGoogleApiClient.disconnect();
        }
    }
}
