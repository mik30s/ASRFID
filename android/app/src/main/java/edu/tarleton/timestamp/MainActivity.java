package edu.tarleton.timestamp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alien.common.KeyCode;
import com.alien.rfid.ReaderException;
import com.alien.rfid.Tag;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    public enum EntryType {BEGIN, END}

    ListView tagListView;
    EntryType currentEntryType;
    ArrayAdapter<String> tagListAdapter;
    ArrayList<String> subjectIds = new ArrayList<>();
    ArrayList<String> timeStamps = new ArrayList<>();

    public void readTag()
    {
        try
        {
            TagReader reader = new TagReader();
            Tag tag = reader.read();
            if (tag.getTID().isEmpty()) {
                Snackbar.make(findViewById(R.id.rootCoordinatorLayout),
                        "Tag read " + tag.getEPC(), Snackbar.LENGTH_SHORT).show();
                SubjectData data =  new SubjectData();
                data.tagId = (tag.getTID().isEmpty())
                             ? BigInteger.ZERO
                             : BigInteger.valueOf(Integer.parseInt(tag.getTID()));

                data.startTime = Calendar.getInstance().getTime();
                SubjectDataWriter subjectDataWriter = new SubjectDataWriter(this, data);
                subjectDataWriter.execute();
                subjectIds.add("" + tag.getTID());
                timeStamps.add("" + Calendar.getInstance().getTime().toString());
                tagListAdapter.notifyDataSetChanged();
            }
            else {
                throw new ReaderException("Cannot read tag");
            }
        }
        catch(ReaderException ex)
        {
            Snackbar.make(findViewById(R.id.rootCoordinatorLayout),
                    ex.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyCode.ALR_H450.SCAN && event.getRepeatCount() == 0) {
            readTag();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // set list adapter
        tagListAdapter = new SubjectListItem(MainActivity.this, subjectIds, timeStamps);

        final ListView tagListView = (ListView)findViewById(R.id.listView);
        tagListView.setAdapter(tagListAdapter);

        Switch entryTypeToggleButton = (Switch) findViewById(R.id.add_subject_switch);
        entryTypeToggleButton.setChecked(true);
        entryTypeToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isOn) {
                // change the text vuiew text
                TextView tv = (TextView) findViewById(R.id.list_subject_time_label);
                if(tv !=  null) {
                    if (isOn) {
                        tv.setText("Start Time");
                        currentEntryType = EntryType.BEGIN;
                    } else {
                        tv.setText("End Time");
                        currentEntryType = EntryType.END;
                    }
                    Log.e("curent type: ", currentEntryType.toString());
                    if (!subjectIds.isEmpty()) subjectIds.clear();
                    if (!timeStamps.isEmpty()) timeStamps.clear();
                    tagListView.setAdapter(tagListAdapter);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
