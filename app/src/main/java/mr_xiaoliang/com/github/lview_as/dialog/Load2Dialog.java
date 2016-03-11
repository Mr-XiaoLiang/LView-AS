package mr_xiaoliang.com.github.lview_as.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.option.LLoadView2Option;
import mr_xiaoliang.com.github.lview_as.view.LLoadView2;

public class Load2Dialog extends Dialog {

	private LLoadView2 load2;

	public Load2Dialog(Context context) {
		super(context);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除屏幕title
		setContentView(R.layout.dialog_load2);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		load2 = (LLoadView2) findViewById(R.id.load2);
		load2.setOption(new LLoadView2Option(new LLoadView2Option.Builder()));
	}
}
