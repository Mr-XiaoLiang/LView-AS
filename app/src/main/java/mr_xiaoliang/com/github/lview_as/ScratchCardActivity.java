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
import mr_xiaoliang.com.github.lview_as.view.LScratchCardView;

/**
 * 刮刮卡测试页面
 */
public class ScratchCardActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,Switch.OnCheckedChangeListener,View.OnClickListener {

    private LScratchCardOption.Builder builder;
    private LScratchCardView scratchCard;
    private TextView mulchText,valueText;
    private Button mulchBtn,valueBtn,mulchTextSizeBarBtn,valueTextSizeBarBtn,touchWidthBarBtn,autoCleanSizeBarBtn,roundRadiusBarBtn;
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
        scratchCard = (LScratchCardView) findViewById(R.id.scratchcard);
        mulchText = (TextView) findViewById(R.id.mulch_text);
        valueText = (TextView) findViewById(R.id.value_text);
        mulchBtn = (Button) findViewById(R.id.mulch_btn);
        valueBtn = (Button) findViewById(R.id.value_btn);
        mulchTextSizeBar = (SeekBar) findViewById(R.id.mulch_size);
        valueTextSizeBar = (SeekBar) findViewById(R.id.value_size);
        touchWidthBar = (SeekBar) findViewById(R.id.touch_width);
        autoCleanSizeBar = (SeekBar) findViewById(R.id.auto_clean_size);
        roundRadiusBar = (SeekBar) findViewById(R.id.round_radius);
        mulchTextSizeBarBtn = (Button) findViewById(R.id.mulch_size_btn);
        valueTextSizeBarBtn = (Button) findViewById(R.id.value_size_btn);
        touchWidthBarBtn = (Button) findViewById(R.id.touch_width_btn);
        autoCleanSizeBarBtn = (Button) findViewById(R.id.auto_clean_size_btn);
        roundRadiusBarBtn = (Button) findViewById(R.id.round_radius_btn);
        transparentBg = (Switch) findViewById(R.id.transparent_bg);
        autoClean = (Switch) findViewById(R.id.auto_clean);
        mulchBtn.setOnClickListener(this);
        valueBtn.setOnClickListener(this);
        mulchTextSizeBarBtn.setOnClickListener(this);
        valueTextSizeBarBtn.setOnClickListener(this);
        touchWidthBarBtn.setOnClickListener(this);
        autoCleanSizeBarBtn.setOnClickListener(this);
        roundRadiusBarBtn.setOnClickListener(this);
        mulchTextSizeBar.setOnSeekBarChangeListener(this);
        valueTextSizeBar.setOnSeekBarChangeListener(this);
        touchWidthBar.setOnSeekBarChangeListener(this);
        autoCleanSizeBar.setOnSeekBarChangeListener(this);
        roundRadiusBar.setOnSeekBarChangeListener(this);
        transparentBg.setOnCheckedChangeListener(this);
        autoClean.setOnCheckedChangeListener(this);

        builder = new LScratchCardOption.Builder();
        builder.setBackgroundColor(Color.parseColor("#00E3E3"),Color.parseColor("#6FB7B7"),Color.parseColor("#84C1FF"))
                .setBackgroundColorAngle(30)
                .setMulchColorAngle(90)
                .setBackgroundImg(BitmapFactory.decodeResource(res,R.mipmap.ic_background))
                .setBackgroundImgScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRoundRadius(20)
                .setValueImg(BitmapFactory.decodeResource(res,R.mipmap.ic_liang))
                .setValueImgScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setText(valueText.getText().toString())
                .setTextColor(Color.BLUE,Color.RED,Color.CYAN)
                .setMulchColor(Color.BLACK,Color.WHITE,Color.GRAY,Color.WHITE,Color.BLACK)
                .setMulchImg(BitmapFactory.decodeResource(res,R.mipmap.ic_launcher))
                .setMulchImgScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setMulchText(mulchText.getText().toString())
                .setMulchTextColor(Color.parseColor("#FF5809"),Color.parseColor("#B15BFF"))
                .setMulchTextSize(mulchTextSizeBar.getProgress()*3)
                .setRoundRadius(roundRadiusBar.getProgress()*3)
                .setTouchWdth(touchWidthBar.getProgress()*3);
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
//        switch (seekBar.getId()){
//            case R.id.mulch_size:
//                builder.setMulchTextSize(seekBar.getProgress());
//                break;
//            case R.id.value_size:
//                builder.setTextSize(seekBar.getProgress());
//                break;
//            case R.id.touch_width:
//                builder.setTouchWdth(seekBar.getProgress());
//                break;
//            case R.id.auto_clean_size:
//                builder.setAutoClean(seekBar.getProgress()*1.0f/100);
//                break;
//            case R.id.round_radius:
//                builder.setRoundRadius(seekBar.getProgress());
//                break;
//        }
//        scratchCard.setOption(new LScratchCardOption(builder));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.transparent_bg:
                builder.setTransparentBg(isChecked);
                break;
            case R.id.auto_clean:
                builder.setAutoCleanEnable(isChecked);
                break;
        }
        scratchCard.setOption(new LScratchCardOption(builder));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mulch_btn:
                builder.setMulchText(mulchText.getText().toString());
                break;
            case R.id.value_btn:
                builder.setText(valueText.getText().toString());
                break;
            case R.id.mulch_size_btn:
                builder.setMulchTextSize(mulchTextSizeBar.getProgress()*3);
                break;
            case R.id.value_size_btn:
                builder.setTextSize(valueTextSizeBar.getProgress()*3);
                break;
            case R.id.touch_width_btn:
                builder.setTouchWdth(touchWidthBar.getProgress()*3);
                break;
            case R.id.auto_clean_size_btn:
                builder.setAutoClean(autoCleanSizeBar.getProgress()*1.0f/100);
                break;
            case R.id.round_radius_btn:
                builder.setRoundRadius(roundRadiusBar.getProgress());
                break;
        }
        scratchCard.setOption(new LScratchCardOption(builder));
    }
}
