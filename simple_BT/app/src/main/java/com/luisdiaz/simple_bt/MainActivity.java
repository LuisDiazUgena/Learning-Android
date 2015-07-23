//based on example http://digitalhacksblog.blogspot.com.es/2012/05/android-example-bluetooth-simple-spp.html

package com.luisdiaz.simple_bt;


import android.app.Activity;
import android.support.v7.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

//Bluetooth
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

//UI Elements
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

//Others
import android.content.Intent;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;


public class MainActivity extends Activity {

    //Debug variables
    private static final String TAG = "simpleBT";
    private static final boolean DEBUG = true;

    //UI Variables
    TextView outputText;
    Button sendButton,connectButton,disconnectButton;
    TextView sendText;

    //BT Variables
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;

    //UUID V4 random generated
    private static final UUID MY_UUID = UUID.fromString("78514d86-0c1a-4bb0-a68f-0d7ae849eda1");

    //Mac Address (hardcoded)
    private static String address = "90:E5:42:E0:1C:91";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Buttons
        sendButton = (Button) findViewById(R.id.SendButton);
        connectButton = (Button) findViewById(R.id.BtnConnect);
        disconnectButton = (Button) findViewById(R.id.BtnDisconnect);

        //Text
        sendText = (EditText) findViewById(R.id.SendText);
        outputText = (TextView) findViewById(R.id.OutputText);

        LogText("In on Create");

        btAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogText("In On Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogText("in On Resume");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = sendText.getText().toString(); // Message to send is here!
                outputText.setText(message);
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBTState();
                LogText("Trying to connect to BT device");

                BluetoothDevice device = btAdapter.getRemoteDevice(address);
                // Two things are needed to make a connection:
                //   A MAC address, which we got above.
                //   A Service ID or UUID.  In this case we are using the
                //     UUID for SPP.
                try{
                    btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                }catch (IOException e){
                    AlertBox("Fatal Error","In onResume(). Socket create failed:" +e.getMessage()+".");
                }

                // Discovery is resource intensive.  Make sure it isn't going on
                // when you attempt to connect and pass your message.
                btAdapter.cancelDiscovery();

                //Establish connection
                try{
                    btSocket.connect();
                }catch (IOException e){
                    try{
                        btSocket.close();
                    }catch (IOException e2){
                        AlertBox("Fatal Error","In onResume. Unable to close socket during connection failure: "+e2.getMessage()+".");
                    }
                }

                //create a data stream to talk to server
                try{
                    outStream = btSocket.getOutputStream();
                }catch (IOException e){
                    AlertBox("Fatal Error","In onResume. Unable to close socket during connection failure: "+e.getMessage()+".");
                }
            }
        });
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

    private void CheckBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            AlertBox("Fatal Error", "Bluetooth Not supported. Aborting.");
        } else {
            if (btAdapter.isEnabled()) {
                LogText("\n...Bluetooth is enabled...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }
    public void AlertBox( String title, String message ){
        new AlertDialog.Builder(this)
                .setTitle( title )
                .setMessage( message + " Press OK to exit." )
                .setPositiveButton("OK", new OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).show();
    }
    public void LogText(String text){
        if (DEBUG){
            Log.e(TAG,text);
        }
        //outputText.append("\n"+text+"\n");
    }
}
