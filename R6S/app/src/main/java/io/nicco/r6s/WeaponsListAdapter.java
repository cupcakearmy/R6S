package io.nicco.r6s;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class WeaponsListAdapter extends RecyclerView.Adapter<WeaponsListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_l;
        public TextView txt_r;
        public LinearLayout l;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_l = (TextView) itemView.findViewById(R.id.weapons_li_l);
            txt_r = (TextView) itemView.findViewById(R.id.weapons_li_r);
            l = (LinearLayout) itemView.findViewById(R.id.weapons_list_item);
        }
    }

    private List<WeaponListItem> data;

    public WeaponsListAdapter(List<WeaponListItem> d) {
        data = d;
    }

    @Override
    public WeaponsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.weapons_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeaponsListAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.txt_l.setText(data.get(position).l);
        viewHolder.txt_r.setText(data.get(position).r);
        viewHolder.l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id", data.get(position).id);
                Fragment f = new weapon_view();
                f.setArguments(b);
                home.ChangeFragment(f);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}