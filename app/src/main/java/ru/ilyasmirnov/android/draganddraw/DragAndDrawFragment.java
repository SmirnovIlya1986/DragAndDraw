package ru.ilyasmirnov.android.draganddraw;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// public class DragAndDrawFragment extends ListFragment {
    public class DragAndDrawFragment extends Fragment {




       private static String SAVED_BOX_DRAWING_VIEW = "box_drawing_view";




    public static DragAndDrawFragment newInstance() {
        return new DragAndDrawFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_drag_and_draw, container, false);

        return v;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
