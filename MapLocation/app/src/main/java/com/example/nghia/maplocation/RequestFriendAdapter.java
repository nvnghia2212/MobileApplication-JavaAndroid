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

public class RequestFriendAdapter extends BaseAdapter {

    private /*Context thành tên màn hình*/ RequestFriendActivity context;
    private int layout;
    private List<User> userList;

    public RequestFriendAdapter(RequestFriendActivity context, int layout, List<User> userList) {
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
        TextView txtGivenName, txtEmail;
        ImageView imgDelete, imgAdd;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView         = inflater.inflate(layout,null);
            holder.txtGivenName = convertView.findViewById(R.id.txtGivenName);
//            holder.txtName      = convertView.findViewById(R.id.txtName);
            holder.txtEmail     = convertView.findViewById(R.id.txtEmail);
//            holder.txtOnlOff    = convertView.findViewById(R.id.txtOnlOff);
            holder.imgDelete    = convertView.findViewById(R.id.imgDelete);
            holder.imgAdd       = convertView.findViewById(R.id.imgAdd);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final User user = userList.get(position);

        holder.txtGivenName.setText(user.getGivenName() +" "+ user.getName());
//        holder.txtName.setText(user.getName());
        holder.txtEmail.setText("Email: "+user.getEmail());

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriend(user.getEmail(), user.getGivenName() +" "+ user.getName());
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDelete(user.getEmail(),user.getGivenName() +" "+ user.getName());
            }
        });
        return convertView;
    }
    private void AddFriend (final String email, String name){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Bạn có chắc muốn thêm" + " " + name + " " + "làm bạn không ?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.ReadJSONOkRequestFriend(email);
            }
        });
        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogDelete.show();
    }
    private void ConfirmDelete (final String email, String name){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Bạn có chắc muốn xóa yêu cầu kết bạn của" + " " + name + " " + "không ?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.ReadJSONDelete(email);
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
