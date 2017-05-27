package io.nicco.r6s;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class error extends Fragment {


    public error() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_error, container, false);

        main.setActionBarTitle(getString(R.string.frag_error_txt));

        return v;
    }

}
