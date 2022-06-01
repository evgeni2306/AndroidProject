package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



    public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {

        private final LayoutInflater inflater;
        private final List<UserSearch> userSearches;

        UserSearchAdapter(Context context, List<UserSearch> userSearches) {
            this.userSearches = userSearches;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public UserSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.user_search_result, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UserSearchAdapter.ViewHolder holder, int position) {
            UserSearch userSearch = userSearches.get(position);
            holder.idView.setText(userSearch.getid());
            holder.nameView.setText(userSearch.getName());
            holder.surnameView.setText(userSearch.getSurname());
        }

        @Override
        public int getItemCount() {
            return userSearches.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final TextView nameView, surnameView, idView;

            ViewHolder(View view) {
                super(view);
                nameView = view.findViewById(R.id.UserSearchName);
                surnameView = view.findViewById(R.id.UserSearchSurname);
                idView = view.findViewById(R.id.UserSearchid);
            }
        }
    }

