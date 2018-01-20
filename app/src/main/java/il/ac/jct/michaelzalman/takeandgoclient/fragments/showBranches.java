package il.ac.jct.michaelzalman.takeandgoclient.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import il.ac.jct.michaelzalman.takeandgoclient.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class showBranches extends Fragment {


    public showBranches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_branches, container, false);
    }

}
