package mr_xiaoliang.com.github.lview_as.view;

/**
 * Created by liuj on 2017/1/16.
 * 下拉刷新Layout中的刷新进度显示View
 */

public interface LRefreshProgressView{
    /**
     * 开始下拉
     * 本方法于用户操作有刷新趋势并且满足刷新条件时触发
     * 此时并没有开始下拉或者开始刷新
     * 建议此时初始化及重置显示的数据
     */
    void onStartPull();

    /**
     * 正在下拉
     * @param dy 下拉的高度,下拉的高度并不等于进度,比如手动调用刷新方法时
     *           如果是手动刷新,则
     *           dy = getMaxPullHight()>getRefreshPullHight()?getRefreshPullHight():getMaxPullHight();
     * @param progress 刷新触发的进度,一半情况下等于下拉高度的比例,
     *                 但是如果手动触发刷新,则为刷新View动画进度
     * @param fromUser 是否是用户触发,即,是由下拉刷新触发的为true,
     *                 如果是手动方法触发的为false,以此来决定动画的显示方式
     */
    void onPullDown(int dy,float progress,boolean fromUser);

    /**
     * 最高的下拉高度
     * 这个方法的返回值,决定了可以下拉的高度
     * 也决定了下拉的进度的值
     * 进度的值为当前下拉高度/最大下拉高度
     * @return
     */
    int getMaxPullHight();

    /**
     * 高度坐标变化时,它自己决定自己的位置偏移
     * 返回值的格式为[x,y]
     * 返回值为相对值
     * 即,如果返回为0,则表示,不做修改,为常规的跟随下拉
     * @param dy 下拉的高度,下拉的高度并不等于进度,比如手动调用刷新方法时
     *           如果是手动刷新,则
     *           dy = getMaxPullHight()>getRefreshPullHight()?getRefreshPullHight():getMaxPullHight();
     * @param progress 刷新触发的进度,一半情况下等于下拉高度的比例,
     *                 但是如果手动触发刷新,则为刷新View动画进度
     * @param fromUser 是否是用户触发,即,是由下拉刷新触发的为true,
     *                 如果是手动方法触发的为false,以此来决定动画的显示方式
     * @return
     */
    int[] getOffsetInWindow(int dy,float progress,boolean fromUser);

    /**
     * 刷新的高度位置
     * 本位置与最大刷新高度位置{@link LRefreshProgressView getMaxPullHighly}
     * 不同,他是最大下拉高度,这里是刷新的高度.
     * 如果下拉高度大于刷新高度时松手,则触发刷新动作
     * 而最大下拉高度则是可以下拉的最大高度.
     * 理论上,刷新高度应该小于等于最大下拉高度,否则永远无法触发刷新动作
     * 为了兼容特殊情况,这里不做处理,但是如果遇到类似问题,
     * 请检查刷新高度与最大下拉高度的值
     * @return
     */
    int getRefreshPullHight();

    /**
     * 刷新中
     * 当layout开始刷新时,触发本方法
     * 每次刷新本方法只触发一次
     * 本方法表示,此时开始循环播放刷新动画
     */
    void onRefreshing();

    /**
     * 刷新结束方法
     * 本方法为手动关闭刷新状态时,触发
     */
    void onRefreshEnd();


}
