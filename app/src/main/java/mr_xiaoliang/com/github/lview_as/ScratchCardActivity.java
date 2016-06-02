package mr_xiaoliang.com.github.lview_as;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import mr_xiaoliang.com.github.lview_as.option.LScratchCardOption;
import mr_xiaoliang.com.github.lview_as.view.LScratchCard;

/**
 * 刮刮卡测试页面
 */
public class ScratchCardActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,Switch.OnCheckedChangeListener,View.OnClickListener {

    private LScratchCardOption.Builder builder;
    private LScratchCard scratchCard;
    private TextView mulchText,valueText;
    private Button mulchBtn,valueBtn;
    private SeekBar mulchTextSizeBar,valueTextSizeBar,touchWidthBar,autoCleanSizeBar,roundRadiusBar;
    private Switch transparentBg,autoClean;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_scratch_card);
        res = getResources();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("刮刮卡");
        toolbar.setBackgroundColor(res.getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scratchCard = (LScratchCard) findViewById(R.id.scratchcard);
        mulchText = (TextView) findViewById(R.id.mulch_text);
        valueText = (TextView) findViewById(R.id.value_text);
        mulchBtn = (Button) findViewById(R.id.mulch_btn);
        valueBtn = (Button) findViewById(R.id.value_btn);
        mulchTextSizeBar = (SeekBar) findViewById(R.id.mulch_size);
        valueTextSizeBar = (SeekBar) findViewById(R.id.value_size);
        touchWidthBar = (SeekBar) findViewById(R.id.touch_width);
        autoCleanSizeBar = (SeekBar) findViewById(R.id.auto_clean_size);
        roundRadiusBar = (SeekBar) findViewById(R.id.round_radius);
        transparentBg = (Switch) findViewById(R.id.transparent_bg);
        autoClean = (Switch) findViewById(R.id.auto_clean);
        builder = new LScratchCardOption.Builder();
        builder.setBackgroundColor(Color.GREEN)
                .setBackgroundImg(BitmapFactory.decodeResource(res,R.mipmap.ic_background))
                .setBackgroundImgScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRoundRadius(20)
                .setValueImg(BitmapFactory.decodeResource(res,R.mipmap.ic_liang))
                .setValueImgScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setText("恭喜你获得一等奖")
                .setTextColor(Color.RED,Color.BLUE,Color.CYAN)
                .setMulchColor(Color.GRAY)
                .setMulchImg(BitmapFactory.decodeResource(res,R.mipmap.ic_launcher))
                .setMulchImgScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setMulchText("刮开图层有奖")
                .setMulchTextColor(Color.BLACK,Color.BLUE);
        scratchCard.setOption(new LScratchCardOption(builder));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onClick(View v) {

    }
}
