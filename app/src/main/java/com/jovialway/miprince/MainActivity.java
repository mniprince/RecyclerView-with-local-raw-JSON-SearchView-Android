package com.jovialway.miprince;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    String TAG="MainActivity";
    ArrayList<Model> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.black));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        recyclerView =  findViewById(R.id.recyclerView);




        adapter = new RecyclerViewAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.getViewTreeObserver().addOnScrollChangedListener(
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        int x = toolbar.getScrollX();
                        int y = toolbar.getScrollY();
                    }
                });

            addItemFromJSON();
    }

    private void addItemFromJSON() {
        try {
            String jsonDataString=readJSONDataFromFile();
            JSONArray jsonArray=new JSONArray(jsonDataString);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject itemObject=jsonArray.getJSONObject(i);

                String surahName=itemObject.getString("surahName");
                String surah=itemObject.getString("surah");
                Model model=new Model(surahName,surah);
                arrayList.add(model);
            }


        }catch (JSONException | IOException e){
            Log.d(TAG, "addItemFromJSON: ", e);
        }
    }

    private String readJSONDataFromFile() throws IOException{
        InputStream inputStream=null;
        StringBuilder builder=new StringBuilder();
        try {
            String jsonstring=null;
            inputStream=getResources().openRawResource(R.raw.quranfiles);
            BufferedReader bufferedReader=new BufferedReader(
                    new InputStreamReader(inputStream,"UTF-8"));
            while ((jsonstring=bufferedReader.readLine())!=null){
                builder.append(jsonstring);
            }

        }finally {
            if (inputStream!=null)
                {
                    inputStream.close();
                }
        }
            return new String(builder);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint("সূরা খোঁজ করুন");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    adapter.filter("");
                } else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

}
