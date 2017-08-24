package com.ijiuqing.videocall.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseActivity;


public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        Blank1Fragment.OnFragmentInteractionListener,
        Blank2Fragment.OnFragmentInteractionListener,
        Blank3Fragment.OnFragmentInteractionListener {
    Blank1Fragment blank1Fragment;
    Blank2Fragment blank2Fragment;
    Blank3Fragment blank3Fragment;
    Fragment displayFragment;
    FragmentManager mFragmentMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initUIandEvent() {
        blank1Fragment = new Blank1Fragment();
        blank2Fragment = new Blank2Fragment();
        blank3Fragment = new Blank3Fragment();
        if (mFragmentMan == null) {
            mFragmentMan = getSupportFragmentManager();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        FragmentTransaction transaction = mFragmentMan.beginTransaction();
        transaction.add(R.id.content, blank1Fragment).commit();
        displayFragment = blank1Fragment;
    }

    @Override
    protected void deInitUIandEvent() {
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                forwardToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void forwardToSettings() {
        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(i);
    }

    public void switchContent(Fragment to) {
        if (displayFragment != to) {
            FragmentTransaction transaction = mFragmentMan.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
            transaction.replace(R.id.content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            displayFragment = to;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.module1:
                if (blank1Fragment == null) {
                    blank1Fragment = new Blank1Fragment();
                }
                switchContent(blank1Fragment);
                break;
            case R.id.module2:
                if (blank2Fragment == null) {
                    blank2Fragment = new Blank2Fragment();
                }
                switchContent(blank2Fragment);
                break;
            case R.id.module3:
                if (blank3Fragment == null) {
                    blank3Fragment = new Blank3Fragment();
                }
                switchContent(blank3Fragment);
                break;
            default:
                break;
        }
        return true;
    }
}
