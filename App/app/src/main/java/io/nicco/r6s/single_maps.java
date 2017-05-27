package io.nicco.r6s;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class single_maps extends Fragment {

    private final List<ModelMap> maps = ModelMap.getMaps();
    private TextView maps_floor_txt;
    private TouchImageView img;
    private ModelMap map;

    private void setImg(ModelMap map) {
        img.setImageDrawable(map.getImage());
        maps_floor_txt.setText(String.valueOf(map.floor));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_single_maps, container, false);

        main.setActionBarTitle(getString(R.string.nav_maps));

        map = new ModelMap(new ID(ID.MAP, "0"));

        Spinner maps_sel = (Spinner) view.findViewById(R.id.single_maps_spinner);
        maps_floor_txt = (TextView) view.findViewById(R.id.single_maps_floor_txt);
        img = (TouchImageView) view.findViewById(R.id.single_maps_img);

        maps_sel.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ModelMap.getMapsNames()));
        maps_sel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                map = maps.get(position);
                setImg(map);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        view.findViewById(R.id.single_maps_btn_up).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                map.setFloor(true);
                setImg(map);
            }
        });
        view.findViewById(R.id.single_maps_btn_down).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                map.setFloor(false);
                setImg(map);
            }
        });

        int showed_toast = Integer.parseInt(getActivity().getPreferences(Context.MODE_PRIVATE).getString(main.TAG_ALERT_MAP, "0"));
        if (showed_toast < main.NUM_ALERTS) {
//            Toast.makeText(getActivity(), "Pinch the Map, You can ZOOM!", Toast.LENGTH_LONG).show();
            getActivity().getPreferences(Context.MODE_PRIVATE).edit().putString(main.TAG_ALERT_MAP, String.valueOf(++showed_toast)).apply();
        }

        img.resetZoom();

        return view;
    }

}
