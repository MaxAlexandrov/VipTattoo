package ru.akisterev.theviptatu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import ru.akisterev.theviptatu.MainFragments.Base64EncodeDecode;
import ru.akisterev.theviptatu.MainFragments.FragmentMainEnrollMaster;
import ru.akisterev.theviptatu.MainFragments.FragmentMainFeedback;
import ru.akisterev.theviptatu.MainFragments.FragmentMainGallery;
import ru.akisterev.theviptatu.MainFragments.FragmentMainMain;
import ru.akisterev.theviptatu.MainFragments.FragmentMainNews;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentMainMain fragmentMainMain;
    FragmentMainNews fragmentMainNews;
    FragmentMainFeedback fragmentMainFeedback;
    FragmentMainEnrollMaster fragmentMainEnrollMaster;
    FragmentMainGallery fragmentMainGallery;

    FragmentManager fragmentManager;
    FloatingActionButton fab;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test base64
        //Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_send_white);
        //String s = new Base64EncodeDecode().getBase64ToBitmapJPEG(b);
        //System.out.println(s);

        //Bitmap gb = new Base64EncodeDecode().getBitmapToBase64(s.toString());
        //System.out.println(gb);
        //ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        //imageView.setImageBitmap(gb);
        //end test base64
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fragmentMainMain = new FragmentMainMain();
        fragmentMainNews = new FragmentMainNews();
        fragmentMainFeedback = new FragmentMainFeedback();
        fragmentMainEnrollMaster = new FragmentMainEnrollMaster();
        fragmentMainGallery = new FragmentMainGallery();

        fragmentManager = getFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putString("aaa", "fggg");
        fragmentMainFeedback.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragmentMainMain)
                .commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTitle(getString(R.string.label_feedback));
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragmentMainFeedback)
                        .commit();
                fab.hide();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_main:
                setTitle(getString(R.string.app_name));
                fragment = fragmentMainMain;
                fab.show();
                break;
            case R.id.nav_news:
                setTitle(getString(R.string.label_news));
                fragment = fragmentMainNews;
                fab.show();
                break;
            case R.id.nav_feedback:
                setTitle(getString(R.string.label_feedback));
                fragment = fragmentMainFeedback;
                fab.hide();
                break;
            case R.id.nav_enroll_master:
                setTitle(getString(R.string.label_enroll_master));
                fragment = fragmentMainEnrollMaster;
                fab.show();
                break;
            case R.id.nav_gallery:
                setTitle(getString(R.string.label_gallery));
                fragment = fragmentMainGallery;
                fab.show();
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ru.akisterev.theviptatu/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ru.akisterev.theviptatu/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
