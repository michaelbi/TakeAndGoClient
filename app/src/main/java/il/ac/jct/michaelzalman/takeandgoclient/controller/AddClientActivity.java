package il.ac.jct.michaelzalman.takeandgoclient.controller;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import il.ac.jct.michaelzalman.takeandgoclient.R;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.DBFactory;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.TakeAndGoConsts;
import il.ac.jct.michaelzalman.takeandgoclient.model.backend.Tools;

public class AddClientActivity extends AppCompatActivity implements View.OnClickListener {

    //-----------------Class Arguments--------------------//
    private EditText FirstName;
    private EditText LastName;
    private EditText CreditCard;
    private EditText EmailAddress;
    private EditText PhoneNumber;
    private EditText ID;
    private Button Add;
    private EditText Password;

    //-----------------AsyncTask Variables----------------//
    private backgroundProcess<Void, Void, Boolean> addClientProcess;
    private backgroundProcess.backgroundProcessActions<Void, Void, Boolean> actions;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        //find all views and set them to the class arguments
        findViews();

        //initialise backgroundProcess Interface
        actions = new backgroundProcess.backgroundProcessActions<Void, Void, Boolean>() {
            private String error=null;

            @Override
            public Boolean doInBackground() {

                try {
                    return addClient();
                } catch (Exception e) {
                    error=e.getMessage();
                    return false;
                }
            }

            @Override
            public void onPostExecute(Boolean aVoid) {
                progressDialog.dismiss();

                if (aVoid)
                    Toast.makeText(AddClientActivity.this, R.string.confirm_client_add, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddClientActivity.this, error, Toast.LENGTH_LONG).show();
            }
        };

        //define ProgressDialog Param
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("מוסיף איש קשר");
        progressDialog.setTitle("בתהליך");

        //set progressDialog cancel Button
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "בטל", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(addClientProcess!=null && addClientProcess.getStatus()== AsyncTask.Status.RUNNING)
                {
                    addClientProcess.cancel(true);
                }

                progressDialog.dismiss();
            }
        });
    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-11-14 20:04:49 by Android Layout Finder
     */
    private void findViews() {

        FirstName = (EditText) findViewById(R.id.FirstName);
        LastName = (EditText) findViewById(R.id.LastName);
        CreditCard = (EditText) findViewById(R.id.CreditCard);
        EmailAddress = (EditText) findViewById(R.id.EmailAddress);
        PhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        ID = (EditText) findViewById(R.id.ID);
        Add = (Button) findViewById(R.id.Add);
        Password = (EditText) findViewById(R.id.Password);

        Add.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-11-14 20:04:49 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == Add ) {
            if (emptyBoxes()) {
                Toast.makeText(this, R.string.error_empty_filleds, Toast.LENGTH_LONG).show();
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(EmailAddress.getText().toString()).matches()) {
                Toast.makeText(this, R.string.error_email_ileagal, Toast.LENGTH_LONG).show();
            }
            else if(!Patterns.PHONE.matcher(PhoneNumber.getText().toString()).matches()) {
                Toast.makeText(this, R.string.error_phonenumber, Toast.LENGTH_LONG).show();
            }
            else if (!checkId()) {
                Toast.makeText(this, R.string.error_id, Toast.LENGTH_LONG).show();
            }
            else if(!Tools.isInternetConnectedToast(this)){}
            else {
                if (addClientProcess == null || addClientProcess.getStatus() != AsyncTask.Status.RUNNING) {
                    addClientProcess = new backgroundProcess(actions);
                    progressDialog.show();
                    addClientProcess.execute();

                } else
                    Toast.makeText(this, "Process is Running", Toast.LENGTH_LONG).show();

            }
        }
    }

    /**
     * Function to Add Client
     *
     * @return  Boolean represents successful adding of Client
     */
    private boolean addClient() throws Exception {

        // Handle clicks for Add
        ContentValues content = new ContentValues();
        content.put(TakeAndGoConsts.ClientConst.FIRST_NAME, FirstName.getText().toString());
        content.put(TakeAndGoConsts.ClientConst.LAST_NAME, LastName.getText().toString());
        content.put(TakeAndGoConsts.ClientConst.ID, ID.getText().toString());
        content.put(TakeAndGoConsts.ClientConst.PHONE_NUMBER, PhoneNumber.getText().toString());
        content.put(TakeAndGoConsts.ClientConst.EMAIL, EmailAddress.getText().toString());
        content.put(TakeAndGoConsts.ClientConst.CREDIT_CARD, CreditCard.getText().toString());
        content.put(TakeAndGoConsts.ClientConst.PASSWORD, Password.getText().toString());


        DBFactory.getIdbManager().addClient(content);

        return true;
    }

    /**
     * Check Validity of ID Number
     *
     * @return  Boolean represents correction of ID
     */
    private boolean checkId() {
        int sum = 0;
        int id;

        try
        {
            id = Integer.parseInt(ID.getText().toString());
        }
        catch (Exception e)
        {
            return false;
        }

        if (id >= 1000000000)
            return false;

        for (int i = 1; id > 0; i = (i % 2) + 1) {
            int digit = (id % 10) * i;
            sum += digit / 10 + digit % 10;

            id=id/10;
        }

        if (sum % 10 != 0)
            return false;

        return true;
    }

    /**
     * Check if All Fields were filled
     *
     * @return  Boolean represents status of empty fields
     */
    private boolean emptyBoxes() {
        return (FirstName.getText().toString().isEmpty() || LastName.getText().toString().isEmpty() ||
                ID.getText().toString().isEmpty() || PhoneNumber.getText().toString().isEmpty() ||
                EmailAddress.getText().toString().isEmpty() || CreditCard.getText().toString().isEmpty() ||
                Password.getText().toString().isEmpty());
    }
}