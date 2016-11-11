package mr_xiaoliang.com.github.lview_as.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by liuj on 2016/11/9.
 * 简单拼接图片的一个工具类
 */
public class ImageJointUtil {
    /**
     * 横向拼接图片
     * @param bitmaps
     * @return
     */
    public static Bitmap jointByHorizontal(Bitmap... bitmaps){
        return jointByHorizontal(Gravity.START,bitmaps);
    }

    /**
     * 横向拼接图片
     * @param bitmaps
     * @return
     */
    public static Bitmap jointByHorizontal(Gravity gravity,Bitmap... bitmaps){
        if(bitmaps==null||bitmaps.length<1){
            //如果输入为空就返回空
            return null;
        }
        if(bitmaps.length<2){
            //如果输入只有一张，那就直接返回这一张
            return bitmaps[0];
        }
        int bWidth = 0;//输出图片的宽度
        int bHeight = 0;//输出图片的高度
        //获取输出图片的尺寸
        for(Bitmap bitmap:bitmaps){
            //横向排列，累加宽度和
            bWidth += bitmap.getWidth();
            //横向排列，获取最大高度值
            if(bHeight<bitmap.getHeight())
                bHeight = bitmap.getHeight();
        }
        //创建最终尺寸的图片
        Bitmap outBmp = Bitmap.createBitmap(bWidth,bHeight, Bitmap.Config.ARGB_8888);
        //拿到最终图片的画板对象
        Canvas canvas = new Canvas(outBmp);
        //当前绘制的起点坐标
        int x = 0;
        int y = 0;
        for(Bitmap bitmap:bitmaps){
            switch (gravity){
                case START:
                    y = 0;
                    break;
                case CENTER:
                    y = (bHeight-bitmap.getHeight())/2;
                    break;
                case END:
                    y = bHeight-bitmap.getHeight();
                    break;
            }
            canvas.drawBitmap(bitmap,x,y,null);
            //移动起始坐标到当前图片的后面
            x += bitmap.getWidth();
        }
        return outBmp;
    }
    /**
     * 纵向拼接图片
     * @param bitmaps
     * @return
     */
    public static Bitmap jointByVertical(Bitmap... bitmaps){
        return jointByVertical(Gravity.START,bitmaps);
    }

    /**
     * 纵向拼接图片
     * @param bitmaps
     * @return
     */
    public static Bitmap jointByVertical(Gravity gravity,Bitmap... bitmaps){
        if(bitmaps==null||bitmaps.length<1){
            //如果输入为空就返回空
            return null;
        }
        if(bitmaps.length<2){
            //如果输入只有一张，那就直接返回这一张
            return bitmaps[0];
        }
        int bWidth = 0;//输出图片的宽度
        int bHeight = 0;//输出图片的高度
        //获取输出图片的尺寸
        for(Bitmap bitmap:bitmaps){
            //纵向排列，累加高度和
            bHeight += bitmap.getHeight();
            //纵向排列，获取最大宽度值
            if(bWidth<bitmap.getWidth())
                bWidth = bitmap.getWidth();
        }
        //创建最终尺寸的图片
        Bitmap outBmp = Bitmap.createBitmap(bWidth,bHeight, Bitmap.Config.ARGB_8888);
        //拿到最终图片的画板对象
        Canvas canvas = new Canvas(outBmp);
        //当前绘制的起点坐标
        int x = 0;
        int y = 0;
        for(Bitmap bitmap:bitmaps){
            switch (gravity){
                case START:
                    x = 0;
                    break;
                case CENTER:
                    x = (bWidth-bitmap.getWidth())/2;
                    break;
                case END:
                    x = bWidth-bitmap.getWidth();
                    break;
            }
            canvas.drawBitmap(bitmap,x,y,null);
            y += bitmap.getHeight();
        }
        return outBmp;
    }

    /**
     * 居中方式
     */
    public enum  Gravity{
        START,CENTER,END
    }

    public class JointByHorizontalRunnable implements Runnable{
        private Gravity gravity;
        private Bitmap[] bitmaps;
        private OnJointCompleteListener listener;

        public JointByHorizontalRunnable(Bitmap[] bitmaps, Gravity gravity, OnJointCompleteListener listener) {
            this.bitmaps = bitmaps;
            this.gravity = gravity;
            this.listener = listener;
        }

        @Override
        public void run() {
            Bitmap bitmap = ImageJointUtil.jointByHorizontal(gravity,bitmaps);
            if(listener!=null)
                listener.onJointComplete(bitmap);
        }
    }

    public class JointByVerticalRunnable implements Runnable{
        private Gravity gravity;
        private Bitmap[] bitmaps;
        private OnJointCompleteListener listener;

        public JointByVerticalRunnable(Bitmap[] bitmaps, Gravity gravity, OnJointCompleteListener listener) {
            this.bitmaps = bitmaps;
            this.gravity = gravity;
            this.listener = listener;
        }

        @Override
        public void run() {
            Bitmap bitmap = ImageJointUtil.jointByVertical(gravity,bitmaps);
            if(listener!=null)
                listener.onJointComplete(bitmap);
        }
    }

    public interface OnJointCompleteListener{
        void onJointComplete(Bitmap bitmap);
    }

}
