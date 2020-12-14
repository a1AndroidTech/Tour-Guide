package com.a1techandroid.tourguide.CustomClasses;

import android.content.Context;
import android.content.SharedPreferences;

import com.a1techandroid.tourguide.Models.UserModel;
import com.google.gson.Gson;

public class Prefrences {

    public static void saveUSer(UserModel model, Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString("MyObject", json);
        editor.apply();
    }

    public static UserModel getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("MyPref", 0);
        Gson gson = new Gson();
        String json = prefs.getString("MyObject", "");
        UserModel obj = gson.fromJson(json, UserModel.class);
        return obj;
    }
}
