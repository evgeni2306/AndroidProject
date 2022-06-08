package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatListClassAdapter extends RecyclerView.Adapter<ChatListClassAdapter.ViewHolder> {

    interface OnStateClickListener {
        void onStateClick(ChatList ListClass, int position);
    }

    private final OnStateClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<ChatList> userMessages;

    ChatListClassAdapter(Context context, List<ChatList> chatLists, OnStateClickListener onClickListener) {
        this.userMessages = chatLists;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @Override
    public ChatListClassAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.chat, parent, false);
        return new ChatListClassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatListClassAdapter.ViewHolder holder, int position) {
        ChatList chatlistt = userMessages.get(position);
        holder.nameView.setText(chatlistt.getName());
        holder.surnameView.setText(chatlistt.getSurname());
        holder.idView.setText(chatlistt.getid());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onStateClick(chatlistt, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, surnameView, idView;

        ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.ChatName);
            surnameView = view.findViewById(R.id.ChatSurname);

            idView = view.findViewById(R.id.ChatId);

        }
    }
}
