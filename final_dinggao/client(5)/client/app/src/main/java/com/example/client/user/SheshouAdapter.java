package com.example.client.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.client.R;


import com.example.client.databinding.ItemSheshouBinding;

import java.util.ArrayList;

public class SheshouAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int i;
    private Context mContext;
    private ArrayList<Sheshou> mDatas;
    private OnRecyclerItemClickListener listener;

    public SheshouAdapter(Context context, ArrayList<Sheshou> datas) {
        this.i = 0;
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSheshouBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sheshou, parent, false);
        return new SheshouHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Sheshou model = mDatas.get(position);
        SheshouHolder vh = (SheshouHolder) holder;
       //射手榜球队名
        vh.binding.tvTeam.setText(mDatas.get(i).getTeam());
        vh.binding.tvPlayer.setText(mDatas.get(i).getName());
        vh.binding.tvBall.setText(mDatas.get(i).getJinqiu());
        vh.binding.tvDian.setText(mDatas.get(i).getDianqiu());
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

    private class SheshouHolder extends RecyclerView.ViewHolder {

        ItemSheshouBinding binding;

        private SheshouHolder(ItemSheshouBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(SheshouAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemClicked(SheshouAdapter adapter, int position);
    }

}
