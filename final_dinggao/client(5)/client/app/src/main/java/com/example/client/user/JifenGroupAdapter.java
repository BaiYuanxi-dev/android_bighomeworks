package com.example.client.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.client.R;
import com.example.client.databinding.ItemJifenGroupBinding;


import java.util.ArrayList;

public class JifenGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int i;
    private Context mContext;
    private ArrayList<JifenGroup> mDatas;
    private OnRecyclerItemClickListener listener;

    public JifenGroupAdapter(Context context, ArrayList<JifenGroup> datas) {
        this.i = 0;
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemJifenGroupBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_jifen_group, parent, false);
        return new JifenGroupHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        JifenGroup model = mDatas.get(position);
        JifenGroupHolder vh = (JifenGroupHolder) holder;

        if(i >= mDatas.get(0).getNum()) i = 0;

        switch (i){
            case 0:{
                vh.binding.tvName.setText("GroupA");
                break;
            }
            case 1:{
                vh.binding.tvName.setText("GroupB");
                break;
            }
            case 2:{
                vh.binding.tvName.setText("GroupC");
                break;
            }
            case 3:{
                vh.binding.tvName.setText("GroupD");
                break;
            }
            case 4:{
                vh.binding.tvName.setText("GroupE");
                break;
            }
            case 5:{
                vh.binding.tvName.setText("GroupF");
                break;
            }
            case 6:{
                vh.binding.tvName.setText("GroupG");
                break;
            }
            default:{
                vh.binding.tvName.setText("NONE");
                break;
            }
        }

        vh.binding.ivPoint.setColorFilter(mContext.getResources().getColor(R.color.theme));

        if(model.isOpen()){
            vh.binding.llyDetail.setVisibility(View.VISIBLE);
            vh.binding.ivPoint.setImageResource(R.drawable.point_up);
            vh.binding.tvMore.setText("收起");
        }else {
            vh.binding.llyDetail.setVisibility(View.GONE);
            vh.binding.ivPoint.setImageResource(R.drawable.point_down);
            vh.binding.tvMore.setText("更多");
        }

        ArrayList<Jifen> jifens = new ArrayList<>();

        String[] strs0 = mDatas.get(i).getMes().split("#");
        for(int j = 0; j < strs0.length; j++){
            String[] strs1 = strs0[j].split(",");
            jifens.add(new Jifen(strs1[1], strs1[2], strs1[3], strs1[4], strs1[5], strs1[6], strs1[7]));
            System.out.println(j);
            System.out.println(strs1[1]+ strs1[2]+ strs1[3]+ strs1[4]+ strs1[5]+ strs1[6]+ strs1[7]);
        }
        i++;

        JifenAdapter adapter = new JifenAdapter(mContext, jifens);
        adapter.setOnItemClickListener(new JifenAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(JifenAdapter adapter, int position) {

            }
        });
        vh.binding.itemRcvList.setLayoutManager(new LinearLayoutManager(mContext));
        vh.binding.itemRcvList.setAdapter(adapter);
        vh.binding.itemRcvList.setNestedScrollingEnabled(false);
        vh.binding.itemRcvList.setHasFixedSize(true);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class JifenGroupHolder extends RecyclerView.ViewHolder {

        ItemJifenGroupBinding binding;

        private JifenGroupHolder(ItemJifenGroupBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.llyHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(JifenGroupAdapter.this, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onItemClicked(JifenGroupAdapter adapter, int position);
    }

}
