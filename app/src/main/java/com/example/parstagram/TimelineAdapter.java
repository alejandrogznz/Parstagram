package com.example.parstagram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseFile;
import com.parse.ui.widget.ParseImageView;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder>{
    Context context;
    List<Post> posts;

    public TimelineAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void add(Post post) {
        posts.add(post);
        notifyDataSetChanged();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ParseImageView ivContent;
        TextView  tvUsername;
        TextView  tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivContent = itemView.findViewById(R.id.ivContent);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Post post) {
            tvUsername.setText("@" + post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            ivContent.setParseFile(post.getImage());
            ParseFile image = post.getImage();
            if (image != null){
                ivContent.setParseFile(image);
                ivContent.loadInBackground();
            }
        }
    }
}
