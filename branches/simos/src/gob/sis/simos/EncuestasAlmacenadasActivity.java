package gob.sis.simos;

import gob.sis.simos.fragment.ListaEncuestasAlmacenadasFragment;
import roboguice.activity.RoboFragmentActivity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class EncuestasAlmacenadasActivity extends RoboFragmentActivity implements ViewPager.OnPageChangeListener, ActionBar.TabListener {

	private ListaEncuestasAlmacenadasFragment fgEncuestas_all;
	private ListaEncuestasAlmacenadasFragment fgEncuestas_sent;
	private ListaEncuestasAlmacenadasFragment fgEncuestas_unsent;
	
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvt_lst_strd_encts);
		
		this.actionBar = getActionBar();
		this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		this.actionBar.setDisplayUseLogoEnabled(false);
		this.actionBar.setIcon(R.drawable.ic_menu_mark);
		
		this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		
		this.mViewPager = (ViewPager)findViewById(R.id.pager);
		this.mViewPager.setAdapter(this.mSectionsPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);
		
		for(int x = 0; x < this.mSectionsPagerAdapter.getCount() ; x++){
			actionBar.addTab(actionBar.newTab()
					.setText(this.mSectionsPagerAdapter.getPageTitle(x))
					.setTabListener(this));
		}
	}
	
	

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Nothing to do here
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Nothing to do here
	}

	@Override
	public void onPageSelected(int position) {
		this.actionBar.setSelectedNavigationItem(position);
	}
	
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			if(position == 0){
				fgEncuestas_all = new ListaEncuestasAlmacenadasFragment();
				return fgEncuestas_all;
			} else if (position == 1){
				fgEncuestas_sent = new ListaEncuestasAlmacenadasFragment();
				return fgEncuestas_sent;
			} else if (position == 2){
				fgEncuestas_unsent = new ListaEncuestasAlmacenadasFragment();
				return fgEncuestas_unsent;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.inquests_all);
			case 1:
				return getString(R.string.inquests_sent);
			case 2:
				return getString(R.string.inquests_unsent);
			}
			return null;
		}
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}



	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		this.mViewPager.setCurrentItem(tab.getPosition());
	}



	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}
}
