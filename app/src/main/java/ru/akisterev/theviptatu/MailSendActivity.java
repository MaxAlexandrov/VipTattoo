package ru.akisterev.theviptatu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MailSendActivity extends AppCompatActivity {
    EditText userEMail, userTextSend;
    FloatingActionButton fab;
    InputMethodManager inm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plant/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getText(R.string.e_mail).toString()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "сообщение от "+userEMail.getText());
                intent.putExtra(Intent.EXTRA_TEXT, userTextSend.getText());

                try{
                    startActivity(Intent.createChooser(intent, "Отправить с помощью..."));
                }catch(android.content.ActivityNotFoundException ex){
                    Toast.makeText(MailSendActivity.this, "Отправка письма не получилась...", Toast.LENGTH_SHORT).show();
                }

                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.mail_send_end), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastContainer = (LinearLayout)toast.getView();
                ImageView imgView = new ImageView(getApplicationContext());
                imgView.setImageResource(R.drawable.ic_action_send_white);
                toastContainer.addView(imgView, 0);
                toast.show();

                finish();
            }
        });

        TextInputLayout tilUserEMail = (TextInputLayout) findViewById(R.id.eMailLayout);
        userEMail = (EditText) tilUserEMail.findViewById(R.id.etEMailUser);
        tilUserEMail.setHint(getString(R.string.label_user_e_mail));

        userEMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showButSend();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //принудительно открываем клавиатуру, при открытии активити
        //inm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        //inm.showSoftInputFromInputMethod(userEMail, InputMethodManager.SHOW_FORCED);
        userEMail.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        TextInputLayout tilTextSend = (TextInputLayout) findViewById(R.id.sendTextLayout);
        userTextSend = (EditText) tilTextSend.findViewById(R.id.etSendText);
        tilTextSend.setHint(getString(R.string.label_send_text));

        userTextSend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showButSend();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showButSend(){
        if(!userEMail.getText().toString().equals("") && !userTextSend.getText().toString().equals("")){
            if(!fab.isShown()){
                fab.show();
            }
        }else{
            if(fab.isShown()){
                fab.hide();
            }
        }
    }
}
