package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText Account,Password;
    Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Account=findViewById(R.id.Login_Account);
        Password=findViewById(R.id.Login_Password);
        Login=findViewById(R.id.Login_Login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountString,passwordString;
                accountString=Account.getText().toString();
                passwordString=Password.getText().toString();
                if()
            }
        });
    }

}