package mr_xiaoliang.com.github.lview_as;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import mr_xiaoliang.com.github.lview_as.view.LChooseBgView;

public class ChooseBgTest extends Activity {

	private LChooseBgView c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose);
		c = (LChooseBgView) findViewById(R.id.c);
		Intent intent = getIntent();
		c.setStyle(intent.getBooleanExtra("type", true));
		c.setLeftName("左边");
		c.setRightName("右边");
		c.setChooseBgViewListener(new LChooseBgView.ChooseBgViewListener() {
			@Override
			public void onChooseViewClick(boolean isLeft) {
				Toast.makeText(ChooseBgTest.this, isLeft?"左":"右", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
