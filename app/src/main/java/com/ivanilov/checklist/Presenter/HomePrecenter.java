package com.ivanilov.checklist.Presenter;


import com.ivanilov.checklist.Activity.HomeActivity;
import com.ivanilov.checklist.Model.Preferences;
import com.ivanilov.checklist.Presenter.Entity.CheckList;

import java.util.ArrayList;


public class HomePrecenter {

    private HomeActivity view;
    private Preferences preferences = new Preferences();

    public void attachView (HomeActivity homeActivity){
        view = homeActivity;
    }


    public ArrayList<CheckList> getCheckList (){

        ArrayList<CheckList> result = preferences.getArrayCheckListPreferences(view);

        return result;

    }

}
