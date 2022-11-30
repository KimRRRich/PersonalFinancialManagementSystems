package edu.hdu.personalfinancialmanagementsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText Account,Password;
    Button Login;
    TextView Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        Account=findViewById(R.id.Login_Account);
        Password=findViewById(R.id.Login_Password);
        Login=findViewById(R.id.Login_Login);
        Title=findViewById(R.id.Login_Title);
        Title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/fonts_1.ttf"));
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountString,passwordString;
                accountString=Account.getText().toString();
                passwordString=Password.getText().toString();
                if(accountString.equals("123")&&passwordString.equals("456")){
                    Intent intent=new Intent(Login.this,MainPage.class);
                    Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}