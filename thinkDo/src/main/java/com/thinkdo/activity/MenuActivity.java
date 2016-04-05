package com.thinkdo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.thinkdo.application.MainApplication;
import com.thinkdo.fragment.FixedPositionFragment;
import com.thinkdo.fragment.MaintenanceFragment;
import com.thinkdo.fragment.SettingFragment;
import com.thinkdo.net.NetConnect;
import com.thinkdo.net.NetSingleConnect;
import com.thinkdo.net.SocketClient;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.view.BarItem;

public class MenuActivity extends SlidingFragmentActivity implements OnClickListener {
    private ViewPager viewPager;
    private long time = 0;
    private boolean transFlag = false, loginFlag = false;
    private NetConnect socketClient, loginConnect;
    private final int synch = 9;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (!transFlag) return true;
            switch (msg.what) {
                case -1:
                    if (loginFlag) {
                        loginFlag = false;
                        Toast.makeText(MenuActivity.this, R.string.tip_connect_failed, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    String reply = msg.getData().getString(MainApplication.head);
                    if (reply == null) return true;
                    int backCode = CommonUtil.getQuestCode(reply);
                    if (backCode == MainApplication.loginUrl) {
                        loginFlag = false;
                        if (loginConnect != null) loginConnect.close();
                        String[] data = SocketClient.parseData(reply);
                        if (data != null && data.length == 2) {
                            int i;
                            try {
                                i = Integer.parseInt(data[1]);
                            } catch (NumberFormatException e) {
                                i = 0;
                            }
                            MainApplication.device = data[0];
                            MainApplication.availableDay = i;
                            MainApplication.loginFlag = true;
                            Toast.makeText(MenuActivity.this, R.string.tip_login_success, Toast.LENGTH_SHORT).show();
                            loadLoginInfo();
                            startSynch();
                        }
                    } else deal(backCode);
                    break;
                case synch:
                    socketClient = new NetConnect(handler, MainApplication.synchShowUrl);
                    break;
            }
            return true;
        }
    });


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_menu);
        initSlidingMenu();
        init();
    }

    private void initSlidingMenu() {
        setBehindContentView(R.layout.fragment_drawer);

        SlidingMenu sm = getSlidingMenu();
        sm.setFadeDegree(0.35f);
        sm.setFadeEnabled(true);
        sm.setMode(SlidingMenu.LEFT);
        sm.setTouchModeAbove(SlidingMenu.LEFT);
        sm.setBehindWidthRes(R.dimen.slidingMenu_width);

        LinearLayout user = (LinearLayout) findViewById(R.id.user_info);
        user.setOnClickListener(this);

        BarItem item = (BarItem) findViewById(R.id.barItem_hostSet);
        item.setOnClickListener(this);

//        item = (BarItem) findViewById(R.id.barItem_db);
//        item.setOnClickListener(this);

        item = (BarItem) findViewById(R.id.barItem_about);
        item.setOnClickListener(this);

        item = (BarItem) findViewById(R.id.barItem_register);
        item.setOnClickListener(this);

        Button btn = (Button) findViewById(R.id.btn_exit);
        btn.setOnClickListener(this);
    }

    private void loadLoginInfo() {
        ImageView iv = (ImageView) findViewById(R.id.imageView_user);
        iv.setImageResource(R.drawable.ib_user_login);
        TextView tv = (TextView) findViewById(R.id.tv_day);
        tv.setVisibility(View.VISIBLE);
        tv.setText(String.format("%s: %s", getString(R.string.remaining_days), MainApplication.availableDay));
        tv = (TextView) findViewById(R.id.tv_cdk);
        tv.setText(String.format("%s: %s", getString(R.string.devicesId), MainApplication.device));
    }

    private void init() {
        Button tv = (Button) findViewById(R.id.btn_toggle);
        tv.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0:
                        radioFontColorChange(checkedId);
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radio1:
                        radioFontColorChange(checkedId);
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.radio2:
                        radioFontColorChange(checkedId);
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });

        viewPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.radio0);
                        break;
                    case 1:
                        radioGroup.check(R.id.radio1);
                        break;
                    case 2:
                        radioGroup.check(R.id.radio2);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainApplication.loginFlag) loadLoginInfo();
        transFlag = true;
        startSynch();
    }

    private void startSynch() {
//        socketClient = new NetConnect(handler, MainApplication.synchShowUrl);

        if (MainApplication.availableDay > 0)
            handler.sendEmptyMessageDelayed(synch, 2000);
        else if (!MainApplication.loginFlag) {
            loginFlag = true;
            loginConnect = new NetConnect(handler, MainApplication.loginUrl);
        } else {
            Toast.makeText(MainApplication.context, R.string.tip_recharge, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        transFlag = false;
        if (loginConnect != null) loginConnect.close();
        if (socketClient != null) socketClient.close();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time < 3000) {
            finish();
        } else {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), R.string.tip_exit_press_again, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    private void deal(int position) {
        switch (position) {
            case MainApplication.pushcarUrl:
                redirect(position);
                break;
            case MainApplication.kingpinUrl:
                redirect(position);
                break;
//            case MainApplication.testDataUrl:
//                redirect(position);
//                break;
            case MainApplication.rearShowUrl:
                redirect(position);
                break;
            case MainApplication.frontShowUrl:
                redirect(position);
                break;
            case MainApplication.samplePictureUrl:
                transFlag = false;
                Intent it = new Intent(this, MaintenanceActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(it);
                break;
//            case MainApplication.specialTestUrl:
//                transFlag = false;
//                it = new Intent(this, SpecialTestActivity.class);
//                it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(it);
//                break;
        }
    }

    private void redirect(int position) {
        transFlag = false;
        Intent it = MainApplication.isCar
                ? new Intent(this, MainActivity.class)
                : new Intent(this, MainActivityLorry.class);
        it.putExtra("position", position);
        startActivity(it);
    }

    public void radioFontColorChange(int radioId) {
        RadioButton rb = (RadioButton) findViewById(R.id.radio0);
        rb.setTextColor(getResources().getColor(R.color.font_black));

        rb = (RadioButton) findViewById(R.id.radio1);
        rb.setTextColor(getResources().getColor(R.color.font_black));

        rb = (RadioButton) findViewById(R.id.radio2);
        rb.setTextColor(getResources().getColor(R.color.font_black));

        rb = (RadioButton) findViewById(radioId);
        rb.setTextColor(getResources().getColor(R.color.font_red_holo));
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final int viewPager_size = 3;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FixedPositionFragment();
                case 1:
                    return new SettingFragment();
                case 2:
                    return new MaintenanceFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return viewPager_size;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info:
                if (MainApplication.loginFlag) {
                    Toast.makeText(this, R.string.tip_login_already, Toast.LENGTH_SHORT).show();
                } else {
                    loginFlag = true;
                }

                break;
            case R.id.btn_toggle:
                toggle();
                break;
            case R.id.barItem_hostSet:
                Intent intent = new Intent(this, HostSetActivity.class);
                startActivity(intent);
                break;
//            case R.id.barItem_db:
//                intent = new Intent(this, DBUpdateActivity.class);
//                startActivity(intent);
//                break;
            case R.id.barItem_register:
                if (MainApplication.loginFlag) {
                    intent = new Intent(this, RegisterActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(this, R.string.tip_please_login, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.barItem_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_exit:
                finish();
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            TextView tv = (TextView) findViewById(R.id.tv_day);
            tv.setText(String.format("%s: %s", getString(R.string.remaining_days), MainApplication.availableDay));
        }
    }
}
