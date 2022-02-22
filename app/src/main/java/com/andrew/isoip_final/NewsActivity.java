package com.andrew.isoip_final;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    public Elements content;
    public ArrayList<String> titleList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView lvnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        lvnews = (ListView) findViewById(R.id.news_list);

        new NewThread().execute();
        adapter = new ArrayAdapter<String>(this, R.layout.news_listitem,R.id.news_item, titleList);
    }

    public class NewThread extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String... arg){
            Document doc;
            try{
                doc = Jsoup.connect("http://www.sssu.ru/").get();
                content = doc.select(".blog_body");
                titleList.clear();
                for (Element contents: content) {
                    titleList.add(contents.text());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            lvnews.setAdapter(adapter);
        }
    }
}
