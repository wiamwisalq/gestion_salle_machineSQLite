package com.example.sqlitesallesmachines.ui.SalleAjout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SalleAjouViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> mText;

    public SalleAjouViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is salles hello hellooooo");
    }

    public LiveData<String> getText() {
        return mText;
    }
}