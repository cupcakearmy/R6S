package io.nicco.r6s;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class single_home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_single_home, container, false);

        main.setActionBarTitle(getString(R.string.nav_home));

        v.findViewById(R.id.single_home_btn_ops).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.transition(new group_ops());
            }
        });
        v.findViewById(R.id.single_home_btn_weapons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.transition(new group_weapons());
            }
        });
        v.findViewById(R.id.single_home_btn_maps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.transition(new single_maps());
            }
        });

        return v;
    }

}
