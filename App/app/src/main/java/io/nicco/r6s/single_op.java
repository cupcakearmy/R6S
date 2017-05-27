package io.nicco.r6s;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class single_op extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.frag_single_op, container, false);

        //Get Operator Info
        Bundle b = this.getArguments();
        final ModelOp op = new ModelOp(new ID(ID.OPERATOR, b.getString("ID")));

        main.setActionBarTitle(op.name);

        LinearLayout row0 = (LinearLayout) v.findViewById(R.id.single_op_row0);
        LinearLayout row1 = (LinearLayout) v.findViewById(R.id.single_op_row1);
        LinearLayout row2 = (LinearLayout) v.findViewById(R.id.single_op_row2);

        // Set the text views
        ((TextView) v.findViewById(R.id.single_op_name)).setText(op.name);
        ((TextView) v.findViewById(R.id.single_op_faction)).setText(op.getFaction());
        ((TextView) v.findViewById(R.id.single_op_armor)).setText(String.valueOf(op.armor));
        ((TextView) v.findViewById(R.id.single_op_speed)).setText(String.valueOf(op.speed));
        ((TextView) v.findViewById(R.id.single_op_type)).setText(op.getTypeLong());
        ((TextView) v.findViewById(R.id.single_op_ability)).setText(op.ability);

        for (ID wpnId : op.weapons) {
            final ModelWeapon wpn = wpnId.get();
            LinearLayout curItem = Static.mkItem(wpn.name, getActivity());

            curItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putString("ID", wpn.id.id);
                    Fragment f = new single_weapon();
                    f.setArguments(b);
                    main.transition(f);
                }
            });
            if (wpn.slot == 0) {
                row0.addView(curItem);
            } else {
                row1.addView(curItem);
            }
        }

        for (final ID gadgetId : op.gadgets) {
            final ModelGadget gadget = gadgetId.get();
            LinearLayout curItem = Static.mkItem(gadget.name, getActivity());

//            curItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle b = new Bundle();
//                    b.putString("ID", curWeapon.id.id);
////                    Fragment f = new ();
////                    f.setArguments(b);
////                    main.transition(f);
//                }
//            });
            row2.addView(curItem);
        }

        // Setting the icon
        ((ImageView) v.findViewById(R.id.single_op_icon)).setImageDrawable(op.getIcon());

        // Setting the bg image
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ImageView op_bg = (ImageView) v.findViewById(R.id.single_op_img);
                Bitmap bitmap = ((BitmapDrawable) op.getImage()).getBitmap();

                int b_h = bitmap.getHeight();
                int b_w = bitmap.getWidth();
                int bg_h = op_bg.getHeight();
                int bg_w = op_bg.getWidth();
                float ratio = (float) bg_h / bg_w;

                try {
                    if (ratio > 1) {
                        // BG is portrait
                        if (b_h > b_w)
                            bitmap = Bitmap.createBitmap(bitmap, 0, 0, b_w, Math.min((int) (b_w * ratio), b_h));
                        else
                            bitmap = Bitmap.createBitmap(bitmap, 0, 0, Math.min((int) (b_w / ratio), b_h), b_w);
                    } else {
                        // BG is landscape
                        if (b_h > b_w)
                            bitmap = Bitmap.createBitmap(bitmap, 0, 0, b_w, (int) (b_w * ratio));
                        else
                            bitmap = Bitmap.createBitmap(bitmap, 0, 0, b_h, (int) (b_h * ratio));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Crop
                op_bg.setImageBitmap(bitmap);
            }
        });

        int showed_toast = Integer.parseInt(getActivity().getPreferences(Context.MODE_PRIVATE).getString(main.TAG_ALERT_OP, "0"));
        if (showed_toast < main.NUM_ALERTS) {
//            Snackbar.make(v, "Scroll down to see more!", Snackbar.LENGTH_LONG).show();
            getActivity().getPreferences(Context.MODE_PRIVATE).edit().putString(main.TAG_ALERT_OP, String.valueOf(++showed_toast)).apply();
        }

        return v;
    }

}
