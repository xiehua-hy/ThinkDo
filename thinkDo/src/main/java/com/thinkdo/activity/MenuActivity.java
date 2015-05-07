package com.thinkdo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.thinkdo.fragment.FixedPositionFragment;
import com.thinkdo.fragment.MaintenanceFragment;
import com.thinkdo.fragment.SettingFragment;
import com.thinkdo.view.BarItem;

public class MenuActivity extends SlidingFragmentActivity implements OnClickListener {

    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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

        BarItem item = (BarItem) findViewById(R.id.barItem_hostSet);
        item.setOnClickListener(this);

        item = (BarItem) findViewById(R.id.barItem_db);
        item.setOnClickListener(this);

        item = (BarItem) findViewById(R.id.barItem_about);
        item.setOnClickListener(this);

        Button btn = (Button) findViewById(R.id.btn_exit);
        btn.setOnClickListener(this);
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
            case R.id.btn_toggle:
                toggle();
                break;
            case R.id.barItem_hostSet:
                Intent intent = new Intent(this, HostSetActivity.class);
                startActivity(intent);
                break;
            case R.id.barItem_db:
                intent = new Intent(this, DBUpdateActivity.class);
                startActivity(intent);
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
}
