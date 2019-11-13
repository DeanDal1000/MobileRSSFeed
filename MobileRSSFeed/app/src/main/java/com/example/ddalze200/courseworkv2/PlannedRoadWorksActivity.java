package com.example.ddalze200.courseworkv2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.joda.time.DateTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ddalze200 on 22/03/2018.
 */

public class  PlannedRoadWorksActivity extends AppCompatActivity {

    private List<TrafficScotland> plannedroadworksList;
    private List<TrafficScotland> secList;
    private ArrayAdapter<TrafficScotland> plannedroadworksAdapter;
    private ArrayAdapter<TrafficScotland> currentincidentsAdapter;
    private Button getDateA;
    private Button getplannedroadworks;
    private EditText entText;
    private ListView plannedroadworksListView;
    private ListView ListViewSelected;
    Date selectDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plannedrw_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getDateA = (Button) findViewById(R.id.getDateA);
        getplannedroadworks = (Button) findViewById(R.id.getplannedroadworks);
        getplannedroadworks.setInputType(0);
        entText = (EditText) findViewById(R.id.entText);
        plannedroadworksListView = (ListView) findViewById(R.id.plannedroadworksListView);
        ListViewSelected = (ListView) findViewById(R.id.ListViewSelected);
        ListViewSelected.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        plannedroadworksList = (ArrayList<TrafficScotland>) getIntent().getSerializableExtra("plannedroadworksList");
        secList = new ArrayList<TrafficScotland>();
        plannedroadworksAdapter = new ArrayAdapterTraffic(PlannedRoadWorksActivity.this, 0, plannedroadworksList);
        plannedroadworksListView.setAdapter(plannedroadworksAdapter);
        plannedroadworksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long RowID) {
                TrafficScotland trafficScotland = plannedroadworksList.get(position);

                Intent intent = new Intent(PlannedRoadWorksActivity.this, InfomationActivity.class);
                intent.putExtra("title", trafficScotland.getTitle());
                intent.putExtra("description", trafficScotland.getDescription());
                intent.putExtra("link", trafficScotland.getLink());
                intent.putExtra("georss", trafficScotland.getGeorss());
                intent.putExtra("author", trafficScotland.getAuthor());
                intent.putExtra("comments", trafficScotland.getComments());
                intent.putExtra("pubDate", trafficScotland.getPubDate());
                startActivity(intent);
            }
        });

        ListViewSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long RowID) {
                TrafficScotland trafficScotland = plannedroadworksList.get(position);
                Intent intent = new Intent(PlannedRoadWorksActivity.this, InfomationActivity.class);
                intent.putExtra("title", trafficScotland.getTitle());
                intent.putExtra("description", trafficScotland.getDescription());
                intent.putExtra("link", trafficScotland.getLink());
                intent.putExtra("georss", trafficScotland.getGeorss());
                intent.putExtra("author", trafficScotland.getAuthor());
                intent.putExtra("comments", trafficScotland.getComments());
                intent.putExtra("pubDate", trafficScotland.getPubDate());
                startActivity(intent);
            }
        });

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                secList = new ArrayList<TrafficScotland>();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                selectDate = calendar.getTime();
                getSelectDate();
                if (secList.size() > 0) {
                    plannedroadworksAdapter = new ArrayAdapterTraffic(PlannedRoadWorksActivity.this, 0, secList);
                    ListViewSelected.setAdapter(plannedroadworksAdapter);
                    ListViewSelected.setVisibility(View.VISIBLE);
                    plannedroadworksListView.setVisibility(View.INVISIBLE);
                } else {
                    showFault();
                    ListViewSelected.setVisibility(View.INVISIBLE);
                    plannedroadworksListView.setVisibility(View.VISIBLE);
                }


            }
        };

        getDateA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View viewA) {
                new DatePickerDialog(PlannedRoadWorksActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        getplannedroadworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewA) {
                try {
                    InputMethodManager inputMethod = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethod.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                secList = new ArrayList<TrafficScotland>();
                String getText = entText.getText().toString();
                getroadworks(getText);
                if (secList.size() > 0) {
                    plannedroadworksAdapter = new ArrayAdapterTraffic(PlannedRoadWorksActivity.this, 0, secList);
                    ListViewSelected.setAdapter(plannedroadworksAdapter);
                    ListViewSelected.setVisibility(View.VISIBLE);
                    ListViewSelected.setVisibility(View.INVISIBLE);
                } else {
                    showFault();
                    ListViewSelected.setVisibility(View.INVISIBLE);
                    plannedroadworksListView(View.VISIBLE);
                }
            }

            private void plannedroadworksListView(int visible) {
            }


        });


    }

    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: finish();
                    return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
    public void showFault() {
        Toast.makeText(PlannedRoadWorksActivity.this, "NONE!", Toast.LENGTH_LONG).show();
    }

    public void getroadworks (String find) {
        for (TrafficScotland TS : plannedroadworksList) {
            String search = TS.getTitle();
            if (search.toLowerCase().indexOf(find.toLowerCase()) != -1) {
                secList.add(TS);
            }
        }
    }

    public void getSelectDate(){
        for(TrafficScotland TS:plannedroadworksList) {
            String pattern ="dd MMMM yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            DateTime DS = new DateTime(selectDate);
            String[] segments = TS.getDescription().split("<br />");
            String segment1 = segments[0];
            String segment2 = segments[1];
            segment1= segment1.substring(segment1.indexOf(',')+1,segment1.indexOf('-')).substring(1);
            segment2= segment2.substring(segment2.indexOf(',')+1,segment2.indexOf('-')).substring(1);
            try{

                Date from =simpleDateFormat.parse(segment1);
                Date to =simpleDateFormat.parse(segment2);

                DateTime start = new DateTime(from);
                DateTime end = new DateTime(to);

                for(DateTime nowdate = start; nowdate.isBefore(end); nowdate=nowdate.plusDays(1)){
                    String nowdatestring = nowdate.toString().substring(0,10);
                    String secDatestring = DS.toString().substring(0,10);
                    if(nowdatestring.equals(secDatestring)){
                        secList.add(TS);
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}