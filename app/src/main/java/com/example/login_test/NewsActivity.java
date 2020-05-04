package com.example.login_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] mDataset = {"1","2"};
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        queue = Volley.newRequestQueue(this);
        getNews();

        // 1. 화면이 로딩 -> 뉴스 정보를 받아온다. -----------
        // 2. 정보 -> 어뎁터로 넘겨준다.
        // 3. 어덥터 -> 셋팅
    }

    public void getNews() {

        String url ="http://newsapi.org/v2/top-headlines?country=kr&apiKey=f562b58490d14a75a0bbce58b88ba95c";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Log.d("NEWS", response);

                        try {
                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");

                            // response ->> NewsData Class 분류
                            List<NewsData> news = new ArrayList<>();

                            for(int i = 0, j = arrayArticles.length(); i < j; i++ ) {
                               JSONObject obj =  arrayArticles.getJSONObject(i);

//                                Log.d("NEWS", obj.toString());

                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setContent(obj.getString("description"));
                                news.add(newsData);
                            }


                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(news, NewsActivity.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Object obj = v.getTag();
                                    if(obj != null){
                                        int position = (int)obj;
                                        ((MyAdapter)mAdapter).getNews(position).getContent();
                                        Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                                        intent.putExtra("news", ((MyAdapter)mAdapter).getNews(position));
                                        startActivity(intent);
                                    }

                                }
                            });
                            recyclerView.setAdapter(mAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
