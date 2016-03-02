package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import mr_xiaoliang.com.github.lview_as.R;

public class LoginBgView extends View {

	private int[] colors = { Color.parseColor("#26abb4"),
			Color.parseColor("#29a4b4"), Color.parseColor("#2b9db5"),
			Color.parseColor("#3097b6"), Color.parseColor("#3492b8"),
			Color.parseColor("#3a8bb8"), Color.parseColor("#4182b8"),
			Color.parseColor("#477fb8"), Color.parseColor("#4d79ba"),
			Color.parseColor("#406db0"), Color.parseColor("#406db0") };
	/**
	 * 画笔
	 */
	private Paint paint;
	/**
	 * 宽度
	 */
	private int width;
	/**
	 * 高度
	 */
	private int height;
	/**
	 * 行高
	 */
	private int lineHeight;
	/**
	 * 透明度
	 */
	private int alpha = 255;
	/**
	 * 是否显示logo
	 */
	private boolean isShowLogo = false;
	/**
	 * 是否开始
	 */
	private boolean isStart = false;
	/**
	 * 当视图结束动画
	 */
	private OnViewEnd end;
	public LoginBgView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setAlpha(alpha);
	}

	public LoginBgView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoginBgView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		lineHeight = height / 10;
		paint.setAlpha(alpha);

		for (int i = 0; i < colors.length; i++) {
			paint.setColor(colors[i]);
			canvas.drawRect(0, i * lineHeight, width, (i + 1) * lineHeight,
					paint);
		}
		if (isShowLogo) {
			Bitmap roundBitmap = BitmapFactory.decodeResource(getResources(),
					R.mipmap.ic_launcher);
			canvas.drawBitmap(roundBitmap, width / 2 - roundBitmap.getWidth()/2,
					height / 2 - roundBitmap.getHeight()/2, paint);
		}
//		if(isStart&&alpha>0){
//			alpha--;
//			invalidate();
//		}
//		if(alpha == 0){
//			if(end!=null){
//				end.theViewIsEnd();
//			}
//		}
	}

	public void startView(){
		isStart = true;
		invalidate();
	}
	public void setShowLogo(boolean isShowLogo) {
		this.isShowLogo = isShowLogo;
		invalidate();
	}
	public interface OnViewEnd{
		public void theViewIsEnd();
	}
	public void setOnViewEnd(OnViewEnd end) {
		this.end = end;
	}

	public void setViewAlpha(int alpha) {
		this.alpha = alpha;
		invalidate();
	}
}
