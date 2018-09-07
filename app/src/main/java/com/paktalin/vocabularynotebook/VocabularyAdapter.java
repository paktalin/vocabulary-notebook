package com.paktalin.vocabularynotebook;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paktalin.vocabularynotebook.pojo.WordItemPojo;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.ViewHolder> {
    private static final String TAG = "VN/" + VocabularyAdapter.class.getSimpleName();
    private List<WordItemPojo> wordItems;

    public VocabularyAdapter(List<WordItemPojo> wordItems) {
        this.wordItems = wordItems;
        Log.d(TAG, "wordItems: " + wordItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WordItemPojo item = wordItems.get(position);
        Log.d(TAG, "bind item: " + item);
        holder.tvWord.setText(item.getWord());
        holder.tvTranslation.setText(item.getTranslation());
    }

    @Override
    public int getItemCount() {
        return wordItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord, tvTranslation;

        ViewHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tvWord);
            tvTranslation = itemView.findViewById(R.id.tvTranslation);
        }
    }
}
