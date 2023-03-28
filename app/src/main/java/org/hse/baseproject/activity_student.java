package org.hse.baseproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class activity_student extends AppCompatActivity {
    private TextView time, status, subject, cabinet, corp, teacher;
    Date currentTime;
    activity_student.Group[] mock = {
            new activity_student.Group(1,"ПИ-20-1"),
            new activity_student.Group(2,"ПИ-20-2"),
            new activity_student.Group(3,"ПИ-19-1"),
            new activity_student.Group(4,"ПИ-19-2"),
    };
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        spinner = findViewById(R.id.groupList);

        List<Group> groups = new ArrayList();
        initGroupList(groups);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedID) {
                Object item = adapter.getItem(selectedItemPosition);
                Log.d(TAG, "selectedItem: " + item);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        time = findViewById(R.id.time);
        initTime();

        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);

        initData();
        View SheduleDay = findViewById(R.id.button_day);
        SheduleDay.setOnClickListener(v->showShedule(MainActivity.SheduleType.DAY));
        View SheduleWeek = findViewById(R.id.button_week);
        SheduleWeek.setOnClickListener(v->showShedule(MainActivity.SheduleType.WEEK));
    }
    private void showShedule(MainActivity.SheduleType type){
        Object selectedItem = spinner.getSelectedItem();
        if (!(selectedItem instanceof activity_student.Group)){
            return;
        }
        showSheduleImp(MainActivity.SheduleMode.STUDENT,type,(activity_student.Group)selectedItem);
    }
    private void showSheduleImp(MainActivity.SheduleMode mode, MainActivity.SheduleType type, activity_student.Group group ){
        Intent intent = new Intent(this,SheduleActivity.class);
        intent.putExtra("ARG_ID",mock[group.getId()-1].getName());
        intent.putExtra("ARG_TYPE",type);
        intent.putExtra("ARG_MODE",mode);
        startActivity(intent);
    }

    private void initGroupList(List<Group> groups){
        String[] c = {"ПИ", "РИС", "УБ", "И"};
        String[] g = {"18", "19", "20", "21", "22"};
        String[] gr = {"1", "2", "3", "4"};

        List<String> itog = new ArrayList<String>();

        Random r = new Random();
        int randomNum = r.nextInt((10 - 2) + 2) + 2;
        int i4 =1;
        for(int i = 0; i < c.length; i++) {
            for(int i2 = 0; i2 < g.length; i2++) {
                for(int i3 = 0; i3 < gr.length; i3++) {
                    String s = c[i] +"-" + g[i2] + "-" + gr[i3];

                    groups.add(new Group(i4, s));
                    i4=i4+1;
                }
            }
        }
        mock= groups.toArray(mock);
    }

    private void initTime(){
        DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {
            @Override
            public String[] getWeekdays() {
                return new String[] {"", "Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
            }
        };
        currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, EEEE", myDateFormatSymbols);
        time.setText(simpleDateFormat.format(currentTime));
    }

    private void initData(){
        status.setText("Нет пар");

        subject.setText("Дисциплина");
        cabinet.setText("Кабинет");
        corp.setText("Корпус");
        teacher.setText("Преподавтель");
    }

    static class Group {
        private Integer id;
        private String name;

        public Group(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}