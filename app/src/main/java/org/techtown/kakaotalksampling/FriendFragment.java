package org.techtown.kakaotalksampling;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class FriendFragment extends Fragment {
    TextView numFriend;
    LinearLayout MyProfile;
    public static final int REQUEST_CODE_MYPROFILE=101;
    public static final int REQUEST_CODE_FPROFILE=102;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        friendRecycler.setLayoutManager(layoutManager);
        FriendAdapter friendAdapter = new FriendAdapter();

        friendAdapter.addItem(new Friend("강지원(21)", "", "010-7599-2001",
                BitmapFactory.decodeResource(getResources(), R.drawable.default_profile)));
        friendAdapter.addItem(new Friend("김시은(21)", "peaches", "010-7637-4041",
                BitmapFactory.decodeResource(getResources(), R.drawable.default_profile)));
        friendAdapter.addItem(new Friend("유소현(21)", "", "010-5031-6394",
                BitmapFactory.decodeResource(getResources(), R.drawable.default_profile)));
        friendAdapter.addItem(new Friend("이창민(ulala)", "", "010-6551-5413",
                BitmapFactory.decodeResource(getResources(), R.drawable.default_profile)));
        friendAdapter.addItem(new Friend("남민석(ulala)", "갓생을 살아보자", "010-2908-9023",
                BitmapFactory.decodeResource(getResources(), R.drawable.default_profile)));
        friendAdapter.addItem(new Friend("이은성(ulala)", "", "010-6767-3243",
                BitmapFactory.decodeResource(getResources(), R.drawable.default_profile)));

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
