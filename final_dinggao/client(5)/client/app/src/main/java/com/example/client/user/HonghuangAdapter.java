package com.example.client.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.client.R;

import com.example.client.databinding.ItemHonghuangBinding;

import java.util.ArrayList;

public class HonghuangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int i;
    private Context mContext;
    private ArrayList<Honghuang> mDatas;
    private OnRecyclerItemClickListener listener;

    public HonghuangAdapter(Context context, ArrayList<Honghuang> datas) {
        this.i = 0;
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHonghuangBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_honghuang, parent, false);
        return new HonghuangHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Honghuang model = mDatas.get(position);
        HonghuangHolder vh = (HonghuangHolder) holder;
        vh.binding.tvTeam.setText(mDatas.get(i).getTeam());
        vh.binding.tvPlayer.setText(mDatas.get(i).getName());
        vh.binding.tvCount.setText(mDatas.get(i).getYellow().equals("0") ? mDatas.get(i).getRed() : mDatas.get(i).getYellow());
        i++;

        if (position % 2 == 1) {
            vh.binding.llyItemParent.setBackgroundResource(R.color.main_bg_two);
        }else {
            vh.binding.llyItemParent.setBackgroundResource(R.color.main_bg);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class HonghuangHolder extends RecyclerView.ViewHolder {

        ItemHonghuangBinding binding;

        private HonghuangHolder(ItemHonghuangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(HonghuangAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemClicked(HonghuangAdapter adapter, int position);
    }

}
