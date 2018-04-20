package com.example.boulocalix.newspaper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by bouloc.alix on 4/16/2018.
 */

public class AsyncDownloaderSpecial extends AsyncTask<Void,Void,ArrayList<FeedItem>> {

    Context context;
    String address;
    URL url ;

    static final String PUB_DATE = "pubDate";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LINK = "link";
    static final String TITLE = "title";
    static final String ITEM = "item";
    static final String IMAGE = "image" ;
    public AsyncResponse delegate ;

    public AsyncDownloaderSpecial(Context context, AsyncResponse delegate, String address) {
        this.delegate = delegate;
        this.context = context;
        this.address = address;
    }


    //This method will execute in background so in this method download rss feeds
    @Override
    protected ArrayList<FeedItem> doInBackground(Void... params) {
        Document data = Getdata() ;
        return ProcessXml(data);
    }

    @Override
    protected void onPostExecute(ArrayList<FeedItem> feedItems) {
        super.onPostExecute(feedItems);
        delegate.processFinish(feedItems);
    }


    // In this method we will process Rss feed  document we downloaded to parse useful information from it
    private ArrayList<FeedItem> ProcessXml(Document data) {
        ArrayList<FeedItem> feedItems = new ArrayList<>();
        if (data != null) {
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node curentchild = items.item(i);
                if (curentchild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item = new FeedItem();
                    NodeList itemchilds = curentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node current = itemchilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(current.getTextContent());
                        }
                    }
                    feedItems.add(item);


                }
            }
        }
        return feedItems ;
    }

    //This method will download rss feed document from specified url
    public Document Getdata() {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}