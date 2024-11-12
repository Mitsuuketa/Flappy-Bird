package com.mobdeve.myflappybird;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder> {

    private Context context;
    private List<Integer> avatarImages;
    private List<String> avatarNames;

    public AvatarAdapter(Context context, List<Integer> avatarImages, List<String> avatarNames) {
        this.context = context;
        this.avatarImages = avatarImages;
        this.avatarNames = avatarNames;
    }

    @Override
    public AvatarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_avatar, parent, false);
        return new AvatarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AvatarViewHolder holder, int position) {
        holder.ivAvatar.setImageResource(avatarImages.get(position));
        holder.tvAvatarName.setText(avatarNames.get(position));
    }

    @Override
    public int getItemCount() {
        return avatarImages.size();
    }

    public static class AvatarViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvAvatarName;

        public AvatarViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvAvatarName = itemView.findViewById(R.id.tv_avatar_name);
        }
    }
}
