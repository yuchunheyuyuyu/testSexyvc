package com.qtin.sexyvc.ui.login.account.bind;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.login.account.bind.di.BindModule;
import com.qtin.sexyvc.ui.login.account.bind.di.DaggerBindComponent;
import com.qtin.sexyvc.ui.main.MainActivity;
import com.qtin.sexyvc.ui.widget.PhoneEditText;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class BindActivity extends MyBaseActivity<BindPresent> implements BindContract.View {

    @BindView(R.id.ivThird)
    ImageView ivThird;
    @BindView(R.id.tvThird)
    TextView tvThird;
    @BindView(R.id.etPhone)
    PhoneEditText etPhone;
    @BindView(R.id.etVertifyCode)
    EditText etVertifyCode;
    @BindView(R.id.tvGetVertify)
    TextView tvGetVertify;

    private static final int TOTAL_TIME=60;//倒计时总时间
    private int countDown=TOTAL_TIME;

    private Handler mHandler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            countDown--;
            if(countDown==0){
                countDown=TOTAL_TIME;
                tvGetVertify.setSelected(true);
                tvGetVertify.setText(getResources().getString(R.string.get_vertify_code));
            }else{
                String str=String.format(getResources().getString(R.string.get_vertify_ing),""+countDown);
                tvGetVertify.setText(str);
                mHandler.postDelayed(this,1000);
            }
        }
    };

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBindComponent.builder().appComponent(appComponent).bindModule(new BindModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.bind_activity;
    }

    @Override
    protected void initData() {
        etPhone.setPhoneVertifyListener(new PhoneEditText.PhoneVertifyListener() {
            @Override
            public void isPhone(boolean isPhone) {
                if(countDown==TOTAL_TIME){
                    if(isPhone){
                        tvGetVertify.setSelected(true);
                    }else{
                        tvGetVertify.setSelected(false);
                    }
                }
            }
        });
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


    @OnClick({R.id.ivBack, R.id.tvGetVertify, R.id.tvBind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvGetVertify:
                if(tvGetVertify.isSelected()){
                    tvGetVertify.setSelected(false);
                    mHandler.post(runnable);
                }
                break;
            case R.id.tvBind:
                gotoActivity(MainActivity.class);
                break;
        }
    }
}
