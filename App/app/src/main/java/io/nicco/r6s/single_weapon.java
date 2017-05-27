package io.nicco.r6s;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class single_weapon extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_single_weapon, container, false);

        //Get Gun Info
        final ModelWeapon wpn = new ModelWeapon(new ID(ID.WEAPON, getArguments().getString("ID")));

        main.setActionBarTitle(wpn.name);

        LinearLayout row0 = (LinearLayout) v.findViewById(R.id.single_weapon_row0);

        ((TextView) v.findViewById(R.id.single_weapon_name)).setText(wpn.name);
        ((TextView) v.findViewById(R.id.single_weapon_type)).setText(wpn.getTypeShort());
        ((TextView) v.findViewById(R.id.single_weapon_dmg_b)).setText(String.valueOf(wpn.dmg_n));
        ((TextView) v.findViewById(R.id.single_weapon_dmg_s)).setText(String.valueOf(wpn.dmg_s));
        ((TextView) v.findViewById(R.id.single_weapon_mag)).setText(String.valueOf(wpn.mag));
        ((TextView) v.findViewById(R.id.single_weapon_mob)).setText(String.valueOf(wpn.mob));
        ((TextView) v.findViewById(R.id.single_weapon_rpm)).setText(String.valueOf(wpn.rpm));
        ((TextView) v.findViewById(R.id.single_weapon_slot)).setText(String.valueOf(wpn.getSlotLong()));

        //Setting Image
        ((ImageView) v.findViewById(R.id.single_weapon_img)).setImageDrawable(wpn.getImage());

        for (final ID opId : wpn.ops) {
            final ModelOp op = opId.get();
            LinearLayout curItem = Static.mkItem(op.name, getActivity());

            curItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putString("ID", op.id.id);
                    Fragment f = new single_op();
                    f.setArguments(b);
                    main.transition(f);
                }
            });

            row0.addView(curItem);
        }

        return v;
    }
}
