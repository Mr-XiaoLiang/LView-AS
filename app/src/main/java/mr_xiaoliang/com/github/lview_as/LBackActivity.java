package mr_xiaoliang.com.github.lview_as;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import mr_xiaoliang.com.github.lview_as.view.LBackDrawable;

public class LBackActivity extends AppCompatActivity implements CheckBox.OnCheckedChangeListener,RadioGroup.OnCheckedChangeListener,SeekBar.OnSeekBarChangeListener {

    private LBackDrawable imgDraw,barDraw;
    private DrawerArrowDrawable arrowDrawable1,arrowDrawable2;
    private boolean auto = false;
    private int pro = 100;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.lback_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((CheckBox)findViewById(R.id.lback_check)).setOnCheckedChangeListener(this);
        ((CheckBox)findViewById(R.id.lback_auto)).setOnCheckedChangeListener(this);
        ((RadioGroup)findViewById(R.id.lback_radiogroup)).setOnCheckedChangeListener(this);
        seekBar = ((SeekBar)findViewById(R.id.lback_seek));
        seekBar.setOnSeekBarChangeListener(this);
        imgDraw = new LBackDrawable(this);
        barDraw = new LBackDrawable(this);
        imgDraw.setColor(Color.BLACK);
        arrowDrawable1 = new DrawerArrowDrawable(this);
        arrowDrawable2 = new DrawerArrowDrawable(this);
        ((Toolbar)findViewById(R.id.lback_tool)).setNavigationIcon(barDraw);
        ((ImageView)findViewById(R.id.lback_img)).setImageDrawable(imgDraw);
//        ((Toolbar)findViewById(R.id.lback_tool)).setNavigationIcon(arrowDrawable1);
//        ((ImageView)findViewById(R.id.lback_img)).setImageDrawable(arrowDrawable2);
//        DrawerArrowDrawable
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //CheckBox
        switch (buttonView.getId()){
            case R.id.lback_check:
                imgDraw.setSpinEnabled(isChecked);
                barDraw.setSpinEnabled(isChecked);
                break;
            case R.id.lback_auto:
                auto = isChecked;
                if(auto){
                    handler.sendEmptyMessageDelayed(200,10);
                }
                break;
        }
    }

    private Handler handler = new Handler(){
        boolean b = true;
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    if(b){
                        pro++;
                    }else{
                        pro--;
                    }
                    seekBar.setProgress(pro);
                    if(pro>100){
                        b = false;
                    }else if(pro<0){
                        b = true;
                    }
                    if(auto)
                        handler.sendEmptyMessageDelayed(200,10);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //RadioGroup
        switch (checkedId){
            case R.id.lback_radio1:
                imgDraw.setBackType(LBackDrawable.BackType.WINGS);
                barDraw.setBackType(LBackDrawable.BackType.WINGS);
                break;
            case R.id.lback_radio2:
                imgDraw.setBackType(LBackDrawable.BackType.GROWTH);
                barDraw.setBackType(LBackDrawable.BackType.GROWTH);
                break;
            case R.id.lback_radio3:
                imgDraw.setBackType(LBackDrawable.BackType.SWIPING);
                barDraw.setBackType(LBackDrawable.BackType.SWIPING);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        pro = progress;
        //SeekBar
        imgDraw.setProgress(pro*0.01f);
        barDraw.setProgress(pro*0.01f);
        arrowDrawable1.setProgress(pro*0.01f);
        arrowDrawable2.setProgress(pro*0.01f);
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //SeekBar
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //SeekBar
    }
}
