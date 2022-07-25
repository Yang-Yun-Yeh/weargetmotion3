package com.example.weargetmotion3;

import static java.lang.Math.sqrt;

import java.lang.Math;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.sql.Timestamp;

import com.example.weargetmotion3.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends Activity {
    private TextView mTextView;
    private ActivityMainBinding binding;

    private TextView M1, M2;
    private Button btnSend, btnStart, btnStop;

    private SensorManager mSensorManager;
    private SensorManager aSensorManager;
    private Sensor mSensor;
    private Sensor aSensor;
    private Timestamp timestamp;
    private String timestamp_str;

    private float gravity[] = new float[3];
    private float linear_acceleration[] = new float[3];
    private float linear_acceleration_user[] = new float[3];
    private float acceleration;
    private float acceleration_user;

    private static final float NS2S = 1.0f / 1000000000.0f;
    private float ang_velocity[] = new float[3];
    private float omega;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp_ang;

    private boolean sendData = false;

    private SensorEventListener mSenserListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // In this example, alpha is calculated as t / (t + dT),
            // where t is the low-pass filter's timestamp-constant and
            // dT is the event delivery rate.
            final double alpha = 0.975; //0.8

            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = (float) (alpha * gravity[0] + (1 - alpha) * event.values[0]);
            gravity[1] = (float) (alpha * gravity[1] + (1 - alpha) * event.values[1]);
            gravity[2] = (float) (alpha * gravity[2] + (1 - alpha) * event.values[2]);

            linear_acceleration_user[0] = linear_acceleration[0] - gravity[0]; //因為要抵抗重力(與重力反向)
            linear_acceleration_user[1] = linear_acceleration[1] - gravity[1];
            linear_acceleration_user[2] = linear_acceleration[2] - gravity[2];

            // Remove the gravity contribution with the high-pass filter.
            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];

            acceleration = (float) sqrt(linear_acceleration[0] * linear_acceleration[0] + linear_acceleration[1] * linear_acceleration[1] + linear_acceleration[2] * linear_acceleration[2]);
            acceleration_user = (float) sqrt(linear_acceleration_user[0] * linear_acceleration_user[0] + linear_acceleration[1] * linear_acceleration_user[1] + linear_acceleration_user[2] * linear_acceleration_user[2]);

            Log.d("Motion g: ", "" + gravity[0]);
            //Log.d("Motion a: ", "" + linear_acceleration[0]);

            M1.setText("Acceleration: " + acceleration);

            Long datetime = System.currentTimeMillis();
            timestamp = new Timestamp(datetime);
            timestamp_str = timestamp.toString();

            /*
            String dataJson = "{ \"" + timestamp + "\" :[{\"Type\":\"gravity\",\"gravity[0]\":\"" + gravity[0] + "\",\"gravity[1]\":\"" + gravity[1] + "\",\"gravity[2]\":\"" + gravity[2] + "\"},{\"Type\":\"linear_acceleration\",\"linear_acceleration[0]\":\"" + linear_acceleration[0] + "\",\"linear_acceleration[1]\":\"" + linear_acceleration[1] + "\",\"linear_acceleration[2]\":\"" + linear_acceleration[2] + "\"}] }";
            String strJson = "{ \"Employee\" :[{\"id\":\"101\",\"name\":\"Sonoo Jaiswal\",\"salary\":\"50000\"},{\"id\":\"102\",\"name\":\"Vimal Jaiswal\",\"salary\":\"60000\"}] }";

            String dataMotion = "";
            try {
                // Create the root JSONObject from the JSON string.
                JSONObject  jsonRootObject = new JSONObject(dataJson);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonRootObject.optJSONArray("" + timestamp);

                //Iterate the jsonArray and print the info of JSONObjects
                String type[] = new String[2];
                double g[] = new double[3];
                double la[] = new double[3];
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    type[0] = jsonObject.optString("Type").toString();
                    g[0] = Double.parseDouble(jsonObject.optString("gravity[0]").toString());
                    g[1] = Double.parseDouble(jsonObject.optString("gravity[1]").toString());
                    g[2] = Double.parseDouble(jsonObject.optString("gravity[2]").toString());

                    jsonObject = jsonArray.getJSONObject(1);

                    type[1] = jsonObject.optString("Type").toString();
                    la[0] = Double.parseDouble(jsonObject.optString("linear_acceleration[0]").toString());
                    la[1] = Double.parseDouble(jsonObject.optString("linear_acceleration[1]").toString());
                    la[2] = Double.parseDouble(jsonObject.optString("linear_acceleration[2]").toString());
                }
                dataMotion += "\n type[0] = " + type[0] + "\n g[0] = " + g[0] + "\n g[1] = " + g[1] + "\n g[2] = " + g[2] +
                                "\n\n type[1] = " + type[1] + "\n la[0] = " + la[0] + "\n la[1] = " + la[1] + "\n la[2] = " + la[2];
                Log.d("json: ", "\n" + dataMotion);
            } catch (JSONException e) {e.printStackTrace();}
             */
            /*
            if(sendData){
                DAORawDataModel dao = new DAORawDataModel();
                RawDataModel motion = new RawDataModel(gravity[0], gravity[1], gravity[2], linear_acceleration[0], linear_acceleration[1], linear_acceleration[2], timestamp_str);
                dao.add(motion).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
             */
            if(sendData){
                sendDataToFirebase();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private SensorEventListener aSenserListener = new SensorEventListener(){
        @Override
        public void onSensorChanged(SensorEvent event) {
            // This timestep's delta rotation to be multiplied by the current rotation
            // after computing it from the gyro sample data.
            if (timestamp_ang != 0) {
                final float dT = (event.timestamp - timestamp_ang) * NS2S;
                // Axis of the rotation sample, not normalized yet.
                float axisX = event.values[0];
                float axisY = event.values[1];
                float axisZ = event.values[2];

                // Calculate the angular speed of the sample
                float omegaMagnitude = (float) sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);
                omega = omegaMagnitude;
                M2.setText("Omega: " + omega);

                // Normalize the rotation vector if it's big enough to get the axis
                // (that is, EPSILON should represent your maximum allowable margin of error)
                /*
                float EPSILON = 0;
                if (omegaMagnitude > EPSILON) {
                    axisX /= omegaMagnitude;
                    axisY /= omegaMagnitude;
                    axisZ /= omegaMagnitude;
                }
                */
                ang_velocity[0] = axisX;
                ang_velocity[1] = axisY;
                ang_velocity[2] = axisZ;
                // Integrate around this axis with the angular speed by the timestep
                // in order to get a delta rotation from this sample over the timestep
                // We will convert this axis-angle representation of the delta rotation
                // into a quaternion before turning it into the rotation matrix.

                float thetaOverTwo = omegaMagnitude * dT / 2.0f;
                float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);
                float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
                deltaRotationVector[0] = sinThetaOverTwo * axisX;
                deltaRotationVector[1] = sinThetaOverTwo * axisY;
                deltaRotationVector[2] = sinThetaOverTwo * axisZ;
                deltaRotationVector[3] = cosThetaOverTwo;

            }

            timestamp_ang = event.timestamp;
            float[] deltaRotationMatrix = new float[9];
            mSensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);

            // User code should concatenate the delta rotation we computed with the current rotation
            // in order to get the updated rotation.
            // rotationCurrent = rotationCurrent * deltaRotationMatrix;

            //Log.d("ang", "" + deltaRotationVector[0]);
            //Log.d("timestamp", "" + timestamp_ang)
            /*
            if(sendData){
                sendDataToFirebase();
            }
             */
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public void sendDataToFirebase(){
        DAORawDataModel dao = new DAORawDataModel();
        RawDataModel motion = new RawDataModel(linear_acceleration[0], linear_acceleration[1], linear_acceleration[2], acceleration, ang_velocity[0], ang_velocity[1], ang_velocity[2], omega, timestamp_str);
        dao.add(motion).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //Toast.makeText(MainActivity.this, "Record is inserted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //mTextView = binding.text;

        M1 = findViewById(R.id.txtM1);
        M2 = findViewById(R.id.txtM2);
        //btnSend = findViewById(R.id.btnSend);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        aSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        aSensor = aSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        /*
        DAORawDataModel dao = new DAORawDataModel();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RawDataModel motion = new RawDataModel(linear_acceleration[0], linear_acceleration[1], linear_acceleration[2], acceleration, ang_velocity[0], ang_velocity[1], ang_velocity[2], omega, timestamp_str);
                dao.add(motion).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        */
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData = true;
                Toast.makeText(MainActivity.this, "Start send data", Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData = false;
                Toast.makeText(MainActivity.this, "Stop send data", Toast.LENGTH_SHORT).show();

                MotionAnalysis MA = new MotionAnalysis();
                MA.getData();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        //mSensorManager.registerListener(mSenserListener, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);
        //mSensorManager.registerListener(aSenserListener, aSensor, mSensorManager.SENSOR_DELAY_NORMAL);
        //mSensorManager.registerListener(mSenserListener, mSensor, 100000);
        //aSensorManager.registerListener(aSenserListener, aSensor, 100000);
        mSensorManager.registerListener(mSenserListener, mSensor, mSensorManager.SENSOR_DELAY_GAME);
        aSensorManager.registerListener(aSenserListener, aSensor, mSensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSenserListener);
        aSensorManager.unregisterListener(aSenserListener);
    }
}