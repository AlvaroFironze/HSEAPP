package org.hse.baseproject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private OnItemClick onItemClick;
    private TextView start;
    private TextView end;
    private TextView type;
    private TextView name;
    private TextView place;
    private TextView teacher;

    public ViewHolder (View itemView, Context context, OnItemClick onItemClick) {
        super(itemView);
        this.context =context;
        this.onItemClick = onItemClick;
        start=itemView.findViewById(R.id.start);
        end=itemView.findViewById(R.id.end);
        type = itemView.findViewById(R.id.type);
        name = itemView.findViewById(R.id.namenonavie);
        place = itemView.findViewById(R.id.place);
        teacher = itemView.findViewById(R.id.teacher1);
    }
    public void bind (final ScheduleItem data) {
        start.setText(data.start);
        end.setText(data.end);
        type.setText(data.type);
        name.setText(data.name);
        place.setText(data.place);
        teacher.setText(data.teacher);
        Log.e("","bind "+name.getText());

    }
}
