package io.nicco.r6s;


import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class maps_view extends Fragment {


    public maps_view() {
    }

    private Context root = null;
    private Spinner maps_sel;
    private TextView cur_floor_txt;
    private Button btn_l;
    private Button btn_r;
    private ImageView img;

    private final String path = "Maps";
    private int cur_floor = 0;
    private String cur_map;
    private String[] maps = null;

    private void setImg() {
        InputStream ims;
        try {
            ims = root.getAssets().open(path + "/" + cur_map + "/" + String.valueOf(cur_floor) + ".jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            img.setImageDrawable(d);
            cur_floor_txt.setText(String.valueOf(cur_floor));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps_view, container, false);

        root = getActivity();
        maps_sel = (Spinner) view.findViewById(R.id.maps_spinner);
        cur_floor_txt = (TextView) view.findViewById(R.id.maps_cur_floor);
        btn_l = (Button) view.findViewById(R.id.maps_btn_l);
        btn_r = (Button) view.findViewById(R.id.maps_btn_r);
        img = (ImageView) view.findViewById(R.id.maps_img);

        List<String> maps_sel_cont = new ArrayList<>();

        try {
            maps = root.getAssets().list(path);
        } catch (Exception e) {
        }

        for (String map : maps) {
            maps_sel_cont.add(map);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(root, android.R.layout.simple_spinner_dropdown_item, maps_sel_cont);
        maps_sel.setAdapter(adapter);

        maps_sel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                cur_map = maps[position];
                setImg();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        btn_l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    root.getAssets().open(path + '/' + cur_map + "/" + String.valueOf(cur_floor - 1) + ".jpg");
                    cur_floor--;
                    setImg();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_r.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    root.getAssets().open(path + '/' + cur_map + "/" + String.valueOf(cur_floor + 1) + ".jpg");
                    cur_floor++;
                    setImg();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

}
