package mr_xiaoliang.com.github.lview_as;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by LiuJ on 2016/3/2.
 * 首页列表的适配器
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] data;
    private static final int item_click = 200;
    private OnItemClickListener onItemClickListener;
    private OnItemClickHandler handler;
    /**
     * 上下文
     */
    private static Context context;

    public MainAdapter(Context context,String[] data){
        super();
        this.data = data;
        this.context = context;
        handler = new OnItemClickHandler();
    }

    public MainAdapter(String[] data, OnItemClickListener onItemClickListener, Context context) {
        this(context, data);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Item(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Item)holder).text.setText(data[position]);
        ((Item)holder).text.setOnClickListener(new OnClick(position));
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    private class Item extends RecyclerView.ViewHolder{
        public TextView text;
        public Item(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.item_main_text);

        }
    }

    private class OnClick implements View.OnClickListener{
        private final int position;

        public OnClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Message message = new Message();
            message.what = item_click;
            message.obj = position;
            handler.sendMessage(message);
        }
    }

    private class OnItemClickHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case item_click:
                    if(onItemClickListener!=null){
                        int p = (int) msg.obj;
                        onItemClickListener.onItemClick(data[p],p);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String data,int position);
    }
}
