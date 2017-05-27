package io.nicco.r6s;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class group_ops extends Fragment {

    private ArrayList<ModelOp> ops = null;
    private ListStd<ModelOp> adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.frag_group_ops, container, false);

        main.setActionBarTitle(getString(R.string.nav_ops));
        setMenuVisibility(true);
        setHasOptionsMenu(true);

        ops = new ArrayList<>();

        // Populate with operators
        Iterator<String> it;
        try {
            it = DB.getDB().getJSONObject("operators").keys();
            while (it.hasNext())
                ops.add(new ModelOp(new ID(ID.OPERATOR, it.next())));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ListStd<>(ops, getContext(), new ListStdBuilder<ModelOp>() {
            public void onClick(ID id) {
                Bundle b = new Bundle();
                b.putString("ID", id.id);
                Fragment f = new single_op();
                f.setArguments(b);
                main.transition(f);
            }

            @Override
            public ListStdItem getItem(ModelOp cur) {
                return new ListStdItem(
                        cur.name,
                        cur.getFaction(),
                        ModelOp.TYPES_SHORT[cur.type],
                        cur.getIcon(),
                        cur.id
                );
            }
        });

        // Set sort from last time
        setSort(getActivity().getPreferences(Context.MODE_PRIVATE).getInt(main.SORT_OPS, R.id.menu_group_ops_sort_faction));

        ((ListView) v.findViewById(R.id.group_ops_list)).setAdapter(adapter);

        return v;
    }

    private void setSort(int i) {
        switch (i) {
            case R.id.menu_group_ops_sort_name:
                Collections.sort(ops, ModelOp.sortByName());
                break;
            case R.id.menu_group_ops_sort_type:
                Collections.sort(ops, ModelOp.sortByType());
                break;
            case R.id.menu_group_ops_sort_faction:
                Collections.sort(ops, ModelOp.sortByFaction());
                break;
            default:
                return;
        }
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putInt(main.SORT_OPS, i).apply();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.group_ops, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setSort(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

}
