package io.nicco.r6s;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OpsListAdapter extends RecyclerView.Adapter<OpsListAdapter.ViewHolder> {

    private Activity root;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_n;
        public TextView txt_f;
        public ImageView txt_i;
        public LinearLayout l;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_n = (TextView) itemView.findViewById(R.id.ops_li_name);
            txt_f = (TextView) itemView.findViewById(R.id.ops_li_faction);
            txt_i = (ImageView) itemView.findViewById(R.id.ops_li_img);
            l = (LinearLayout) itemView.findViewById(R.id.ops_list_item);
        }
    }

    private List<OpsListItem> data;

    public OpsListAdapter(List<OpsListItem> d, Activity r) {
        data = d;
        root = r;
    }

    @Override
    public OpsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.ops_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OpsListAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.txt_n.setText(data.get(position).n);
        viewHolder.txt_f.setText(data.get(position).f);
        try {
            InputStream ims = root.getAssets().open(data.get(position).i);
            viewHolder.txt_i.setImageDrawable(Drawable.createFromStream(ims, null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewHolder.l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id", data.get(position).id);
                Fragment f = new op_view();
                f.setArguments(b);
                home.ChangeFragment(f, root);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}