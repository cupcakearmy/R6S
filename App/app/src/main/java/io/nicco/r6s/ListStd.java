package io.nicco.r6s;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

interface ListStdBuilder<T> {
    void onClick(ID id);

    ListStdItem getItem(T cur);
}

class ListStdItem {
    String name, sub, attr;
    Drawable img;
    ID id;

    ListStdItem(String name, String sub, String attr, Drawable img, ID id) {
        this.name = name;
        this.sub = sub;
        this.attr = attr;
        this.img = img;
        this.id = id;
    }
}

class ListStd<T> extends ArrayAdapter<T> implements View.OnClickListener {

    private ListStdBuilder<T> h = null;

    ListStd(ArrayList<T> data, Context context, ListStdBuilder<T> h) {
        super(context, R.layout.frag_list_std, data);
        this.h = h;
    }

    @Override
    public void onClick(View v) {
        h.onClick((ID) v.getTag());
    }

    @NonNull
    @Override
    public View getView(int position, View v, @NonNull ViewGroup p) {
        ListStdItem item = h.getItem(getItem(position));

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.frag_list_std, p, false);
        }

        ((TextView) v.findViewById(R.id.list_std_title)).setText(item.name);
        ((TextView) v.findViewById(R.id.list_std_sub)).setText(item.sub);
        ((TextView) v.findViewById(R.id.list_std_attr)).setText(item.attr);
        ((ImageView) v.findViewById(R.id.list_std_icon)).setImageDrawable(item.img);

        v.setTag(item.id);
        v.setOnClickListener(this);

        return v;
    }
}
