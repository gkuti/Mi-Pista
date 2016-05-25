package com.andela.gkuti.mipista;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ArrayList<History> historyList = new ArrayList();
    private Datastore datastore;
    private String location;
    private String date;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        userData = new UserData(this);
        datastore = new Datastore(this);
        location = getIntent().getExtras().getString("location");
        date = userData.getCurrentDate("date");
        generateList();
        initializeView();
    }

    private void generateList() {
        Cursor cursor = datastore.getHistoryByLocation(location, date);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String startTime = cursor.getString(cursor.getColumnIndex(Constants.START_TIME_COLUMN.getValue()));
            String endTime = cursor.getString(cursor.getColumnIndex(Constants.END_TIME_COLUMN.getValue()));
            String duration = DateUtils.formatElapsedTime(cursor.getInt(cursor.getColumnIndex(Constants.DURATION_COLUMN.getValue())));
            History history = new History(startTime, endTime, duration);
            historyList.add(history);
            cursor.moveToNext();
        }
    }

    private void initializeView() {
        RecyclerView historyView = (RecyclerView) findViewById(R.id.history_list);
        HistoryAdapter historyAdapter = new HistoryAdapter(historyList, this);
        historyView.setLayoutManager(new LinearLayoutManager(this));
        historyView.setAdapter(historyAdapter);
    }
}
