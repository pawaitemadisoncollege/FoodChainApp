package com.paulawaite.foodchain;

import android.app.Application;

/**
 * Created by paulawaite on 7/6/16.
 */
public class GlobalVariables extends Application {

    private static GlobalVariables instance;

    private DataSetUp gameData;

    private GlobalVariables(){}

    public DataSetUp getEventData() {
        if (gameData == null) {
            gameData = new DataSetUp();
        }
        return gameData;
    }

    public void setEventData(DataSetUp eventData) {
        this.gameData = eventData;
    }

    public static synchronized GlobalVariables getInstance(){
        if (instance == null){
            instance = new GlobalVariables();
        }
        return instance;
    }

}
