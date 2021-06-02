package com.example.project.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText1,mText2;

    public NotificationsViewModel() {
        mText1 = new MutableLiveData<>();
        mText1.setValue("Hello,");
        mText2 = new MutableLiveData<>();
        mText2.setValue("Guest User");
    }

    public LiveData<String> getText1() {
        return mText1;
    }
    public LiveData<String> getText2() {
        return mText2;
    }
}