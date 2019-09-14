package com.example.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<String> mMutableLiveDataFirstName = new MutableLiveData<>();

    public MutableLiveData<String> mMutableLiveDataMiddleName = new MutableLiveData<>();

    public MutableLiveData<String> mMutableLiveDataLastName = new MutableLiveData<>();

    public MutableLiveData<String> mMutableLiveDataStatus = new MutableLiveData<>();

    public MutableLiveData<String> getmMutableLiveDataFirstName() {
        return mMutableLiveDataFirstName;
    }

    public MutableLiveData<String> getmMutableLiveDataMiddleName() {
        return mMutableLiveDataMiddleName;
    }

    public MutableLiveData<String> getmMutableLiveDataLastName() {
        return mMutableLiveDataLastName;
    }

    public MutableLiveData<String> getmMutableLiveDataStatus() {
        return mMutableLiveDataStatus;
    }


    public MainViewModel(@NonNull Application application) {
        super(application);
    }


    DataTask mDataTask = new DataTask();

    public void onButtonClicked() {

        mMutableLiveDataFirstName.setValue(mDataTask.getmFirstName());

        mMutableLiveDataMiddleName.setValue(mDataTask.getmMiddleName());

        mMutableLiveDataLastName.setValue(mDataTask.getmLastName());

        mMutableLiveDataStatus.setValue(mDataTask.getStatus());

        mDataTask.mCheckValue.onValueSet();

    }

    public void initialdata()
    {
        mMutableLiveDataStatus.setValue(mDataTask.getStatus());

        mDataTask.mCheckValue.onValueSet();
    }
}
