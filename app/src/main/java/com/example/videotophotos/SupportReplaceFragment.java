package com.example.videotophotos;

import android.content.Context;
import android.text.Layout;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SupportReplaceFragment {
    public void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(view, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }
    public void removeFragmet(FragmentManager fragmentManager, Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment).commit();
    }
}
