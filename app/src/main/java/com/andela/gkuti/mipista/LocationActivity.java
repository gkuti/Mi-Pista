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
    }

    private void initializeComponent() {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(day).append("/")
                .append(month).append("/")
                .append(year);
        historyGenerator = new HistoryGenerator(this);
        locationsList = historyGenerator.getList(Date.getDate());
        initializeView();
    }

    private void initializeView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.location_list);
        locationAdapter = new LocationAdapter(locationsList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(locationAdapter);
        recyclerView.addItemDecoration(new Decorator(this));
    }
}
