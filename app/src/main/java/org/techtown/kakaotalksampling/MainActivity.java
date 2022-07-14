package org.techtown.kakaotalksampling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView friendRecycler = findViewById(R.id.friendRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        friendRecycler.setLayoutManager(layoutManager);
        FriendAdapter friendAdapter = new FriendAdapter();

        friendAdapter.addItem(new Friend("강지원", ""));
        friendAdapter.addItem(new Friend("김시은", "peaches"));
        friendAdapter.addItem(new Friend("유소현", ""));
        friendAdapter.addItem(new Friend("이창민", ""));
        friendAdapter.addItem(new Friend("남민석", "갓생을 살아보자"));
        friendAdapter.addItem(new Friend("이은성", ""));
        friendAdapter.addItem(new Friend("윤소예", ""));

        friendRecycler.setAdapter(friendAdapter);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.friendTab:
                                return true;

                            case R.id.chatTab:
                                return true;

                            case R.id.viewTab:
                                return true;

                            case R.id.shoppingTab:
                                return true;

                            case R.id.moreTab:
                                return true;

                            case R.id.scaleTab:
                                return true;
                        }
                        return false;
                    }

                }
        );
    }
}