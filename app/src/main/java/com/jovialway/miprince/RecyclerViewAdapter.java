package com.jovialway.miprince;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    static final int TYPE=1;
    Context mContext;
    LayoutInflater inflater;
    List<Model> modellist = new ArrayList<>();
    List<Model> tempModelList = new ArrayList<>();


    RecyclerViewAdapter(Context context, List<Model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView surahName;
        TextView surah;
        View parentView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.parentView = itemView;
            this.surahName = itemView.findViewById(R.id.surahNameTV);
            this.surah = itemView.findViewById(R.id.surahTV);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE:
            default:
                View layoutview=LayoutInflater.from(mContext).inflate(R.layout.item_view, parent, false);
                return new ViewHolder(layoutview);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        int viewtype=getItemViewType(position);
        switch (viewtype){
            case TYPE:
            default:
                ViewHolder itemviewHolder=(ViewHolder) holder;
                Model model=(Model) modellist.get(position);
                itemviewHolder.surahName.setText(model.getSurahName());
                itemviewHolder.surah.setText(model.getSurah());

        }
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, ViewActivity.class);
                intent.putExtra("surahName", ((Model) modellist.get(position)).getSurahName()).toString();
                intent.putExtra("surah", ((Model) modellist.get(position)).getSurah()).toString();

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modellist.size();
    }


    //filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (tempModelList.size() < 1){
            tempModelList.addAll(this.modellist);
        }

        modellist.clear();
        if (charText.length() == 0) {
            modellist.addAll(tempModelList);
        } else {
            for (Model model : tempModelList) {
                if (model.getSurahName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    modellist.add(model);
                } else if (model.getSurah().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }

}
