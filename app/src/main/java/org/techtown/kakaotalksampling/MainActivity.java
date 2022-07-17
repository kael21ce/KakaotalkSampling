package org.techtown.kakaotalksampling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FriendFragment friendFragment;
    ChatFragment chatFragment;
    MoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friendFragment = new FriendFragment();
        chatFragment = new ChatFragment();
        moreFragment = new MoreFragment();
        //
        getSupportFragmentManager().beginTransaction().replace(R.id.container,friendFragment).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.friendTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,friendFragment).commit();

                                return true;
                            case R.id.chatTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,chatFragment).commit();

                                return true;
                            case R.id.moreTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,moreFragment).commit();

                                return true;
                            case R.id.scaleTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,friendFragment).commit();

                                return true;
                        }
                        return false;
                    }

                }
        );
    }
}