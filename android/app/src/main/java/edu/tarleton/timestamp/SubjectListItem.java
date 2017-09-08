package edu.tarleton.timestamp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SubjectListItem extends ArrayAdapter<String>{

    private final ArrayList<String> ids;
    private final ArrayList<String> times;
    private final Activity context;

    public SubjectListItem(Activity context, ArrayList<String> id, ArrayList<String> time) {
        super(context, R.layout.list_single, id);
        this.context = context;
        this.ids = id;
        this.times = time;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.list_subject_id);
        TextView imageView = (TextView) rowView.findViewById(R.id.list_subject_time);

        txtTitle.setText(ids.get(position));
        imageView.setText(times.get(position));
        return rowView;
    }
}