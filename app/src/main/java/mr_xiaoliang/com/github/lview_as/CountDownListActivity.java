package mr_xiaoliang.com.github.lview_as;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import mr_xiaoliang.com.github.lview_as.view.LCountDownView;
import mr_xiaoliang.com.github.lview_as.view.LCountDownView2;
import mr_xiaoliang.com.github.lview_as.view.LGoodsSizeChangeView;

/**
 * 倒计时View测试类
 * 
 * @author LiuJ
 *
 */
public class CountDownListActivity extends Activity {
	/**
	 * 数据集
	 */
	private ArrayList<Bean> beans;

	private ListView list;

	private TextView hint;

	private ShowAdapter adapter;
	
	private CDListener listener;
	
	private GSCListener listener2;
	
//	private CDListener2 listener3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.count_down);
		list = (ListView) findViewById(R.id.count_down_list);
		hint = (TextView) findViewById(R.id.count_down_hint);
		hint.setText("每个item的时间在初始化的时候是相差1分钟\n因为暂停及还原算法有点问题，所以存在事件误差，不做比较不太明显，但是不建议以此倒计时作为标准，建议仅作为展示用");

		init();

	}

	private void init() {
		beans = new ArrayList<Bean>();
		for (int i = 0; i < 50; i++) {
			Bean bean = new Bean(System.currentTimeMillis(), "这是第"+(i+1)+"条，起始：" + i + ":00", (i*60),i+1);
			beans.add(bean);
		}
		listener = new CDListener();
		listener2 = new GSCListener();
//		listener3 = new CDListener2();
		list.setAdapter(adapter = new ShowAdapter());
		list.invalidate();
		adapter.notifyDataSetInvalidated();
	}

	private class ShowAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return beans.size();
		}

		@Override
		public Object getItem(int position) {
			return beans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
				convertView = layoutInflator.inflate(R.layout.count_down_item, null);
				holder = new Holder();
				holder.text = (TextView) convertView.findViewById(R.id.count_down_item_text);
				holder.downView = (LCountDownView) convertView.findViewById(R.id.count_down_item_view);
				holder.changeView = (LGoodsSizeChangeView) convertView.findViewById(R.id.count_down_item_size);
//				holder.downView2 = (LCountDownView2) convertView.findViewById(R.id.count_down_item_view2);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.text.setText(beans.get(position).getText());
			holder.downView.setTime(position, 0, beans.get(position).getData(), beans.get(position).getTimestamp());
			holder.downView.setListener(listener);
//			holder.downView2.setTime(position, 0, beans.get(position).getData(), beans.get(position).getTimestamp());
//			holder.downView2.setListener(listener3);
			holder.changeView.setGoodsSize(position,beans.get(position).getSize());
			holder.changeView.setListener(listener2);
			return convertView;
		}

		class Holder {
			TextView text;
			LCountDownView downView;
			LGoodsSizeChangeView changeView;
//			LCountDownView2 downView2;
		}
	}

	private class GSCListener implements LGoodsSizeChangeView.OnLGoodsSizeChangeViewListener {
		@Override
		public void onGoodSizeChange(int thisPosition, String thisID, int goodsSize) {
			Bundle bundle = new Bundle();
			bundle.putInt("thisPosition", thisPosition);
			bundle.putInt("goodsSize", goodsSize);
			Message message = new Message();
			message.what = 3;
			message.setData(bundle);
			handler.sendMessage(message);
		}
	}
	
	private class CDListener implements LCountDownView.LCountDownViewListener {
		@Override
		public void onCountDown(int thisPosition, int thisID, int second, String date, long timestamp) {
			Bundle bundle = new Bundle();
			bundle.putInt("thisPosition", thisPosition);
			bundle.putInt("date", second);
			bundle.putLong("timestamp", timestamp);
			Message message = new Message();
			message.what = 1;
			message.setData(bundle);
			handler.sendMessage(message);
		}

		@Override
		public void onCountDownForEnd(int thisPosition, int thisID) {
			Bundle bundle = new Bundle();
			bundle.putInt("thisPosition", thisPosition);
			Message message = new Message();
			message.what = 2;
			message.setData(bundle);
			handler.sendMessage(message);
		}
	}
	
	private class CDListener2 implements LCountDownView2.LCountDownViewListener2 {
		@Override
		public void onCountDown(int thisPosition, int thisID, int second, String date, long timestamp) {
			Bundle bundle = new Bundle();
			bundle.putInt("thisPosition", thisPosition);
			bundle.putInt("date", second);
			bundle.putLong("timestamp", timestamp);
			Message message = new Message();
			message.what = 1;
			message.setData(bundle);
			handler.sendMessage(message);
		}

		@Override
		public void onCountDownForEnd(int thisPosition, int thisID) {
			Bundle bundle = new Bundle();
			bundle.putInt("thisPosition", thisPosition);
			Message message = new Message();
			message.what = 2;
			message.setData(bundle);
			handler.sendMessage(message);
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Bundle bundle;
			switch (msg.what) {
			case 1:
				bundle = msg.getData();
				beans.get(bundle.getInt("thisPosition")).setTime(bundle.getInt("date"), bundle.getLong("timestamp"));
				break;
			case 2:
				bundle = msg.getData();
				beans.get(bundle.getInt("thisPosition")).setText("倒计时结束");
				break;
			case 3:
				bundle = msg.getData();
				beans.get(bundle.getInt("thisPosition")).setSize(bundle.getInt("goodsSize"));
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private class Bean {
		private Long timestamp;
		private String text;
		private int data;
		private int size;

		public Bean(Long timestamp, String text, int data,int Size) {
			super();
			this.timestamp = timestamp;
			this.text = text;
			this.data = data;
			this.size = Size;
		}

		public Long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Long timestamp) {
			this.timestamp = timestamp;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public void setTime(int data, Long timestamp) {
			this.data = data;
			this.timestamp = timestamp;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}
		
	}

}
