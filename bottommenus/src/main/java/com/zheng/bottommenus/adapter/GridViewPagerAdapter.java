package com.zheng.bottommenus.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * 底部菜单页适配器
 * */
public class GridViewPagerAdapter extends PagerAdapter {

    private List<GridView> mListGridViews;

    public GridViewPagerAdapter(List<GridView> mListGridViews) {
        this.mListGridViews = mListGridViews;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)  {
        container.removeView(mListGridViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        container.addView(mListGridViews.get(position), 0);
        return mListGridViews.get(position);
    }
    @Override
    public int getCount() {
        return  mListGridViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
