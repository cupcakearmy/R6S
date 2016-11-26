package io.nicco.r6s;


import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class weapons_view extends Fragment {

    public weapons_view() {
    }

    private Activity root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = getActivity();

        View v = inflater.inflate(R.layout.fragment_weapons_view, container, false);
        RecyclerView weapon_list = (RecyclerView) v.findViewById(R.id.weapons_list);
        List<WeaponListItem> data = new ArrayList<>();

        // Get DB List
        SQLiteDatabase db = home.mkdb();
        Cursor c = db.rawQuery("SELECT * FROM weapons ORDER BY class ASC, name ASC", null);
        try {
            while (c.moveToNext()) {
                data.add(new WeaponListItem(
                        c.getString(c.getColumnIndex("name")),
                        c.getString(c.getColumnIndex("class")),
                        c.getInt(c.getColumnIndex("id"))));
            }
        } finally {
            c.close();
        }
        db.close();

        weapon_list.setAdapter(new WeaponsListAdapter(data, root));
        weapon_list.setLayoutManager(new LinearLayoutManager(root));

        return v;
    }

}
