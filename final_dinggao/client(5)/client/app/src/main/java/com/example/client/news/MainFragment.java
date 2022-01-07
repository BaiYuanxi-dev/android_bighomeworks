package com.example.client.news;

import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.client.utils.TimeCount;
import com.jude.rollviewpager.RollPagerView;
import java.util.ArrayList;
import java.util.List;
import com.example.client.R;

public class MainFragment extends Fragment {

    private View view;

    private ViewPager viewPager;

    private List<String> titleList;
    private List<Fragment> fragmentList;

    private FragmentAdapter fragmentAdapter;

    private TechFragment tech_fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        fragmentChange();
        TimeCount.getInstance().setTime(System.currentTimeMillis());
        return view;
    }

    private void initView() {
        viewPager = view.findViewById(R.id.view_pager);
    }

    private void fragmentChange() {
        fragmentList = new ArrayList<>();

        tech_fragment = new TechFragment();

        fragmentList.add(tech_fragment);

        titleList = new ArrayList<>();
        titleList.add("足球");

        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList, titleList);
        fragmentAdapter.notifyDataSetChanged();
        viewPager.setAdapter(fragmentAdapter);

        //将tabLayout与viewPager连起来
        //tabLayout.setupWithViewPager(viewPager);
    }


}
