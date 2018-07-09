package calculadora.com.calculadora;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class registerActivity extends AppCompatActivity {
    Button reg;
    TextInputLayout user,ps1,ps2,tname,tlastname, tuniversity,ttelf;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        realm = Realm.getDefaultInstance();

        reg = findViewById(R.id.buttonreg);

        user = findViewById(R.id.reguser);
        ps1 = findViewById(R.id.regpass1);
        ps2 = findViewById(R.id.regpass2);
        tname = findViewById(R.id.textInputname);
        tlastname = findViewById(R.id.textInputlastname);
        tuniversity = findViewById(R.id.textInputuniversity);
        ttelf = findViewById(R.id.textInputtelf);
        //email = findViewById(R.id.textInputuniversity);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getEditText().getText().toString().trim();
                String pass1 = ps1.getEditText().getText().toString().trim();
                String pass2 = ps2.getEditText().getText().toString().trim();
                String name = tname.getEditText().getText().toString().trim();
                String lastname = tlastname.getEditText().getText().toString().trim();
                String university = tuniversity.getEditText().getText().toString().trim();
                String phone = ttelf.getEditText().getText().toString().trim();
                //String email = ps2.getEditText().getText().toString().trim();

                if (validatereg(username,name,lastname,pass1,pass2,university,phone)) {
                    User user = new User(username, name, lastname, pass1,university,phone);
                    if (saveUserToRealm(user)) {
                        //NOTIFICACIO CONFORME REG CORRECTE O USUARI JA EN US
                        Intent regok = new Intent(getApplicationContext(), log_activity.class);
                        startActivity(regok);
                        //finishActivty
                    } else {

                    }
                }
            }
        });
    }

    public boolean validatereg(String u, String n,String ln, String pass1,String pass2, String uni,String tlf) {
        if(u.isEmpty() ) Toast.makeText(getApplicationContext(), "Username can not be blank", Toast.LENGTH_LONG).show();
        else if (ln.isEmpty()) Toast.makeText(getApplicationContext(), "Lastname can not be blank", Toast.LENGTH_LONG).show();
        else if (n.isEmpty() ) Toast.makeText(getApplicationContext(), "Name can not be blank", Toast.LENGTH_LONG).show();
        else if (pass1.isEmpty() ) Toast.makeText(getApplicationContext(), "Password can not be blank", Toast.LENGTH_LONG).show();
        else if (pass2.isEmpty() ) Toast.makeText(getApplicationContext(), "Password can not be blank", Toast.LENGTH_LONG).show();
        else if (pass1.length() < 6) Toast.makeText(getApplicationContext(),"Password can not contain less than 6 characters", Toast.LENGTH_LONG).show();
        else if(!Objects.equals(pass1,pass2)) Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
        else if (!Objects.equals(realm.where(User.class).equalTo("Username",u).findAll().size(),0)) Toast.makeText(getApplicationContext(), "This Username already exists", Toast.LENGTH_LONG).show();

        else return true;
        return false;
    }
    private boolean saveUserToRealm(User user) {
        final RealmResults<User> us = realm.where(User.class).equalTo("Username",user.getUsername()).findAll();
        if (Objects.equals(us.size(),0) ) {
            realm.beginTransaction();
            realm.copyToRealm(user);
            realm.commitTransaction();
            return true;
        }
        else {
            return false;
        }
    }
    private void savePuntuationToRealm(Puntuation grade) {
        realm.beginTransaction();
        realm.copyToRealm(grade);
        realm.commitTransaction();
    }
}
