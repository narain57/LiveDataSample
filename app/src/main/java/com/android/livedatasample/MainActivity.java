package com.android.livedatasample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.android.livedatasample.adpater.FavoriteAdapter;
import com.android.livedatasample.model.Favorite;
import com.android.livedatasample.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private List<Favorite> mFav;
    private FavoriteAdapter mFavAdapter;
    private FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        final Observer<List<Favorite>> observer = new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable final List<Favorite> favorites) {
                if (mFav == null) {
                    mFav = new ArrayList<>();
                    mFav.addAll(favorites);
                    mFavAdapter = new FavoriteAdapter(mFav,favoriteViewModel);
                    recyclerView.setAdapter(mFavAdapter);
                }else {
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                        @Override
                        public int getOldListSize() {
                            return mFav.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return favorites.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return mFav.get(oldItemPosition).getName() ==
                                    favorites.get(newItemPosition).getName();
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            Favorite oldFav = mFav.get(oldItemPosition);
                            Favorite newFav = favorites.get(newItemPosition);
                            return oldFav.equals(newFav);
                        }
                    });
                    mFav.clear();
                    mFav.addAll(favorites);
                    result.dispatchUpdatesTo(mFavAdapter);
                    mFavAdapter.notifyDataSetChanged();
                }
            }
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nameView = new EditText(MainActivity.this);
                final EditText colorView = new EditText(MainActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("New favourite")
                        .setMessage("Add a fruit below")
                        .setView(nameView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = String.valueOf(nameView.getText());
                                String color = String.valueOf(colorView.getText());

                                favoriteViewModel.addFav(name, color);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        favoriteViewModel.getFavs().observe(this, observer);
    }
}
