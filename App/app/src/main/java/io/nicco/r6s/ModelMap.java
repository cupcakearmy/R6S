package io.nicco.r6s;

import android.graphics.drawable.Drawable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModelMap {

    private static List<ModelMap> maps = null;
    String name;
    ID id;
    int floor;

    ModelMap(ID id) {
        this.id = id;
        this.floor = 0;

        JSONObject d = id.getJSON();
        if (d != null) {
            try {
                // Safe Parses
                name = d.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    static List<ModelMap> getMaps() {
        if (maps == null) {
            maps = new ArrayList<>();
            Iterator<String> it;
            try {
                it = DB.getDB().getJSONObject("maps").keys();
                while (it.hasNext())
                    maps.add(new ModelMap(new ID(ID.MAP, it.next())));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return maps;
    }

    static List<String> getMapsNames() {
        if (maps == null)
            getMaps();

        Log.i("MAPS", maps.toString());
        List<String> ret = new ArrayList<>();
        for (ModelMap map : maps)
            ret.add(map.name);
        return ret;
    }

    Drawable getImage() {
        try {
            InputStream is = main.am.open("img/maps/" + id.id + "/" + String.valueOf(floor) + ".jpg");
            return Drawable.createFromStream(is, null);
        } catch (IOException ignore) {
            Log.i("IMAGE", "CLoud not find");
            return null;
        }
    }

    private boolean check_floor(int f) {
        try {
            main.am.open("img/maps/" + id.id + "/" + String.valueOf(f) + ".jpg");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    void setFloor(int f) {
        if (check_floor(f))
            floor = f;
    }

    void setFloor(boolean up) {
        int n = up ? floor + 1 : floor - 1;
        if (check_floor(n))
            floor = n;
    }

}
