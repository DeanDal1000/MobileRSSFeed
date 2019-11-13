
package com.example.ddalze200.courseworkv2;
/**
 * Created by ddalze200 on 22/03/2018.
 */
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context context;

    private String plannedroadworksURL="http://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String currentincidentsURL="http://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private Button getplannedroadworks;
    private Button getcurrentincidents;
    private Button enterdata;

    private ArrayList<TrafficScotland>plannedroadworksList;
    private ArrayList<TrafficScotland>currentincidentsList;
    private ArrayAdapter<TrafficScotland> plannedroadworksAdapter;
    private ArrayAdapter<TrafficScotland> currentincidentsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getplannedroadworks = (Button) findViewById(R.id.getplannedroadworks);
        getcurrentincidents = (Button) findViewById(R.id.getcurrentincidents);
        plannedroadworksList = new ArrayList<TrafficScotland>();
        currentincidentsList = new ArrayList<TrafficScotland>();
        new FetchFeedTask().execute((Void)null);

        getplannedroadworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,PlannedRoadWorksActivity.class);
                myIntent.putExtra("plannedroadworksList",plannedroadworksList);
                MainActivity.this.startActivity(myIntent);
            }

            });
        getcurrentincidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, CurrentIncidentsActivity.class);
                myIntent.putExtra("CurrentincidentsList", currentincidentsList);
                MainActivity.this.startActivity(myIntent);
            }});

        }

        public final class FetchFeedTask extends AsyncTask<Void, Void, Boolean>{

            @Override
            protected Boolean doInBackground(Void... voids) {

                String URLLink = plannedroadworksURL;

                if (TextUtils.isEmpty(URLLink))
                    return false;

                try {
                    if(!URLLink.startsWith("http://") && !URLLink.startsWith("http://"))
                        URLLink = "http://" + URLLink;

                    URL url = new URL(plannedroadworksURL);
                    InputStream inputStream = url.openConnection().getInputStream();
                    plannedroadworksList = MainActivity.parseFeed(inputStream);

                    URL urlA = new URL(currentincidentsURL);
                    InputStream inputStreamA = urlA.openConnection().getInputStream();
                    currentincidentsList = MainActivity.parseFeed(inputStreamA);

                   return true;
                } catch (IOException e) {
                    Log.e(TAG, "Wrong", e);
                } catch (XmlPullParserException e) {
                    Log.e(TAG, "Wrong, e");
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean sucess) {

                if (sucess) {

                    plannedroadworksAdapter = new ArrayAdapterTraffic(MainActivity.this, 0, plannedroadworksList);
                    currentincidentsAdapter = new ArrayAdapterTraffic(MainActivity.this, 0, currentincidentsList);

                }else {

                }
            }
        }

    public static ArrayList<TrafficScotland> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        String georss = null;
        String author = null;
        String comments = null;
        String pubDate = null;
        boolean isItem = false;
        ArrayList<TrafficScotland> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MainActivity", "Parsing name " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("georss:point")) {
                    georss = result;
                } else if (name.equalsIgnoreCase("author")) {
                    author = result;
                    if (author == "") {
                        author = "N/A";
                    }
                } else if (name.equalsIgnoreCase("comments")) {
                    comments = result;
                    if (comments == "") {
                        comments = "N/A";
                    }
                } else if (name.equalsIgnoreCase("pubDate")) {
                    pubDate = result;
                }


                if (description != null && description.contains("<br\\s*/>")) {
                    description.replace("<br\\s*/>", " ");
                }

                if (title != null && link != null && description != null && georss != null) {
                    if (isItem) {
                        TrafficScotland item = new TrafficScotland(title, description, link, georss, author, comments, pubDate);
                        items.add(item);
                    } else {

                    }

                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }
}
