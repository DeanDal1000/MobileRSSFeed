package com.example.ddalze200.courseworkv2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ddalze200 on 22/03/2018.
 */

public class ArrayAdapterTraffic extends ArrayAdapter<TrafficScotland> implements Serializable {

    private Context context;
    private ArrayList<TrafficScotland> prop;

    public ArrayAdapterTraffic(Context context, int resource, List<TrafficScotland> objects){
        super(context,resource,objects);

        this.context=context;
        this.prop=(ArrayList)objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TrafficScotland trafficScotland = prop.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.prop_layout, null);

        if (trafficScotland.getDescription().contains("Date Start")){
            String pattern = "EEEE, dd MMMM yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String[] segments=trafficScotland.getDescription().split("<br />");
            String segment1 = segments[0];
            String segment2 = segments[1];

            segment1 = segment1.substring(segment1.indexOf(':') +1, segment1.indexOf('-'));
            segment2 = segment2.substring(segment2.indexOf(':') +1, segment2.indexOf('-'));

            try{
                Date date = simpleDateFormat.parse(segment1);
                Date date2 = simpleDateFormat.parse(segment2);
                long change = Math.abs(date.getTime() - date2.getTime());
                long changeA = change / (24 * 60 * 60 * 1000);
                if (changeA !=1) {
                    changeA = changeA +1;
                }
                if (changeA <=1) {
                    view.setBackgroundColor(Color.BLUE);
                } else if (changeA >=1 && changeA <=3){
                    view.setBackgroundColor(Color.RED);
                } else {

                    view.setBackgroundColor(Color.YELLOW);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView link = (TextView) view.findViewById(R.id.link);
        ImageView image =(ImageView) view.findViewById(R.id.image);

        title.setText(trafficScotland.getTitle());
        description.setText(trafficScotland.getDescription());
        link.setText(trafficScotland.getLink());

        int descriptionLength =trafficScotland.getDescription().length();
        if(descriptionLength >=100){
            String descT = trafficScotland.getDescription().substring(0,100) + ":)";
            description.setText(descT);

        }else{
            description.setText(trafficScotland.getDescription());
        }
        int imageID = context.getResources().getIdentifier("Road", "Draw", context.getPackageName());
        image.setImageResource(imageID);
        return view;
    }












}
