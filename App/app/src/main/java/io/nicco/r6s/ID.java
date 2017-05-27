package io.nicco.r6s;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class ID {

    static final int OPERATOR = 0;
    static final int WEAPON = 1;
    static final int GADGET = 2;
    static final int FACTION = 3;
    static final int MAP = 4;
    String id;
    private int type;

    ID(int t, String id) {
        this.type = t;
        this.id = id;
    }

    static ID[] fromJSONArray(int type, JSONArray a) {
        List<ID> tmp = new ArrayList<>();
        for (int i = 0; i < a.length(); i++)
            try {
                tmp.add(new ID(type, a.getString(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return tmp.toArray(new ID[0]);
    }

    <T> T get() {
        switch (this.type) {
            case OPERATOR:
                return (T) new ModelOp(this);
            case WEAPON:
                return (T) new ModelWeapon(this);
            case GADGET:
                return (T) new ModelGadget(this);
            case FACTION:
                return (T) new ModelFaction(this);
            case MAP:
                return (T) new ModelMap(this);
            default:
                return null;
        }
    }

    JSONObject getJSON() {
        try {
            switch (this.type) {
                case OPERATOR:
                    return DB.getDB().getJSONObject("operators").getJSONObject(this.id);
                case WEAPON:
                    return DB.getDB().getJSONObject("weapons").getJSONObject(this.id);
                case GADGET:
                    return DB.getDB().getJSONObject("gadgets").getJSONObject(this.id);
                case FACTION:
                    return DB.getDB().getJSONObject("factions").getJSONObject(this.id);
                case MAP:
                    return DB.getDB().getJSONObject("maps").getJSONObject(this.id);
                default:
                    return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}