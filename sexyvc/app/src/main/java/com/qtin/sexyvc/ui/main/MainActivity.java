package com.qtin.sexyvc.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.popupwindow.GuidePopupwindow;
import com.qtin.sexyvc.popupwindow.OnPopupWindowClickListener;
import com.qtin.sexyvc.ui.add.CommentObjectActivity;
import com.qtin.sexyvc.ui.bean.AppUpdateBean;
import com.qtin.sexyvc.ui.bean.DialogType;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.choose.ChooseActivity;
import com.qtin.sexyvc.ui.main.di.DaggerMainComponent;
import com.qtin.sexyvc.ui.main.di.MainModule;
import com.qtin.sexyvc.ui.main.fragInvestor.FragInvestor;
import com.qtin.sexyvc.ui.main.fragconcern.FragConcern;
import com.qtin.sexyvc.ui.main.fraghome.FragHome;
import com.qtin.sexyvc.ui.main.fragmine.FragMine;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;
import com.qtin.sexyvc.ui.user.project.add.AddProjectActivity;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.qtin.sexyvc.utils.update.updater.Updater;
import com.qtin.sexyvc.utils.update.updater.UpdaterConfig;
import com.zhy.autolayout.utils.AutoUtils;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
    private static final int REQUEST_CODE_SELECTED_TYPE=0x223;

    private Dialog updateDialog;

    private int guideIndex=0;

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
        mPresenter.queryUpdate();

        SharedPreferences preferences=getSharedPreferences("app_start_time", Context.MODE_PRIVATE);
        boolean isFirstTime=preferences.getBoolean("is_first_time",true);
        //第一次显示引导，第二次显示弹窗
        if(isFirstTime){
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("is_first_time",false);
            editor.commit();

            Observable.just(1)
                    .delay(300, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
                            showGuideWindow();
                        }
                    });

        }else{
            final UserInfoEntity entity=mPresenter.getUserInfo();
            if(entity!=null){
                if(entity.getU_auth_state()==ConstantUtil.AUTH_STATE_UNPASS){
                    Observable.just(1)
                            .delay(300, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Integer>() {
                                @Override
                                public void call(Integer integer) {
                                    showHintDialog(DialogType.TYPE_IDENTITY, new ComfirmListerner() {
                                        @Override
                                        public void onComfirm() {
                                            Bundle bundle=new Bundle();
                                            bundle.putParcelable(UserInfoActivity.INTENT_USER,entity);
                                            gotoActivity(UserInfoActivity.class,bundle);
                                        }
                                    });
                                }
                            });

                }else{
                    if(entity.getU_auth_type()==ConstantUtil.AUTH_TYPE_FOUNDER&&entity.getHas_project()==0){
                        Observable.just(1)
                                .delay(300, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Integer>() {
                                    @Override
                                    public void call(Integer integer) {
                                        showHintDialog(DialogType.TYPE_PROJECT, new ComfirmListerner() {
                                            @Override
                                            public void onComfirm() {
                                                dismissHintDialog();
                                                Bundle bundle = new Bundle();
                                                bundle.putBoolean(ConstantUtil.INTENT_IS_EDIT, false);
                                                gotoActivity(AddProjectActivity.class, bundle);
                                            }
                                        });
                                    }
                                });
                    }
                }
            }
        }
    }

    private void showGuideWindow(){
        new GuidePopupwindow(new OnPopupWindowClickListener() {
            @Override
            public void onClick() {
                if(guideIndex<4){
                    guideIndex++;
                    showGuideWindow();
                }
            }
        }).show(MainActivity.this,guideIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public void queryUpdateSuccess(AppUpdateBean updateBean) {
        if(updateBean!=null){
            int localCode=getAppVersionCode();
            if(updateBean.getVersion_code()>localCode){
                showTwoButtonDialog(updateBean);
            }
        }
    }

    /**
     * app更新
     */
    protected void showTwoButtonDialog(final AppUpdateBean updateBean) {

        if(updateDialog!=null&&updateDialog.isShowing()){
            return;
        }

        View view = View.inflate(this, R.layout.app_update_dialog, null);
        TextView tvDialogTitle= (TextView) view.findViewById(R.id.tvDialogTitle);
        Button btnLeft= (Button) view.findViewById(R.id.btnLeft);
        Button btnRight= (Button) view.findViewById(R.id.btnRight);
        TextView tvLog= (TextView) view.findViewById(R.id.tvLog);
        View verticalLine=view.findViewById(R.id.verticalLine);
        tvLog.setText(StringUtil.formatString(updateBean.getUpdate_log()));
        if(StringUtil.isBlank(updateBean.getNew_version())){
            tvDialogTitle.setText("发现新版本");
        }else{
            tvDialogTitle.setText("发现新版本("+updateBean.getNew_version()+")");
        }

        if(updateBean.getConstraint()==0){
            btnLeft.setVisibility(View.VISIBLE);
            verticalLine.setVisibility(View.VISIBLE);
        }else{
            btnLeft.setVisibility(View.GONE);
            verticalLine.setVisibility(View.GONE);
        }


        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialogDismiss();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialogDismiss();
                UpdaterConfig config = new UpdaterConfig.Builder(MainActivity.this)
                        .setTitle(getResources().getString(R.string.app_name))
                        .setDescription("版本更新")
                        .setFileUrl(updateBean.getApk_file_url())
                        .setCanMediaScanner(true)
                        .build();
                Updater.get().showLog(true).download(config);
            }
        });

        AutoUtils.autoSize(view);
        updateDialog = new Dialog(this);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setContentView(view);
        Window regionWindow = updateDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateDialog.setCanceledOnTouchOutside(true);
        updateDialog.setCancelable(false);
        updateDialog.show();
    }


    private void updateDialogDismiss(){
        if(updateDialog!=null&&updateDialog.isShowing()){
            updateDialog.dismiss();
            updateDialog=null;
        }
    }

    /**
     * 获取应用版本号
     * @return app 版本号
     */
    public int getAppVersionCode() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }
}
