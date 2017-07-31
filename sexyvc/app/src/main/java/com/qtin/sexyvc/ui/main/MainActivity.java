package com.qtin.sexyvc.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.popupwindow.ChoosePopupwindow;
import com.qtin.sexyvc.ui.add.CommentObjectActivity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.choose.ChooseActivity;
import com.qtin.sexyvc.ui.main.di.DaggerMainComponent;
import com.qtin.sexyvc.ui.main.di.MainModule;
import com.qtin.sexyvc.ui.main.fragInvestor.FragInvestor;
import com.qtin.sexyvc.ui.main.fragconcern.FragConcern;
import com.qtin.sexyvc.ui.main.fraghome.FragHome;
import com.qtin.sexyvc.ui.main.fragmine.FragMine;
import com.qtin.sexyvc.ui.user.project.add.AddProjectActivity;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.vector.update_app.UpdateAppManager;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/14.
 */
public class MainActivity extends MyBaseActivity<MainPresent> implements MainContract.View{

    @BindView(R.id.ivTab1)
    ImageView ivTab1;
    @BindView(R.id.tvTab1)
    TextView tvTab1;
    @BindView(R.id.ivTab2)
    ImageView ivTab2;
    @BindView(R.id.tvTab2)
    TextView tvTab2;
    @BindView(R.id.ivTab3)
    ImageView ivTab3;
    @BindView(R.id.tvTab3)
    TextView tvTab3;
    @BindView(R.id.ivTab4)
    ImageView ivTab4;
    @BindView(R.id.tvTab4)
    TextView tvTab4;

    private MyBaseFragment[] frags;
    private int currentIndex, clickIndex;
    private long exitTime = 0;

    private ChoosePopupwindow popupwindow;

    private static final int REQUEST_CODE_SELECTED_TYPE=0x223;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).mainModule(new MainModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return true;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initData() {
        //沉浸式状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        FragHome fragHome = new FragHome();
        FragInvestor fragProject = new FragInvestor();
        FragConcern fragConcern=new FragConcern();
        FragMine fragMine = new FragMine();

        frags = new MyBaseFragment[]{fragHome, fragProject,fragConcern, fragMine};
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, frags[0]).show(frags[0]).commit();

        ivTab1.setSelected(true);
        tvTab1.setSelected(true);

        //最简方式
        new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(this)
                //更新地址
                .setUpdateUrl("")
                //实现httpManager接口的对象
                //.setHttpManager(new UpdateAppHttpUtil())
                .build()
                .update();

    }


    @OnClick({R.id.llFrag1, R.id.llFrag2, R.id.llFrag3, R.id.llFrag4,R.id.ivCenter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llFrag1:
                clickIndex = 0;
                gotoFrag();
                break;
            case R.id.llFrag2:
                clickIndex = 1;
                gotoFrag();
                break;
            case R.id.llFrag3:
                clickIndex = 2;
                gotoFrag();
                break;
            case R.id.llFrag4:
                clickIndex = 3;
                gotoFrag();
                break;
            case R.id.ivCenter:
                UserInfoEntity userInfoEntity=mPresenter.getUserInfo();

                if(userInfoEntity!=null){
                    Bundle bundle=new Bundle();
                    bundle.putInt(ChooseActivity.AUTH_TYPE,userInfoEntity.getU_auth_type());

                    if (mPresenter.getUserInfo().getU_auth_type() == ConstantUtil.AUTH_TYPE_FOUNDER) {
                        if(mPresenter.getUserInfo().getHas_project()==0){
                            showTwoButtonDialog(getResources().getString(R.string.please_complete_project),
                                    getResources().getString(R.string.cancle),
                                    getResources().getString(R.string.comfirm),
                                    new TwoButtonListerner() {
                                        @Override
                                        public void leftClick() {
                                            dismissTwoButtonDialog();
                                        }

                                        @Override
                                        public void rightClick() {
                                            dismissTwoButtonDialog();
                                            Bundle bundle=new Bundle();
                                            bundle.putBoolean(ConstantUtil.INTENT_IS_EDIT,false);
                                            gotoActivity(AddProjectActivity.class,bundle);
                                        }
                                    });
                            return;
                        }
                        gotoActivityFadeForResult(ChooseActivity.class,bundle,REQUEST_CODE_SELECTED_TYPE);
                    }else{
                        gotoActivityFadeForResult(ChooseActivity.class,bundle,REQUEST_CODE_SELECTED_TYPE);
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_SELECTED_TYPE:
                if(data!=null){
                    int type=data.getExtras().getInt(ConstantUtil.COMMENT_TYPE_INTENT);
                    if(type!=ConstantUtil.COMMENT_TYPE_NONE){
                        Bundle bundle=new Bundle();
                        bundle.putInt(ConstantUtil.COMMENT_TYPE_INTENT,type);
                        gotoActivity(CommentObjectActivity.class,bundle);
                    }
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
        if(outState!=null){
            outState.putInt("index",clickIndex);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            clickIndex=savedInstanceState.getInt("index");
            gotoFrag();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void gotoInvestor(){
        clickIndex = 1;
        gotoFrag();
    }

    private void gotoFrag() {
        if (currentIndex == clickIndex) {
            return;
        }
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(frags[currentIndex]);
        if (!frags[clickIndex].isAdded()) {
            trx.add(R.id.fragmentContainer, frags[clickIndex]);
        }
        trx.show(frags[clickIndex]).commit();
        upDateTabView();
        currentIndex = clickIndex;
    }

    private void upDateTabView() {
        switch (clickIndex) {
            case 0:
                ivTab1.setSelected(true);
                tvTab1.setSelected(true);
                break;
            case 1:
                ivTab2.setSelected(true);
                tvTab2.setSelected(true);
                break;
            case 2:
                ivTab3.setSelected(true);
                tvTab3.setSelected(true);
                break;
            case 3:
                ivTab4.setSelected(true);
                tvTab4.setSelected(true);
                break;
        }
        switch (currentIndex) {
            case 0:
                ivTab1.setSelected(false);
                tvTab1.setSelected(false);
                break;
            case 1:
                ivTab2.setSelected(false);
                tvTab2.setSelected(false);
                break;
            case 2:
                ivTab3.setSelected(false);
                tvTab3.setSelected(false);
                break;
            case 3:
                ivTab4.setSelected(false);
                tvTab4.setSelected(false);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fixInputMethodManagerLeak(this);
        frags = null;
    }

    /**
     * 解决InputMethodManager泄露问题
     *
     * @param destContext
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                UiUtils.SnackbarText(getResources().getString(R.string.exit_warn));
                exitTime = System.currentTimeMillis();
            } else {
                UiUtils.exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
