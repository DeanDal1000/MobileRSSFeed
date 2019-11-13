package com.example.ddalze200.courseworkv2;

import java.io.Serializable;


/**
 * Created by ddalze200 on 22/03/2018.
 */

public class TrafficScotland implements Serializable {

    private String title;
    private String description;
    private String link;
    private String georss;
    private String author;
    private String comments;
    private String pubDate;

    public TrafficScotland()
    {
    title ="";
    description ="";
    link ="";
    georss ="";
    author ="";
    comments ="";
    pubDate ="";
}

public TrafficScotland(String date)
{
    description = date;
}

public TrafficScotland(String Atitle , String Adescription, String Alink, String Ageorss, String Aauthor, String Acomments, String ApubDate)
{
    title = Atitle;
    description = Adescription;
    link = Alink;
    georss = Ageorss;
    author = Aauthor;
    comments = Acomments;
    pubDate = ApubDate;
}

public String getTitle() {return title;}
public void setTitle(String title) {this.title = title;}

public String getDescription() {return description;}
public void setDescription(String description) {this.description = description;}

public String getLink() {return link;}
public void setLink(String link) {this.link = link;}

public String getGeorss() {return georss;}
public void setGeorss(String georss) {this.georss = georss;}

public String getAuthor() {return author;}
public void setAuthor(String author) {this.author = author;}

public String getComments(){return comments;}
public void setComments(String comments) {this.comments = comments;}

public  String getPubDate() {return pubDate;}
public void setPubDate(String pubDate) {this.pubDate = pubDate;}
}
