package com.andela.gkuti.mipista;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class LocationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    ArrayList<Location> locationsList = new ArrayList<>(); ;
    private int day;
    private int month;
    private int year;
    Datastore datastore;
    LocationAdapter locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.location_list);
        locationAdapter = new LocationAdapter(locationsList,this);
        datastore = new Datastore(this);
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(day).append("/")
                .append(month).append("/")
                .append(year);
        createData(Date.getDate());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(locationAdapter);
        recyclerView.addItemDecoration(new Decorator(this));
    }
    public void createData(String date){
        locationsList.clear();
        ArrayList<String> locationList = new ArrayList<>();
        HashMap<String,Integer> locationHits = new HashMap();
        Cursor cursor = datastore.getHistoryByDate(date);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String Location = cursor.getString(cursor.getColumnIndex("Location"));
            if(locationList.contains(Location)){
                int hits = locationHits.get(Location);
                hits++;
                locationHits.put(Location, hits);
            }
            else {
                locationList.add(Location);
                locationHits.put(Location, 1);
            }
            cursor.moveToNext();
        }
        for (int i = 0; i < locationList.size(); i++){
            String loc = locationList.get(i);
            String hits = "hits "+String.valueOf(locationHits.get(loc));
            Location location = new Location(loc,hits);
            locationsList.add(location);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_location) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,year,month,day);
            datePickerDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dayOfMonth).append("/")
                    .append(monthOfYear + 1).append("/")
                    .append(year);
            createData(stringBuilder.toString());
            locationAdapter.notifyDataSetChanged();
    };
}
