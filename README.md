# BottomMenuView
使用方法示例
====
## 1、项目build.gradle中配置jitpack：
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
## 2、添加版本依赖：
```
dependencies {
	        implementation 'com.github.zhengjh3:BottomMenuView:Tag'
	}
```
## 3、在布局中加入BottomMenuView：
```
<com.zheng.bottommenus.view.BottomMenusView
        android:id="@+id/view_bottom_menus"
        android:layout_width="match_parent"
        android:layout_height="260dp"
        app:page_menu_rows="2"
        app:page_menu_columns="4"
        app:menu_icon_width="66dp"
        app:menu_icon_height="66dp"
        app:menu_text_size="11sp"
        app:menu_text_color="@android:color/holo_red_light"
        app:indicator_select="@drawable/circle_indicator_select"
        app:indicator_unselect="@drawable/circle_indicator_unselect"
        app:layout_constraintBottom_toBottomOf="parent"/>
```
## 4、在代码中构造菜单数据，设置监听事件：
```
 public void initBottomMenus() {
        bottomMenusView = findViewById(R.id.view_bottom_menus);
        List<Menu> listMenu = new ArrayList<>();
        listMenu.add(new Menu("路飞", R.drawable.lufei));
        listMenu.add(new Menu("罗宾", R.drawable.luobin));
        listMenu.add(new Menu("索隆", R.drawable.suolong));
        listMenu.add(new Menu("布鲁克", R.drawable.buluke));
        listMenu.add(new Menu("雷利", R.drawable.leili));
        listMenu.add(new Menu("萨波", R.drawable.sabo));
        listMenu.add(new Menu("路飞", R.drawable.lufei));
        listMenu.add(new Menu("罗宾", R.drawable.luobin));
        listMenu.add(new Menu("索隆", R.drawable.suolong));
        listMenu.add(new Menu("布鲁克", R.drawable.buluke));
        listMenu.add(new Menu("雷利", R.drawable.leili));
        listMenu.add(new Menu("萨波", R.drawable.sabo));
        bottomMenusView.setDatas(listMenu);
        bottomMenusView.setOnMenuListener(new BottomMenusView.OnMenuListener() {
            @Override
            public void onClickMenu(Menu menu) {
                Toast.makeText(MainActivity.this, "点击" + menu.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickMenu(Menu menu) {
                Toast.makeText(MainActivity.this, "长按" + menu.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
```
属性说明
====
```
<declare-styleable name ="BottomMenusView">
        <!--指示器选中-->
        <attr name="indicator_select" format="reference" />
        <!--指示器未选中-->
        <attr name="indicator_unselect" format="reference" />
        <!--每页菜单行数-->
        <attr name="page_menu_rows" format="integer" />
        <!--每页菜单列数-->
        <attr name="page_menu_columns" format="integer" />
        <!--菜单字体大小-->
        <attr name="menu_text_size" format="dimension" />
        <!--菜单字体颜色-->
        <attr name="menu_text_color" format="color" />
        <!--菜单icon宽度-->
        <attr name="menu_icon_width" format="dimension" />
        <!--菜单icon高度-->
        <attr name="menu_icon_height" format="dimension" />
    </declare-styleable>
```
