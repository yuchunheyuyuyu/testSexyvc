package com.qtin.sexyvc.ui.rate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.rate.di.DaggerRateComponent;
import com.qtin.sexyvc.ui.rate.di.RateModule;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class RateActivity extends MyBaseActivity<RatePresent> implements RateContract.View {

    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.ivAnthStatus)
    ImageView ivAnthStatus;
    @BindView(R.id.tvInvestorName)
    TextView tvInvestorName;
    @BindView(R.id.tvFundName)
    TextView tvFundName;
    @BindView(R.id.tvPosition)
    TextView tvPosition;
    @BindView(R.id.ratingScore)
    BaseRatingBar ratingScore;
    @BindView(R.id.flowLayout)
    TagFlowLayout flowLayout;
    private TagAdapter tagAdapter;

    private EditText etInputOption;
    private Dialog optionDialog;

    private ArrayList<TagEntity> tags = new ArrayList<>();

    private InvestorInfoBean investorInfoBean;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRateComponent.builder().appComponent(appComponent).rateModule(new RateModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.rate_activity;
    }

    @Override
    protected void initData() {
        investorInfoBean = getIntent().getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);

        if (investorInfoBean.getInvestor_uid() == 0) {
            ivAnthStatus.setVisibility(View.GONE);
        } else {
            ivAnthStatus.setVisibility(View.VISIBLE);
        }
        mImageLoader = customApplication.getAppComponent().imageLoader();
        //头像
        mImageLoader.loadImage(customApplication, GlideImageConfig
                .builder()
                .errorPic(R.drawable.avatar_blank)
                .placeholder(R.drawable.avatar_blank)
                .url(CommonUtil.getAbsolutePath(investorInfoBean.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(this))
                .imageView(ivAvatar)
                .build());
        tvInvestorName.setText(StringUtil.formatString(investorInfoBean.getInvestor_name()));
        tvFundName.setText(StringUtil.formatString(investorInfoBean.getFund_name()));
        tvPosition.setText(StringUtil.formatString(investorInfoBean.getTitle()));
        //ratingScore
        if (investorInfoBean.getTags() != null) {
            tags.addAll(investorInfoBean.getTags());
        }

        TagEntity addTag=new TagEntity();
        addTag.setTag_name("+ 添加");
        tags.add(addTag);

        tagAdapter = new TagAdapter<TagEntity>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, TagEntity o) {
                TextView tv = (TextView) LayoutInflater.from(RateActivity.this).inflate(R.layout.item_filter_textview6, flowLayout, false);

                StringBuilder sb = new StringBuilder();
                sb.append(o.getTag_name());

                if (o.getTag_count() > 0) {
                    sb.append(" (");
                    sb.append(o.getTag_count());
                    sb.append(")");
                }
                tv.setText(sb.toString());
                if(tags.get(position).isSelected()){
                    tv.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                    tv.setBackgroundResource(R.drawable.pink_shape_11);
                    tv.setSelected(true);
                }else{
                    tv.setTextColor(getResources().getColor(R.color.black30));
                    tv.setBackgroundResource(R.drawable.black30_shape_11);
                    tv.setSelected(false);
                }

                return tv;
            }
        };
        flowLayout.setAdapter(tagAdapter);
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(position== tags.size()-1){
                    showOptionDialog();
                }else{
                    if(tags.get(position).isSelected()){
                        tags.get(position).setSelected(false);
                    }else{
                        tags.get(position).setSelected(true);
                    }
                    tagAdapter.notifyDataChanged();
                }
                return true;
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

    @OnClick({R.id.ivLeft, R.id.tvRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                break;
            case R.id.tvRight:
                break;
        }
    }

    public void showOptionDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_option_dialog, null);
        etInputOption = (EditText) view.findViewById(R.id.etInputOption);
        //etInputQuestion.setHint(hint);
        View tvAddOption = view.findViewById(R.id.tvAddOption);

        tvAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInputOption.getText().toString();
                if (StringUtil.isBlank(content)) {
                    showMessage("标签不能为空");
                    return;
                }
                optionDialog.dismiss();
                TagEntity tagEntity=new TagEntity();
                tagEntity.setSelected(true);
                tagEntity.setTag_name(content);
                tags.add(tags.size()-1,tagEntity);

                tagAdapter.notifyDataChanged();
            }
        });
        optionDialog = new Dialog(this);
        optionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        optionDialog.setContentView(view);
        Window inputWindow = optionDialog.getWindow();
        WindowManager.LayoutParams params = inputWindow.getAttributes();
        inputWindow.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);

        //inputWindow.setFlags( WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        inputWindow.setGravity(Gravity.BOTTOM);
        inputWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inputWindow.setWindowAnimations(R.style.dialog_fade_animation);
        inputWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        optionDialog.setCanceledOnTouchOutside(true);
        optionDialog.show();

        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        etInputOption.setFocusable(true);
                        InputMethodManager inputMethodManager = (InputMethodManager) etInputOption.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    }
                });
    }
}
