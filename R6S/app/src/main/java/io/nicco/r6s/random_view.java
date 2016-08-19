package io.nicco.r6s;


import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class random_view extends Fragment {

    TextView gen_op;
    TextView gen_p;
    TextView gen_s;
    TextView gen_g;

    public random_view() {
    }

    class op {
        public String n;
        public String t;
        public List<String> p = new ArrayList<>();
        public List<String> s = new ArrayList<>();
        public List<String> g = new ArrayList<>();

        public op(String N, String T, List<String> P, List<String> S, List<String> G) {
            n = N;
            t = T;
            p = P;
            s = S;
            g = G;
        }
    }

    void randop(List<op> ops, boolean ad) {
        op cur;
        String sel = "Defender";

        if (ad)
            sel = "Attacker";

        do {
            cur = ops.get(rand(ops.size()));
        }
        while (cur.t.equals(sel));

        gen_op.setText(cur.n);
        gen_p.setText(cur.p.get(rand(cur.p.size())));
        gen_s.setText(cur.s.get(rand(cur.s.size())));
        gen_g.setText(cur.g.get(rand(cur.g.size())));
    }

    int rand(int max) {
        return new Random().nextInt(max);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_random_view, container, false);

        Button gen_a = (Button) v.findViewById(R.id.rand_a);
        Button gen_d = (Button) v.findViewById(R.id.rand_d);

        gen_op = (TextView) v.findViewById(R.id.rand_op);
        gen_p = (TextView) v.findViewById(R.id.rand_primary);
        gen_s = (TextView) v.findViewById(R.id.rand_secondary);
        gen_g = (TextView) v.findViewById(R.id.rand_gadget);

        final List<op> ops = new ArrayList();

        SQLiteDatabase db = home.mkdb();
        Cursor c = db.rawQuery("SELECT * FROM operators", null);
        while (c.moveToNext()) {
            List<String> p = new ArrayList<>();
            List<String> s = new ArrayList<>();
            List<String> g = new ArrayList<>();
            for (String weapon : c.getString(c.getColumnIndex("wid")).split(",")) {
                Cursor wc = db.rawQuery("SELECT * FROM weapons WHERE id=" + weapon, null);
                wc.moveToFirst();
                if (wc.getString(wc.getColumnIndex("class")).equals("Secondary"))
                    s.add(wc.getString(wc.getColumnIndex("name")));
                else
                    p.add(wc.getString(wc.getColumnIndex("name")));
            }
            for (String weapon : c.getString(c.getColumnIndex("gadget")).split(",")) {
                Cursor wc = db.rawQuery("SELECT * FROM gadget WHERE id=" + weapon, null);
                wc.moveToFirst();
                g.add(wc.getString(wc.getColumnIndex("name")));
            }
            ops.add(new op(
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("type")),
                    p, s, g
            ));
        }
        db.close();

        gen_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randop(ops, true);
            }
        });

        gen_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randop(ops, false);
            }
        });

        return v;
    }

}
