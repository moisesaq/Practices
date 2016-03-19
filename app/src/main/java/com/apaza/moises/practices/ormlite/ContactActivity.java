package com.apaza.moises.practices.ormlite;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.apaza.moises.practices.R;

public class ContactActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, ActionBar.TabListener{

	private ActionBar actionBar;
	private ViewPager viewPager;
	private TabPageAdapter tabPageAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		setupTab();
	}

	private void setupTab() {
		viewPager = (ViewPager)findViewById(R.id.pager);
		actionBar = getSupportActionBar();
		tabPageAdapter = new TabPageAdapter(getSupportFragmentManager());
		
		viewPager.setAdapter(tabPageAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab tab1 = actionBar.newTab().setText("New");
		ActionBar.Tab tab2 = actionBar.newTab().setText("List");
		tab1.setTabListener(this);
		tab2.setTabListener(this);
		
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		
		viewPager.setOnPageChangeListener(this);
		/*Fragment frag1 = new NewContactFragment();
		Fragment frag2 = new ListContactFragment();*/
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	//-------------------METODOS VIEW PAGER LISTENER---------------------
	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixel) {
	}

	@Override
	public void onPageSelected(int position) {
		actionBar.setSelectedNavigationItem(position);
		
	}

	//-------------------METODOS TAB LISTENER---------------------
	@Override
	public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

	}
}
