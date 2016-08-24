package mr_xiaoliang.com.github.lview_as.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.view.LClockView;

/**
 * Created by LiuJ on 2016/8/24.
 * 时间选择dialog
 */

public class ClockDialog extends Dialog implements View.OnClickListener,LClockView.ClockViewListener {

    private TextView hourText;
    private TextView minuteText;
    private int hours = 0,minutes = 0;
    private LClockView clockView;
    private View enter,cancel;
    private int selectedColor = Color.parseColor("#FE007F");
    private ClockDialogListener listener;

    public ClockDialog(Context context) {
        super(context);
    }

    public interface ClockDialogListener{
        void onAffirmClock(int hour,int minute);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除屏幕title
        setContentView(R.layout.dialog_clock);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        hourText = (TextView) findViewById(R.id.dialog_clock_hour);
        minuteText = (TextView) findViewById(R.id.dialog_clock_minute);
        clockView = (LClockView) findViewById(R.id.dialog_clock_clock);
        enter = findViewById(R.id.dialog_clock_enter);
        cancel = findViewById(R.id.dialog_clock_cancel);
        enter.setOnClickListener(this);
        cancel.setOnClickListener(this);
        hourText.setOnClickListener(this);
        minuteText.setOnClickListener(this);
        clockView.setClockViewListener(this);
        setSelect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_clock_enter:
                if(listener!=null){
                    listener.onAffirmClock(hours,minutes);
                }
            case R.id.dialog_clock_cancel:
                dismiss();
                break;
            case R.id.dialog_clock_hour:
                clockView.setType(LClockView.TYPE_HOURS);
                setSelect();
                break;
            case R.id.dialog_clock_minute:
                clockView.setType(LClockView.TYPE_MINUTES);
                setSelect();
                break;
        }
    }

    private void setSelect(){
        if(clockView==null)
            return;
        hourText.setText(formatNumber(hours));
        minuteText.setText(formatNumber(minutes));
        switch (clockView.getType()){
            case LClockView.TYPE_HOURS:
                clockView.setSelected(hours);
                hours = clockView.getSelected();
                hourText.setTextColor(selectedColor);
                minuteText.setTextColor(Color.GRAY);
                break;
            case LClockView.TYPE_MINUTES:
                clockView.setSelected(minutes);
                minutes = clockView.getSelected();
                minuteText.setTextColor(selectedColor);
                hourText.setTextColor(Color.GRAY);
                break;
        }
    }

    @Override
    public void onClockChange(int t) {
        switch (clockView.getType()){
            case LClockView.TYPE_HOURS:
                hours = t;
                hourText.setText(formatNumber(hours));
                break;
            case LClockView.TYPE_MINUTES:
                minutes = t;
                minuteText.setText(formatNumber(minutes));
                break;
        }
    }

    private String formatNumber(int i){
        if(i<10){
            return "0"+i;
        }else
         return ""+i;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public ClockDialogListener getListener() {
        return listener;
    }

    public void setListener(ClockDialogListener listener) {
        this.listener = listener;
    }
}
