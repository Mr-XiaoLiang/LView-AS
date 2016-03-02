package mr_xiaoliang.com.github.lview_as.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import mr_xiaoliang.com.github.lview_as.R;
import mr_xiaoliang.com.github.lview_as.view.LProgressView;

public class ProgressDialog extends Dialog {
	/**
	 * 进度总数
	 * 建议100
	 */
	private int allInt = 0;
	/**
	 * 已完成数量
	 * 建议不要大于进度总数
	 */
	private int havingInt = 0;
	/**
	 * 进度控件
	 */
	private LProgressView progressView;

	public ProgressDialog(Context context, int allInt, int havingInt) {
		super(context);
		this.allInt = allInt;
		this.havingInt = havingInt;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除屏幕title
		setContentView(R.layout.dialog_progress);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		progressView = (LProgressView) findViewById(R.id.dialog_progress_pro);
		progressView.setAllInt(allInt);
		progressView.setHavingInt(havingInt);
	}
	/**
	 * 获取进度条颜色
	 * @return
	 */
	public int getProColor() {
		return progressView.getProColor();
	}

	/**
	 * 设置进度条颜色
	 * @param proColor
	 */
	public void setProColor(int proColor) {
		progressView.setProColor(proColor);
	}

	/**
	 * 获取文字颜色
	 * @return
	 */
	public int getTextColor() {
		return progressView.getTextColor();
	}

	/**
	 * 设置文字颜色
	 * @param textColor
	 */
	public void setTextColor(int textColor) {
		progressView.setTextColor(textColor);
	}

	/**
	 * 获取背景颜色
	 * @return
	 */
	public int getBgColor() {
		return progressView.getBgColor();
	}

	/**
	 * 设置背景颜色
	 * @param bgColor
	 */
	public void setBgColor(int bgColor) {
		progressView.setBgColor(bgColor);
	}

	/**
	 * 获取总数
	 * @return
	 */
	public int getAllInt() {
		return progressView.getAllInt();
	}

	/**
	 * 设置总数
	 * @param allInt
	 */
	public void setAllInt(int allInt) {
		progressView.setAllInt(allInt);
	}

	/**
	 * 获取已经完成进度
	 * @return
	 */
	public int getHavingInt() {
		return progressView.getHavingInt();
	}

	/**
	 * 设置已完成进度
	 * @param havingInt
	 */
	public void setHavingInt(int havingInt) {
		progressView.setHavingInt(havingInt);
	}

	/**
	 * 获取是否显示进度条背景
	 * @return
	 */
	public boolean isProHaveBg() {
		return progressView.isProHaveBg();
	}

	/**
	 * 设置是否显示进度条背景
	 * @param proHaveBg
	 */
	public void setProHaveBg(boolean proHaveBg) {
		progressView.setProHaveBg(proHaveBg);
	}
}
