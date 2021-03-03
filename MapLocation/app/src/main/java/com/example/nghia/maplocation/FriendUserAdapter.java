package com.example.nghia.maplocation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FriendUserAdapter extends BaseAdapter {

    private /*Context thành tên màn hình*/ FriendActivity context;
    private int layout;
    private List<User> userList;

    public FriendUserAdapter(FriendActivity context, int layout, List<User> userList) {
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
        TextView txtGivenName, txtName, txtEmail, txtOnlOff;
        ImageView imgDelete,imgLocation, imgChat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView             = inflater.inflate(layout,null);
            holder.txtGivenName     = convertView.findViewById(R.id.txtGivenName);
//            holder.txtName      = convertView.findViewById(R.id.txtName);
            holder.txtEmail         = convertView.findViewById(R.id.txtEmail);
            holder.txtOnlOff        = convertView.findViewById(R.id.txtOnlOff);
            holder.imgDelete        = convertView.findViewById(R.id.imgDelete);
            holder.imgLocation      = convertView.findViewById(R.id.imgLocation);
            holder.imgChat          = convertView.findViewById(R.id.imgChat);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final User user = userList.get(position);

        holder.txtGivenName.setText(user.getGivenName() +" "+ user.getName());
//        holder.txtName.setText(user.getName());
        holder.txtEmail.setText("Email: "+user.getEmail());

        if (user.getOnlOff() == 1){

            holder.txtOnlOff.setText("Online");
            holder.txtOnlOff.setTypeface(holder.txtOnlOff.getTypeface(),Typeface.BOLD_ITALIC);

            holder.txtOnlOff.setTextColor(Color.BLUE);
            holder.txtGivenName.setTextColor(Color.BLACK);
            holder.txtEmail.setTextColor(Color.BLACK);

            holder.imgLocation.setEnabled(true);

        }else if (user.getOnlOff() == 0){

            holder.txtOnlOff.setText("Offline");

            holder.txtOnlOff.setTextColor(Color.GRAY);
            holder.txtGivenName.setTextColor(Color.GRAY);
            holder.txtEmail.setTextColor(Color.GRAY);

            holder.imgLocation.setEnabled(false);
        }

        holder.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, user.getEmail() +" "+ user.getLatitude() +" "+ user.getLongitude(),
//                        Toast.LENGTH_SHORT).show();
                MainActivity.AddMarker(user.getLatitude(),user.getLongitude(),user.getGivenName() +" "+ user.getName(),user.getEmail());
                context.onBackPressed();
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDelete(user.getEmail(),user.getGivenName() +" "+ user.getName());
            }
        });

        holder.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.IntentMess(user.getEmail());
            }
        });
        return convertView;
    }

    //xác nhận xóa
    private void ConfirmDelete (final String email, String name){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Bạn có chắc muốn xóa" + " " + name + " " + "không ?");
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
