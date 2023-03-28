package org.hse.baseproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class activity_settings extends AppCompatActivity implements SensorEventListener {
    private static final Integer REQUEST_PERMISSION_CODE = 1;
    private static final Integer REQUEST_IMAGE_CAPTURE = 2;
    private static final String PREF_NAME = "name";
    private static final String PREF_AVATAR = "avatar";
    private PreferenceManager preferenceManager;
    private SensorManager sensorManager;
    private Sensor light;
    private TextView sensorLight;
    private ImageView userPhoto;
    private EditText nameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        preferenceManager = new PreferenceManager(this);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorLight = findViewById(R.id.lightLevel);
        sensorLight.setText("0 lux");

        nameEdit = findViewById(R.id.name);
        getName();

        TextView allSensors = findViewById(R.id.allSensors);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder allSensorsText = new StringBuilder();
        for (Sensor sensor : sensors)
            allSensorsText.append(sensor.getName()).append('\n');
        allSensors.setText(allSensorsText.toString());
        allSensors.setMovementMethod(new ScrollingMovementMethod());

        Button buttonPhoto = findViewById(R.id.button);

        buttonPhoto.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            checkPermission();
        }

    });
        Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        userPhoto = findViewById(R.id.userPhoto);
        getPhoto();
    }

    private void getName() {
        String name = preferenceManager.getValue(PREF_NAME, "");
        if (!name.isEmpty())
            nameEdit.setText(name);
    }
    private void getPhoto() {
        String uri = preferenceManager.getValue(PREF_AVATAR, "");
        if (!uri.isEmpty()){
            photoURI = Uri.parse(uri);
            userPhoto.setImageURI(photoURI);
        }
    }
    private void showExplanation(String title, String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface
                                                        dialog, int id) {
                                requestPermission(permission,
                                        permissionRequestCode);
                            }
                        });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permissionName},
                permissionRequestCode);
    }

    public void checkPermission() {
        int permissionCheck = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                showExplanation("Нужно предоставить права",
                        "Для снятия фото нужно предоставить права на фото", Manifest.permission.CAMERA, REQUEST_PERMISSION_CODE);
            } else {
                requestPermission(Manifest.permission.CAMERA, REQUEST_PERMISSION_CODE);
            }
        } else {
            dispatchTakePictureIntent();
        }
    }

    private String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    Uri photoURI = null;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.e(TAG, "Create file", ex);
        }
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, "Start activity", e);
            }

        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            userPhoto.setImageURI(photoURI);
        }
    }
    private void save() {
        if (nameEdit.getText() != null)
            preferenceManager.saveValue(PREF_NAME, nameEdit.getText().toString());
        if (photoURI != null)
            preferenceManager.saveValue(PREF_AVATAR, photoURI.toString());

    }
    private final static String TAG = "MainActivity";

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        sensorLight.setText(lux + "lux");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,light,SensorManager.SENSOR_DELAY_NORMAL);

    }
}
