package com.luisdiaz.gpsspeedwarning;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    Context context;

    private Button btnStart;
    private TextView lblSpeed, lblSetSpeed;
    private EditText inputSpeed;
    private ImageView image;
    private CheckBox checkboxUnits;

    private float gpsSpeed=0,userSpeed=0;
    private boolean isPlayClicked=false;

    private LocationListener locationListener;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.BtnStart);

        lblSpeed = (TextView) findViewById(R.id.LblSpeed);
        lblSetSpeed = (TextView) findViewById(R.id.LblSetSpeed);
        inputSpeed = (EditText) findViewById(R.id.InputSpeed);

        image = (ImageView) findViewById(R.id.Image);

        checkboxUnits = (CheckBox) findViewById(R.id.CheckBoxUnits);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayClicked = !isPlayClicked;
                if (isPlayClicked) {
                    btnStart.setText(R.string.BtnStop);
                    lblSetSpeed.setText(inputSpeed.getText());
                } else {
                    btnStart.setText(R.string.BtnStart);
                    lblSetSpeed.setText(R.string.setSpeed);
                    locationManager.removeUpdates(locationListener);
                }

            }
        });

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        //Define a listener that responds to location updates
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(isPlayClicked) {
                    location.getLatitude();
                    gpsSpeed = location.getSpeed();
                    if(!checkboxUnits.isChecked()){
                        //Use SI
                        gpsSpeed = gpsSpeed + (float)3.6; //To kmh
                    }else{
                        //Use Imperial
                        gpsSpeed = gpsSpeed * (float)2.23693629;//To miles
                    }
                    lblSpeed.setText(Float.toString(gpsSpeed));

                    if (gpsSpeed > userSpeed) {
                        image.setImageResource(R.drawable.red_warning);
                    } else {
                        image.setImageResource(R.drawable.okimage);
                    }
                }
                //Toast.makeText(context,"Current speed: "+location.getSpeed(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0,locationListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
