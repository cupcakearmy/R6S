package io.nicco.r6s;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Comparator;

public class ModelWeapon {

    static final String[] TYPES = {"Assault Rifle", "Submachine Gun", "Light Machine Gun", "Sniper Rifle", "Shotgun", "Secondary", "Shield"};
    static final String[] TYPES_SHORT = {"AR", "SMG", "LMG", "SR", "SG", "PS", "SH"};
    static final String[] SLOTS = {"Primary", "Secondary"};
    static final String[] SLOTS_SHORT = {"P", "S"};

    String name;
    int dmg_n, dmg_s, rpm, mob, mag, type, slot;
    ID id;
    ID[] ops;

    ModelWeapon(ID id) {
        this.id = id;

        JSONObject d = id.getJSON();
        if (d != null) {
            try {
                // Safe Parses
                ops = ID.fromJSONArray(ID.OPERATOR, d.getJSONArray("op"));
                type = d.getInt("class");
                name = d.getString("name");
                slot = d.getInt("slot");

                dmg_n = d.getInt("dmg_n");
                mob = d.getInt("mob");
                rpm = d.getInt("rpm");
                mag = d.getInt("mag");

                if (!d.isNull("dmg_s"))
                    dmg_s = d.getInt("dmg_s");
                else
                    dmg_s = -1;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    String getTypeShort() {
        return TYPES_SHORT[type];
    }

    String getTypeLong() {
        return TYPES[type];
    }

    String getSlotShort() {
        return SLOTS_SHORT[slot];
    }

    String getSlotLong() {
        return SLOTS[slot];
    }

    Drawable getImage() {
        try {
            return Drawable.createFromStream(main.am.open("img/weapons/" + this.id.id + ".png"), null);
        } catch (IOException ignore) {
            try {
                return Drawable.createFromStream(main.am.open("img/no_img.png"), null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    static Comparator<ModelWeapon> sortByName() {
        return new Comparator<ModelWeapon>() {
            @Override
            public int compare(ModelWeapon a, ModelWeapon b) {
                return a.name.compareTo(b.name);
            }
        };
    }

    static Comparator<ModelWeapon> sortByClass() {
        return new Comparator<ModelWeapon>() {
            @Override
            public int compare(ModelWeapon a, ModelWeapon b) {
                return a.type > b.type ? 1 : -1;
            }
        };
    }

}
