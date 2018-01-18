package il.ac.jct.michaelzalman.takeandgoclient.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import il.ac.jct.michaelzalman.takeandgoclient.R;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.DBFactory;

public class MainLogin extends AppCompatActivity implements View.OnClickListener{


    private EditText username;
    private EditText password;
    private Button login;
    private TextView register;
    private ContentValues userPassword;

    private backgroundProcess<Void,Void,Void> loginBgp;
    private backgroundProcess.backgroundProcessActions<Void,Void,Void> loginAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        loginAction = new backgroundProcess.backgroundProcessActions<Void, Void, Void>() {
            private String error;

            @Override
            public Void doInBackground() {
                try {
                    error = null;
                    DBFactory.getIdbManager().isClientExist(userPassword);
                    DBFactory.getIdbManager().loginCheck(userPassword);
                }
                catch (Exception e)
                {
                    error = e.getMessage();
                }

                return null;
            }

            @Override
            public void onPostExecute(Void aVoid) {

                if(null == error)
                {
                    Intent intent = new Intent(MainLogin.this,MainActivity.class);
                    intent.putExtra("_id",username.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(MainLogin.this, error, Toast.LENGTH_LONG).show();

            }
        };

        findViews();
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-17 19:32:49 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        username = (EditText)findViewById( R.id.username );
        password = (EditText)findViewById( R.id.password );
        login = (Button)findViewById( R.id.login );
        register = (TextView)findViewById( R.id.register );

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-01-17 19:32:49 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == login ) {
            userPassword = new ContentValues();
            userPassword.put("_id", username.getText().toString());
            userPassword.put("password",password.getText().toString());

            if(loginBgp == null || loginBgp.getStatus() != AsyncTask.Status.RUNNING) {
                loginBgp = new backgroundProcess<>(loginAction);
                loginBgp.execute();
            }
            else
                Toast.makeText(this,"תהליך בפעולה",Toast.LENGTH_LONG).show();
        }
        if( v == register)
        {
            register.setTextColor(Color.parseColor("#0B0080"));
            Intent intent = new Intent(this,AddClientActivity.class);
            startActivity(intent);
        }
    }

}
