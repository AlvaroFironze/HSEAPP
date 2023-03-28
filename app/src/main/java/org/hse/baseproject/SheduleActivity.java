package org.hse.baseproject;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SheduleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private TextView time;
    private TextView fio;
    private Spinner spinner;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule);
        MainActivity.SheduleType type = (MainActivity.SheduleType) getIntent().getSerializableExtra("ARG_TYPE");
        MainActivity.SheduleMode mode = (MainActivity.SheduleMode) getIntent().getSerializableExtra("ARG_MODE");
        String id = getIntent().getStringExtra("ARG_ID");
        String time1 = getIntent().getStringExtra("TIME");

        recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ItemAdapter(this::onScheduleItemClick);
        recyclerView.setItemViewCacheSize(0);
        recyclerView.setAdapter(adapter);
        time= findViewById(R.id.timeFromTo);
        fio= findViewById(R.id.fio);
        spinner = findViewById(R.id.spinner);
        String[] mounths = new String[]{"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь", "Октябрь","Ноябрь","Декабрь"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,mounths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        fio.setText(id);
        initData();


            time.setText(time1);
    }

    private void initData() {
        List<ScheduleItem> list = new ArrayList<>();

        ScheduleItemHeader header = new ScheduleItemHeader();
        header.title = "Понедельник, 28 января";

        list.add(header);
        ScheduleItem item = new ScheduleItem();
        item.start = "10:00";
        item.end = "11:00";
        item.type = "Практическое занятие";
        item.name = "Анализ данныx (aHr)";
        item.place = "Ауд. 503, Кочновский пр-д, д.3";
        item.teacher = "Пред.Гущим Михаил Иванович";
        list.add(item);
        item = new ScheduleItem();
        item.start = "12:00";
        item.end = "13:00";
        item.type = "Практическое занятие";
        item.name = "Анализ данных (анг)";
        item.place = "Ауд. 503, Кочновский пр-д, д.3";
        item.teacher = "Пред. Гущим Михаил Иванович";
        list.add(item);
        adapter.setDataList(list);
    }

    private void onScheduleItemClick(ScheduleItem scheduleItem) {
    }


}
