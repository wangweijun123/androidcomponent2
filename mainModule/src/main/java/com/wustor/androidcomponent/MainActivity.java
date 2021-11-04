package com.wustor.androidcomponent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wustor.basemodule.APath;
import com.wustor.basemodule.BaseActivity;
import com.wustor.basemodule.CommonUtils;

public class MainActivity extends BaseActivity {
    RadioButton bottomHome;
    RadioGroup radioGroup;
    private Fragment[] list;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        bottomHome = findViewById(R.id.bottom_home);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bottom_home:
                        changeFragment(1);
                        break;
                    case R.id.bottom_sort:
                        changeFragment(2);
                        break;
                    case R.id.bottom_cart:
                        changeFragment(3);
                        break;
                    case R.id.bottom_user:
                        changeFragment(4);
                        break;
                }
            }
        });
        Fragment fragmentHome = (Fragment) ARouter.getInstance().build(APath.GOODS_HOME).navigation();
        Fragment fragmentSort = (Fragment) ARouter.getInstance().build(APath.GOODS_SORT).navigation();
        Fragment fragmentCart = (Fragment) ARouter.getInstance().build(APath.CARTS_FRAGMENT).navigation();
        Fragment fragmentMine = (Fragment) ARouter.getInstance().build(APath.USER_MINE).navigation();
        if (fragmentHome == null || fragmentSort == null || fragmentCart == null || fragmentMine == null) {
            CommonUtils.showToast(mContext, "当前处于组件模式");
        } else {
            list = new Fragment[]{fragmentHome, fragmentSort, fragmentCart, fragmentMine};
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            beginTransaction.add(R.id.main, fragmentHome);
            beginTransaction.add(R.id.main, fragmentSort);
            beginTransaction.add(R.id.main, fragmentCart);
            beginTransaction.add(R.id.main, fragmentMine);
            beginTransaction.commit();
            showHome();
        }


    }


    public void showHome() {
        bottomHome.setChecked(true);
        changeFragment(1);
    }

    public void changeFragment(int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < list.length; i++) {
            if (i + 1 != index) {
                // 隐藏选项卡
                fragmentTransaction.hide(list[i]);
            } else {
                // 显示选项卡
                fragmentTransaction.show(list[i]);

            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
