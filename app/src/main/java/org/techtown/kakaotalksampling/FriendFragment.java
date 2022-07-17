package org.techtown.kakaotalksampling;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
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

        friendAdapter.addItem(new Friend("강지원", ""));
        friendAdapter.addItem(new Friend("김시은", "peaches"));
        friendAdapter.addItem(new Friend("유소현", ""));
        friendAdapter.addItem(new Friend("이창민", ""));
        friendAdapter.addItem(new Friend("남민석", "갓생을 살아보자"));
        friendAdapter.addItem(new Friend("이은성", ""));

        friendAdapter.setOnItemClickListener(new FriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String name, String fstate) {
                //Toast.makeText(v.getContext(), name+"\n"+fstate, Toast.LENGTH_SHORT).show();
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
