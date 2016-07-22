package mr_xiaoliang.com.github.lview_as.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.option.LLoadView2Option;
import mr_xiaoliang.com.github.lview_as.option.LLoadView3Option;
import mr_xiaoliang.com.github.lview_as.view.LLoadView2;
import mr_xiaoliang.com.github.lview_as.view.LLoadView3;

public class Load3Dialog extends Dialog {

	private LLoadView3 load3;
	private int showType = LLoadView3Option.SHOW_TYPE_ALL;
	private LLoadView3Option o;

	public Load3Dialog(Context context) {
		super(context);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除屏幕title
		setContentView(R.layout.dialog_load3_all);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		load3 = (LLoadView3) findViewById(R.id.load3);
		if(o==null){
			LLoadView3Option.Builder b = new LLoadView3Option.Builder();
			b.setValues("小良★◆▼")
					.setTextSize(100)
					.setColors(Color.RED,Color.BLUE)
					.setShadowColors(Color.RED,Color.BLUE)
					.setShowType(showType)
					.setTypefaces(Typeface.DEFAULT,Typeface.SERIF,Typeface.SANS_SERIF);
			o = new LLoadView3Option(b);
		}
		load3.setOption(o);
	}

	public Load3Dialog(Context context, int showType) {
		super(context);
		this.showType = showType;
	}

	public Load3Dialog(Context context, LLoadView3Option o) {
		super(context);
		this.o = o;
	}
}
