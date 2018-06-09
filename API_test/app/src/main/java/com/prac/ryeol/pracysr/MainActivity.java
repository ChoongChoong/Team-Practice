package com.prac.ryeol.pracysr;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    /* ViewPager */
    ViewPager vp;
    private int currentPosition = 0;

    /* Dropdown */
    LinearLayout dropdownBody;
    LinearLayout dropdownList;
    LinearLayout dropdownItem;
    TextView currentMenu;
    TextView selectedMenu;
    TextView showingNow;
    TextView toBeShown;
    private String strCurrentMenu = "";

    /* Animation */
    Animation mDropdown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* ViewPager */
        vp = (ViewPager) findViewById(R.id.view_pager);
        // getSupportFragmentManager를 넘겨줘야함
        vp.setAdapter(new BasicFragmentAdapter(getSupportFragmentManager()));
        vp.setClipToPadding(false);
        vp.setPageMargin(50);
        vp.setOffscreenPageLimit(5);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /** Unused **/
            }
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                Log.i("Current Page", ": " + currentPosition);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                /** Unused **/
            }
        });

        /* Dropdown */
        dropdownBody = (LinearLayout) findViewById(R.id.ll_dropdown_body);
        dropdownList = (LinearLayout) findViewById(R.id.ll_dropdown_list);
        dropdownItem = (LinearLayout) findViewById(R.id.ll_dropdown_item);
        // Dropdown animation
        mDropdown = AnimationUtils.loadAnimation(this, R.anim.dropdown);

        currentMenu = (TextView) findViewById(R.id.tv_current_menu);
        selectedMenu = (TextView) findViewById(R.id.tv_selected_menu);
        showingNow = (TextView) findViewById(R.id.tv_showing_now);
        toBeShown = (TextView) findViewById(R.id.tv_to_be_shown);

        dropdownBody.setClickable(true);

    }

    /** ViewPager **/
    class BasicFragmentAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> mFragment = new ArrayList<>();
        private String[] poster = getResources().getStringArray(R.array.movie_poster_arr);
        private String[] title  = getResources().getStringArray(R.array.movie_title_arr);
        private String[] age    = getResources().getStringArray(R.array.movie_age_arr);
        private String[] rate   = getResources().getStringArray(R.array.movie_rate_arr);
        private String[] grade  = getResources().getStringArray(R.array.movie_grade_arr);

        public BasicFragmentAdapter(FragmentManager fm) {
            super(fm);
            for (int i = 0; i < 4; i++) {
                mFragment.add(BasicFragment.newInstance(poster[i], title[i], age[i], rate[i], grade[i]));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    /** Dropdown **/
    /* Dropdown Open */
    public void dropdownOpen(View v) {
        strCurrentMenu = currentMenu.getText().toString();
        selectedMenu.setText(strCurrentMenu);

        if(showingNow.getText().toString() == strCurrentMenu) {
            showingNow.setTextColor(Color.parseColor("#6495ed"));
            toBeShown.setTextColor(Color.parseColor("#FFFFFF"));
        } else if(toBeShown.getText().toString() == strCurrentMenu) {
            showingNow.setTextColor(Color.parseColor("#FFFFFF"));
            toBeShown.setTextColor(Color.parseColor("#6495ed"));
        }

        dropdownBody.setVisibility(View.VISIBLE);
        dropdownItem.startAnimation(mDropdown);
    }

    /* Dropdown Close */
    public void dropdownClose(View v) {
        dropdownBody.setVisibility(View.INVISIBLE);
    }
}