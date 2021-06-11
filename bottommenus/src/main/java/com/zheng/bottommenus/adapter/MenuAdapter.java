package com.zheng.bottommenus.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zheng.bottommenus.R;
import com.zheng.bottommenus.bean.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * author: zhengjh
 * desc:菜单适配器
 * date:2021/6/7
 */
public class MenuAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater layoutInflater;

    //菜单列表
    private List<Menu> mListMenu = new ArrayList<>();

    private int mIconWidth;
    private int mIconHeight;
    private int mTextSize;
    private int mTextColor;

    public MenuAdapter() {}

    public MenuAdapter(Context context, List<Menu> listMenu) {
        mContext = context;
        mListMenu = listMenu;
        layoutInflater = LayoutInflater.from(mContext);
    }

    public MenuAdapter(Context context, List<Menu> listMenu, int iconWidth, int iconHeight, int textSize, int textColor) {
        this(context, listMenu);
        mIconWidth = iconWidth;
        mIconHeight = iconHeight;
        mTextSize = textSize;
        mTextColor = textColor;
    }

    @Override
    public int getCount() {
        return mListMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return mListMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.layout_menu_item, null);
        //设置图片
        ImageView menuView = convertView.findViewById(R.id.iv_menu);
        setIconSize(menuView, mIconWidth, mIconHeight);
        menuView.setBackgroundResource(mListMenu.get(position).getIcon());
        //设置文字
        TextView titleView = convertView.findViewById(R.id.tv_name);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        titleView.setTextColor(mTextColor);
        titleView.setText(mListMenu.get(position).getName());
        return convertView;
    }

    private void setIconSize(ImageView imageView, int width, int height) {
        if(imageView != null) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = width;
            params.height = height;
            imageView.setLayoutParams(params);
        }
    }
}
