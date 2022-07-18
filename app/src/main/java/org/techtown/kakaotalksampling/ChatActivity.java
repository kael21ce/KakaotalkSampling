package org.techtown.kakaotalksampling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    TextView fName;
    TextView myChat;
    EditText chatText;
    String chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fName=findViewById(R.id.CfName);
        myChat=findViewById(R.id.myChat);
        chatText=findViewById(R.id.editChat);
        chat="";

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        fName.setText(intent.getStringExtra("name"));

        Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatting();
            }
        });
    }

    public void chatting() {
        myChat.append(chatText.getText().toString()+"\n");
    }

}