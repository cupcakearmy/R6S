package io.nicco.r6s;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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

public class ops_view extends Fragment {

    public ops_view() {
    }

    private Activity root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = getActivity();

        View v = inflater.inflate(R.layout.fragment_ops_view, container, false);
        RecyclerView op_a_list = (RecyclerView) v.findViewById(R.id.ops_list_a);
        RecyclerView op_d_list = (RecyclerView) v.findViewById(R.id.ops_list_d);
        List<OpsListItem> data_a = new ArrayList<>();
        List<OpsListItem> data_d = new ArrayList<>();

        // Get DB List
        SQLiteDatabase db = home.mkdb();
        Cursor op_a = db.rawQuery("SELECT * FROM operators WHERE type='Attacker' ORDER BY id ASC", null);
        Cursor op_d = db.rawQuery("SELECT * FROM operators WHERE type='Defender' ORDER BY id ASC", null);
        try {
            while (op_a.moveToNext()) {
                data_a.add(new OpsListItem(
                        op_a.getString(op_a.getColumnIndex("name")),
                        op_a.getString(op_a.getColumnIndex("faction")),
                        "Operators/" + op_a.getString(op_a.getColumnIndex("type")) + "/" + op_a.getString(op_a.getColumnIndex("name")) + ".png",
                        op_a.getInt(op_a.getColumnIndex("id"))));
            }
            while (op_d.moveToNext()) {
                data_d.add(new OpsListItem(
                        op_d.getString(op_d.getColumnIndex("name")),
                        op_d.getString(op_d.getColumnIndex("faction")),
                        "Operators/" + op_d.getString(op_d.getColumnIndex("type")) + "/" + op_d.getString(op_d.getColumnIndex("name")) + ".png",
                        op_d.getInt(op_d.getColumnIndex("id"))));
            }
        } finally {
            op_a.close();
            op_d.close();
        }
        db.close();

        op_a_list.setAdapter(new OpsListAdapter(data_a, root));
        op_a_list.setLayoutManager(new LinearLayoutManager(root));
        op_d_list.setAdapter(new OpsListAdapter(data_d, root));
        op_d_list.setLayoutManager(new LinearLayoutManager(root));

        return v;
    }

}
