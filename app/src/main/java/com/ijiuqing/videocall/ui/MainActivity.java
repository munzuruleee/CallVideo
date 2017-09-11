package com.ijiuqing.videocall.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ijiuqing.videocall.R;
import com.ijiuqing.videocall.base.BaseActivity;
import com.ijiuqing.videocall.base.BaseFragment;
import com.ijiuqing.videocall.common.Constant;


public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        BaseFragment.OnFragmentInteractionListener {
    PreviewFragment previewFragment;
    CamGirlFragment camGirlFragment;
    UserFragment userFragment;
    Fragment displayFragment;
    FragmentManager mFragmentMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initUIandEvent() {
        previewFragment = new PreviewFragment();
        camGirlFragment = new CamGirlFragment();
        userFragment = new UserFragment();
        if (mFragmentMan == null) {
            mFragmentMan = getSupportFragmentManager();
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        Constant.navigationHeight = navigation.getHeight();
        FragmentTransaction transaction = mFragmentMan.beginTransaction();
        transaction.add(R.id.content, previewFragment).commit();
        displayFragment = previewFragment;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(displayFragment).add(R.id.content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(displayFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            displayFragment = to;
        }
    }

    @Override
    public void onFragmentInteraction(String uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.module1:
                if (previewFragment == null) {
                    previewFragment = new PreviewFragment();
                }
                switchContent(previewFragment);
                break;
            case R.id.module2:
                if (camGirlFragment == null) {
                    camGirlFragment = new CamGirlFragment();
                }
                switchContent(camGirlFragment);
                break;
            case R.id.module3:
                if (userFragment == null) {
                    userFragment = new UserFragment();
                }
                switchContent(userFragment);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗?");
            // 添加选择按钮并注册监听
            isExit.setButton(AlertDialog.BUTTON_POSITIVE, "确定", listener);
            isExit.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", listener);
            // 显示对话框
            isExit.show();
        }

        return false;
    }

    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

}
