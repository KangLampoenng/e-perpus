package com.example.e_perpus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private OnItemClickCallback onItemClickCallback;
    Context context;
    ArrayList<Books> dataBooks;

    public RvAdapter(ArrayList<Books> dataBooks) {
        this.dataBooks = dataBooks;
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int position) {
        holder.bindData(dataBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return dataBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        public void  bindData(final Books data){
            TextView tvTitle = itemView.findViewById(R.id.tv_title);
            TextView tvAuthor = itemView.findViewById((R.id.tv_author));
            tvTitle.setText(data.name);
            tvAuthor.setText(data.author);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(data);
                }
            });
        }
    }

    public void setOnItemClickCallback (OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback{
        void onItemClicked(Books data);
    }
}
