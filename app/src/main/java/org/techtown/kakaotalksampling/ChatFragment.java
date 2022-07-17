package org.techtown.kakaotalksampling;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView chatRecycler = v.findViewById(R.id.chatRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        chatRecycler.setLayoutManager(layoutManager);
        ChatAdapter chatAdapter = new ChatAdapter();

        chatAdapter.addItem(new Chat("강지원", " ", " "));
        chatAdapter.addItem(new Chat("김시은", " ", " "));
        chatAdapter.addItem(new Chat("유소현", " ", " "));
        chatAdapter.addItem(new Chat("이창민", " ", " "));
        chatAdapter.addItem(new Chat("남민석", " ", " "));
        chatAdapter.addItem(new Chat("이은성", " ", " "));

        chatAdapter.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String name, String last_message, String date) {
                Intent chatIntent = new Intent(v.getContext(), ChatActivity.class);
                chatIntent.putExtra("name",name);
                startActivity(chatIntent);
            }
        });

        chatRecycler.setAdapter(chatAdapter);

        return v;
    }
}
