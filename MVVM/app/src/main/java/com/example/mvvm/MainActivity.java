package com.example.mvvm;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mvvm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final MainViewModel mainViewModel = new MainViewModel(getApplication());

        activityMainBinding.setLifecycleOwner(this);

        mainViewModel.initialdata();

        mainViewModel.getmMutableLiveDataLastName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("getmMutableLiveDataLastName() = ",s);
            }
        });

        mainViewModel.getmMutableLiveDataFirstName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("getmMutableLiveDataFirstName() = ",s);
            }
        });

        mainViewModel.getmMutableLiveDataMiddleName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("getmMutableLiveDataMiddleName() = ",s);
            }
        });

        activityMainBinding.setViewModel(mainViewModel);
    }
}

