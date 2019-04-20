package com.studio.pci.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.studio.pci.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedsFragment extends Fragment {

    private ViewFlipper viewFlipper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        viewFlipper = view.findViewById(R.id.feed_view_flipper);
        ButterKnife.bind(this, view);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();
        return view;
    }

    @OnClick(R.id.feed_button_left)
    public void previousView(){
        viewFlipper.setInAnimation(getContext(),R.anim.slide_in_right);
        viewFlipper.setOutAnimation(getContext(),R.anim.slide_out_left);
        viewFlipper.showPrevious();
    }

    @OnClick(R.id.feed_button_right)
    public void nextView(){
        viewFlipper.setInAnimation(getContext(),R.anim.slide_in_right);
        viewFlipper.setOutAnimation(getContext(),R.anim.slide_out_left);
        viewFlipper.showNext();
    }
}
