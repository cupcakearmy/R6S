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

public class group_weapons extends Fragment {

    private ArrayList<ModelWeapon> weapons = null;
    private ListStd<ModelWeapon> adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.frag_group_weapons, container, false);

        main.setActionBarTitle(getString(R.string.nav_weapons));
        setMenuVisibility(true);
        setHasOptionsMenu(true);

        weapons = new ArrayList<>();

        // Populate with operators
        Iterator<String> it;
        try {
            it = DB.getDB().getJSONObject("weapons").keys();
            while (it.hasNext())
                weapons.add(new ModelWeapon(new ID(ID.WEAPON, it.next())));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ListStd<>(weapons, getContext(), new ListStdBuilder<ModelWeapon>() {
            public void onClick(ID id) {
                Bundle b = new Bundle();
                b.putString("ID", id.id);
                Fragment f = new single_weapon();
                f.setArguments(b);
                main.transition(f);
            }

            @Override
            public ListStdItem getItem(ModelWeapon cur) {
                return new ListStdItem(
                        cur.name,
                        "",
                        cur.getTypeShort(),
                        cur.getImage(),
                        cur.id
                );
            }
        });

        // Set sort from last time
        setSort(getActivity().getPreferences(Context.MODE_PRIVATE).getInt(main.SORT_WEAPONS, R.id.menu_group_ops_sort_faction));
        ((ListView) v.findViewById(R.id.group_weapons_list)).setAdapter(adapter);

        return v;
    }

    private void setSort(int i) {
        switch (i) {
            case R.id.menu_group_weapons_sort_name:
                Collections.sort(weapons, ModelWeapon.sortByName());
                break;
            case R.id.menu_group_weapons_sort_class:
                Collections.sort(weapons, ModelWeapon.sortByClass());
                break;
            default:
                return;
        }
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putInt(main.SORT_WEAPONS, i).apply();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.group_weapons, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setSort(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

}
