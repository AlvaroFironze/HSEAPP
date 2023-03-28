package org.hse.baseproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View buttonTeacher = findViewById(R.id.buttonTeacher);
        View buttonStudent = findViewById(R.id.buttonStudent);
        View buttonSettings = findViewById(R.id.buttonSettings);

        buttonTeacher.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showTeacher();
            }

        });
        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showStudent();
            }
        });
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showSettings();
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showStudent() {
        Intent intent = new Intent(this, activity_student.class);
        startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showTeacher() {

        Intent intent = new Intent(this, activity_teacher.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showSettings() {

        Intent intent = new Intent(this, activity_settings.class);
        startActivity(intent);
    }
    enum SheduleType{
        DAY,
        WEEK
    }
    enum SheduleMode{
        STUDENT,
        TEACHER
    }
}

       
     


