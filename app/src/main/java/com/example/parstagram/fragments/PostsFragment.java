package com.example.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parstagram.Post;
import com.example.parstagram.R;
import com.example.parstagram.TimelineAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    private static final String TAG = "PostsFragment";
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView rvTimeline;
    TimelineAdapter adapter;


    List<Post> timelinePosts;

    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTimeline = view.findViewById(R.id.rvTimeline);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        timelinePosts = new ArrayList<>();
        queryPosts();
        Log.i(TAG, "Size: " + Integer.toString(timelinePosts.size()));

        adapter = new TimelineAdapter(getContext(), timelinePosts);

        rvTimeline.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTimeline.setAdapter(adapter);

        //Swipe Container Listeners
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 queryPosts();
                 swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> retPosts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post: retPosts) {
                    Log.i(TAG, "Post: " + post.getDescription() +", USERNAME: " + post.getUser().getUsername());
                    adapter.clear();
                    adapter.addAll(retPosts);
                }
            }
        });
    }

}
