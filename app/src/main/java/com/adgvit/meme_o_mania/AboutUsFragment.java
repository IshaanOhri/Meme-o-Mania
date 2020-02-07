package com.adgvit.meme_o_mania;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class AboutUsFragment extends Fragment {

    private TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabHost = view.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("TAB ONE");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Profile");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("TAB TWO");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Team");
        tabHost.addTab(spec);

        int tab = tabHost.getCurrentTab();
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            // When tab is not selected
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#EEEEF0"));
            TextView tv = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(14);
        }
        // When tab is selected
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFFFFF"));
        TextView tv = tabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(14);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int tab = tabHost.getCurrentTab();
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    // When tab is not selected
                    tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#EEEEF0"));
                    TextView tv = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(14);
                }
                // When tab is selected
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFFFFF"));
                TextView tv = tabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(14);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }
}
