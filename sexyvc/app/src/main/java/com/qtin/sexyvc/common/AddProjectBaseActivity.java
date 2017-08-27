package com.qtin.sexyvc.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.mvp.Presenter;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CashierInputFilter;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/8/18.
 */
public abstract class AddProjectBaseActivity<P extends Presenter> extends MyBaseActivity<P> {

    private Dialog projectDialog;
    private Dialog domainDialog;
    private Dialog stageDialog;
    private TagAdapter domainAdapter;
    private TagAdapter stageAdapter;

    protected ArrayList<FilterEntity> domainData = new ArrayList<>();
    protected ArrayList<FilterEntity> stageData = new ArrayList<>();

    protected ProjectBean projectBean=new ProjectBean();

    public static final int TYPE_DOMAIN = 0x001;//行业
    public static final int TYPE_STAGE = 0x002;//阶段

    public static interface OnProjectComfirmListener{
        void onComfirm(ProjectBean projectBean);
    }

    public static interface OnDomainComfirmListener{
        void onDomainComfirm();
    }

    public static interface OnStageComfirmListener{
        void onStageComfirm();
    }

    protected void initStageData(ArrayList<FilterEntity> list){
        stageData.clear();

        FilterEntity entity=new FilterEntity();
        entity.setType_id(0);
        entity.setType_name("未融资");
        stageData.add(entity);
        if(list!=null){
            stageData.addAll(list);
        }
    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reset();
    }

    private void reset(){
        projectBean.setLast_currency(1);
        projectBean.setLogo("");
        projectBean.setProject_name("未填写");
        projectBean.setLast_stage_id(ConstantUtil.SPECIAL_ID);
        projectBean.setDomain_id(0);
        projectBean.setShort_intro("未填写");
        projectBean.setLast_financial_amount(0);
    }

    protected void showProjectDialog(final OnProjectComfirmListener listener) {

        View view = View.inflate(this, R.layout.project_dialog, null);
        final TextView tvDomain= (TextView) view.findViewById(R.id.tvDomain);
        final TextView tvStage= (TextView) view.findViewById(R.id.tvStage);

        view.findViewById(R.id.llDomain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDomainDialog(new OnDomainComfirmListener() {
                    @Override
                    public void onDomainComfirm() {
                        setDomainText(tvDomain);
                    }
                });
            }
        });

        view.findViewById(R.id.llStage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStageDialog(new OnStageComfirmListener() {
                    @Override
                    public void onStageComfirm() {
                        setStageText(tvStage);
                    }
                });
            }
        });

        view.findViewById(R.id.btnLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                dismissProjectDialog();
            }
        });

        view.findViewById(R.id.btnRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(projectBean.getDomain_id()==0){
                    UiUtils.showToastShort(AddProjectBaseActivity.this,"行业不能为空");
                    return;
                }
                if(projectBean.getLast_stage_id()==ConstantUtil.SPECIAL_ID){
                    UiUtils.showToastShort(AddProjectBaseActivity.this,"请选择轮次标签");
                    return;
                }
                listener.onComfirm(projectBean);
            }
        });
        projectDialog = new Dialog(this);
        projectDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        projectDialog.setContentView(view);
        Window regionWindow = projectDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        projectDialog.setCanceledOnTouchOutside(true);
        projectDialog.setCancelable(false);
        projectDialog.show();
    }

    /**
     * 显示行业的dialog
     */
    private void showDomainDialog(final OnDomainComfirmListener listener) {
        View view = LayoutInflater.from(this).inflate(R.layout.domain_dialog, null);
        final DomainHolder holder = new DomainHolder(view);
        holder.tvCancleDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDomainDialog();
            }
        });

        holder.tvComfirmDomain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismissDomainDialog();
                long domain_id=0;
                for(FilterEntity entity:domainData){
                    if(entity.isSelected()){
                        domain_id=entity.getType_id();
                        break;
                    }
                }
                projectBean.setDomain_id(domain_id);
                listener.onDomainComfirm();
            }
        });
        domainAdapter = new TagAdapter<FilterEntity>(domainData) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                View view = LayoutInflater.from(AddProjectBaseActivity.this).inflate(R.layout.item_filter, holder.flowLayout, false);
                //AutoUtils.auto(tv);
                TextView tv= (TextView) view.findViewById(R.id.tvFilter);
                if(o.isSelected()){
                    tv.setSelected(true);
                }else{
                    tv.setSelected(false);
                }
                tv.setText(o.getType_name());
                return view;
            }
        };
        holder.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(view instanceof LinearLayout){
                    LinearLayout layout= (LinearLayout) view;
                    TextView tv= (TextView) layout.getChildAt(0);
                    if(!domainData.get(position).isSelected()){
                        for(FilterEntity entity:domainData){
                            entity.setSelected(false);
                        }
                        domainData.get(position).setSelected(true);
                        tv.setSelected(true);
                        domainAdapter.notifyDataChanged();
                    }
                }

                return false;
            }
        });

        setDomainStatus();

        holder.flowLayout.setAdapter(domainAdapter);
        holder.flowLayout.setMaxSelectCount(1);
        domainDialog = new Dialog(this);
        domainDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        domainDialog.setContentView(view);
        Window regionWindow = domainDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        domainDialog.setCanceledOnTouchOutside(true);
        domainDialog.setCancelable(false);
        domainDialog.show();
    }

    private void dismissDomainDialog(){
        if(domainDialog!=null&&domainDialog.isShowing()){
            domainDialog.dismiss();
            domainDialog=null;
        }
    }

    /**
     * 显示阶段的dialog
     */
    public void showStageDialog(final OnStageComfirmListener listener) {
        View view = LayoutInflater.from(this).inflate(R.layout.stage_dialog, null);

        final StageHolder holder=new StageHolder(view);
        stageAdapter=new TagAdapter<FilterEntity>(stageData) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                View view = LayoutInflater.from(AddProjectBaseActivity.this).inflate(R.layout.item_filter, holder.flowLayout, false);
                //AutoUtils.auto(tv);
                TextView tv= (TextView) view.findViewById(R.id.tvFilter);
                tv.setText(o.getType_name());
                if(o.isSelected()){
                    tv.setSelected(true);
                }else{
                    tv.setSelected(false);
                }
                return view;
            }
        };
        holder.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(view instanceof LinearLayout){
                    LinearLayout layout= (LinearLayout) view;
                    TextView tv= (TextView) layout.getChildAt(0);

                    if(!stageData.get(position).isSelected()){
                        for(FilterEntity entity:stageData){
                            entity.setSelected(false);
                        }
                        stageData.get(position).setSelected(true);
                        tv.setSelected(true);
                        stageAdapter.notifyDataChanged();
                    }
                }

                return false;
            }
        });

        setStageStatus();
        holder.flowLayout.setAdapter(stageAdapter);
        holder.flowLayout.setMaxSelectCount(1);

        holder.changeMoneyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(projectBean.getLast_currency()==0){
                    projectBean.setLast_currency(1);
                    holder.tvMoneyType.setText(getResources().getString(R.string.renminbi));
                }else{
                    projectBean.setLast_currency(0);
                    holder.tvMoneyType.setText(getResources().getString(R.string.dollar));
                }
            }
        });

        InputFilter[] filters={new CashierInputFilter()};
        holder.etMoney.setFilters(filters);
        String money=""+projectBean.getLast_financial_amount();
        if(projectBean.getLast_financial_amount()==0){
            holder.etMoney.setText("");
        }else{
            holder.etMoney.setText(money);
            holder.etMoney.setSelection(money.length());
        }

        if(projectBean.getLast_currency()==0){
            holder.tvMoneyType.setText(getResources().getString(R.string.dollar));
        }else{
            holder.tvMoneyType.setText(getResources().getString(R.string.renminbi));
        }

        holder.tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissStageDialog();
            }
        });
        holder.tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissStageDialog();
                long stage_id=ConstantUtil.SPECIAL_ID;
                for(FilterEntity entity:stageData){
                    if(entity.isSelected()){
                        stage_id=entity.getType_id();
                        break;
                    }
                }
                if(stage_id==ConstantUtil.SPECIAL_ID){
                    UiUtils.showToastShort(AddProjectBaseActivity.this,"请选择轮次标签");
                    return;
                }
                projectBean.setLast_stage_id(stage_id);
                if(stage_id==0){
                    projectBean.setLast_financial_amount(0);
                }else{
                    try{
                        projectBean.setLast_financial_amount(Long.parseLong(holder.etMoney.getText().toString()));
                    }catch(Exception e){
                        e.printStackTrace();
                        projectBean.setLast_financial_amount(0);
                    }
                }
                listener.onStageComfirm();
            }
        });

        stageDialog = new Dialog(this);
        stageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        stageDialog.setContentView(view);
        Window inputWindow = stageDialog.getWindow();
        WindowManager.LayoutParams params = inputWindow.getAttributes();
        inputWindow.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);

        //inputWindow.setFlags( WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        inputWindow.setGravity(Gravity.BOTTOM);
        inputWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inputWindow.setWindowAnimations(R.style.dialog_fade_animation);
        inputWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        stageDialog.setCanceledOnTouchOutside(true);
        stageDialog.setCancelable(false);
        stageDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.etMoney.setFocusable(true);
                InputMethodManager inputMethodManager = (InputMethodManager) holder.etMoney.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }, 300);
    }

    public void dismissStageDialog() {
        if (stageDialog != null && stageDialog.isShowing()) {
            stageDialog.dismiss();
        }
    }

    static class StageHolder {
        @BindView(R.id.etMoney)
        EditText etMoney;
        @BindView(R.id.tvMoneyType)
        TextView tvMoneyType;
        @BindView(R.id.changeMoneyContainer)
        LinearLayout changeMoneyContainer;
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;
        @BindView(R.id.tvCancle)
        TextView tvCancle;
        @BindView(R.id.tvComfirm)
        TextView tvComfirm;

        StageHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class DomainHolder {
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;
        @BindView(R.id.tvCancleDomain)
        TextView tvCancleDomain;
        @BindView(R.id.tvComfirmDomain)
        TextView tvComfirmDomain;

        DomainHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void setDomainText(TextView tvDomain){
        if(domainData!=null){
            for(int i=0;i<domainData.size();i++){
                if(domainData.get(i).getType_id()==projectBean.getDomain_id()){
                    tvDomain.setText(domainData.get(i).getType_name());
                    domainData.get(i).setSelected(true);
                    break;
                }
            }
        }
    }

    private void setDomainStatus(){
        if(domainData!=null){
            for(int i=0;i<domainData.size();i++){
                if(domainData.get(i).getType_id()==projectBean.getDomain_id()){
                    domainAdapter.setSelectedList(i);
                    break;
                }
            }
        }
    }

    private void setStageText(TextView tvStage){
        if(stageData!=null){
            for(int i=0;i<stageData.size();i++){
                if(stageData.get(i).getType_id()==projectBean.getLast_stage_id()){
                    setStageValue(tvStage,stageData.get(i).getType_name());
                    stageData.get(i).setSelected(true);
                    break;
                }
            }
        }
    }

    private void setStageValue(TextView tvStage,String stage){
        long money=projectBean.getLast_financial_amount();
        if(money<=0){
            tvStage.setText(StringUtil.formatString(stage));
        }else{
            StringBuilder sb=new StringBuilder();
            sb.append(stage);
            sb.append("  ");

            String moneyValue=money/10000f+"";
            sb.append(moneyValue);
            sb.append("万");
            if(projectBean.getLast_currency()==0){
                sb.append(getResources().getString(R.string.dollar));
            }else{
                sb.append(getResources().getString(R.string.renminbi));
            }
            tvStage.setText(sb.toString());
        }
    }

    private void setStageStatus(){
        if(stageData!=null){
            for(int i=0;i<stageData.size();i++){
                if(stageData.get(i).getType_id()==projectBean.getLast_stage_id()){
                    stageAdapter.setSelectedList(i);
                    break;
                }
            }
        }
    }


    protected void dismissProjectDialog(){
        if(projectDialog!=null&&projectDialog.isShowing()){
            projectDialog.dismiss();
            projectDialog=null;
        }
    }
}
