package io.nicco.r6s;


import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class weapon_view extends Fragment {

    public weapon_view() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Save the view for modification
        View v = inflater.inflate(R.layout.fragment_weapon_view, container, false);

        //Get Gun Info
        Bundle b = this.getArguments();
        SQLiteDatabase db = home.mkdb();
        Cursor c = db.rawQuery("SELECT * FROM weapons WHERE id=" + b.getInt("id"), null);
        c.moveToFirst();

        // Map Of Text Views
        Map<String, TextView> txts = new HashMap();
        txts.put("name", (TextView) v.findViewById(R.id.weapon_name));
        txts.put("class", (TextView) v.findViewById(R.id.weapon_class));
        txts.put("dmg_n", (TextView) v.findViewById(R.id.weapon_dmg_n));
        txts.put("dmg_s", (TextView) v.findViewById(R.id.weapon_dmg_s));
        txts.put("rpm", (TextView) v.findViewById(R.id.weapon_rpm));
        txts.put("mob", (TextView) v.findViewById(R.id.weapon_mob));
        txts.put("mag", (TextView) v.findViewById(R.id.weapon_mag));
        txts.put("ops", (TextView) v.findViewById(R.id.weapon_ops));

        txts.get("name").setText(c.getString(c.getColumnIndex("name")));
        txts.get("class").setText(c.getString(c.getColumnIndex("class")));
        txts.get("dmg_n").setText(c.getString(c.getColumnIndex("dmg_n")));
        txts.get("dmg_s").setText(c.getString(c.getColumnIndex("dmg_s")));
        if (c.getString(c.getColumnIndex("rpm")).equals("0"))
            txts.get("rpm").setText("-");
        else
            txts.get("rpm").setText(c.getString(c.getColumnIndex("rpm")));
        txts.get("mob").setText(c.getString(c.getColumnIndex("mobility")));
        txts.get("mag").setText(c.getString(c.getColumnIndex("mag")));
        txts.get("ops").setText(
                Arrays.toString(c.getString(c.getColumnIndex("op")).split(","))
        );

        db.close();

        return v;
    }

}
