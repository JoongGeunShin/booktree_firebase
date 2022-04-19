package org.techtown.booktree_firebase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MyTreeFragment extends Fragment {
    private static final String TAG = "fragmentMyTree";


    public MyTreeFragment() {
        // Required empty public constructor
    }
    public static MyTreeFragment newInstance(String param1, String param2) {
        MyTreeFragment fragment = new MyTreeFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_mytree, container, false);
    }*/
}
