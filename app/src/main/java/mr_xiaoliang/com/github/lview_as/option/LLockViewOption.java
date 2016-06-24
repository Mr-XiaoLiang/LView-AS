package mr_xiaoliang.com.github.lview_as.option;

import android.graphics.Color;

/**
 * Created by  on 2016/6/24.
 * 图形锁的参数
 */

public class LLockViewOption {

    public final boolean typeLine;
    public final boolean showBorder;
    public final boolean showBtnBg;
    public final boolean showCenterCircle;
    public final boolean showLine;
    public final int centerRadius;
    public final int borderColor;
    public final int btnBgColor;
    public final int centerCircleColor;
    public final int borderColorPress;
    public final int btnBgColorPress;
    public final int centerCircleColorPress;
    public final int lineColor;
    public final int lineWidth;
    public final int rowSize,colSize;
    public final int interval;
    public final LayoutType layoutType;
    public final ShapeType shapeType;
    public final int borderWidth;


    public LLockViewOption(Builder b) {
        this.borderColor = b.borderColor;
        this.borderColorPress = b.borderColorPress;
        this.btnBgColor = b.btnBgColor;
        this.typeLine = b.typeLine;
        this.showBorder = b.showBorder;
        this.showBtnBg = b.showBtnBg;
        this.showCenterCircle = b.showCenterCircle;
        this.showLine = b.showLine;
        this.centerRadius = b.centerRadius;
        this.centerCircleColor = b.centerCircleColor;
        this.btnBgColorPress = b.btnBgColorPress;
        this.centerCircleColorPress = b.centerCircleColorPress;
        this.lineColor = b.lineColor;
        this.lineWidth = b.lineWidth;
        this.rowSize = b.rowSize;
        this.colSize = b.colSize;
        this.interval = b.interval;
        this.layoutType = b.layoutType;
        this.shapeType = b.shapeType;
        this.borderWidth = b.borderWidth;
    }

    public static class Builder {
        private boolean typeLine = true;
        private boolean showBorder = true;
        private boolean showBtnBg = true;
        private boolean showCenterCircle = true;
        private boolean showLine = true;
        private int centerRadius = 5;
        private int borderColor = Color.WHITE;
        private int borderColorPress = Color.WHITE;
        private int borderWidth = 1;
        private int btnBgColor = Color.TRANSPARENT;
        private int centerCircleColor = Color.WHITE;
        private int btnBgColorPress = Color.argb(128,255,255,255);
        private int centerCircleColorPress = Color.WHITE;
        private int lineColor = Color.WHITE;
        private int lineWidth = 5;
        private int rowSize = 3,colSize = 3;
        private int interval = 0;
        private LayoutType layoutType = LayoutType.Center;
        private ShapeType shapeType = ShapeType.Circle;

        public Builder setBorderColor(int borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        public Builder setBorderColorPress(int borderColorPress) {
            this.borderColorPress = borderColorPress;
            return this;
        }

        public Builder setBtnBgColor(int btnBgColor) {
            this.btnBgColor = btnBgColor;
            return this;
        }

        public Builder setBtnBgColorPress(int btnBgColorPress) {
            this.btnBgColorPress = btnBgColorPress;
            return this;
        }

        public Builder setCenterCircleColor(int centerCircleColor) {
            this.centerCircleColor = centerCircleColor;
            return this;
        }

        public Builder setCenterCircleColorPress(int centerCircleColorPress) {
            this.centerCircleColorPress = centerCircleColorPress;
            return this;
        }

        public Builder setCenterRadius(int centerRadius) {
            this.centerRadius = centerRadius;
            return this;
        }

        public Builder setColSize(int colSize) {
            this.colSize = colSize;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setLayoutType(LayoutType layoutType) {
            this.layoutType = layoutType;
            return this;
        }

        public Builder setLineColor(int lineColor) {
            this.lineColor = lineColor;
            return this;
        }

        public Builder setTypeLine(boolean typeLine) {
            this.typeLine = typeLine;
            return this;
        }

        public Builder setLineWidth(int lineWidth) {
            this.lineWidth = lineWidth;
            return this;
        }

        public Builder setRowSize(int rowSize) {
            this.rowSize = rowSize;
            return this;
        }

        public Builder setShapeType(ShapeType shapeType) {
            this.shapeType = shapeType;
            return this;
        }

        public Builder setShowBorder(boolean showBorder) {
            this.showBorder = showBorder;
            return this;
        }

        public Builder setShowBtnBg(boolean showBtnBg) {
            this.showBtnBg = showBtnBg;
            return this;
        }

        public Builder setShowCenterCircle(boolean showCenterCircle) {
            this.showCenterCircle = showCenterCircle;
            return this;
        }

        public Builder setShowLine(boolean showLine) {
            this.showLine = showLine;
            return this;
        }

        public Builder setBorderWidth(int borderWidth) {
            this.borderWidth = borderWidth;
            return this;
        }
    }
    public enum LayoutType{
        Center,FitXY
    }

    public enum ShapeType{
        Square,Circle
    }
}
