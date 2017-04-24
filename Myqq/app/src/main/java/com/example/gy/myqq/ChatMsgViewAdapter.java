package com.example.gy.myqq;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gy on 2017/1/18.
 */

public class ChatMsgViewAdapter extends BaseAdapter {
    public static interface IMsgViewType{
        //将信息显示状态初始化为发送的信息
        int IMVT_COM_MSG = 0;
        int IMVT_TO_MSG = 1;
    }

    private List<ChatMsgEntity> coll;
    private Context ctx;
    private LayoutInflater inflater;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll){
        this.ctx = context;
        this.coll = coll;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return coll.size();
    }

    @Override
    public Object getItem(int position) {
        return coll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMsgEntity entity = coll.get(position);
        if(entity.getMsgType()){
            return IMsgViewType.IMVT_COM_MSG;
        }else{
            return IMsgViewType.IMVT_TO_MSG;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsgEntity entity = coll.get(position);
        boolean isComMsg = entity.getMsgType();

        ViewHolder viewHolder = null;
        if(convertView == null){
            //如果当前的信息为接收的信息，则使用chatting_item_msg_text_left进行显示
            //否则使用chatting_item_msg_text_right进行显示
            if(isComMsg){
                convertView = inflater.inflate(R.layout.chatting_item_msg_text_left, null);
            }else{
                convertView = inflater.inflate(R.layout.chatting_item_msg_text_right, null);
            }
            viewHolder = new ViewHolder();
            viewHolder.tvUserName = (TextView)convertView.findViewById(R.id.tv_username);
            viewHolder.tvContent = (TextView)convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.isComMsg = isComMsg;

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tvUserName.setText(entity.getName());
        viewHolder.tvContent.setText(entity.getText());
        return convertView;
    }

    static class ViewHolder{
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }
}
