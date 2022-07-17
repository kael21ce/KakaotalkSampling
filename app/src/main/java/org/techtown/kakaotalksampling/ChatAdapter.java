package org.techtown.kakaotalksampling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    ArrayList<Chat> items=new ArrayList<Chat>();

    public interface OnItemClickListener {
        void onItemClicked(int position, String name);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener (OnItemClickListener listener) {
        itemClickListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.chat_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Chat item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chatTitle;
        TextView lastMessage;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);

            chatTitle=itemView.findViewById(R.id.chatTitle);
            lastMessage=itemView.findViewById(R.id.last_message);
            date=itemView.findViewById(R.id.date);
        }

        public void setItem(Chat item) {
            chatTitle.setText(item.getName());
            lastMessage.setText(item.getLast_message());
            date.setText(item.getDate());
        }
    }
    public void addItem(Chat item) {
        items.add(item);
    }

    public void setItems(ArrayList<Chat> items) {
        this.items=items;
    }

    public Chat getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Chat item) {
        items.set(position, item);
    }
}
