package org.techtown.kakaotalksampling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    ArrayList<Friend> items = new ArrayList<Friend>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.friend_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Friend item = items.get(position);
        viewHolder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView friendName;
        TextView fstateMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            friendName=itemView.findViewById(R.id.fName);
            fstateMessage=itemView.findViewById(R.id.fstateMessage);
        }

        public void setItem(Friend item) {
            friendName.setText(item.getName());
            fstateMessage.setText(item.getStateMessage());
        }
    }

    public void addItem(Friend item) {
        items.add(item);
    }

    public void setItems(ArrayList<Friend> items) {
        this.items=items;
    }

    public Friend getItems(int position) {
        return items.get(position);

    }

    public void setItem(int position, Friend item) {
        items.set(position, item);
    }
}
