package com.example.boulocalix.newspaper;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intrusoft.squint.DiagonalView;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

import static android.graphics.Typeface.*;

public class DetailActivity extends AppCompatActivity {

    TextView title ;
    TextView date ;
    TextView information ;
    DiagonalView FirstImage ;
    FloatingActionButton back ;
    FeedItem item ;
    boolean firstPicture = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        item = (FeedItem) intent.getSerializableExtra("Info") ;
        title = findViewById(R.id.title_detail) ;
        date = findViewById(R.id.date_detail) ;
        information = findViewById(R.id.information_detail) ;
        FirstImage = findViewById(R.id.image_detail) ;
        back = findViewById(R.id.back) ;
        title.setText(item.getTitle());
        date.setText(item.getPubDate());
        information.setText(item.getDescription());
        String imageLink = item.getImage() ;
        if (imageLink!= null) {
            Picasso.with(getApplicationContext()).load(imageLink).resize(Resources.getSystem().getDisplayMetrics().widthPixels,0).into(FirstImage) ;
            firstPicture = true ;
        }
        FetchInformationFromWeb fetchInformationFromWeb = new FetchInformationFromWeb() ;
        fetchInformationFromWeb.execute();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish() ;
            }
        });


    }
    public class FetchInformationFromWeb extends AsyncTask<Void,Void,ArrayList<String>> {

        String link;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            link = item.getLink();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            return getWebsite(link);
        }

        @Override
        protected void onPostExecute(ArrayList<String> dataListFromHtml) {
            super.onPostExecute(dataListFromHtml);
            LinearLayout linearLayout = findViewById(R.id.website);
            String dataStringTmp;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(70,70,70,0) ;
            LinearLayout.LayoutParams paramsPicture = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsPicture.setMargins(70,0,70,70) ;
            paramsPicture.gravity = Gravity.CENTER ;
            boolean img = false ;
            for (int i = 0 ; i < dataListFromHtml.size() ; i++ ) {

                dataStringTmp = dataListFromHtml.get(i);
                if (dataStringTmp.contains("http") && dataStringTmp.contains("jpg")) {
                    if (!firstPicture){
                        firstPicture = true ;
                        Picasso.with(getApplicationContext()).load(dataStringTmp).resize(Resources.getSystem().getDisplayMetrics().widthPixels,0).into(FirstImage) ;
                    }
                    ImageView image = new ImageView(getApplicationContext());
                    Picasso.with(getApplicationContext()).load(dataStringTmp).into(image);
                    image.setLayoutParams(params);
                    linearLayout.addView(image);
                    img = true ;
                } else if (!dataStringTmp.contains("http") && !dataStringTmp.equals("") && !dataStringTmp.contains("data")  && !dataStringTmp.contains("Video")) {
                    TextView text = new TextView(getApplicationContext());
                    text.setText(dataStringTmp);
                    if (img) {
                        img = false ;
                        text.setLayoutParams(paramsPicture);
                        text.setTypeface(Typeface.defaultFromStyle(Typeface.SANS_SERIF.ITALIC));
                        text.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    }else {
                        text.setLayoutParams(params);
                    }
                    linearLayout.addView(text);
                }else if (dataStringTmp.contains("data:")&& dataListFromHtml.get(i+2).contains("data:") && dataListFromHtml.get(i+4).contains("data:")) {
                    linearLayout.removeViewAt(linearLayout.getChildCount()-1);
                    linearLayout.removeViewAt(linearLayout.getChildCount()-1);
                    break ;
                }
            }
        }


        private ArrayList<String> getWebsite(String webLink) {
            final ArrayList<String> dataInfoFromHtml = new ArrayList<>();

            try {
                Document document = Jsoup.connect(webLink).get();
                Elements nodes = document.select("p,img");
                for (Element node : nodes) {
                    dataInfoFromHtml.add(node.attr("src"));
                    dataInfoFromHtml.add(node.text());
                }
            } catch (IOException e) {
                dataInfoFromHtml.add(e.getMessage());
            }
            return dataInfoFromHtml;
        }
    }
}
