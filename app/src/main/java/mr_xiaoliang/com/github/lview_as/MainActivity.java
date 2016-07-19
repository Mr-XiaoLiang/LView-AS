package mr_xiaoliang.com.github.lview_as;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mr_xiaoliang.com.github.lview_as.dialog.CalendarDialog;
import mr_xiaoliang.com.github.lview_as.dialog.LWheelDialog;
import mr_xiaoliang.com.github.lview_as.util.DialogUtil;
import mr_xiaoliang.com.github.lview_as.util.ShortcutUtil;
import mr_xiaoliang.com.github.lview_as.view.LCalendarView;
import mr_xiaoliang.com.github.lview_as.view.LClockView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainAdapter.OnItemClickListener {

    private ClipData myClip;
    private ClipboardManager myClipboard;
    private static final String GitPath = "https://github.com/Mr-XiaoLiang";
    private FloatingActionButton fab;
    private String[] names = {
            "日历选择-日期没有限制&全向拖动", "日历选择-日期有限制&单向", "选项卡切换-三角形", "选项卡切换-线形", "时间选择-小时",
            "时间选择-分钟", "加载等待动画", "饼图", "进度图", "雷达图",
            "圆形图片", "滑动按钮", "温度计", "进度条按钮", "页面下方小点",
            "tab小点", "日期滚轮", "时间滚轮", "全套滚轮", "tab条形",
            "倒计时View", "商品列表", "支付宝咻一咻", "系统自带的抽屉用法演示", "现在较流行的抽屉样式",
            "带涟漪的Layout", "渐变的View", "通讯录", "添加快捷方式", "删除快捷方式",
            "水滴加载动画", "跑马灯", "折线图", "刮刮卡", "转动的心"};
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private DialogUtil dialogUtil;
    private Calendar calendar;
    private Intent intent;
    private ShortcutUtil shortcutUtil = null;
    private int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        fab.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter = new MainAdapter(names, this, this));
        dialogUtil = new DialogUtil();
        calendar = Calendar.getInstance();
        shortcutUtil = new ShortcutUtil(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, GitPath, Snackbar.LENGTH_LONG)
                        .setAction("复制", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myClip = ClipData.newPlainText("text", GitPath);
                                myClipboard.setPrimaryClip(myClip);
                            }
                        }).show();
                break;
        }
    }

    @Override
    public void onItemClick(String data, int position) {
        switch (position) {
            case 0:
                dialogUtil.getCalendarDialog(this, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.DAY_OF_MONTH), LCalendarView.SlideType.Both, new CalendarDialog.CalendarDialogListener() {
                            @Override
                            public void calendarDialogListener(int year, int month, int day) {
                                t(year + "-" + month + "-" + day);
                            }
                        });
                break;
            case 1:
                dialogUtil.getCalendarDialog(this, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.DAY_OF_MONTH), LCalendarView.SlideType.Horizontal,
                        new CalendarDialog.CalendarDialogListener() {
                            @Override
                            public void calendarDialogListener(int year, int month, int day) {
                                t(year + "-" + month + "-" + day);
                            }
                        });
                break;
            case 2:
                intent = new Intent(this, ChooseBgTest.class);
                intent.putExtra("type", true);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, ChooseBgTest.class);
                intent.putExtra("type", false);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, ClockTest.class);
                intent.putExtra("type", LClockView.TYPE_HOURS);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(this, ClockTest.class);
                intent.putExtra("type", LClockView.TYPE_MINUTES);
                startActivity(intent);
                break;
            case 6:
                dialogUtil.getLoadDialog(this);
                break;
            case 8:
                dialogUtil.getProgressDialog(this, 100, 100);
                break;
            case 14:
                intent = new Intent(this, PageTest.class);
                intent.putExtra("type", PageTest.thisType_bottom);
                startActivity(intent);
                break;
            case 15:
                intent = new Intent(this, PageTest.class);
                intent.putExtra("type", PageTest.thisType_top);
                startActivity(intent);
                break;
            case 16:
                dialogUtil.getWheelDialog(this, LWheelDialog.LWheelDialogType.DATE, null);
                break;
            case 17:
                dialogUtil.getWheelDialog(this, LWheelDialog.LWheelDialogType.TIME, null);
                break;
            case 18:
                dialogUtil.getWheelDialog(this, LWheelDialog.LWheelDialogType.ALL, null);
                break;
            case 19:
                intent = new Intent(this, PageTest.class);
                intent.putExtra("type", PageTest.thisType_top_line);
                startActivity(intent);
                break;
            case 20:
                intent = new Intent(this, CountDownListActivity.class);
                startActivity(intent);
                break;
            case 21:
                intent = new Intent(this, ShopListTest.class);
                startActivity(intent);
                break;
            case 27:
                intent = new Intent(this, PhoneBook.class);
                startActivity(intent);
                break;
            case 28:
                shortcutUtil.addShortcut(getString(R.string.app_name), size, R.mipmap.ic_launcher, MainActivity.class);
                t("添加成功");
                size++;
                break;
            case 29:
                shortcutUtil.deleteShortcut(getString(R.string.app_name), MainActivity.class);
                t("删除成功");
                break;
            case 30:
                dialogUtil.getLoadDialog2(this);
                size++;
                break;
            case 33:
                startActivity(new Intent(this, ScratchCardActivity.class));
                break;
            default:
                intent = new Intent(this, ViewTest.class);
                intent.putExtra("type", position - 7);
                startActivity(intent);
                break;
        }
    }

    private void t(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
