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

public class GroupPeopleAdapter extends BaseAdapter {
    private /*Context thành tên màn hình*/ GroupPeopleActivity context;
    private int layout;
    private List<User> userList;

    public GroupPeopleAdapter(GroupPeopleActivity context, int layout, List<User> userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }


    @Override
    public int getCount() {
        return userList.size();
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
        TextView txtName, txtEmailFriend;
        ImageView imgDeleteFriendGroup;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView             = inflater.inflate(layout,null);

            holder.txtName                    = convertView.findViewById(R.id.txtName);
            holder.txtEmailFriend             = convertView.findViewById(R.id.txtEmailFriend);
            holder.imgDeleteFriendGroup          = convertView.findViewById(R.id.imgDeleteFriendGroup);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final User user = userList.get(position);

        holder.txtName.setText(user.getGivenName() +" "+ user.getName());
//        holder.txtName.setText(user.getName());
        holder.txtEmailFriend.setText("Email: "+user.getEmail());


        holder.imgDeleteFriendGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDeleteMember(user.getEmail());
//                Toast.makeText(context, user.getEmail() +" "+ user.getLatitude() +" "+ user.getLongitude(),
//                        Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
    private void ConfirmDeleteMember (final String userMember){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Bạn muốn xóa" + " " + userMember + " " + "khỏi nhóm ?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.ReadJSONDeletePeopleGroup(userMember);
                context.ReadJSONInsertMessGroup(userMember);
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
