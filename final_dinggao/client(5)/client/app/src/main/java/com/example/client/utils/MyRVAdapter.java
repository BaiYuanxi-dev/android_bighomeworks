package com.example.client.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.client.R;

import java.util.ArrayList;
import java.util.List;


public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.MyTVHolder> {

    private final game[] mArray;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public MyRVAdapter(Context context, String mes) {

        String[] strs1 = mes.split("#");
        mArray = new game[strs1.length];
        for(int i = 0; i < strs1.length; i++){
            String[] strs2 = strs1[i].split(",");
            mArray[i] = new game(strs2[0],strs2[1],strs2[2],strs2[3],strs2[4]);
        }

        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }



    @Override
    public MyTVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyTVHolder(mLayoutInflater.inflate(R.layout.cardviewitem, parent, false));
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onBindViewHolder(MyTVHolder holder, int position) {
        holder.gameName.setText(mArray[position].name);
        holder.gameSponsor.setText(mArray[position].sponsor);
        holder.gameYear.setText(mArray[position].year);
        holder.gameState.setText(mArray[position].state);
        holder.gameState1.setText(mArray[position].state1);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return mArray == null ? 0 : mArray.length;
    }

    public void updateReceive(String mes){

    }

    public class MyTVHolder extends RecyclerView.ViewHolder {

        TextView gameName;
        TextView gameSponsor;
        TextView gameYear;
        TextView gameState;
        TextView gameState1;

        public MyTVHolder(View itemView) {
            super(itemView);
            gameName = (TextView) itemView.findViewById(R.id.game_name);
            gameSponsor = (TextView) itemView.findViewById(R.id.game_sponsor);
            gameYear = (TextView) itemView.findViewById(R.id.game_year);
            gameState = (TextView) itemView.findViewById(R.id.game_state);
            gameState1 = (TextView) itemView.findViewById(R.id.game_state1);
        }
    }

}


