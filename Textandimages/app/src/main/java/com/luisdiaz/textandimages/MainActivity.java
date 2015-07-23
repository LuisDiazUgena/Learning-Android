package com.luisdiaz.textandimages;

import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private Button buttonSwitch;
    private ImageView image;
    private Boolean clicked=false,debug = true;
    private CheckBox checkBoxDebug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSwitch = (Button) findViewById(R.id.BtnSwitch);
        image = (ImageView) findViewById(R.id.Img);
        checkBoxDebug = (CheckBox) findViewById(R.id.DebugCheckBox);

        checkBoxDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debug=checkBoxDebug.isChecked();
            }
        });

        buttonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clicked) {
                    image.setImageResource(R.drawable.ic_launcher2);
                }else{
                    image.setImageResource(R.drawable.ic_launcher1);
                }
                clicked=!clicked;
                if(debug) {
                    Log.e("TextAndImages", "clicked is " + clicked);
                    Log.e("TextAndImages", "Click on button");
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(debug) {
                    Log.e("TextAndImages", "Click on image");
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
}
