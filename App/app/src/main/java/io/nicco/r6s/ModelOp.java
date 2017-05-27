package io.nicco.r6s;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ModelOp {

    static final String[] TYPES = {"Attacker", "Defender", "Recruit"};
    static final String[] TYPES_SHORT = {"A", "D", "R"};

    //    Cache
    private static ModelOp[][] cache_ops = null;
    String name, ability, ability_msg;
    int armor, speed, type, faction;
    ID id;
    ID[] weapons, gadgets;
    private ModelWeapon[][] cache_weapons = null;

    ModelOp(ID id) {
        this.id = id;

        JSONObject d = id.getJSON();
        if (d != null) {
            try {
                armor = d.getInt("armor");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                speed = d.getInt("speed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                type = d.getInt("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                name = d.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                ability = d.getString("ability");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                faction = d.getInt("faction");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                weapons = ID.fromJSONArray(ID.WEAPON, d.getJSONArray("wid"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                gadgets = ID.fromJSONArray(ID.GADGET, d.getJSONArray("gadget"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static ModelOp[] getAttackers() {
        mk_ops();
        return cache_ops[0];
    }

    public static ModelOp[] getDefenders() {
        mk_ops();
        return cache_ops[1];
    }

    private static void mk_ops() {
        if (cache_ops == null) {
            List<ModelOp> a = new ArrayList<>();
            List<ModelOp> d = new ArrayList<>();

            // Populate with operators
            Iterator<String> it;
            try {
                it = DB.getDB().getJSONObject("operators").keys();
                while (it.hasNext()) {
                    ModelOp cur = new ModelOp(new ID(ID.OPERATOR, it.next()));
                    if (cur.type == 0)
                        a.add(cur);
                    else
                        d.add(cur);
                }
                cache_ops = new ModelOp[][]{
                        a.toArray(new ModelOp[0]),
                        d.toArray(new ModelOp[0])
                };
            } catch (JSONException e) {
                e.printStackTrace();
                cache_ops = null;
            }
        }

    }

    // COMPARATORS
    static Comparator<ModelOp> sortByName() {
        return new Comparator<ModelOp>() {
            @Override
            public int compare(ModelOp a, ModelOp b) {
                return a.name.compareTo(b.name);
            }
        };
    }

    static Comparator<ModelOp> sortByType() {
        return new Comparator<ModelOp>() {
            @Override
            public int compare(ModelOp a, ModelOp b) {
                return a.type > b.type ? 1 : -1;
            }
        };
    }

    static Comparator<ModelOp> sortByFaction() {
        return new Comparator<ModelOp>() {
            @Override
            public int compare(ModelOp a, ModelOp b) {
                return a.faction > b.faction ? 1 : -1;
            }
        };
    }

    String getFaction() {
        try {
            return ((ModelFaction) new ID(ID.FACTION, String.valueOf(faction)).get()).name;
        } catch (Exception e) {
            return "";
        }
    }

    String getTypeLong() {
        return TYPES[type];
    }

    String getTypeShort() {
        return TYPES_SHORT[type];
    }

    Drawable getIcon() {
        return getAsset("icon", "png");
    }

    Drawable getImage() {
        return getAsset("full", "jpg");
    }

    ModelWeapon[] getPrimary() {
        mk_weapons();
        return cache_weapons[0];
    }

    ModelWeapon[] getSecondary() {
        mk_weapons();
        return cache_weapons[1];
    }

    public ID[] getGadgetsIds() {
        return gadgets;
    }

    private void mk_weapons() {
        if (cache_weapons == null) {
            List<ModelWeapon> prim = new ArrayList<>();
            List<ModelWeapon> side = new ArrayList<>();

            // Populate with operators
            for (ID id : weapons) {
                ModelWeapon cur = new ModelWeapon(id);
                if (cur.slot == 0)
                    prim.add(cur);
                else
                    side.add(cur);
            }
            cache_weapons = new ModelWeapon[][]{
                    prim.toArray(new ModelWeapon[0]),
                    side.toArray(new ModelWeapon[0])
            };
        }

    }

    private Drawable getAsset(String p, String f) {
        try {
            InputStream is = main.am.open("img/ops/" + p + "/" + this.id.id + "." + f);
            return Drawable.createFromStream(is, null);
        } catch (IOException ignore) {
            try {
                return Drawable.createFromStream(main.am.open("img/no_img.png"), null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}