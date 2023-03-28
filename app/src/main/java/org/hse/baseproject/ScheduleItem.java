package org.hse.baseproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleItem extends AppCompatActivity {
    public String start;
    public String end;
    public String type;
    public String name;
    public String place;
    public String teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule_item);

    }

}
interface OnItemClick{
    void OnClick(ScheduleItem data);
}