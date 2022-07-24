package org.techtown.kakaotalksampling;



import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class FriendFragment extends Fragment {
    TextView numFriend;
    LinearLayout MyProfile;
    public static final int REQUEST_CODE_MYPROFILE=101;

    DatabaseHelper dbHelper;
    SQLiteDatabase database;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_friend, container, false);

        RecyclerView friendRecycler = v.findViewById(R.id.friendRecycler);

        numFriend = v.findViewById(R.id.numFriend);

        MyProfile = v.findViewById(R.id.MyProfile);
        MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mpIntent = new Intent(v.getContext(), MyprofileActivity.class);
                startActivityForResult(mpIntent, REQUEST_CODE_MYPROFILE);
            }
        });

        //데이터베이스 생성
        dbHelper = new DatabaseHelper(v.getContext());
        database = dbHelper.getWritableDatabase();

        //레코드 추가
        database.execSQL("insert into Friend" + "(name, stateMessage, mobile, lastMessage, lastDate) "
                + " values "
                + "('강지원(20)', '', '010-7599-2001', '', '')");
        database.execSQL("insert into Friend" + "(name, stateMessage, mobile, lastMessage, lastDate) "
                + " values "
                + "('김시은(21)', 'peaches', '010-7637-4041', '', '')");
        database.execSQL("insert into Friend" + "(name, stateMessage, mobile, lastMessage, lastDate) "
                + " values "
                + "('유소현(21)', '', '010-5031-6394', '', '')");
        database.execSQL("insert into Friend" + "(name, stateMessage, mobile, lastMessage, lastDate) "
                + " values "
                + "('이창민(ulala)', '', '010-6551-5413', '', '')");
        database.execSQL("insert into Friend" + "(name, stateMessage, mobile, lastMessage, lastDate) "
                + " values "
                + "('남민석(ulala)', '갓생을 살아보자', '010-2908-9023', '', '')");
        database.execSQL("insert into Friend" + "(name, stateMessage, mobile, lastMessage, lastDate) "
                + " values "
                + "('이은성(ulala)', '갓생을 살아보자', '010-6767-3243', '', '')");

        //커서 객체 생성
        Cursor cursor = database.rawQuery("select name, stateMessage, mobile", null);
        int recordCount = cursor.getCount();

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        friendRecycler.setLayoutManager(layoutManager);
        FriendAdapter friendAdapter = new FriendAdapter();

        //데이터베이스에서 리사이클러뷰에 추가
        for (int i = 0; i<recordCount; i++) {
            cursor.moveToNext();
            String name = cursor.getString(0);
            String stateMessage = cursor.getString(1);
            String mobile = cursor.getString(2);
            friendAdapter.addItem(new Friend(name, stateMessage, mobile));
        }

        friendAdapter.setOnItemClickListener(new FriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String name, String fstate) {
                Intent profileIntent = new Intent(v.getContext(), FriendprofileActivity.class);
                profileIntent.putExtra("name", name);
                profileIntent.putExtra("fstate", fstate);
                startActivity(profileIntent);
            }
        });

        Integer n=friendAdapter.getItemCount();
        numFriend.setText("친구 "+n);

        friendRecycler.setAdapter(friendAdapter);


        return v;
    }

}
