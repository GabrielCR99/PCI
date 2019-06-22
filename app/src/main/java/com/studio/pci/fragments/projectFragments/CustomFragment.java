package com.studio.pci.fragments.projectFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;


public abstract class CustomFragment extends Fragment {

    public interface NewProjectListener{
        void onPartFilled(int part, Bundle bundle);
    }

    protected NewProjectListener listener;
    protected Bundle bundle;
    protected View view;
    protected Context context;

    public CustomFragment(NewProjectListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void setComponents(){
        bundle = new Bundle();
    }

    public abstract boolean savePart();
}
