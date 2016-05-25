package com.andela.gkuti.mipista;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class LocationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ArrayList<Location> locationsList = new ArrayList<>();
    private int day;
    private int month;
    private int year;
    private LocationAdapter locationAdapter;
    private HistoryGenerator historyGenerator;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initializeComponent();
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
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
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
        locationsList = historyGenerator.getList(stringBuilder.toString());
        locationAdapter.notifyDataSetChanged();
        userData.saveCurrentDate("date", stringBuilder.toString());
        setTitle(stringBuilder.toString());
    }

    private void initializeComponent() {
        userData = new UserData(this);
        historyGenerator = new HistoryGenerator(this);
        locationsList = historyGenerator.getList(Date.getDate());
        initializeView();
    }

    private void initializeView() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.location_list);
        locationAdapter = new LocationAdapter(locationsList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(locationAdapter);
        recyclerView.addItemDecoration(new Decorator(this));
        userData.saveCurrentDate("date", Date.getDate());
        setTitle(Date.getDate());
    }
    private void setTitle(String date){
        getSupportActionBar().setTitle(date);
    }
}
