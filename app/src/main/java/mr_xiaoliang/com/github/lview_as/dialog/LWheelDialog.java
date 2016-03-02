package mr_xiaoliang.com.github.lview_as.dialog;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.option.LWheelViewOption;
import mr_xiaoliang.com.github.lview_as.view.LWheelView;

public class LWheelDialog extends Dialog implements LWheelView.LWheelViewListener,View.OnClickListener {
	/**
	 * 类型
	 */
	private LWheelDialogType type = LWheelDialogType.ALL;
	/**
	 * 回掉监听
	 */
	private LWheelView.LWheelViewListener listener = null;
	/**
	 * 外壳
	 */
	private LinearLayout layout1, layout2, layout3, layout4, layout5, layout6;
	/**
	 * 滚轮
	 */
	private LWheelView wheel1, wheel2, wheel3, wheel4, wheel5, wheel6;
	/**
	 * 显示
	 */
	private String year, month, day, hour, minute, seconds;

	private View esc,enter;
	
	public enum LWheelDialogType {
		ALL, TIME, DATE
	}

	/**
	 * 数据类型
	 * 
	 * @author Administrator
	 *
	 */
	private enum DataType {
		YEAR, MONTH, DAY, HOUR, MINUTE, SECONDS
	}

	/**
	 * 日历
	 */
	private Calendar calendar = Calendar.getInstance();

	public LWheelDialog(Context context, LWheelDialogType type, LWheelView.LWheelViewListener listener) {
		super(context);
		this.type = type;
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除屏幕title
		setContentView(R.layout.wheel);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		layout1 = (LinearLayout) findViewById(R.id.year_l);
		layout2 = (LinearLayout) findViewById(R.id.month_l);
		layout3 = (LinearLayout) findViewById(R.id.day_l);
		layout4 = (LinearLayout) findViewById(R.id.hour_l);
		layout5 = (LinearLayout) findViewById(R.id.minute_l);
		layout6 = (LinearLayout) findViewById(R.id.seconds_l);
		wheel1 = (LWheelView) findViewById(R.id.year);
		wheel2 = (LWheelView) findViewById(R.id.month);
		wheel3 = (LWheelView) findViewById(R.id.day);
		wheel4 = (LWheelView) findViewById(R.id.hour);
		wheel5 = (LWheelView) findViewById(R.id.minute);
		wheel6 = (LWheelView) findViewById(R.id.seconds);
		esc = findViewById(R.id.wheel_esc);
		enter = findViewById(R.id.wheel_enter);
		esc.setOnClickListener(this);
		enter.setOnClickListener(this);
		initWheel();
	}

	private void initWheel() {
		LWheelViewOption option = new LWheelViewOption(new LWheelViewOption.Builder().setCycle(true));
		LWheelViewOption option2 = 
				new LWheelViewOption(
						new LWheelViewOption.Builder()
						.setCycle(false));
		wheel1.setOption(option2);
		wheel2.setOption(option2);
		wheel3.setOption(option);
		wheel4.setOption(option2);
		wheel5.setOption(option);
		wheel6.setOption(option);
		wheel1.setListener(this);
		wheel2.setListener(this);
		wheel3.setListener(this);
		wheel4.setListener(this);
		wheel5.setListener(this);
		wheel6.setListener(this);
		switch (type) {
		case ALL:
			initDate();
			initTime();
			break;
		case DATE:
			initDate();
			layout4.setVisibility(View.GONE);
			layout5.setVisibility(View.GONE);
			layout6.setVisibility(View.GONE);
			break;
		case TIME:
			initTime();
			layout1.setVisibility(View.GONE);
			layout2.setVisibility(View.GONE);
			layout3.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
	
	private void initDate(){
		year = calendar.get(Calendar.YEAR) + "";
		changeData(DataType.YEAR);
		wheel1.selected(calendar.get(Calendar.YEAR) - 1980);
		month = calendar.get(Calendar.MONTH) + 1 + "";
		changeData(DataType.MONTH);
		wheel2.selected(calendar.get(Calendar.MONTH));
		day = calendar.get(Calendar.DAY_OF_MONTH) + "";
		changeData(DataType.DAY);
		wheel3.selected(Integer.parseInt(day)-1);
	}
	
	private void initTime(){
		changeData(DataType.HOUR);
		wheel4.selected(calendar.get(Calendar.HOUR_OF_DAY));
		changeData(DataType.MINUTE);
		wheel5.selected(calendar.get(Calendar.MINUTE));
		changeData(DataType.SECONDS);
		wheel6.selected(calendar.get(Calendar.SECOND));
	}

	private void changeData(DataType dt) {
		ArrayList<String> list = new ArrayList<String>();
		switch (dt) {
		case YEAR:
			for (int i = 0; i < 50; i++) {
				list.add(i + 1980 + "");
			}
			wheel1.setData(list);
			break;
		case MONTH:
			for (int i = 0; i < 12; i++) {
				list.add(i + 1 + "");
			}
			wheel2.setData(list);
			break;
		case DAY:
			for (int i = 0; i < getDaysByYearMonth(calendar, Integer.parseInt(year), Integer.parseInt(month)); i++) {
				list.add(i + 1 + "");
			}
			wheel3.setData(list);
			break;
		case HOUR:
			for (int i = 0; i < 24; i++) {
				list.add(i + "");
			}
			wheel4.setData(list);
			break;
		case MINUTE:
			for (int i = 0; i < 60; i++) {
				list.add(i + "");
			}
			wheel5.setData(list);
			break;
		case SECONDS:
			for (int i = 0; i < 60; i++) {
				list.add(i + "");
			}
			wheel6.setData(list);
			break;
		}
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByYearMonth(Calendar a, int year, int month) {
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	@Override
	public void onLWheelViewChange(LWheelView view, String value, int position) {
		if (listener != null) {
			listener.onLWheelViewChange(view, value, position);
		}
		switch (view.getId()) {
		case R.id.year:
			this.year = value;
			break;
		case R.id.month:
			this.month = value;
			changeData(DataType.DAY);
			break;
		case R.id.day:
			this.day = value;
			break;
		case R.id.hour:
			this.hour = value;
			break;
		case R.id.minute:
			this.minute = value;
			break;
		case R.id.seconds:
			this.seconds = value;
			break;
		}
//		switch (type) {
//		case ALL:
//			Toast.makeText(getContext(), year + "年" + month + "月" + day + "日 " + hour + ":" + minute + ":" + seconds,
//					Toast.LENGTH_SHORT).show();
//			break;
//		case DATE:
//			Toast.makeText(getContext(), year + "年" + month + "月" + day + "日", Toast.LENGTH_SHORT).show();
//			break;
//		case TIME:
//			Toast.makeText(getContext(), hour + ":" + minute + ":" + seconds, Toast.LENGTH_SHORT).show();
//			break;
//		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wheel_enter:
			switch (type) {
			case ALL:
				Toast.makeText(getContext(), year + "年" + month + "月" + day + "日 " + hour + ":" + minute + ":" + seconds,
						Toast.LENGTH_SHORT).show();
				break;
			case DATE:
				Toast.makeText(getContext(), year + "年" + month + "月" + day + "日", Toast.LENGTH_SHORT).show();
				break;
			case TIME:
				Toast.makeText(getContext(), hour + ":" + minute + ":" + seconds, Toast.LENGTH_SHORT).show();
				break;
			}
			dismiss();
			break;
		case R.id.wheel_esc:
			dismiss();
			break;
		default:
			break;
		}
	}
	
}
