package org.techtown.kakaotalksampling;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FriendprofileActivity extends AppCompatActivity {
    TextView fName;
    TextView fState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendprofile);

        fName=findViewById(R.id.fNameText);
        fState=findViewById(R.id.fstateText);

        Button exitButton = findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        fName.setText(intent.getStringExtra("name"));
        fState.setText(intent.getStringExtra("fstate"));
    }
}