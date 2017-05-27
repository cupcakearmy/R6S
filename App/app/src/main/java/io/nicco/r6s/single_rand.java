package io.nicco.r6s;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class single_rand extends Fragment {

    private TextView t0, t1, t2, t3;

    private int rand_clr() {
        // Retorn a random color
        return Color.argb(255, rand_int(256), rand_int(256), rand_int(256));
    }

    private int rand_int(int max) {
        if (max < 1)
            return 0;
        else
            return new Random().nextInt(max);
    }

    private Rand rand_obj(int i) {
        Rand ret = new Rand();

        ModelOp[] ops;
        switch (i) {
            case 0:
                ops = ModelOp.getAttackers();
                break;
            case 1:
                ops = ModelOp.getDefenders();
                break;
            default:
                ops = new ModelOp[0];
        }

        // Get a random op
        ret.op = ops[rand_int(ops.length)];

        // From the random op choose random attributes
        try {
            // Get a random from primary guns
            ret.main = ret.op.getPrimary()[
                    rand_int(ret.op.getPrimary().length)];
            // Get a random from Side guns
            ret.side = ret.op.getSecondary()[
                    rand_int(ret.op.getSecondary().length)];
            // Get a random from pgadgets
            ret.gadget = new ModelGadget(
                    ret.op.getGadgetsIds()[
                            rand_int(ret.op.getGadgetsIds().length)]);

        } catch (ArrayIndexOutOfBoundsException e) {
            // If there is aproblem with the json, don't make it crash
            e.printStackTrace();
        }

        return ret;
    }

    private void rand(int i) {
        final Rand r = rand_obj(i);

        // Set the text content
        t0.setText(r.op.name);
        t1.setText(r.main.name);
        t2.setText(r.side.name);
        t3.setText(r.gadget.name);

        // Set onclick listener to get to the specifics
        t0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("ID", r.op.id.id);
                Fragment f = new single_op();
                f.setArguments(b);
                main.transition(f);
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("ID", r.main.id.id);
                Fragment f = new single_weapon();
                f.setArguments(b);
                main.transition(f);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("ID", r.side.id.id);
                Fragment f = new single_weapon();
                f.setArguments(b);
                main.transition(f);
            }
        });

        // Set colors
        t0.setBackgroundColor(rand_clr());
        t1.setBackgroundColor(rand_clr());
        t2.setBackgroundColor(rand_clr());
        t3.setBackgroundColor(rand_clr());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_single_rand, container, false);

        main.setActionBarTitle(getString(R.string.nav_rand));

        t0 = (TextView) v.findViewById(R.id.single_rand_t0);
        t1 = (TextView) v.findViewById(R.id.single_rand_t1);
        t2 = (TextView) v.findViewById(R.id.single_rand_t2);
        t3 = (TextView) v.findViewById(R.id.single_rand_t3);

        v.findViewById(R.id.single_rand_btn_att).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rand(0);
            }
        });

        v.findViewById(R.id.single_rand_btn_def).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rand(1);
            }
        });

        return v;
    }

    private class Rand {
        ModelOp op;
        ModelWeapon main;
        ModelWeapon side;
        ModelGadget gadget;

        Rand() {
        }
    }

}
