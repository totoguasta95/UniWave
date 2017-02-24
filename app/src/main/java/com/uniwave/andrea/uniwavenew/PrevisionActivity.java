package com.uniwave.andrea.uniwavenew;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.andrea.uniwavenew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*Copyright [2017] [The Alliance]

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.*/

public class PrevisionActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevision);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {
                case 0:
                    return PlaceholderFragment01.newInstance(position + 1);
                case 1:
                    return PlaceholderFragment02.newInstance(position + 1);
                case 2:
                    return PlaceholderFragment03.newInstance(position + 1);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM");
            String formattedDate01 = df.format(c.getTime());
            try {
                Date date = df.parse(formattedDate01);
                date.setTime(date.getTime() + 86_400_000);
                String formattedDate02 = df.format(date);
                date.setTime(date.getTime() + 86_400_000);
                String formattedDate03 = df.format(date);
                switch (position) {
                    case 0:
                        return formattedDate01;
                    case 1:
                        return formattedDate02;
                    case 2:
                        return formattedDate03;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public static class PlaceholderFragment01 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment01 newInstance(int sectionNumber) {
            PlaceholderFragment01 fragment = new PlaceholderFragment01();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment01() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_prevision, container, false);
            ListView listview = (ListView) rootView.findViewById(R.id.listView);
            ListAdapter listViewAdapter = new SimpleAdapter(
                    getContext(), PrevisionGetter.heightList01,
                    android.R.layout.simple_list_item_2,
                    new String[]{"Data","TipoValore"},
                    new int[]{android.R.id.text1, android.R.id.text2}
            );
            listview.setAdapter(listViewAdapter);
            return rootView;
        }
    }



    public static class PlaceholderFragment02 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment02 newInstance(int sectionNumber) {
            PlaceholderFragment02 fragment = new PlaceholderFragment02();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment02() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_prevision, container, false);
            ListView listview = (ListView) rootView.findViewById(R.id.listView);
            ListAdapter listViewAdapter = new SimpleAdapter(
                    getContext(), PrevisionGetter.heightList02,
                    android.R.layout.simple_list_item_2,
                    new String[]{"Data","TipoValore"},
                    new int[]{android.R.id.text1, android.R.id.text2}
            );
            listview.setAdapter(listViewAdapter);
            return rootView;
        }
    }



    public static class PlaceholderFragment03 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment03 newInstance(int sectionNumber) {
            PlaceholderFragment03 fragment = new PlaceholderFragment03();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment03() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_prevision, container, false);
            ListView listview = (ListView) rootView.findViewById(R.id.listView);
            ListAdapter listViewAdapter = new SimpleAdapter(
                    getContext(), PrevisionGetter.heightList03,
                    android.R.layout.simple_list_item_2,
                    new String[]{"Data","TipoValore"},
                    new int[]{android.R.id.text1, android.R.id.text2}
            );
            listview.setAdapter(listViewAdapter);
            return rootView;
        }
    }
}
