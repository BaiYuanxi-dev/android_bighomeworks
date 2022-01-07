package com.example.client.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.client.R;


import com.example.client.databinding.FragmentJifenBinding;

import java.util.ArrayList;


/**
 * 积分榜
 *
 */
public class JiFenFragment extends BaseFragment {

    private FragmentJifenBinding mBinding;
    /**
     * 积分榜数据源
     */
    private ArrayList<JifenGroup> datas = new ArrayList<>();
    /**
     * 适配器
     */
    private JifenGroupAdapter jifenGroupAdapter;

    public JiFenFragment() {
        // Required empty public constructor
    }

    /**
     * fragment 初始化
     * @return
     */
    public static JiFenFragment newInstance(String mes) {
        JiFenFragment fragment = new JiFenFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_jifen, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String mes = getArguments().getString("key");

        String[] strs0 = mes.split("&");
        for(int i = 0; i < strs0.length; i++){
            datas.add(new JifenGroup(strs0[i], strs0.length));
        }

        //列表适配器初始化
        jifenGroupAdapter = new JifenGroupAdapter(getActivity(), datas);
        jifenGroupAdapter.setOnItemClickListener(new JifenGroupAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(JifenGroupAdapter adapter, int position) {
                datas.get(position).setOpen(!datas.get(position).isOpen());
                adapter.notifyDataSetChanged();
            }
        });
        mBinding.rcvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.rcvList.setAdapter(jifenGroupAdapter);
    }

}
