package com.example.client.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.client.R;

import com.example.client.databinding.FragmentSheshouBinding;
import com.example.client.utils.game;

import java.util.ArrayList;


/**
 * 射手榜
 * version: 1.0
 */
public class SheshouFragment extends BaseFragment {

    private FragmentSheshouBinding mBinding;
    /**
     * 射手榜数据源
     */
    private ArrayList<Sheshou> datas = new ArrayList<>();;
    /**
     * 适配器
     */
    private SheshouAdapter sheshouAdapter;

    public SheshouFragment() {
        // Required empty public constructor
    }

    /**
     * fragment 初始化
     * @return
     */
    public static SheshouFragment newInstance(String mes) {
        SheshouFragment fragment = new SheshouFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sheshou, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String mes = getArguments().getString("key");

        String[] strs1 = mes.split("#");
        for(int i = 0; i < strs1.length; i++){
            String[] strs2 = strs1[i].split(",");
            if(strs2.length > 1 && strs2[0].equals("null")) continue;
            datas.add(new Sheshou(strs2[0],strs2[1],strs2[2],strs2[3]));
        }

        System.out.println(datas);
        //列表适配器初始化
        sheshouAdapter = new SheshouAdapter(getActivity(), datas);
        sheshouAdapter.setOnItemClickListener(new SheshouAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(SheshouAdapter adapter, int position) {

            }
        });
        mBinding.rcvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.rcvList.setAdapter(sheshouAdapter);
    }

}
