package com.example.ddalze200.courseworkv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ddalze200 on 3/23/2018.
 */

public class CurrentIncidentsActivity extends AppCompatActivity {

    private String currentincidentsURL="http://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private List<TrafficScotland> currentincidentsList;
    private ArrayAdapter<TrafficScotland> currentincidentsAdapter;
    private ListView currentincidentsListView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentincidents_layout);

        currentincidentsListView = (ListView) findViewById(R.id.currentincideListView);
        currentincidentsList = (ArrayList<TrafficScotland>)getIntent().getSerializableExtra("CurrentincidentsList");
        currentincidentsAdapter = new ArrayAdapterTraffic(CurrentIncidentsActivity.this, 0, currentincidentsList);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentincidentsListView.setAdapter(currentincidentsAdapter);
        currentincidentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long RowID) {

                TrafficScotland trafficScotland = currentincidentsList.get(position);
                Intent intent = new Intent(CurrentIncidentsActivity.this, InfomationActivity.class);
                intent.putExtra("title", trafficScotland.getTitle());
                intent.putExtra("description", trafficScotland.getDescription());
                intent.putExtra("link", trafficScotland.getLink());
                intent.putExtra("georss", trafficScotland.getGeorss());
                intent.putExtra("comments", trafficScotland.getComments());
                intent.putExtra("pubDate", trafficScotland.getPubDate());
                startActivity(intent);


            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
