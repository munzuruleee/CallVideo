package com.ijiuqing.videocall.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseActivity;
import com.ijiuqing.videocall.common.ConstantApp;
import com.ijiuqing.videocall.ui.view.VideoProfileAdapter;

public class SettingsActivity extends BaseActivity {
    private VideoProfileAdapter mVideoProfileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void initUIandEvent() {
        RecyclerView v_profiles = (RecyclerView) findViewById(R.id.profiles);
        v_profiles.setHasFixedSize(true);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int prefIndex = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_PROFILE_IDX, ConstantApp.DEFAULT_PROFILE_IDX);
        mVideoProfileAdapter = new VideoProfileAdapter(this, prefIndex);
        mVideoProfileAdapter.setHasStableIds(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        v_profiles.setLayoutManager(layoutManager);
        v_profiles.setAdapter(mVideoProfileAdapter);
    }

    @Override
    protected void deInitUIandEvent() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.confirm:
                doSaveProfile();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doSaveProfile() {
        int profileIndex = mVideoProfileAdapter.getSelected();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(ConstantApp.PrefManager.PREF_PROPERTY_PROFILE_IDX, profileIndex);
        editor.apply();
    }
}
