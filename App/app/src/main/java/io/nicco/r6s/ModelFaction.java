package io.nicco.r6s;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelFaction {

    String name;
    ID id;
    ID[] ops;

    ModelFaction(ID id) {
        this.id = id;

        JSONObject d = id.getJSON();
        if (d == null) {
            this.name = "";
        } else
            try {
                this.name = d.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
}
