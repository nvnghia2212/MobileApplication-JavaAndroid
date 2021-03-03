package com.example.nghia.maplocation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class GroupAdapter extends BaseAdapter {

    private /*Context thành tên màn hình*/ GroupActivity context;
    private int layout;
    private List<Group> groupList;

    public GroupAdapter(GroupActivity context, int layout, List<Group> groupList) {
        this.context = context;
        this.layout = layout;
        this.groupList = groupList;
    }

    @Override
    public int getCount() {
        return groupList.size();
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
        ImageView imgChat,imgAddMember, imgMember,imgOutGroup,imgDelete;
        TextView txtNameGroup;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater     = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView                 = inflater.inflate(layout,null);

            holder.imgChat              = convertView.findViewById(R.id.imgChat);
            holder.txtNameGroup         = convertView.findViewById(R.id.txtNameGroup);
            holder.imgAddMember         = convertView.findViewById(R.id.imgAddMember);
            holder.imgMember            = convertView.findViewById(R.id.imgMember);
            holder.imgOutGroup          = convertView.findViewById(R.id.imgOutGroup);
            holder.imgDelete            = convertView.findViewById(R.id.imgDelete);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Group group = groupList.get(position);

        holder.txtNameGroup.setText(group.getNameGroup());

//        if (group.getUserAdd().equals(GroupActivity.emailSend)){
//            holder.imgOutGroup.setVisibility(View.GONE);
//            holder.imgDelete.setVisibility(View.VISIBLE);
//        }else {
            holder.imgOutGroup.setVisibility(View.VISIBLE);
            holder.imgDelete.setVisibility(View.GONE);
//        }

        holder.txtNameGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.IntentChatGroup(group.getNameGroup(),group.getUserAdd(), group.getDateTimeAdd());
            }
        });

        holder.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.IntentChatGroup(group.getNameGroup(),group.getUserAdd(), group.getDateTimeAdd());
            }
        });
        holder.imgAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.IntentFriendAddGroup(group.getNameGroup(),group.getUserAdd(), group.getDateTimeAdd());
            }
        });
        holder.imgMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.IntentGroupPeople(group.getNameGroup(),group.getUserAdd(), group.getDateTimeAdd());
            }
        });
        holder.imgOutGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmLeave(GroupActivity.emailSend,group.getNameGroup(),group.getUserAdd(),group.getDateTimeAdd());
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDelete(group.getNameGroup(),group.getUserAdd(), group.getDateTimeAdd());
            }
        });

        return convertView;
    }

    private void ConfirmDelete (final String nameGroup, final String userAdd, final String dateTimeAdd){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Bạn muốn rời khỏi và xóa nhóm chát" + " " + nameGroup + " " + "?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.ReadJSONDelete(nameGroup,userAdd,dateTimeAdd);
            }
        });
        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogDelete.show();
    }

    private void ConfirmLeave (final String userMember, final String nameGroup, final String userAdd , final String dateTimeAdd){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Bạn muốn rời khỏi và xóa nhóm chát" + " " + nameGroup + " " + "?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.ReadJSONLeave(userMember,nameGroup,userAdd,dateTimeAdd);
                context.ReadJSONInsertMessGroup(userMember,nameGroup,userAdd,dateTimeAdd);
            }
        });
        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogDelete.show();
    }
}
