package com.example.project.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText1,mText2,mText3;

    public HomeViewModel() {
        mText1 = new MutableLiveData<>();
        mText1.setValue("Sleep Apnea is a potentially serious sleep disorder in which breathing repeatedly stops and starts.");
        mText2 = new MutableLiveData<>();
        mText2.setValue("The signs and symptoms of obstructive and central sleep apneas overlap, sometimes making it difficult to determine which type you have.");
        mText3 = new MutableLiveData<>();
        mText3.setValue("Treatment often includes lifestyle changes, such as weight loss and the use of a breathing assistance device at night, such as CPAP.");
    }

    public LiveData<String> getText1() {
        return mText1;
    }
    public LiveData<String> getText2() {
        return mText2;
    }
    public LiveData<String> getText3() {
        return mText3;
    }
}