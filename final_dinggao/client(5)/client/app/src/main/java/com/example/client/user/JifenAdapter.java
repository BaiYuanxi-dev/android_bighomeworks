package com.example.client.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.client.R;


import com.example.client.databinding.ItemJifenBinding;

import java.util.ArrayList;

public class JifenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int i;
    private Context mContext;
    private ArrayList<Jifen> mDatas;
    private OnRecyclerItemClickListener listener;

    public JifenAdapter(Context context, ArrayList<Jifen> datas) {
        this.i = 0;
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemJifenBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_jifen, parent, false);
        return new JifenHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Jifen model = mDatas.get(position);
        JifenHolder vh = (JifenHolder) holder;

        vh.binding.tvTeam.setText(mDatas.get(i).getTeam());
        vh.binding.tvTotle.setText(mDatas.get(i).getSum());
        vh.binding.tvWin.setText(mDatas.get(i).getWin());
        vh.binding.tvLose.setText(mDatas.get(i).getLose());
        vh.binding.tvAverage.setText(mDatas.get(i).getEqual());
        vh.binding.tvBall.setText(mDatas.get(i).getJinqiu());
        vh.binding.tvJifen.setText(mDatas.get(i).getScore());

        i++;

        if (position % 2 == 1) {
            vh.binding.llyItemParent.setBackgroundResource(R.color.main_bg_two);
        }else {
            vh.binding.llyItemParent.setBackgroundResource(R.color.main_bg);
        }
    }

    @Override
    public int getItemCount() {
//        return 5;
        return mDatas.size();
    }


    private class JifenHolder extends RecyclerView.ViewHolder {

        ItemJifenBinding binding;

        private JifenHolder(ItemJifenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(JifenAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemClicked(JifenAdapter adapter, int position);
    }

}
