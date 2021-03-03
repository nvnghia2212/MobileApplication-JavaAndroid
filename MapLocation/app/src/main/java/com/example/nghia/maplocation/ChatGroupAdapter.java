package com.example.nghia.maplocation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

// Phần ChatGroup này copy của mess qua sửa lại (activity cũng vậy và bổ sung thêm)
public class ChatGroupAdapter extends BaseAdapter {

    private /*Context thành tên màn hình*/ ChatGroupActivity context;
    private int layout;
    private List<ChatGroup> sendMessengerGroupList;

    public ChatGroupAdapter(ChatGroupActivity context, int layout, List<ChatGroup> sendMessengerGroupList) {
        this.context = context;
        this.layout = layout;
        this.sendMessengerGroupList = sendMessengerGroupList;
    }

    @Override
    public int getCount() {
        return sendMessengerGroupList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtEmailFriend, txtFriend, txtDateTime;
        RelativeLayout message_container;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater     = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView                 = inflater.inflate(layout,null);

            holder.txtEmailFriend       = convertView.findViewById(R.id.txtEmailFriend);
            holder.txtFriend            = convertView.findViewById(R.id.txtFriend);
            holder.txtDateTime       = convertView.findViewById(R.id.txtDateTime);

            holder.message_container    = convertView.findViewById(R.id.message_container);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ChatGroup chatGroup = sendMessengerGroupList.get(position);

        if (chatGroup.getMemberSend().equals(context.emailSend)) {

//            holder.txtEmailFriend.setVisibility(View.GONE);

            holder.txtEmailFriend.setText(chatGroup.getMemberSend());
            holder.txtEmailFriend.setTextColor(Color.BLACK);

            holder.txtFriend.setText(chatGroup.getTextSend());
            holder.txtFriend.setTextColor(Color.BLACK);

            holder.txtDateTime.setText(chatGroup.getDateMess()+" "+chatGroup.getTimeMess());

            holder.txtEmailFriend.setPadding(400,0,0,0);
            holder.message_container.setPadding(400,0,0,0);

//            RelativeLayout.LayoutParams layoutParams
//                    = (RelativeLayout.LayoutParams) holder.message_container.getLayoutParams();
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            holder.message_container.setLayoutParams(layoutParams);

        }else {

//            holder.txtEmailFriend.setVisibility(View.VISIBLE);

            holder.txtEmailFriend.setText(chatGroup.getMemberSend());
            holder.txtEmailFriend.setTextColor(Color.BLUE);

            holder.txtFriend.setText(chatGroup.getTextSend());
            holder.txtFriend.setTextColor(Color.BLUE);

            holder.txtDateTime.setText(chatGroup.getDateMess()+" "+chatGroup.getTimeMess());

            holder.txtEmailFriend.setPadding(0,0,300,0);
            holder.message_container.setPadding(0,0,300,0);

//            RelativeLayout.LayoutParams layoutParams
//                    = (RelativeLayout.LayoutParams) holder.message_container.getLayoutParams();
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            holder.message_container.setLayoutParams(layoutParams);

        }
        return convertView;
    }
}
