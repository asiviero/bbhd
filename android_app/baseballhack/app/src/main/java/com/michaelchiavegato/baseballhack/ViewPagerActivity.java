package com.michaelchiavegato.baseballhack;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.ViewPager;

import org.json.JSONException;
import org.json.JSONObject;


public class ViewPagerActivity extends FragmentActivity {

    private ViewPager mViewPager;

    private String[] questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new QuestionPagerAdapter(getSupportFragmentManager()));
    }

    public class QuestionPagerAdapter extends FragmentPagerAdapter {


        public QuestionPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int pos) {
            switch(pos) {
                case 9:
                    try {

                        return ScoreboardFragment.newInstance(MainActivity.Quiz.getJSONObject(8));
                    }
                    catch (JSONException e) {

                    }
                    break;
                default:
                    System.out.println(pos);
                    try {
                        return ViewPageFragment.newInstance(MainActivity.Quiz.getJSONObject(pos), pos);
                    }
                    catch (JSONException e) {

                    }
                    break;

            }
            return ScoreboardFragment.newInstance(new JSONObject());
        }

        @Override
        public int getCount() {
            return 10;
        }

    }

}
