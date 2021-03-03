package com.example.nghia.maplocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MemberDatingAdapter extends BaseAdapter {

    private MemberDatingActivity context;
    private int layout;
    private List<User> userList;

    public MemberDatingAdapter(MemberDatingActivity context, int layout, List<User> userList) {
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
        TextView txtNameUser, txtEmailUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView             = inflater.inflate(layout,null);

            holder.txtNameUser                    = convertView.findViewById(R.id.txtNameUser);
            holder.txtEmailUser                   = convertView.findViewById(R.id.txtEmailUser);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final User user = userList.get(position);

        holder.txtNameUser.setText(user.getGivenName() +" "+ user.getName());
        holder.txtEmailUser.setText(user.getEmail());


        return convertView;
    }
}
