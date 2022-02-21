package com.example.se328_mid1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity3 extends ListActivity{ //must extends ListActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main); not needed because it is declared in the adapter

        String[] attraction =
                {
                        "Art Institute of Chicago", "Magnificent Mile", "Willis Tower",
                        "Navy Peer", "Water Tower"
                };

        setListAdapter(new ArrayAdapter<String>(this,
                R.layout.activity_list_pics,
                R.id.list,
                attraction));



    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        switch(position){

            case 0:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.alfaisal.edu")));
                break;

            case 1:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")));
                break;

        }

    }

}