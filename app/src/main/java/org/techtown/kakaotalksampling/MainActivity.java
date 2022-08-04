package org.techtown.kakaotalksampling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActionBar abar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_search:
                Toast.makeText(this, "검색", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_friendplus:
                Toast.makeText(this, "친구추가", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_music:
                Toast.makeText(this, "뮤직", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_setting:
                Toast.makeText(this, "설정", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    FriendFragment friendFragment;
    ChatFragment chatFragment;
    MoreFragment moreFragment;
    ScaleFragment scaleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        abar = getSupportActionBar();

        friendFragment = new FriendFragment();
        chatFragment = new ChatFragment();
        moreFragment = new MoreFragment();
        scaleFragment = new ScaleFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,friendFragment).commit();
        abar.setTitle("친구");
        abar.setTitle(Html.fromHtml("<font color='#FF000000'>ActionBarTitle </font>"));

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.friendTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                        friendFragment).commit();
                                abar.setTitle("친구");

                                return true;
                            case R.id.chatTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                        chatFragment).commit();
                                abar.setTitle("채팅");

                                return true;
                            case R.id.moreTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                        moreFragment).commit();
                                abar.setTitle("더보기");

                                return true;
                            case R.id.scaleTab:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                        scaleFragment).commit();
                                abar.setTitle("저울");

                                return true;
                        }
                        return false;
                    }

                }
        );
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
            AndPermission.with(this)
                    .runtime()
                    .permission(Permission.READ_CALL_LOG, Permission.READ_CONTACTS,
                            Permission.READ_PHONE_NUMBERS, Permission.READ_SMS, Permission.RECEIVE_SMS,
                            Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> permissions) {
                            Toast.makeText(MainActivity.this, "허용된 권한 개수"+permissions.size(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> permissions) {
                            Toast.makeText(MainActivity.this, "거부된 권한 개수"+permissions.size(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .start();
        } else {
            AndPermission.with(this)
                    .runtime()
                    .permission(Permission.READ_CALL_LOG, Permission.READ_CONTACTS,
                            Permission.READ_PHONE_STATE, Permission.READ_SMS, Permission.RECEIVE_SMS,
                            Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> permissions) {
                            Toast.makeText(MainActivity.this, "허용된 권한 개수"+permissions.size(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> permissions) {
                            Toast.makeText(MainActivity.this, "거부된 권한 개수"+permissions.size(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .start();
        }
    }

}