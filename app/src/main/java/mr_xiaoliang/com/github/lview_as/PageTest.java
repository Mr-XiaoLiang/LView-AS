package mr_xiaoliang.com.github.lview_as;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mr_xiaoliang.com.github.lview_as.option.LTabLineViewOption;
import mr_xiaoliang.com.github.lview_as.view.LPagePointView;
import mr_xiaoliang.com.github.lview_as.view.LTabLineView;
import mr_xiaoliang.com.github.lview_as.view.LTabPointView;
import mr_xiaoliang.com.github.lview_as.view.NoScrollViewPager;

public class PageTest extends FragmentActivity implements OnClickListener {

	private int thisType;
	private NoScrollViewPager viewPager;
	public static final int thisType_bottom = 0;
	public static final int thisType_top = 1;
	public static final int thisType_top_line = 2;
	private View[] viewList = new View[5];
	private int[] colors = {Color.BLACK,Color.BLUE,Color.DKGRAY,Color.GREEN,Color.RED};
	private LPagePointView pointView;
	private LTabPointView tabView;
	private LTabLineView tabLineView;
	private TextView tab0,tab1,tab2,tab3,tab4;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.page_test);
		viewPager = (NoScrollViewPager) findViewById(R.id.viewpage);
		pointView = (LPagePointView) findViewById(R.id.pointview);
		tabView = (LTabPointView) findViewById(R.id.tabview);
		tabLineView = (LTabLineView) findViewById(R.id.tabview2);
		tab0 = (TextView) findViewById(R.id.tab0);
		tab1 = (TextView) findViewById(R.id.tab1);
		tab2 = (TextView) findViewById(R.id.tab2);
		tab3 = (TextView) findViewById(R.id.tab3);
		tab4 = (TextView) findViewById(R.id.tab4);
		tab0.setOnClickListener(this);
		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
		tab4.setOnClickListener(this);
		thisType = getIntent().getIntExtra("type", thisType_bottom);
		tabView.setVisibility(View.GONE);
		pointView.setVisibility(View.GONE);
		tabLineView.setVisibility(View.GONE);
		viewPager.setNoScroll(false);
		viewPager.setOnPageChangeListener(changeListener);
		LinearLayout.LayoutParams point;
		for(int i  = 0;i<viewList.length;i++){
			ImageView image = new ImageView(this);
			point = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			image.setLayoutParams(point);
			image.setBackgroundColor(colors[i]);
			image.setImageResource(R.mipmap.ic_launcher);
			viewList[i] = image;
		}
		switch (thisType) {
		case thisType_bottom:
			pointView.setVisibility(View.VISIBLE);
			pointView.setPointSize(viewList.length);
			break;
		case thisType_top:
			tabView.setVisibility(View.VISIBLE);
			tabView.setTabSize(new int[]{1,2,1,2,1});
			break;
		case thisType_top_line:
			tabLineView.setVisibility(View.VISIBLE);
			LTabLineViewOption lineViewOption = new LTabLineViewOption(new LTabLineViewOption.Builder().setTabWeight(new int[]{1,2,1,2,1}).setTabTextLength(new int[]{2,4,2,4,2}).setTextSize((int) tab0.getTextSize()));
			tabLineView.setOption(lineViewOption);
			break;
		default:
			break;
		}
		viewPager.setAdapter(pagerAdapter);
	}
	private OnPageChangeListener changeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			System.out.println("onPageSelected==>>"+arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			System.out.println("onPageScrolled==>>"+arg0+" , "+arg1+" , "+arg2);
			pointView.onChange(arg0, arg1);
			tabView.onChange(arg0, arg1);
			tabLineView.onChange(arg0, arg1);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			System.out.println("onPageScrollStateChanged==>>"+arg0);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tab0:
				viewPager.setCurrentItem(0, true);
				break;
			case R.id.tab1:
				viewPager.setCurrentItem(1, true);
				break;
			case R.id.tab2:
				viewPager.setCurrentItem(2, true);
				break;
			case R.id.tab3:
				viewPager.setCurrentItem(3, true);
				break;
			case R.id.tab4:
				viewPager.setCurrentItem(4, true);
				break;
		}
	}
	private PagerAdapter pagerAdapter = new PagerAdapter(){

		@Override
		public int getCount() {
			return viewList.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(viewList[position]);
		}

		/**
		 * 载入图片进去
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(viewList[position], 0);
			return viewList[position];
		}

	};

}
