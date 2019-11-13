package com.example.ddalze200.courseworkv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ddalze200 on 22/03/2018.
 */

public class InfomationActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView titleTextView = (TextView) findViewById(R.id.title);
        TextView descriptionTextView = (TextView) findViewById(R.id.description);
        TextView linkTextView = (TextView) findViewById(R.id.link);
        TextView georssTextView = (TextView) findViewById(R.id.georss);
        TextView authorTextView = (TextView) findViewById(R.id.author);
        TextView commentsTextView = (TextView) findViewById(R.id.comments);
        TextView pubDateTextView = (TextView) findViewById(R.id.pubDate);
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String link = intent.getStringExtra("link");
        String georss = intent.getStringExtra("georss");
        String author = intent.getStringExtra("author");
        String comment = intent.getStringExtra("comment");
        String pubDate = intent.getStringExtra("pubDate");

        Integer imageID = this.getResources().getIdentifier("Roadwork", "Drawable", this.getPackageName());
        String image = intent.getStringExtra("image");

        imageView.setImageResource(imageID);
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        linkTextView.setText("link: " + link);
        georssTextView.setText("Georss: " + georss);
        authorTextView.setText("Author: " + author);
        commentsTextView.setText("Comment: " + comment);
        pubDateTextView.setText("Date: " + pubDate);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                default:
                    return super.onOptionsItemSelected(item);
        }
    }


}
