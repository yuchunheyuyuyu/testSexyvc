package com.qtin.sexyvc.ui.user.influency;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.add.CommentObjectActivity;
import com.qtin.sexyvc.ui.bean.InfluencyBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.user.influency.di.DaggerInfluencyComponent;
import com.qtin.sexyvc.ui.user.influency.di.InfluencyModule;
import com.qtin.sexyvc.ui.user.info.UserInfoActivity;
import com.qtin.sexyvc.ui.user.position.PositionActivity;
import com.qtin.sexyvc.ui.user.project.add.AddProjectActivity;
import com.qtin.sexyvc.utils.ConstantUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class InfluencyActivity extends MyBaseActivity<InfluencyPresent> implements InfluencyContract.View {

    @BindView(R.id.tvIdentityStatus)
    TextView tvIdentityStatus;
    @BindView(R.id.tvProjectStatus)
    TextView tvProjectStatus;
    @BindView(R.id.tvInfluencyNum)
    TextView tvInfluencyNum;

    private UserInfoEntity userInfo;
    private static final int REQUEST_CODE_IDENTITY=0x001;
    private static final int REQUEST_CODE_PROJECT=0x002;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerInfluencyComponent.builder().appComponent(appComponent).influencyModule(new InfluencyModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.influency_activity;
    }

    @Override
    protected void initData() {
        userInfo=getIntent().getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);
        setStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.queryInfluency();
    }

    private void setStatus(){
        if(userInfo.getU_auth_state()==ConstantUtil.AUTH_STATE_PASS){
            tvIdentityStatus.setText(getString(R.string.has_complete));
        }else {
            tvIdentityStatus.setText(getString(R.string.not_complete));
        }

        if(userInfo.getHas_project()==0){
            tvProjectStatus.setText(getString(R.string.not_complete));
        }else{
            tvProjectStatus.setText(getString(R.string.has_complete));
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            return;
        }
        switch(requestCode){
            case REQUEST_CODE_IDENTITY:
                userInfo=data.getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);
                setStatus();
                break;
            case REQUEST_CODE_PROJECT:
                int has_project=data.getExtras().getInt("has_project");
                userInfo.setHas_project(has_project);
                setStatus();
                break;
        }
    }

    @OnClick({R.id.ivLeft, R.id.identityContainer, R.id.projectContainer, R.id.roadContainer, R.id.commentContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.identityContainer:
                if(userInfo.getU_auth_state()!=ConstantUtil.AUTH_STATE_PASS){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(UserInfoActivity.INTENT_USER, userInfo);
                    gotoActivityForResult(PositionActivity.class, bundle, REQUEST_CODE_IDENTITY);
                }

                break;
            case R.id.projectContainer:
                if(userInfo.getHas_project()==0){
                    Bundle bundle=new Bundle();
                    bundle.putBoolean(ConstantUtil.INTENT_IS_EDIT,false);
                    gotoActivityForResult(AddProjectActivity.class,bundle,REQUEST_CODE_PROJECT);
                }

                break;
            case R.id.roadContainer:
                if(userInfo.getHas_project()==0){
                    showMessage("请先填写项目信息");
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putInt(ConstantUtil.COMMENT_TYPE_INTENT,ConstantUtil.COMMENT_TYPE_ROAD);
                gotoActivity(CommentObjectActivity.class,bundle);
                break;
            case R.id.commentContainer:
                if(userInfo.getHas_project()==0){
                    showMessage("请先填写项目信息");
                    return;
                }
                Bundle bundle2=new Bundle();
                bundle2.putInt(ConstantUtil.COMMENT_TYPE_INTENT,ConstantUtil.COMMENT_TYPE_EDIT);
                gotoActivity(CommentObjectActivity.class,bundle2);
                break;
        }
    }

    @Override
    public void queryInfluencySuccess(InfluencyBean influencyBean) {
        int influencyScore=0;
        if(influencyBean.getAuth_passed()==1){
            influencyScore+=200;
        }
        if(influencyBean.getHas_project()==1){
            influencyScore+=200;
        }
        influencyScore+=(100*influencyBean.getComment_number()+100*influencyBean.getRoadshow_number());
        influencyScore+=(10*influencyBean.getComment_praised_number());

        tvInfluencyNum.setText(""+influencyScore);
    }
}
