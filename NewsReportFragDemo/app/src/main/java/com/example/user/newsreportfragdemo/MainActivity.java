package com.example.user.newsreportfragdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.StreamHandler;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {

    public ListView listView;
    String jsonString;
    ArrayList<String> headlineList = new ArrayList<>();
    ArrayList<HashMap<String,String>> news_snippet =  new ArrayList<HashMap<String,String>> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.newslist);
        jsonString = loadJSONFromAsset();
        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray root = obj.getJSONArray("headlines");

            Log.v("JSON ARRAY", String.valueOf(root));
            Log.v("JSON LENGTH", String.valueOf(root.length()));

            for (int i = 0; i < root.length(); i++)
            {
                JSONObject jsonObject = root.getJSONObject(i);
                Log.v("JSONOBJECT", String.valueOf(jsonObject));
                String headline_value = jsonObject.getString("headline");
                String author_value = jsonObject.getString("author");
                String contentnews = jsonObject.getString("detail-content");
                //Log.v("Details>", headline_value); working

                headlineList.add(headline_value);
                HashMap<String, String> mylist = new HashMap<String, String>();
                mylist.put("headline", headline_value);
                mylist.put("author", author_value);
                mylist.put("detail-content", contentnews);

                news_snippet.add(mylist);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("News Snippet", String.valueOf(news_snippet));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,headlineList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("Item Click", String.valueOf(news_snippet.get(i)));
                HashMap<String,String> hashMap = news_snippet.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("headline",hashMap.get("headline"));
                bundle.putString("author",hashMap.get("author"));
                bundle.putString("detail-content",hashMap.get("detail-content"));
                NewsReportFragment fragment = new NewsReportFragment();
                fragment.setArguments(bundle);
                listView.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("newsreport.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragmentManager().popBackStackImmediate();
        listView.setVisibility(View.VISIBLE);

    }
}
