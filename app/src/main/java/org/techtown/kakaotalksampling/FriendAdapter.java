package org.techtown.kakaotalksampling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    ArrayList<Friend> items = new ArrayList<Friend>();

    public interface OnItemClickListener {
        void onItemClicked(int position, String name, String fstate);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener (OnItemClickListener listener) {
        itemClickListener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.friend_item, viewGroup, false);

        FriendAdapter.ViewHolder viewHolder = new FriendAdapter.ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name="";
                String fstate="";
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    name=items.get(position).getName();
                    fstate=items.get(position).getStateMessage();
                }
                itemClickListener.onItemClicked(position, name, fstate);
            }
        });

        return viewHolder;
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
        ImageView profile;

        public ViewHolder(View itemView) {
            super(itemView);

            friendName=itemView.findViewById(R.id.fName);
            fstateMessage=itemView.findViewById(R.id.fstateMessage);
            profile=itemView.findViewById(R.id.friendProfile);
        }

        public void setItem(Friend item) {
            friendName.setText(item.getName());
            fstateMessage.setText(item.getStateMessage());
            profile.setImageBitmap(item.getProfile());
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
