package ru.akisterev.theviptatu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PostDetailActivity extends AppCompatActivity {
    EditText userEMail, userTextSend;
    FloatingActionButton fab;
    InputMethodManager inm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView titile_detail = (TextView) findViewById(R.id.title_post_detail);
        TextView text_detail = (TextView) findViewById(R.id.text_post_detail);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        titile_detail.setText(title);
        text_detail.setText(text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
