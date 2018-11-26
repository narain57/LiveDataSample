package com.android.livedatasample.adpater;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.livedatasample.R;
import com.android.livedatasample.model.Favorite;
import com.android.livedatasample.viewmodel.FavoriteViewModel;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {
    private List<Favorite> list;
    private FavoriteViewModel mFavViewModel;

    public FavoriteAdapter(List<Favorite> mFav,FavoriteViewModel viewModel) {
        this.list = mFav;
        this.mFavViewModel = viewModel;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new FavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, final int position) {

        holder.name.setText(list.get(position).getName());
        holder.color.setText(list.get(position).getColor());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favourites = list.get(position);
                mFavViewModel.removeFav(favourites.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FavViewHolder extends RecyclerView.ViewHolder {
        TextView name,color;
        ImageButton delete;
        public FavViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            color = itemView.findViewById(R.id.color);
            delete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
