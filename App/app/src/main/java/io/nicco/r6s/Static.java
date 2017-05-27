package io.nicco.r6s;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Static {

    static LinearLayout mkItem(final String s, final Activity a) {
        LinearLayout frame = new LinearLayout(a);
        TextView tmp = new TextView(a);

        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        int marg = a.getResources().getDimensionPixelSize(R.dimen.spacing05x);
        fp.setMargins(marg, marg, marg, marg);
        frame.setLayoutParams(fp);
        frame.setOrientation(LinearLayout.HORIZONTAL);

        tmp.setText(s);
        tmp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        int pad = a.getResources().getDimensionPixelSize(R.dimen.spacing1x);
        tmp.setPadding(pad, pad, pad, pad);
        tmp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tmp.setBackgroundResource(R.drawable.tag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tmp.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Medium);
        }
        tmp.setTextColor(0xffffffff);
        frame.addView(tmp);
        return frame;
    }

}
