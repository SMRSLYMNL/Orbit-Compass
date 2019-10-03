package com.exampleapp.sscompany.compassapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Compass_Orbit extends AppCompatActivity implements SensorEventListener
{
    /*
    private ImageView compass;
    private float[] myGravity = new float[3];
    private float[] myGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float currentAziuth = 0f;
    private SensorManager mySensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compass = (ImageView) findViewById(R.id.compassPNG);
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener(this, mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);

        mySensorManager.registerListener(this, mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        final float alpha = 0.97f;

        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            {
                myGravity[0] = alpha*myGravity[0] + (1 - alpha) * event.values[0];
                myGravity[1] = alpha*myGravity[1] + (1 - alpha) * event.values[1];
                myGravity[2] = alpha*myGravity[2] + (1 - alpha) * event.values[2];
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            {
                myGeomagnetic[0] = alpha*myGeomagnetic[0] + (1 - alpha) * event.values[0];
                myGeomagnetic[1] = alpha*myGeomagnetic[1] + (1 - alpha) * event.values[1];
                myGeomagnetic[2] = alpha*myGeomagnetic[2] + (1 - alpha) * event.values[2];
            }

            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, myGravity, myGeomagnetic);

            if(success)
            {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                azimuth = (float) Math.toDegrees(orientation[0]);
                azimuth = (azimuth + 360) % 360;

                float degree = Math.round(event.values[0]);
                System.out.println("Degree:   " + degree);

                Animation animation = new RotateAnimation(-currentAziuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

                currentAziuth = azimuth;

                animation.setDuration(500);
                animation.setRepeatCount(0);
                animation.setFillAfter(true);

                compass.startAnimation(animation);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    */
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    TextView heading;
    TextView degreeView;
    RelativeLayout general;

    Drawable background;

    private int compassId = 1;
    private int layoutId = 1;

    public static final String PREFS_NAME = "MyPrefsFile2";
    public static final String LAYOUT_MODE = "Mode";
    public CheckBox dontShowAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mode = getSharedPreferences(LAYOUT_MODE, Context.MODE_PRIVATE);
        String modeString = mode.getString("mode", "1");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // our compass image
        image = (ImageView) findViewById(R.id.compassPNG);
        general = (RelativeLayout) findViewById(R.id.general_layout);

        heading = (TextView) findViewById(R.id.orientation);

        degreeView = (TextView) findViewById(R.id.degree);


        if(modeString.equals("1"))
        {
            image.setImageResource(R.drawable.compass_1);
            general.setBackgroundResource(R.drawable.background_1);
            background = general.getBackground();
            background.setAlpha(130);
        }
        else if(modeString.equals("2"))
        {
            image.setImageResource(R.drawable.compass_2);
            general.setBackgroundResource(R.drawable.background_2);
            background = general.getBackground();
            background.setAlpha(130);
        }
        else if(modeString.equals("3"))
        {
            image.setImageResource(R.drawable.compass_3);
            general.setBackgroundResource(R.drawable.background_3);
            background = general.getBackground();
            background.setAlpha(130);
        }


        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences mode = getSharedPreferences(LAYOUT_MODE, Context.MODE_PRIVATE);
                String modeString = mode.getString("mode", "1");
                System.out.println("MODE :  " + modeString);

                SharedPreferences.Editor editor = mode.edit();

                if(modeString.equals("1"))
                {
                    image.setImageResource(R.drawable.compass_2);
                    general.setBackgroundResource(R.drawable.background_2);
                    background = general.getBackground();
                    background.setAlpha(130);

                    editor.putString("mode", "2");
                    editor.commit();
                }
                else if(modeString.equals("2"))
                {
                    image.setImageResource(R.drawable.compass_3);
                    general.setBackgroundResource(R.drawable.background_3);
                    background = general.getBackground();
                    background.setAlpha(130);
                    //heading.setTextColor(Color.parseColor("#33FF00"));
                    //degreeView.setTextColor(Color.parseColor("#FFFF00"));

                    editor.putString("mode", "3");
                    editor.commit();
                }
                else if(modeString.equals("3"))
                {
                    image.setImageResource(R.drawable.compass_1);
                    general.setBackgroundResource(R.drawable.background_1);
                    background = general.getBackground();
                    background.setAlpha(130);

                    editor.putString("mode", "1");
                    editor.commit();
                }
                return true;
            }
        });

        general.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                SharedPreferences mode = getSharedPreferences(LAYOUT_MODE, Context.MODE_PRIVATE);
                String modeString = mode.getString("mode", "1");
                System.out.println("MODE :  " + modeString);
                SharedPreferences.Editor editor = mode.edit();

                if(modeString.equals("1"))
                {
                    image.setImageResource(R.drawable.compass_2);
                    general.setBackgroundResource(R.drawable.background_2);

                    background = general.getBackground();
                    background.setAlpha(130);

                    editor.putString("mode", "2");
                    editor.commit();
                }
                else if(modeString.equals("2"))
                {
                    image.setImageResource(R.drawable.compass_3);
                    general.setBackgroundResource(R.drawable.background_3);
                    background = general.getBackground();
                    background.setAlpha(130);
                    //heading.setTextColor(Color.parseColor("#33FF00"));
                    //degreeView.setTextColor(Color.parseColor("#FFFF00"));

                    editor.putString("mode", "3");
                    editor.commit();
                }
                else if(modeString.equals("3"))
                {
                    image.setImageResource(R.drawable.compass_1);
                    general.setBackgroundResource(R.drawable.background_1);
                    background = general.getBackground();
                    background.setAlpha(130);

                    editor.putString("mode", "1");
                    editor.commit();
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

        AlertDialog.Builder adb = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipMessage", "NOT checked");

        dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);
        adb.setTitle("Attention");
        adb.setMessage("\nInstructions: Please, place the phone onto a plain surface.\n\n" +
        "You can change the mode by a long click.");

        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";

                if (dontShowAgain.isChecked()) {
                    checkBoxResult = "checked";
                }

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString("skipMessage", checkBoxResult);
                editor.commit();

                // Do what you want to do on "OK" action

                return;
            }
        });

        if (!skipMessage.equals("checked")) {
            adb.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        degreeView.setText((int)degree + "°");

        if(degree < 11.25 || degree >= 345.75)
            heading.setText("NORTH");

        if(degree < 345.75 && degree >= 326.25)
            heading.setText("North-NorthWest");

        if(degree < 326.25 && degree >= 303.75)
            heading.setText("NorthWest");

        if(degree < 303.75 && degree >= 281.25)
            heading.setText("West-NorthWest");

        if(degree < 281.25 && degree >= 258.75)
            heading.setText("WEST");

        if(degree < 258.75 && degree >= 236.25)
            heading.setText("West-SouthWest");

        if(degree < 236.25 && degree >= 213.75)
            heading.setText("SouthWest");

        if(degree < 213.75 && degree >= 191.25)
            heading.setText("South-SouthWest");

        if(degree < 191.25 && degree >= 168.75)
            heading.setText("SOUTH");

        if(degree < 168.75 && degree >= 146.25)
            heading.setText("South-SouthEast");

        if(degree < 146.25 && degree >= 123.75)
            heading.setText("SouthEast");

        if(degree < 123.75 && degree >= 101.25)
            heading.setText("East-SouthEast");

        if(degree < 101.25 && degree >= 78.75)
            heading.setText("EAST");

        if(degree < 78.75 && degree >= 56.25)
            heading.setText("East-NorthEast");

        if(degree < 56.25 && degree >= 33.75)
            heading.setText("NorthEast");

        if(degree < 33.75 && degree >= 11.25)
            heading.setText("North-NorthEast");



        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation animation = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        // how long the animation will take place
        animation.setDuration(20);

        // set the animation after the end of the reservation status
        animation.setFillAfter(true);

        // Start the animation
        image.startAnimation(animation);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}
