package org.techtown.kakaotalksampling;

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

import org.w3c.dom.Text;

public class FriendFragment extends Fragment {
    TextView numFriend;
    LinearLayout MyProfile;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_friend, container, false);

        RecyclerView friendRecycler = v.findViewById(R.id.friendRecycler);

        numFriend = v.findViewById(R.id.numFriend);

        MyProfile = v.findViewById(R.id.MyProfile);
        MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //나의 프로필 보여주기
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

        friendRecycler.setAdapter(friendAdapter);


        return v;
    }
}
