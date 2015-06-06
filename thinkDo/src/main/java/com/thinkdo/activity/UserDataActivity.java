package com.thinkdo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.thinkdo.entity.CustomerModel;
import com.thinkdo.fragment.CustomerManageFragment;
import com.thinkdo.fragment.CustomerManageFragment.CustomerDeleteCallback;
import com.thinkdo.fragment.HistoryUserDataFragment;

/**
 * Created by Administrator on 2015/6/2.
 */
public class UserDataActivity extends FragmentActivity implements View.OnClickListener, CustomerDeleteCallback, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_customer_data);
        init();
    }

    private void init() {
        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        final TextView tv_add = (TextView) findViewById(R.id.tv_add);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_add) {
                    tv_add.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(0);
                } else if (checkedId == R.id.radio_history) {
                    tv_add.setVisibility(View.INVISIBLE);
                    viewPager.setCurrentItem(1);
                }
            }
        });
        tv_add.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_add:
                Intent intent = new Intent(this, AddCustomActivity.class);
                startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            viewPager.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.radio_add);
                break;
            case 1:
                radioGroup.check(R.id.radio_history);
                break;
        }
    }

    public void queryClientInfo(String clientId) {
        ((SectionsPagerAdapter) viewPager.getAdapter()).refresh(clientId);
        viewPager.setCurrentItem(1);
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {
        private boolean refresh = false;
        private String refreshValue;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    CustomerManageFragment fragment = new CustomerManageFragment();
                    fragment.setListViewItemClick(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView tv = (TextView) view.findViewById(R.id.id);
                            queryClientInfo(tv.getText().toString());
                        }
                    });
                    return fragment;

                case 1:
                    return new HistoryUserDataFragment();
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 1 && refresh) {
                HistoryUserDataFragment fragment = (HistoryUserDataFragment) super.instantiateItem(container, position);
                fragment.setCustomerId(refreshValue);
                refresh = false;
                return fragment;
            }
            return super.instantiateItem(container, position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 2;
        }

        public void refresh(String value) {
            refresh = true;
            refreshValue = value;
            super.notifyDataSetChanged();
        }

    }

    @Override
    public void edit(int i, CustomerModel data) {
        if (i == 1 && data != null) {
            Intent intent = new Intent(this, AddCustomActivity.class);
            intent.putExtra("CustomerModel", data);
            startActivityForResult(intent, 0);
        }
    }

}
