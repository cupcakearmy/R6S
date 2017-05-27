package io.nicco.r6s;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelGadget {

    String name, msg;
    ID id;

    ModelGadget(ID id) {
        this.id = id;

        JSONObject d = id.getJSON();
        if (d != null) {
            try {
                // Safe Parses
                name = d.getString("name");
                // msg = d.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
