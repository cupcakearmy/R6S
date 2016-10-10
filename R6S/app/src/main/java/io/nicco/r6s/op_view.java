package io.nicco.r6s;


import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class op_view extends Fragment {


    public op_view() {
    }

    private LinearLayout mkItem(String s) {
        LinearLayout frame = new LinearLayout(home.root());
        TextView tmp = new TextView(home.root());

        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        fp.setMargins(8, 8, 8, 8);
        frame.setLayoutParams(fp);
        frame.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams tmpp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tmp.setText(s);
        tmp.setLayoutParams(tmpp);
        tmp.setPadding(16, 16, 16, 16);
        tmp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tmp.setBackgroundResource(R.drawable.weapon_selector);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tmp.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Medium);
        }
        tmp.setTextColor(0xffffffff);
        frame.addView(tmp);
        return frame;
    }

    private int id = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_op_view, container, false);

        //Get Operator Info
        Bundle b = this.getArguments();
        SQLiteDatabase db = home.mkdb();
        id = b.getInt("id");
        Cursor c = db.rawQuery("SELECT * FROM operators WHERE id=" + id, null);
        c.moveToFirst();

        // Map Of Text Views
        Map<String, TextView> txts = new HashMap();
        txts.put("name", (TextView) v.findViewById(R.id.op_name));
        txts.put("faction", (TextView) v.findViewById(R.id.op_faction));
        txts.put("armor", (TextView) v.findViewById(R.id.op_armor));
        txts.put("speed", (TextView) v.findViewById(R.id.op_speed));
        txts.put("type", (TextView) v.findViewById(R.id.op_type));
        txts.put("ability", (TextView) v.findViewById(R.id.op_ability));

        txts.get("name").setText(c.getString(c.getColumnIndex("name")));
        txts.get("faction").setText(c.getString(c.getColumnIndex("faction")));
        txts.get("armor").setText(c.getString(c.getColumnIndex("armor")));
        txts.get("speed").setText(c.getString(c.getColumnIndex("speed")));
        txts.get("type").setText(c.getString(c.getColumnIndex("type")));
        txts.get("ability").setText(c.getString(c.getColumnIndex("ability")));

        // Set Weapons
        Cursor w = db.rawQuery("SELECT * FROM weapons WHERE id IN (" + c.getString(c.getColumnIndex("wid")) + ")", null);
        LinearLayout primary = (LinearLayout) v.findViewById(R.id.op_p_w);
        LinearLayout secondary = (LinearLayout) v.findViewById(R.id.op_s_w);
        while (w.moveToNext()) {
            final int cur_id = w.getInt(w.getColumnIndex("id"));
            LinearLayout tmp = mkItem(w.getString(w.getColumnIndex("name")));
            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putInt("id", cur_id);
                    Fragment f = new weapon_view();
                    f.setArguments(b);
                    home.ChangeFragment(f);
                }
            });
            if (w.getString(w.getColumnIndex("class")).equals("Secondary")) {
                secondary.addView(tmp);
            } else {
                primary.addView(tmp);
            }
        }

        // Set Gadgets
        Cursor g = db.rawQuery("SELECT * FROM gadget WHERE id IN (" + c.getString(c.getColumnIndex("gadget")) + ")", null);
        LinearLayout gl = (LinearLayout) v.findViewById(R.id.op_g);
        while (g.moveToNext()) {
            gl.addView(mkItem(g.getString(g.getColumnIndex("name"))));
        }

        //Setting Images
        try {
            InputStream ims = home.root().getAssets().open("Operators/" + c.getString(c.getColumnIndex("type")) + "/" + c.getString(c.getColumnIndex("name")) + ".png");
            ((ImageView) v.findViewById(R.id.op_img)).setImageDrawable(Drawable.createFromStream(ims, null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("Trelelele", "ok");
                try {

                    //Drawable d = Drawable.createFromStream(home.root().getAssets().open("OPs/" + String.valueOf(id) + ".jpg"), null);
                    ImageView op_bg = (ImageView) v.findViewById(R.id.op_bg);
                    RelativeLayout img_cont = (RelativeLayout) v.findViewById(R.id.img_cont);
                    Bitmap bitmap = BitmapFactory.decodeStream(home.root().getAssets().open("OPs/" + String.valueOf(id) + ".jpg"));

                    int b_h = bitmap.getHeight();
                    int b_w = bitmap.getWidth();
                    //float ratio = op_bg.getHeight() / op_bg.getWidth();
                    Log.i("W & H: ", op_bg.getMeasuredHeight() + " - " + op_bg.getHeight());
            /*
            if (ratio > 1) {
                // BG is portrait
                if (b_h > b_w) {
                    //img is portrait
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, b_h, (int) (b_h * ratio));
                } else {
                    //img is landscape
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) (b_w / ratio), b_w);
                }
            } else {
                //BG is landscape
                if (b_h > b_w) {
                    //img is portrait
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) (b_w / ratio), b_w);
                } else {
                    //img is landscape
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, b_h, (int) (b_h * ratio));
                }
            }*/

                    //Crop
                    op_bg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        db.close();

        return v;
    }

}
