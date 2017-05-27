package io.nicco.r6s;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class single_about extends Fragment {

    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_single_about, container, false);

        main.setActionBarTitle(getString(R.string.nav_about));

        setHasOptionsMenu(false);

        // Set the current version name
        ((TextView) v.findViewById(R.id.single_about_version)).setText("V" + BuildConfig.VERSION_NAME + "  ");

        v.findViewById(R.id.single_about_changelog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(main.ABOUT_CHANGES);
            }
        });

        v.findViewById(R.id.single_about_contribute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(main.ABOUT_GIT);
            }
        });

        v.findViewById(R.id.single_about_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("message/rfc822");
//                i.putExtra(Intent.EXTRA_EMAIL, main.ABOUT_EMAIL);
//                i.putExtra(Intent.EXTRA_SUBJECT, "Bughunter");
//                i.putExtra(Intent.EXTRA_TEXT, "Hi, i found this awesome bug!");
//                startActivity(Intent.createChooser(i, "Send..."));

                openUrl(main.ABOUT_BUG);
            }
        });

        v.findViewById(R.id.single_about_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(main.ABOUT_GIT);
            }
        });

        return v;
    }
}
