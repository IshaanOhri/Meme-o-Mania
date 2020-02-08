package com.adgvit.meme_o_mania;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Type;

public class NavigationActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static String name, regNo,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bottomNavigationView = findViewById(R.id.nav_view);

        SharedPreferences sharedPreferences = getSharedPreferences("com.adgvit.meme_o_mania", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        Type type = new TypeToken<String>(){}.getType();

        String json = sharedPreferences.getString("name","Name");
        name = gson.fromJson(json,type);
        json = sharedPreferences.getString("regNo","Reg.No.");
        regNo = gson.fromJson(json,type);
        json = sharedPreferences.getString("email","Email");
        email = gson.fromJson(json,type);

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
                        selectedFragment = new UploadRulesFragment();
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

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
