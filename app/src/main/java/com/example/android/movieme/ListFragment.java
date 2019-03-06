package com.example.android.movieme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Fragment displayed as the second tab, containing an ArrayList of
 * Films displayed as a list with the help of ListViewAdapter.
 * Corresponding layout file is list_tab.xml
 */

public class ListFragment extends Fragment{

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_tab, container, false);

        // Create a list of items
       /* final ArrayList<Films> films = new ArrayList<Films>();
        films.add(new Films("Jurassic Park", "200 min", R.drawable.placeholder));
        films.add(new Films("Jurassic Park", "200 min", R.drawable.placeholder));
        films.add(new Films("Jurassic Park", "200 min", R.drawable.placeholder));
        films.add(new Films("Jurassic Park", "200 min", R.drawable.placeholder));
        films.add(new Films("Jurassic Park", "200 min", R.drawable.placeholder));
        films.add(new Films("Jurassic Park", "200 min", R.drawable.placeholder));
        films.add(new Films("Jurassic Park", "200 min", R.drawable.placeholder));

        ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), films);
        ListView listView = rootView.findViewById(R.id.list_view);
        listView.setAdapter(listViewAdapter);
        8*/

        return rootView;
    }

}
