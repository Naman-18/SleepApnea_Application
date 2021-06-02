package com.example.project.ui.dashboard;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText1,mText0;

    public DashboardViewModel() {
        mText1 = new MutableLiveData<>();
        mText1.setValue("Check Your Risk!");
        mText0=new MutableLiveData<>();
        mText0.setValue("Location:");
    }


    public LiveData<String> getText1(){
        return mText1;

    }
    public LiveData<String> getText0(){
        return mText0;

    }

}