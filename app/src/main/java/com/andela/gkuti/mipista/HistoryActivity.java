package com.andela.gkuti.mipista;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ArrayList<History> historyList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        String location = getIntent().getExtras().getString("location");
        Datastore datastore = new Datastore(this);
        Cursor cursor = datastore.getHistoryByLocation(location);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String startTime = cursor.getString(cursor.getColumnIndex("StartTime"));
            String endTime = cursor.getString(cursor.getColumnIndex("EndTime"));
            History history = new History(startTime,endTime);
            historyList.add(history);
            cursor.moveToNext();
        }
        RecyclerView historyView = (RecyclerView) findViewById(R.id.history_list);
        HistoryAdapter historyAdapter = new HistoryAdapter(historyList,this);
        historyView.setLayoutManager(new LinearLayoutManager(this));
        historyView.setAdapter(historyAdapter);
    }
}
