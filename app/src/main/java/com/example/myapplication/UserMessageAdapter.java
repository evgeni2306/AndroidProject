
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

    public class UserMessageAdapter  extends RecyclerView.Adapter<UserMessageAdapter.ViewHolder>{
        private final LayoutInflater inflater;
        private final List<UserMessage> userMessages;

        UserMessageAdapter(Context context, List<UserMessage> userMessages) {
            this.userMessages = userMessages;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public UserMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.message, parent, false);
            return new UserMessageAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UserMessageAdapter.ViewHolder holder, int position) {
            UserMessage UserMessage = userMessages.get(position);
            holder.textView.setText(UserMessage.gettext());
            holder.nameView.setText(UserMessage.getName());
            holder.surnameView.setText(UserMessage.getSurname());
        }

        @Override
        public int getItemCount() {
            return userMessages.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final TextView nameView, surnameView, textView;

            ViewHolder(View view) {
                super(view);
                nameView = view.findViewById(R.id.MessageName);
                surnameView = view.findViewById(R.id.MessageSurname);
                textView = view.findViewById(R.id.MessageText);

            }
        }
}
