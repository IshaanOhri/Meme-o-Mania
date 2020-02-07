package com.adgvit.meme_o_mania;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class NavigationActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bottomNavigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new TimelineFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId())
                {
                    case R.id.navigation_timeline:
                        selectedFragment = new TimelineFragment();
                        break;

                    case R.id.navigation_quiz:
                        selectedFragment = new QuizFragment();
                        break;

                    case R.id.navigation_meme:
                        selectedFragment = new UploadFragment();
                        break;

                    case R.id.navigation_about:
                        selectedFragment = new AboutUsFragment();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectedFragment).commit();

                return true;

            }
        });
    }

}
