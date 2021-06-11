package com.zheng.bottommenus.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.zheng.bottommenus.R;
import com.zheng.bottommenus.adapter.GridViewPagerAdapter;
import com.zheng.bottommenus.adapter.MenuAdapter;
import com.zheng.bottommenus.bean.Menu;
import com.zheng.bottommenus.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: zhengjh
 * desc:底部菜单列表自定义View
 * date:2021/6/7
 */
public class BottomMenusView extends ConstraintLayout {

    private static final String TAG = "BottomMenusView";

    private Context mContext;
    //默认行数
    private static final int DEFAULT_ROWS = 2;
    //默认列数
    private static final int DEFAULT_COLUMNS = 4;

    //菜单圆点指示器布局
    private LinearLayout llMenusIndicator;
    //菜单页ViewPager
    private ViewPager vpMenu;
    //指示器圆点
    private ImageView[] ivIndicators;
    //每页菜单行数
    private int mMenuRows;
    //每页菜单列数
    private int mMenuColumns;
    //菜单宽度
    private int mMenuIconWidth;
    //菜单宽度
    private int mMenuIconHeight;
    //菜单字体大小
    private int mMenuTextSize;
    //菜单字体颜色
    private int mMenuTextColor;
    //菜单列表
    private List<Menu> mListMenu = new ArrayList<>();
    //菜单总页数
    private int mPageSize;
    //指示器选中drawable
    private Drawable mIndicatorSelectDrawable;
    //指示器未选中drawable
    private Drawable mIndicatorUnSelectDrawable;
    //GridView菜单适配器
    private GridViewPagerAdapter mMenuPagerAdapter;
    //菜单交互事件回调
    private OnMenuListener onMenuListener;

    public BottomMenusView(Context context) {
        super(context);
    }

    public BottomMenusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public BottomMenusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_menus_view, this, true);
        llMenusIndicator = findViewById(R.id.ll_menus_indicator);
        vpMenu = findViewById(R.id.vp_menus);

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomMenusView);
        //每页菜单的行数和列数
        mMenuRows = mTypedArray.getInt(R.styleable.BottomMenusView_page_menu_rows, DEFAULT_ROWS);
        mMenuColumns = mTypedArray.getInt(R.styleable.BottomMenusView_page_menu_columns, DEFAULT_COLUMNS);
        //菜单的icon大小
        mMenuIconWidth = mTypedArray.getDimensionPixelSize(R.styleable.BottomMenusView_menu_icon_width, ConvertUtils.dp2px(mContext, 64));
        mMenuIconHeight = mTypedArray.getDimensionPixelSize(R.styleable.BottomMenusView_menu_icon_height, ConvertUtils.dp2px(mContext, 64));
        //菜单的字体大小和颜色
        mMenuTextSize = mTypedArray.getDimensionPixelSize(R.styleable.BottomMenusView_menu_text_size, ConvertUtils.sp2px(mContext, 14));
        mMenuTextColor = mTypedArray.getColor(R.styleable.BottomMenusView_menu_text_color, Color.BLACK);
        //指示器drawable
        mIndicatorSelectDrawable = mTypedArray.getDrawable(R.styleable.BottomMenusView_indicator_select);
        mIndicatorUnSelectDrawable = mTypedArray.getDrawable(R.styleable.BottomMenusView_indicator_unselect);
        mTypedArray.recycle();
    }

    public void setDatas(List<Menu> listMenu) {
        if(mListMenu != null) {
            mListMenu = listMenu;
            int menuCountPerPage = mMenuColumns * mMenuRows;
            int menuSum = listMenu.size();
            //总页数等于总菜单个数/每页菜单数 + 总菜单数/每页菜单数的余数
            mPageSize = menuSum / menuCountPerPage + (menuSum % menuCountPerPage > 0 ?  1 : 0);
            List<GridView> listGridViews = new ArrayList<>();
            Log.d(TAG, "setDatas mPageSize =" + mPageSize + ", menuCountPerPage=" + menuCountPerPage + ", menuSum=" + menuSum);
            //初始化指示器圆点
            ivIndicators = new ImageView[mPageSize];
            for (int i = 0; i < mPageSize; i++) {
                if(mPageSize > 1) {
                    //添加指示器圆点
                    ivIndicators[i] = new ImageView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
                    params.setMargins(10, 0, 10, 0);
                    ivIndicators[i].setLayoutParams(params);
                    if(i == 0) {
                        //第一个页面为选中状态
                        ivIndicators[i].setBackground(mIndicatorSelectDrawable);
                    } else {
                        ivIndicators[i].setBackground(mIndicatorUnSelectDrawable);
                    }
                    llMenusIndicator.addView(ivIndicators[i]);
                }
                //当前页的菜单列表
                List<Menu> listCurPageMenu;
                if(i == mPageSize - 1) {
                    //最后一页
                    listCurPageMenu = mListMenu.subList(i * menuCountPerPage, mListMenu.size());
                } else {
                    listCurPageMenu = mListMenu.subList(i * menuCountPerPage, (i + 1) * menuCountPerPage);
                }
                Log.d(TAG, "setDatas i = " + i + ", listCurPageMenu.size() = " + listCurPageMenu.size());
                //添加菜单页
                GridView gridView = new GridView(mContext);
                gridView.setNumColumns(mMenuColumns);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(onMenuListener != null) {
                            onMenuListener.onClickMenu(listCurPageMenu.get(position));
                        }
                    }
                });
                gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if(onMenuListener != null) {
                            onMenuListener.onLongClickMenu(listCurPageMenu.get(position));
                        }
                        return true;
                    }
                });
                //初始化菜单Adapter
                MenuAdapter menuAdapter = new MenuAdapter(mContext, listCurPageMenu, mMenuIconWidth, mMenuIconHeight, mMenuTextSize, mMenuTextColor);
                gridView.setAdapter(menuAdapter);
                listGridViews.add(gridView);
            }
            mMenuPagerAdapter = new GridViewPagerAdapter(listGridViews);
            vpMenu.setAdapter(mMenuPagerAdapter);
            vpMenu.setCurrentItem(0);
            vpMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(mPageSize > 1) {
                        for(int i = 0; i < ivIndicators.length; i ++) {
                            if(position == i) {
                                ivIndicators[i].setBackground(mIndicatorSelectDrawable);
                            } else {
                                ivIndicators[i].setBackground(mIndicatorUnSelectDrawable);
                            }
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }

    public interface OnMenuListener {
        void onClickMenu(Menu menu);
        void onLongClickMenu(Menu menu);
    }

    public void setOnMenuListener(OnMenuListener listener) {
        this.onMenuListener = listener;
    }

}
