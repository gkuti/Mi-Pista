package com.andela.gkuti.mipista.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;

import com.andela.gkuti.mipista.util.Constants;
import com.andela.gkuti.mipista.dal.Datastore;
import com.andela.gkuti.mipista.model.History;
import com.andela.gkuti.mipista.adapter.HistoryAdapter;
import com.andela.gkuti.mipista.R;
import com.andela.gkuti.mipista.dal.UserData;

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
        setTitle(location);
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
        HistoryAdapter historyAdapter = new HistoryAdapter(historyList);
        historyView.setLayoutManager(new LinearLayoutManager(this));
        historyView.setAdapter(historyAdapter);
    }
    private void setTitle(String date){
        getSupportActionBar().setTitle(date);
    }
}
