package me.tsengem.parstagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class LogOutActivity extends AppCompatActivity {

    private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        logOutButton = findViewById(R.id.logout_btn);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogOut();
            }
        });
    }

    public void userLogOut() {
        Log.d("Logout", "in userLogOut");
        ParseUser.logOut();
        Intent intent = new Intent(LogOutActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
