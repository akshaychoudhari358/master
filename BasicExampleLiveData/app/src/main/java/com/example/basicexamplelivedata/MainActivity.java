package com.example.basicexamplelivedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.list);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        MainActivityViewModel model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        model.getFruitList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> fruitlist) {
                // update UI
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, fruitlist);
                // Assign adapter to ListView
                listView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

