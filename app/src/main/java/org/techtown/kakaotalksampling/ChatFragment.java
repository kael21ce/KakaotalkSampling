package org.techtown.kakaotalksampling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends Fragment {
    LinearLayout ChatButton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView chatRecycler = v.findViewById(R.id.chatRecycler);

        ChatButton = v.findViewById(R.id.ChatButton);
        ChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //채팅방 들어가기
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        chatRecycler.setLayoutManager(layoutManager);
        ChatAdapter chatAdapter = new ChatAdapter();

        chatAdapter.addItem(new Chat("강지원", "", ""));
        chatAdapter.addItem(new Chat("김시은", "", ""));
        chatAdapter.addItem(new Chat("유소현", "", ""));
        chatAdapter.addItem(new Chat("이창민", "", ""));
        chatAdapter.addItem(new Chat("남민석", "", ""));
        chatAdapter.addItem(new Chat("이은성", "", ""));

        chatRecycler.setAdapter(chatAdapter);

        return v;
    }
}
