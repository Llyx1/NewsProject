package com.example.boulocalix.newspaper;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by bouloc.alix on 4/12/2018.
 */

public class AsyncDownloader extends AsyncTask<Void,Void,ArrayList<FeedItem>> {

    Context context;
    String address;


    static final String PUB_DATE = "pubDate";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LINK = "link";
    static final String TITLE = "title";
    static final String ITEM = "item";
    static final String IMAGE = "image";
    public AsyncResponse delegate;

    public AsyncDownloader(Context context, AsyncResponse delegate, String address) {
        this.delegate = delegate;  //this will inform the fragment that the Assync task is finished so we can assign the adapter
        this.context = context;
        this.address = address;
    }


    //This method will execute in background so in this method download rss feeds
    @Override
    protected ArrayList<FeedItem> doInBackground(Void... params) {

        return ProcessXml();
    }

    @Override
    protected void onPostExecute(ArrayList<FeedItem> feedItems) {
        super.onPostExecute(feedItems);
        delegate.processFinish(feedItems);
    }


    // In this method we will process Rss feed  document we downloaded to parse useful information from it
    private ArrayList<FeedItem> ProcessXml() {

        ArrayList<FeedItem> feedItems = new ArrayList<>();

        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            // auto-detect the encoding from the stream
            stream = new URL(address).openConnection().getInputStream();
            parser.setInput(stream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            FeedItem item = null;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name = null;
                try {
                    switch (eventType) { //                                                         The goal is to determine what tag we are currently reading :
                        case XmlPullParser.START_DOCUMENT://                                        Beginning of the document
                            break;
                        case XmlPullParser.START_TAG://                                             New tag : Here we try to know what kind of tag we are dealing with so we can fetch the data we want
                            name = parser.getName();
                            if (name.equalsIgnoreCase(ITEM)) {//                                        Start of a new item
                                Log.i("new item", "Create new item");
                                item = new FeedItem();
                            } else if (item != null) {
                                if (name.equalsIgnoreCase(LINK)) {//                                    Link of the article to the 24h website
                                    Log.i("Attribute", "setLink");
                                    item.setLink(parser.nextText());
                                } else if (name.equalsIgnoreCase(DESCRIPTION)) {//                      Description of the article
                                    Log.i("Attribute", "description");
                                    String desc = parser.nextText();
                                    desc = desc.replaceAll("&", "&amp;");
                                    desc = desc.replaceAll("\\?", "&#63;");
                                    String imageUrl;
                                    if (desc != null) {
                                        int index = desc.indexOf("/>") + 2; //so we know if there is a picture file
                                        if (index != 0) {
                                            item.setDescription(desc.substring(desc.indexOf("/>") + 2));
                                            try {
                                                imageUrl = desc.substring(desc.indexOf("src=") + 5, desc.indexOf("alt") -2);//selection of the picture URL
                                            } catch (StringIndexOutOfBoundsException e) {
                                                imageUrl=null ;
                                            }
                                            item.setImage(imageUrl);
                                        } else item.setDescription(desc);
                                    }
                                } else if (name.equalsIgnoreCase(PUB_DATE)) {//                         Publication date
                                    Log.i("Attribute", "date");
                                    item.setPubDate(parser.nextText());
                                } else if (name.equalsIgnoreCase(TITLE)) {//                            Title of the article
                                    Log.i("Attribute", "title");
                                    item.setTitle(parser.nextText().trim());
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG://                                               End of a tag
                            name = parser.getName();
                            Log.i("End tag", name);
                            if (name.equalsIgnoreCase(ITEM) && item != null) { // end an item, we add it to the arrayList
                                Log.i("Added", item.toString());
                                feedItems.add(item);
                            } else if (name.equalsIgnoreCase(CHANNEL)) { //end of the rss file
                                done = true;
                            }
                            break;
                    }
                    eventType = parser.next();  //                                                  We skip to the next node
                } catch (XmlPullParserException e) {
                    eventType = parser.next();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return feedItems;
    }


}