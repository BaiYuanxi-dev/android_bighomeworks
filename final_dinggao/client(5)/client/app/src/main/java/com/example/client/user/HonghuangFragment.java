package com.example.client.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.client.R;

import com.example.client.databinding.FragmentHonghuangBinding;

import java.util.ArrayList;


/**
 * 红黄
 * version: 1.0
 */
public class HonghuangFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHonghuangBinding mBinding;
    /**
     * 适配器
     */
    private HonghuangAdapter honghuangAdapter;
    /**
     * 红黄牌数据源
     */
    private ArrayList<Honghuang> datas = new ArrayList<>();


    public HonghuangFragment() {
        // Required empty public constructor
    }

    /**
     * fragment 初始化
     * @return
     */
    public static HonghuangFragment newInstance(String mes) {
        HonghuangFragment fragment = new HonghuangFragment();
        Bundle args = new Bundle();
        args.putString("key", mes);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 加载布局
     * @param inflater
     * @param container
     * @return
     */
    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_honghuang, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String mes = getArguments().getString("key");

        String[] strs0 = mes.split("&");
        for(int i = 0; i < strs0.length; i++){
            String[] strs1 = strs0[i].split("#");
            for(int j = 0; j < strs1.length; j++){
                String[] strs2 = strs1[j].split(",");
                if(strs1.length > 1 && strs2[0].equals("null")) continue;
                if(i == 0){
                    datas.add(new Honghuang(strs2[0],strs2[1],strs2[2],"0"));
                }else{
                    datas.add(new Honghuang(strs2[0],strs2[1],"0",strs2[2]));
                }
            }
        }

        //列表适配器初始化
        honghuangAdapter = new HonghuangAdapter(getActivity(), datas);
        honghuangAdapter.setOnItemClickListener(new HonghuangAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(HonghuangAdapter adapter, int position) {

            }
        });
        mBinding.rcvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.rcvList.setAdapter(honghuangAdapter);

        mBinding.llyYellow.setOnClickListener(this);
        mBinding.llyRed.setOnClickListener(this);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lly_yellow:
                mBinding.vRed.setVisibility(View.INVISIBLE);
                mBinding.vYellow.setVisibility(View.VISIBLE);
                break;
            case R.id.lly_red:
                mBinding.vRed.setVisibility(View.VISIBLE);
                mBinding.vYellow.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }
}
