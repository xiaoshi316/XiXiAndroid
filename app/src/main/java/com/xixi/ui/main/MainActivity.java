package com.xixi.ui.main;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.xixi.R;
import com.xixi.ui.magpie.NewMagpieActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    PagerSlidingTabStrip tabs;

    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    MenuItem menuAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init fragment
        fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentMagpie());
        fragmentList.add(new FragmentDiscover());
        fragmentList.add(new FragmentMe());
        fragmentList.add(new FragmentCircle());

        // init pager
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setCurrentItem(0);

        // init PagerSlidingTabStrip
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setTextSize((int)(tabs.getTextSize()*1.5));
        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(new MainOnPageChangeListener());
    }


    /**
     * Change option menu according to current fragment
     */
    private class MainOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int state) {}

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                menuAdd.setVisible(true);
            } else {
                menuAdd.setVisible(false);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuAdd = menu.findItem(R.id.action_new_magpie);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new_magpie) {
            Intent intent = new Intent(MainActivity.this, NewMagpieActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
