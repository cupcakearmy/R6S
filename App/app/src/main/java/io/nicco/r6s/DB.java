package io.nicco.r6s;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

class DB {

    static private JSONObject db = null;

    private static void load() {
        Log.i("JSON", "Ini...");
        try {
            InputStream is = main.am.open("r6s.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            db = new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException | JSONException e) {
            db = null;
            e.printStackTrace();
        }
    }

    static JSONObject getDB() {
        if (db == null)
            load();
        return db;
    }

}
