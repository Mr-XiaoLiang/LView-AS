package mr_xiaoliang.com.github.lview_as.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LiuJ on 2016/3/14.
 * 跑马灯文本显示器,四个方向，1顶4
 */
public class LScrollingTextView extends TextView{
    private ArrayList<String> text;
    private ScrolDirection scrolDirection = ScrolDirection.LEFT;
    private int index;
    private int offset;
    private int step;
    private int maxStepNum = 300;
    private int width,height;
    private Paint paint;
    private LScrollingTextViewListener lScrollingTextViewListener;
    private int downX,downY;
    private MyHandler handler;
    private int delayedTime = 5000;
    private boolean stop = false;
    @Override
    protected void onDraw(Canvas canvas) {
        if(text==null||text.size()<1)return;
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textY;
        switch (scrolDirection){
            case LEFT:
            case RIGHT:
                textY = height/2 - fm.descent + (fm.descent - fm.ascent) / 2;
                canvas.drawText(text.get(index),offset,textY,paint);
                break;
            case TOP:
            case Bottom:
                if(Math.abs(height/2-offset)<step){
                    textY = height/2 - fm.descent + (fm.descent - fm.ascent) / 2;
                }else{
                    textY = offset - fm.descent + (fm.descent - fm.ascent) / 2;
                }
                canvas.drawText(text.get(index), width / 2, textY,paint);
        }
        change();
    }
    private void change(){
        if(!stop)
            return;
        switch (scrolDirection){
            case LEFT:
                if(offset<(text.get(index).length()*getTextSize()/2*-1)){
                    index++;
                    index%=text.size();
                    offset = (int) (text.get(index).length()/2*getTextSize()+width);
                }else{offset-=step;}
                invalidate();
                break;
            case RIGHT:
                if(offset>(text.get(index).length()*getTextSize()/2+width)){
                    index++;
                    index%=text.size();
                    offset = (int) (text.get(0).length()/2*getTextSize()*-1);
                }else{offset+=step;}
                invalidate();
                break;
            case TOP:
                if(offset<(0.5*getTextSize()*-1)){
                    index++;
                    index%=text.size();
                    offset = (int) (0.5*getTextSize()+height);
                    invalidate();
                }else if(offset/step==height/2/step){
                    offset-=step;
                    handler.sendEmptyMessageDelayed(200, delayedTime);
                }else{offset-=step;invalidate();}
                break;
            case Bottom:
                if(offset>(0.5*getTextSize()+height)){
                    index++;
                    index%=text.size();
                    offset = (int) (0.5*getTextSize()*-1);
                    invalidate();
                }else if(offset/step==height/2/step){
                    offset+=step;
                    handler.sendEmptyMessageDelayed(200,delayedTime);
                }else{offset+=step;invalidate();}
                break;
        }
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==200)
                invalidate();
            super.handleMessage(msg);
        }
    };

    private void init(){
        index = 0;
        switch (scrolDirection){
            case LEFT:
                if(text!=null)offset = (int) (text.get(0).length()/2*getTextSize()+width);
            case RIGHT:
                if(text!=null)offset = (int) (text.get(0).length()/2*getTextSize()*-1);
                step = (int) (1.0*width/maxStepNum);
                break;
            case TOP:
                if(text!=null)offset = (int) (0.5*getTextSize()+height);
            case Bottom:
                if(text!=null)offset = (int) (0.5*getTextSize()*-1);
                step = (int) (1.0*height/maxStepNum*50);
                if(step<1){
                    step = 1;
                }
        }
        if(handler==null)
            handler = new MyHandler();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if(lScrollingTextViewListener!=null&&
                        downX>0&&downX<width&&downY>0&&downY<height&&
                        event.getX()>0&&event.getX()<width&&event.getY()>0&&event.getY()<height){
                    lScrollingTextViewListener.onScrollingTextClick(this,index,text.get(index));
                }
                downX = -1;
                downY = -1;
                break;
        }
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        width = getWidth();
        height = getHeight();
        init();
    }
    public interface LScrollingTextViewListener{
        public void onScrollingTextClick(View v,int i,String s);
    }
    public LScrollingTextView(Context context) {
        this(context, null, 0);}
    public LScrollingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);}
    public LScrollingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = getPaint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(getTextColors().getDefaultColor());
        paint.setTextSize(getTextSize());
    }
    public void setText(ArrayList<String> text) {
        this.text = text;
        init();
        invalidate();
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        paint.setTextSize(size);
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        paint.setColor(color);
    }

    public void setText(String text) {
        if(this.text==null)
            this.text = new ArrayList<>();
        this.text.add(text);
    }

    public void removeText(String t){
        if(text==null||text.size()<1)
            return;
        text.remove(t);
    }

    public void removeText(int p){
        if(text==null||text.size()<1)
            return;
        text.remove(p);
    }

    public void setScrolDirection(ScrolDirection scrolDirection) {
        this.scrolDirection = scrolDirection;
        init();
        invalidate();
    }
    public void setDelayedTime(int delayedTime) {
        this.delayedTime = delayedTime;
        init();
        invalidate();
    }
    public void setMaxStepNum(int maxStepNum) {
        this.maxStepNum = maxStepNum;
        init();
        invalidate();
    }
    public void stop(){
        stop = true;
    }
    public void start(){
        stop = false;
        invalidate();
    }
    public void setlScrollingTextViewListener(LScrollingTextViewListener lScrollingTextViewListener) {
        this.lScrollingTextViewListener = lScrollingTextViewListener;
    }
    public enum ScrolDirection {
        LEFT,RIGHT,TOP,Bottom
    }
}
