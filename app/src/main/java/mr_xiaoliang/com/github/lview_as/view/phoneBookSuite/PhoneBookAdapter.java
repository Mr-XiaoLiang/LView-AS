package mr_xiaoliang.com.github.lview_as.view.phoneBookSuite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import mr_xiaoliang.com.github.lview_as.R;

/**
 * 电话本列表适配器
 * @author LiuJ
 * 电话本列表套件之一
 */
public class PhoneBookAdapter extends Adapter<ViewHolder>{

	private static final int OnItemChecked = 200;
	private static final int ItemViewType_Division = 233;
	private static final int ItemViewType_Contact = 666;
	private List<ContactBean> beans;
	private Context context;
	private OnCheckedPhoneListener checkedPhoneListener;
	private int maxCheckSize = 10;
//	private HashMap<String, Boolean> checked;//储存已经选择过的项
	
	public PhoneBookAdapter(Context context,List<ContactBean> beans) {
		super();
		this.beans = beans;
		this.context = context;
		//根据说明，本行代码会辅助数据显示，防止反复刷新，但是加上之后出现数据重复问题，暂时不纠结
//		setHasStableIds(true);
	}

	@Override
	public long getItemId(int position) {
		return beans.get(position).getContactId();
	}



	@Override
	public int getItemCount() {                                       
		return beans.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		switch (getItemViewType(position)) {
		case ItemViewType_Contact:
			((ContactHolder)holder).setBeans(beans.get(position),position );
			break;
		case ItemViewType_Division:
			((DivisionHolder)holder).setBeans(beans.get(position));
			break;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
		case ItemViewType_Contact:
			ContactHolder contactHolder = new ContactHolder(
					LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false));
			return contactHolder;
		case ItemViewType_Division:
			DivisionHolder divisionHolder = new DivisionHolder(
					LayoutInflater.from(context).inflate(R.layout.item_first_letter, parent, false));
			return divisionHolder;
		}
		return null;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(beans.get(position).isPhone()){
			return ItemViewType_Contact;
		}else{
			return ItemViewType_Division;
		}
	}
	
	private class ContactHolder extends ViewHolder{
		
		public TextView phone;
		public TextView name;
		private CheckBox checkBox;
		private OnItemChecked checked;
		private View itemView;
		
		public ContactHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			phone = (TextView) itemView.findViewById(R.id.item_contact_tel);
			name = (TextView) itemView.findViewById(R.id.item_contact_name);
			checkBox = (CheckBox) itemView.findViewById(R.id.item_contact_check);
		}
		public void setBeans(ContactBean b,int position){
			phone.setText(b.getPhoneNum());
			name.setText(b.getDesplayName());
			checkBox.setChecked(b.isChecked());
			checked = new OnItemChecked(position);
			itemView.setOnClickListener(checked);
//			phone.setOnClickListener(checked);
//			checkBox.setOnCheckedChangeListener(checked);
//			name.setOnClickListener(checked);
		}
	}
	
	private class DivisionHolder extends ViewHolder{
		public TextView letter;
		public DivisionHolder(View itemView) {
			super(itemView);
			letter = (TextView) itemView.findViewById(R.id.item_first_letter_text);
		}
		public void setBeans(ContactBean b){
			letter.setText(b.getLetter());
		}
	}
	
	public void checkAll(boolean isChecked){
		//为局部刷新用
		ArrayList<Integer> change = new ArrayList<Integer>();
		int checkedSize = 0;
//		int index = 0;
		if(isChecked){
			checkedSize = getCheckedSize();
			int p = 0;
			while(checkedSize<maxCheckSize){
				if(beans.get(p).isChecked()!=isChecked&&beans.get(p).isPhone()){
					beans.get(p).setChecked(isChecked);
					checkedSize++;
					change.add(p);
				}
				p++;
			}
		}else{
			for(int i = 0;i<beans.size();i++){
				if(beans.get(i).isChecked()!=isChecked){
					beans.get(i).setChecked(isChecked);
					change.add(i);
				}
			}
		}
		updateItems(change);
	}
	
	private void updateItems(ArrayList<Integer> change){
		for(Integer integer : change){
			notifyItemChanged(integer);
		}
		if(checkedPhoneListener!=null){
			ContactBean[] returnBeans = new ContactBean[getCheckedSize()];
			int index = 0;
			for(ContactBean b : beans){
				if(b.isChecked()){
					returnBeans[index] = b;
					index++;
				}		
			}
			checkedPhoneListener.onCheckedPhone(returnBeans);
		}
	}
	
	private int getCheckedSize(){
		int checkedSize = 0;
//		for(int i = 0;i<beans.size();i++){
//			if(beans.get(i).isChecked()==true){
//				checkedSize++;
//			}
//		}
		for(ContactBean b : beans){
			if(b.isChecked())
				checkedSize++;
		}
		return checkedSize;
	}
	
	private class OnItemChecked implements View.OnClickListener,OnCheckedChangeListener{
		
		private final int p;
		private Message msg;
		
		public OnItemChecked(int p) {
			super();
			this.p = p;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			msg = new Message();
			msg.what = OnItemChecked;
			msg.obj = this.p;
			handler.sendMessage(msg);
		}

		@Override
		public void onClick(View v) {
			msg = new Message();
			msg.what = OnItemChecked;
			msg.obj = this.p;
			handler.sendMessage(msg);
		}

	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case OnItemChecked:
				//为局部刷新用
				ArrayList<Integer> change = new ArrayList<Integer>();
				int p = (Integer) msg.obj;
				change.add(p);
				if(beans.get(p).isChecked()){
					beans.get(p).setChecked(false);
				}else{
					int size = getCheckedSize();
					while(size>=10){
						for(int i = 0;i<beans.size();i++){
							if(beans.get(i).isChecked()){
								beans.get(i).setChecked(false);
								change.add(i);
								size--;
								break;
							}
						}
					}
					beans.get(p).setChecked(true);
				}
				updateItems(change);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	public interface OnCheckedPhoneListener{
		public void onCheckedPhone(ContactBean[] checked);
	}

	public OnCheckedPhoneListener getCheckedPhoneListener() {
		return checkedPhoneListener;
	}

	public void setCheckedPhoneListener(OnCheckedPhoneListener checkedPhoneListener) {
		this.checkedPhoneListener = checkedPhoneListener;
	}

}
