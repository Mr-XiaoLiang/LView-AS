package mr_xiaoliang.com.github.lview_as.view;

import java.util.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import mr_xiaoliang.com.github.lview_as.R;

public class LCalendarView extends View {
	/**
	 * 年份
	 */
	private int year;
	/**
	 * 月份
	 */
	private int month;
	/**
	 * 日期数组 9组，代表了8个方向所有可能滑动的方向
	 */
	private int[] days, leftTopDays, rightTopDays, leftBottomDays, rightBottomDays, leftDays, topDays, rightDays,
			bottomDays;
	/**
	 * 今天
	 */
	private int today = 0, thisYear = 0, thisMonth = 0;
	/**
	 * 选中日期
	 */
	private int selectedDay = 0;
	/**
	 * 字体大小
	 */
	private float textSize;
	/**
	 * 字体中心点
	 */
	private float textY;
	/**
	 * 选中颜色
	 */
	private int selectColor;
	/**
	 * 数字颜色
	 */
	private int textColor;
	/**
	 * 星期颜色
	 */
	private int weekColor;
	/**
	 * 背景色
	 */
	private int backgroundColor;
	/**
	 * 今天颜色
	 */
	private int todayColor;;
	/**
	 * 文字的画笔
	 */
	private Paint textPaint;
	/**
	 * 点的画笔
	 */
	private Paint pointPaint;
	/**
	 * 今天画笔
	 */
	private Paint todayPaint;
	/**
	 * 背景笔
	 */
	private Paint backgroundPaint;
	/**
	 * 日历计算类
	 */
	private Calendar getDaysCalendar = Calendar.getInstance();
	/**
	 * 星期
	 */
	private String[] weeks = { "日", "一", "二", "三", "四", "五", "六" };
	/**
	 * 单元格的宽度
	 */
	int width = 0;
	/**
	 * 单元格的高度
	 */
	int height = 0;
	/**
	 * 日期选择监听器
	 */
	private CalendarViewListener calendarViewListener;
	/**
	 * 偏移量
	 */
	private int offsetX = 0, offsetY = 0;
	/**
	 * 按下位置
	 */
	private float downX, downY;
	/**
	 * 手指是否抬起
	 */
	private boolean isTouchUp = true;
	/**
	 * 速度
	 */
	private float speed = 0.8F;
	/**
	 * 滑动类型
	 * @author LiuJ
	 *
	 */
	public enum SlideType{
		Vertical,
		Horizontal,
		Both
	}
	/**
	 * 当前滑动类型
	 */
	private SlideType slideType = SlideType.Both;
	/**
	 * 起始年
	 */
	private int startYear = -1;
	/**
	 * 起始月
	 */
	private int startMonth = -1;
	/**
	 * 起始天
	 */
	private int startDay = -1;
	public interface CalendarViewListener {
		public void calendarSelected(int year, int month, int d);

		public void calendarChange(int year, int month);
	}

	public LCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		days = new int[42];
		year = 2015;
		month = 9;
		today = 0;
		textSize = 0;
		textY = 0;
		selectColor = Color.parseColor("#99c9f2");
		textColor = Color.BLACK;
		weekColor = Color.GRAY;
		backgroundColor = Color.parseColor("#50EEEEEE");
		todayColor = Color.parseColor("#50000000");
		/**
		 * 获得我们所定义的自定义样式属性
		 */
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.calendarview, defStyleAttr, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.calendarview_background_color:
				backgroundColor = a.getColor(attr, Color.parseColor("#ebebeb"));
				break;
			case R.styleable.calendarview_month:
				month = a.getInt(attr, 0);
				break;
			case R.styleable.calendarview_selected:
				selectedDay = a.getInt(attr, 0);
				break;
			case R.styleable.calendarview_selected_color:
				selectColor = a.getColor(attr, Color.parseColor("#99c9f2"));
				break;
			case R.styleable.calendarview_today:
				today = a.getInt(attr, 0);
				break;
			case R.styleable.calendarview_text_color:
				textColor = a.getColor(attr, Color.BLACK);
				break;
			case R.styleable.calendarview_today_color:
				todayColor = a.getColor(attr, Color.parseColor("#50000000"));
				break;
			case R.styleable.calendarview_weeks_color:
				weekColor = a.getColor(attr, Color.GRAY);
				break;
			case R.styleable.calendarview_year:
				year = a.getColor(attr, 0);
				break;
			}
		}
		a.recycle();
		init();
		initPaint();
	}

	public LCalendarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LCalendarView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		width = getWidth() / 7;
		height = getHeight() / 7;
		if (textSize == 0) {
			textSize = height * 0.5f;
			textPaint.setTextSize(textSize);
		}
		// 绘制当月
		drawText(canvas, days, offsetX, offsetY, year, month);
		// 绘制其他月，选择性绘制，避免浪费
		if (offsetX > 0) {
			if (leftDays == null)
				leftDays = getDays(year, month - 1);
			drawText(canvas, leftDays, offsetX - getWidth(), offsetY, year, month - 1);
			if (offsetY > 0) {
				if (leftTopDays == null)
					leftDays = getDays(year - 1, month - 1);
				drawText(canvas, leftTopDays, offsetX - getWidth(), offsetY - getHeight(), year - 1, month - 1);
			}
			if (offsetY < 0) {
				if (leftBottomDays == null)
					leftBottomDays = getDays(year + 1, month - 1);
				drawText(canvas, leftBottomDays, offsetX - getWidth(), offsetY + getHeight(), year + 1, month - 1);
			}
		}
		if (offsetX < 0) {
			if (rightDays == null)
				rightDays = getDays(year, month + 1);
			drawText(canvas, rightDays, offsetX + getWidth(), offsetY, year, month + 1);
			if (offsetY > 0) {
				if (rightTopDays == null)
					rightTopDays = getDays(year - 1, month + 1);
				drawText(canvas, rightTopDays, offsetX + getWidth(), offsetY - getHeight(), year - 1, month + 1);
			}
			if (offsetY < 0) {
				if (rightBottomDays == null)
					rightBottomDays = getDays(year + 1, month + 1);
				drawText(canvas, rightBottomDays, offsetX + getWidth(), offsetY + getHeight(), year + 1, month + 1);
			}
		}
		if (offsetY > 0) {
			if (topDays == null)
				topDays = getDays(year - 1, month);
			drawText(canvas, topDays, offsetX, offsetY - getHeight(), year - 1, month);
		}
		if (offsetY < 0) {
			if (bottomDays == null)
				bottomDays = getDays(year + 1, month);
			drawText(canvas, bottomDays, offsetX, offsetY + getHeight(), year + 1, month);
		}

		if (isTouchUp) {
//			if (Math.abs(offsetX) > getWidth() / 2) {
//				if (offsetX > 0) {
//					offsetX -= getWidth();
//					month--;
//					if (month < 1) {
//						month = 12;
//						year--;
//					}
//				} else {
//					offsetX += getWidth();
//					month++;
//					if (month > 12) {
//						month = 1;
//						year++;
//					}
//				}
//				if (calendarViewListener != null) {
//					calendarViewListener.calendarChange(year, month);
//				}
//				getDay(0);
//			}
//			if (Math.abs(offsetY) > getHeight() / 2) {
//				if (offsetY > 0) {
//					offsetY -= getHeight();
//					year--;
//				} else {
//					offsetY += getHeight();
//					year++;
//				}
//				if (calendarViewListener != null) {
//					calendarViewListener.calendarChange(year, month);
//				}
//				getDay(0);
//			}
			offsetX *= speed;
			offsetY *= speed;
			if (Math.abs(offsetX) < 1) {
				offsetX = 0;
				invalidate();
			}
			if (Math.abs(offsetY) < 1) {
				offsetY = 0;
				invalidate();
			}
			if (Math.abs(offsetX) > 1 || Math.abs(offsetY) > 1) {
				invalidate();
			} else {
				leftTopDays = null;
				rightTopDays = null;
				leftBottomDays = null;
				rightBottomDays = null;
				leftDays = null;
				topDays = null;
				rightDays = null;
				bottomDays = null;
			}
		}
		// super.onDraw(canvas);
	}
	/**
	 * 获取对应的日历数组
	 * @param i 对应T9键盘的位置
	 * 1 2 3
	 * 4 5 6
	 * 7 8 9
	 */
	private void getDay(int i) {
		switch (i) {
		case 1:
			leftTopDays = getDays(year - 1, month - 1);
			break;
		case 2:
			switch (slideType) {
			case Both:
				topDays = getDays(year - 1, month);
				break;
			case Vertical:
				topDays = getDays(year, month - 1);
				break;
			}
			break;
		case 3:
			rightTopDays = getDays(year - 1, month + 1);
			break;
		case 4:
			leftDays = getDays(year, month - 1);
			break;
		case 5:
			days = getDays(year, month);
			break;
		case 6:
			rightDays = getDays(year, month + 1);
			break;
		case 7:
			leftBottomDays = getDays(year + 1, month - 1);
			break;
		case 8:
			switch (slideType) {
			case Both:
				bottomDays = getDays(year + 1, month);
				break;
			case Vertical:
				bottomDays = getDays(year, month + 1);
				break;
			}
			break;
		case 9:
			rightBottomDays = getDays(year + 1, month + 1);
			break;
		default:
			days = getDays(year, month);
			if (offsetX > 0) {
				leftDays = getDays(year, month - 1);
				if (offsetY > 0) {
					leftTopDays = getDays(year - 1, month - 1);
				}
				if (offsetY < 0) {
					leftBottomDays = getDays(year + 1, month - 1);
				}
			}
			if (offsetX < 0) {
				rightDays = getDays(year, month + 1);
				if (offsetY > 0) {
					rightTopDays = getDays(year - 1, month + 1);
				}
				if (offsetY < 0) {
					rightBottomDays = getDays(year + 1, month + 1);
				}
			}
			if (offsetY > 0) {
				switch (slideType) {
				case Both:
					topDays = getDays(year - 1, month);
					break;
				case Vertical:
					topDays = getDays(year, month - 1);
					break;
				}
			}
			if (offsetY < 0) {
				switch (slideType) {
				case Both:
					bottomDays = getDays(year + 1, month);
					break;
				case Vertical:
					bottomDays = getDays(year, month + 1);
					break;
				}
			}
			break;
		}
	}

	private void drawText(Canvas canvas, int[] daysArray, int xOffset, int yOffset, int y, int m) {
		if (daysArray == null) {
			return;
		}
		if(xOffset!=0||yOffset!=0){
			if((y+m)%2>0){
				backgroundPaint.setColor(backgroundColor);
			}else{
				backgroundPaint.setColor(Color.WHITE);
			}
			RectF rect = new RectF(xOffset, yOffset, getWidth()+xOffset, getHeight()+yOffset);
			canvas.drawRect(rect, backgroundPaint);
		}
		
		FontMetrics fm = textPaint.getFontMetrics();
		textY = height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
		textPaint.setColor(weekColor);
		for (int j = 0; j < 7; j++) {
			canvas.drawText(weeks[j], (width * j) + width / 2 + xOffset, textY + yOffset, textPaint);
		}
		textPaint.setColor(textColor);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (daysArray[i * 7 + j] == 0) {
					continue;
				}
				if (today != 0 && daysArray[i * 7 + j] == today && thisYear == y && thisMonth == m) {
					canvas.drawCircle((width * j) + width / 2 + xOffset, (height * (i + 1)) + height / 2 + yOffset,
							textSize * 1f, todayPaint);
				}
				if (selectedDay != 0 && daysArray[i * 7 + j] == selectedDay && year == y && month == m) {
					canvas.drawCircle((width * j) + width / 2 + xOffset, (height * (i + 1)) + height / 2 + yOffset,
							textSize * 1f, pointPaint);
				}
				canvas.drawText(daysArray[i * 7 + j] + "", (width * j) + width / 2 + xOffset,
						(height * (i + 1)) + textY + yOffset, textPaint);
			}
		}
	}

	/**
	 * 获取指定月份的日历数组
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int[] getDays(int year, int month) {
		int[] daysArray = new int[42];
		getDaysCalendar.set(Calendar.YEAR, year);
		getDaysCalendar.set(Calendar.MONTH, month - 1);
		getDaysCalendar.set(Calendar.DAY_OF_MONTH, getDaysCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		int start = getDaysCalendar.get(Calendar.DAY_OF_WEEK) - 1;
		int end = getDaysCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int day = 1;
			if(year<startYear||(year==startYear&&month<startMonth)){
				return null;
			}
		if(year==startYear&&month==startMonth){
			for (int i = 0; i < days.length; i++) {
				if (i < start || day > end) {
					daysArray[i] = 0;
				} else {
					if(day>=startDay){
						daysArray[i] = day;
					}
					day++;
				}
			}
			return daysArray;
		}
		for (int i = 0; i < days.length; i++) {
			if (i < start || day > end) {
				daysArray[i] = 0;
			} else {
				daysArray[i] = day;
				day++;
			}
		}
		return daysArray;
	}

	private void init() {
		Calendar calendar = Calendar.getInstance();
		if (year == 0) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == 0) {
			month = calendar.get(Calendar.MONTH);
		}
		days = getDays(year, month);
		invalidate();
	}

	private void initPaint(){
		textPaint = new Paint();
		textPaint.setTextSize(textSize);
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Align.CENTER);
		pointPaint = new Paint();
		pointPaint.setColor(selectColor);
		pointPaint.setAntiAlias(true);
		todayPaint = new Paint();
		todayPaint.setColor(todayColor);
		todayPaint.setAntiAlias(true);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(backgroundColor);
		backgroundPaint.setAntiAlias(true);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = 0;
		int y = 0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = (int) event.getX() / width;
			y = (int) event.getY() / height;
			/**
			 * 判断是不是在我们的日历里面
			 */
			if (y >= 1 && x >= 0 && y < 7 && x < 7 && days[((y - 1) * 7) + x] != 0) {
				selectedDay = days[((y - 1) * 7) + x];
			}
			// 按下位置
			downX = event.getX();
			downY = event.getY();
			isTouchUp = false;
			break;
		case MotionEvent.ACTION_MOVE:
			switch (slideType) {
			case Vertical:
				offsetY = (int) (event.getY() - downY);
				break;
			case Horizontal:
				offsetX = (int) (event.getX() - downX);
				break;
			case Both:
				offsetX = (int) (event.getX() - downX);
				offsetY = (int) (event.getY() - downY);
				break;
			}
			if (Math.abs(offsetX) > getWidth() / 2) {
				if (offsetX > 0) {
					if(year>startYear||(year==startYear&&month>startMonth)){
						offsetX -= getWidth();
						downX += getWidth();
						month--;
						if (month < 1) {
							month = 12;
							year--;
						}
					}
				} else {
					offsetX += getWidth();
					downX -= getWidth();
					month++;
					if (month > 12) {
						month = 1;
						year++;
					}
				}
				getDay(0);
				if (calendarViewListener != null) {
					calendarViewListener.calendarChange(year, month);
				}
			}
			if (Math.abs(offsetY) > getHeight() / 2) {
				if (offsetY > 0) {
					if(year>startYear||(year==startYear&&month>startMonth)){
						offsetY -= getHeight();
						downY += getHeight();
						switch (slideType) {
						case Vertical:
							month--;
							if (month < 1) {
								month = 12;
								year--;
							}
							break;
						case Both:
							year--;
							if(year<startYear){
								year++;
							}
							break;
						}
					}
				} else {
					offsetY += getHeight();
					downY -= getHeight();
					switch (slideType) {
					case Vertical:
						month++;
						if (month > 12) {
							month = 1;
							year++;
						}
						break;
					case Both:
						year++;
						break;
					}
				}
				getDay(0);
				if (calendarViewListener != null) {
					calendarViewListener.calendarChange(year, month);
				}
			}

			break;
		case MotionEvent.ACTION_UP:
			isTouchUp = true;
			break;
		}
		if (calendarViewListener != null) {
			calendarViewListener.calendarSelected(year,month,selectedDay);
		}
		invalidate();
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = (int) (textSize * 11) + getPaddingLeft() + getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = (int) (textSize * 11) + getPaddingTop() + getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	/**
	 * 获取年份
	 *
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 设置年份
	 *
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
		init();
	}

	/**
	 * 获取月份
	 *
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * 设置月份
	 *
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month;
		init();
	}

	/**
	 * 获取今天
	 *
	 * @return
	 */
	public int getToday() {
		return today;
	}

	/**
	 * 设置今天
	 *
	 * @param today
	 */
	public void setToday(int today, int year, int month) {
		this.today = today;
		this.thisYear = year;
		this.thisMonth = month;
		init();
	}

	/**
	 * 获取选中日期
	 *
	 * @return
	 */
	public int getSelectedDay() {
		return selectedDay;
	}

	/**
	 * 设置选中日期
	 *
	 * @param selectedDay
	 */
	public void setSelectedDay(int selectedDay) {
		this.selectedDay = selectedDay;
		init();
	}

	/**
	 * 获取字体大小
	 *
	 * @return
	 */
	public float getTextSize() {
		return textSize;
	}

	/**
	 * 设置字体大小
	 *
	 * @param textSize
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
		initPaint();
		init();
	}

	/**
	 * 获取选中背景色
	 *
	 * @return
	 */
	public int getSelectColor() {
		return selectColor;
	}

	/**
	 * 设置选中背景色
	 *
	 * @param selectColor
	 */
	public void setSelectColor(int selectColor) {
		this.selectColor = selectColor;
		initPaint();
		init();
	}

	/**
	 * 获取字体颜色
	 *
	 * @return
	 */
	public int getTextColor() {
		return textColor;
	}

	/**
	 * 设置字体颜色
	 *
	 * @param textColor
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
		initPaint();
		init();
	}

	/**
	 * 获取星期颜色
	 *
	 * @return
	 */
	public int getWeekColor() {
		return weekColor;
	}

	/**
	 * 设置星期颜色
	 *
	 * @param weekColor
	 */
	public void setWeekColor(int weekColor) {
		this.weekColor = weekColor;
		initPaint();
		init();
	}

	/**
	 * \ 获取背景色
	 *
	 * @return
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * 设置背景颜色
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
		initPaint();
		init();
	}

	/**
	 * 获取今天背景色
	 *
	 * @return
	 */
	public int getTodayColor() {
		return todayColor;
	}

	/**
	 * 设置今天背景色
	 *
	 * @param todayColor
	 */
	public void setTodayColor(int todayColor) {
		this.todayColor = todayColor;
		initPaint();
		init();
	}

	/**
	 * 获取日历选择监听器
	 *
	 * @return
	 */
	public CalendarViewListener getCalendarViewListener() {
		return calendarViewListener;
	}

	/**
	 * 设置日历选择监听器
	 *
	 * @param calendarViewListener
	 */
	public void setCalendarViewListener(CalendarViewListener calendarViewListener) {
		this.calendarViewListener = calendarViewListener;
	}

	/**
	 * 更新日期
	 *
	 * @param year
	 * @param month
	 * @param today
	 * @param selected
	 */
	public void setData(int year, int month, int today, int selected, int thisyear, int thismonth) {
		this.year = year;
		this.month = month;
		this.today = today;
		this.selectedDay = selected;
		this.thisYear = thisyear;
		this.thisMonth = thismonth;
		init();
	}

	/**
	 * 设置可选项 传入可选项的范围
	 * 
	 * @param min
	 * @param max
	 */
	public void setItems(int min, int max) {
		if (max < min) {
			max = min;
		}
		int[] items = new int[days.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = 0;
		}
		for (int i = min; i <= max; i++) {
			B: for (int j = 0; j < days.length; j++) {
				if (days[j] == i) {
					items[j] = i;
					break B;
				}
			}
		}
		days = items;
		invalidate();
	}

	/**
	 * 设置可选项
	 * 
	 * @param items
	 *            传入可选项的数组
	 */
	public void setItems(int[] item) {
		int[] items = new int[days.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = 0;
		}
		for (int i = 0; i < item.length; i++) {
			B: for (int j = 0; j < days.length; j++) {
				if (days[j] == item[i]) {
					items[j] = item[i];
					break B;
				}
			}
		}
		days = items;
		invalidate();
	}

	public SlideType getSlideType() {
		return slideType;
	}

	public void setSlideType(SlideType slideType) {
		this.slideType = slideType;
	}
/**
 * 设置起始日期
 * @param startYear
 * @param startMonth
 * @param startDay
 */
	public void setStart(int startYear, int startMonth, int startDay) {
		this.startYear = startYear;
		this.startMonth = startMonth;
		this.startDay = startDay;
		getDay(0);
		invalidate();
	}
}
