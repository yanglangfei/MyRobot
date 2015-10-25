package com.ylf.adapter;

import java.util.List;

import com.ylf.bean.ChatMessage;
import com.ylf.myrobot.R;
import com.ylf.view.CircleImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ChatAdapter extends BaseAdapter {

	private Context mContext;
	private List<ChatMessage> chatMessages;

	public ChatAdapter(Context mContext, List<ChatMessage> chatMessages) {
		this.mContext = mContext;
		this.chatMessages = chatMessages;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chatMessages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return chatMessages.get(position);
	}

	@Override
	public int getItemViewType(int position) {

		return chatMessages.get(position).getMessageType();
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View containView, ViewGroup group) {
		ChatMessage message = chatMessages.get(position);
		int type = getItemViewType(position);
		ViewHolder viewHolder;

		if (type == 0) {
			if (containView == null) {
				containView = LayoutInflater.from(mContext).inflate(
						R.layout.chat_from_item, null);
				viewHolder = new ViewHolder();
				viewHolder.tv_date = (TextView) containView
						.findViewById(R.id.tv_date);
				viewHolder.iv_from_logo=(CircleImageView) containView.findViewById(R.id.iv_from_logo);
			    viewHolder.tv_msg=(TextView) containView.findViewById(R.id.tv_msg);
			    containView.setTag(viewHolder);
			} else {
				viewHolder=(ViewHolder) containView.getTag();
			}
			viewHolder.tv_msg.setText(message.getMessage());
			viewHolder.tv_date.setText(message.getTime());
		} else if (type == 1) {
			if (containView == null) {
				containView = LayoutInflater.from(mContext).inflate(
						R.layout.chat_me_item, null);
				viewHolder=new ViewHolder();
				viewHolder.tv_time=(TextView) containView.findViewById(R.id.tv_time);
				viewHolder.tv_me_msg=(TextView) containView.findViewById(R.id.tv_me_msg);
			    containView.setTag(viewHolder);
			} else {
                viewHolder=(ViewHolder) containView.getTag();
			}
			viewHolder.tv_me_msg.setText(message.getMessage());
			viewHolder.tv_time.setText(message.getTime());
		}
		return containView;
	}

	class ViewHolder {
		TextView tv_date;
		CircleImageView iv_from_logo;
		TextView tv_msg;
		TextView tv_time;
		TextView tv_me_msg;

	}

}
