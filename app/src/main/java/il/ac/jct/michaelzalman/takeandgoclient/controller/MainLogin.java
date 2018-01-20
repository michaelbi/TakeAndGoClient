package il.ac.jct.michaelzalman.takeandgoclient.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import il.ac.jct.michaelzalman.takeandgoclient.R;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.DBFactory;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.TakeAndGoConsts;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.Tools;

public class MainLogin extends AppCompatActivity implements View.OnClickListener {


    //-------------Class Arguments----------------//
    private EditText username;
    private EditText password;
    private Button login;
    private TextView register;
    private ContentValues userPassword;
    private CheckBox rememberMe;

    //------------BackGround Process Arguments------------//
    private backgroundProcess<Void, Void, Void> loginBgp;
    private backgroundProcess.backgroundProcessActions<Void, Void, Void> loginAction;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        loginAction = new backgroundProcess.backgroundProcessActions<Void, Void, Void>() {
            private String error;

            @Override
            public Void doInBackground() {
                try {
                    error = null;

                    if ((!sharedPreferences.contains(TakeAndGoConsts.sharePrefConsts.USER_NAME)) ||
                            username.getText().toString().compareTo(sharedPreferences.getString(TakeAndGoConsts.sharePrefConsts.USER_NAME, null)) != 0 ||
                            password.getText().toString().compareTo(sharedPreferences.getString(TakeAndGoConsts.sharePrefConsts.PASSWORD, null)) != 0
                            ) {

                        if (!Tools.isInternetConnected(MainLogin.this)) {
                            error = "אין חיבור נתונים\nהתחבר ונסה שנית";
                            return null;
                        }
                        DBFactory.getIdbManager().isClientExist(userPassword);
                        DBFactory.getIdbManager().loginCheck(userPassword);

                        editor.putString(sharedPreferences.getString(TakeAndGoConsts.sharePrefConsts.USER_NAME, null), username.getText().toString());
                        editor.putString(sharedPreferences.getString(TakeAndGoConsts.sharePrefConsts.PASSWORD, null), password.getText().toString());
                    } else {
                        if (
                                username.getText().toString().compareTo(sharedPreferences.getString(TakeAndGoConsts.sharePrefConsts.USER_NAME, null)) != 0 ||
                                        password.getText().toString().compareTo(sharedPreferences.getString(TakeAndGoConsts.sharePrefConsts.PASSWORD, null)) != 0) {
                            error = "שם משתמש או סיסמא שגויים";
                        }
                    }

                    if (rememberMe.isChecked()) {
                        editor.putString(TakeAndGoConsts.sharePrefConsts.REMEMBER_ME, "TRUE");
                    } else if (sharedPreferences.contains(TakeAndGoConsts.sharePrefConsts.REMEMBER_ME)) {
                        editor.remove(TakeAndGoConsts.sharePrefConsts.REMEMBER_ME);
                    }
                    editor.commit();

                } catch (Exception e) {
                    error = e.getMessage();
                }

                return null;
            }

            @Override
            public void onPostExecute(Void aVoid) {

                if (null == error) {
                    Intent intent = new Intent(MainLogin.this, MainActivity.class);
                    intent.putExtra("_id", username.getText().toString());
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(MainLogin.this, error, Toast.LENGTH_LONG).show();

            }
        };

        findViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPreferences.contains(TakeAndGoConsts.sharePrefConsts.USER_NAME) &&
                sharedPreferences.contains(TakeAndGoConsts.sharePrefConsts.REMEMBER_ME)) {
            username.setText(sharedPreferences.getString(TakeAndGoConsts.sharePrefConsts.USER_NAME, null));
            password.setText(sharedPreferences.getString(TakeAndGoConsts.sharePrefConsts.PASSWORD, null));
            rememberMe.setChecked(true);
        }
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-17 19:32:49 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        rememberMe = (CheckBox) findViewById(R.id.login_remember_me);

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
        if (v == login) {
            userPassword = new ContentValues();
            userPassword.put("_id", username.getText().toString());
            userPassword.put("password", password.getText().toString());

            if (loginBgp == null || loginBgp.getStatus() != AsyncTask.Status.RUNNING) {
                loginBgp = new backgroundProcess<>(loginAction);
                loginBgp.execute();
            } else
                Toast.makeText(this, "תהליך בפעולה", Toast.LENGTH_LONG).show();
        }
        if (v == register) {
            register.setTextColor(Color.parseColor("#0B0080"));
            Intent intent = new Intent(this, AddClientActivity.class);
            startActivity(intent);
        }
    }

}
