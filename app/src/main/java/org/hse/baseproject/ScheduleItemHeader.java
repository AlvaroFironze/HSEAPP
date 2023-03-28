package org.hse.baseproject;

import android.os.Bundle;

public class ScheduleItemHeader extends ScheduleItem {
    public String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule_item_header);
    }
}
