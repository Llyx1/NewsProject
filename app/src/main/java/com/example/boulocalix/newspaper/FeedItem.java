package com.example.boulocalix.newspaper;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import java.io.Serializable;

/**
 * Created by bouloc.alix on 4/12/2018.
 */

public class FeedItem implements Serializable{
    String title ;
    String link ;
    String description ;
    String pubDate ;
    String picture ;

    public String parseHtml(String string) {
        return Html.fromHtml(string).toString() ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = parseHtml(title);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = parseHtml(description).replaceAll("#39;", "'");
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = parseHtml(pubDate);
    }

    public String getImage() {
        return picture;
    }

    public void setImage(String picture) {
        this.picture = picture ;
     }

}
