package com.devta.githubsample.ui.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.devta.githubsample.R;
import com.devta.githubsample.data.rest.pojo.GitHubResponse;
import com.devta.githubsample.databinding.LayoutItemBinding;
import com.devta.headstart.helpers.ValidationHelper;

import java.util.ArrayList;

/**
 * Created on : May, 01, 2020 at 16:19
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoVH> {

    private ArrayList<GitHubResponse.ItemsBean> items;

    public RepoAdapter(ArrayList<GitHubResponse.ItemsBean> items) {
        if(items == null) items = new ArrayList<>();
        this.items = items;
    }

    public void addMoreItems(ArrayList<GitHubResponse.ItemsBean> items) {
        if(items == null || items.isEmpty()) return;
        int oldCount = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(oldCount, this.items.size());
    }

    public void onRefresh(ArrayList<GitHubResponse.ItemsBean> items) {
        this.items.clear();
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RepoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_item, parent, false);
        return new RepoVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoVH holder, int position) {
        GitHubResponse.ItemsBean item = items.get(holder.getAdapterPosition());

        holder.binding.viewTitle.setText(ValidationHelper.optional(item.getFullName()));
        holder.binding.viewDescription.setText(ValidationHelper.optional(item.getDescription()));

        if(TextUtils.isEmpty(item.getOwner().getAvatar_url())) {
            holder.binding.viewImage.setImageDrawable(
                    ContextCompat.getDrawable(
                            holder.binding.viewImage.getContext(),
                            R.drawable.default_placeholder)
            );
        }else {
            Glide.with(holder.binding.viewImage.getContext())
                    .load(item.getOwner().getAvatar_url())
                    .placeholder(R.drawable.default_placeholder)
                    .fitCenter()
                    .transform(new RoundedCorners(5))
                    .into(holder.binding.viewImage);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class RepoVH extends RecyclerView.ViewHolder {
        private LayoutItemBinding binding;
        public RepoVH(@NonNull LayoutItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
