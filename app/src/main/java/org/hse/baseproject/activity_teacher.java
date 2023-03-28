package org.hse.baseproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class activity_teacher extends AppCompatActivity {
    private TimeInterval timeInterval;
    private TextView time, status, subject, cabinet, corp, teacher;
    Date currentTime;
    activity_student.Group[] mock = {
            new activity_student.Group(1,"Радионова"),
            new activity_student.Group(2,"Яборов"),
            new activity_student.Group(3,"Лядова"),
            new activity_student.Group(4,"Суворов"),
            new activity_student.Group(5,"Ветров")
    };
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        spinner = findViewById(R.id.groupList);

        List<activity_student.Group> groups = new ArrayList<>();
        initGroupList(groups,mock);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                Object item = adapter.getItem(selectedItemPosition);
                Log.d("TAG", "selectedItem" + item);

            }

            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        time = findViewById(R.id.time);


        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);



        View SheduleDay = findViewById(R.id.button_day);
        SheduleDay.setOnClickListener(v->showShedule(MainActivity.SheduleType.DAY));
        View SheduleWeek = findViewById(R.id.button_week);
        SheduleWeek.setOnClickListener(v->showShedule(MainActivity.SheduleType.WEEK));
        final TimeIntervalProvider timeIntervalProvider = new TimeIntervalProvider();
        timeInterval = timeIntervalProvider.getTimeInterval(MainActivity.SheduleType.WEEK);
        initData();
        initTime();
    }
    private void showShedule(MainActivity.SheduleType type){
        Object selectedItem = spinner.getSelectedItem();
        if (!(selectedItem instanceof activity_student.Group)){
            return;
        }
        showSheduleImp(MainActivity.SheduleMode.TEACHER,type,(activity_student.Group)selectedItem);
    }
    private void showSheduleImp(MainActivity.SheduleMode mode, MainActivity.SheduleType type, activity_student.Group group ){
        Intent intent = new Intent(this,SheduleActivity.class);
        intent.putExtra("ARG_ID",mock[group.getId()-1].getName());
        intent.putExtra("ARG_TYPE",type);
        intent.putExtra("ARG_MODE",mode);
        intent.putExtra("TIME",timeInterval.getStartTime());

        startActivity(intent);
    }

    private void initGroupList(List<activity_student.Group> groups, activity_student.Group[] list){
        for (activity_student.Group teacher: list){
            groups.add(new activity_student.Group(teacher.getId(),teacher.getName()));
        }
    }

    private void initTime(){
        DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {
            @Override
            public String[] getWeekdays() {
                return new String[] {"", "Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
            }
        };


        time.setText((timeInterval.getStartTime()));
    }

    private void initData(){
        status.setText("Нет пар");

        subject.setText("Дисциплина");
        cabinet.setText("Кабинет");
        corp.setText("Корпус");
        teacher.setText("Преподавтель");
    }
}