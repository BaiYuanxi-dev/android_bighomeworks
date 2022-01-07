package com.example.client.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import com.example.client.R;


public class myRVAdapterNew extends RecyclerView.Adapter<myRVAdapterNew.MyTVHolder> {

    private final groupGame[] mArray;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    private myRVAdapterNew.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public myRVAdapterNew(Context context, String mes) {

        String[] strs1 = mes.split("#");
        mArray = new groupGame[strs1.length];
        for(int i = 0; i < strs1.length; i++){
            String[] strs2 = strs1[i].split(",");
            mArray[i] = new groupGame(strs2[0],strs2[1],strs2[2],strs2[3],strs2[4],strs2[5],strs2[6]);
        }

//        mArray = new groupGame[]{new groupGame("计算机", "运输", "0","2","2021", "进行中", "决赛"),
//                new groupGame("运输", "电信", "5","4","2021", "进行中", "小组赛"),
//                new groupGame("计算机", "土建", "1","0","2021", "未开始", "小组赛"),
//                new groupGame("运输", "土建", "8","7","2021", "已结束", "决赛")};

        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public MyTVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyTVHolder(mLayoutInflater.inflate(R.layout.cardviewitemnew, parent, false));
    }

    public void setOnItemClickListener(myRVAdapterNew.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onBindViewHolder(MyTVHolder holder, int position) {
        holder.gameTeam.setText(mArray[position].team);
        holder.gameTeam1.setText(mArray[position].team1);
        holder.gameScore.setText(mArray[position].score);
        holder.gameScore1.setText(mArray[position].score1);
        holder.gameYear.setText(mArray[position].year);
        holder.gameState.setText(mArray[position].state);
        holder.gameState1.setText(mArray[position].state1);

//        if(mArray[position].state.equals("进行中")){
//            holder.record.setVisibility(View.VISIBLE);
//        }
//        else if(mArray[position].state.equals("未开始")){
//            holder.modify.setVisibility(View.VISIBLE);
//            holder.begin.setVisibility(View.VISIBLE);
//        }

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

    public class MyTVHolder extends RecyclerView.ViewHolder {
        TextView gameTeam;
        TextView gameTeam1;
        TextView gameScore;
        TextView gameScore1;
        TextView gameYear;
        TextView gameState;
        TextView gameState1;
        MaterialButton record;
        MaterialButton modify;
        MaterialButton begin;

        public MyTVHolder(View itemView) {
            super(itemView);
            gameTeam = (TextView) itemView.findViewById(R.id.game_team);
            gameTeam1 = (TextView) itemView.findViewById(R.id.game_team1);
            gameScore = (TextView) itemView.findViewById(R.id.game_score);
            gameScore1 = (TextView) itemView.findViewById(R.id.game_score1);
            gameYear = (TextView) itemView.findViewById(R.id.game_year);
            gameState = (TextView) itemView.findViewById(R.id.game_state);
            gameState1 = (TextView) itemView.findViewById(R.id.game_state1);
            record=itemView.findViewById(R.id.game_record);
            modify=itemView.findViewById(R.id.game_modify);
            begin=itemView.findViewById(R.id.game_begin);
        }
    }
}
