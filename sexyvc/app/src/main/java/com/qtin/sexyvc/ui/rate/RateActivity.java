package com.qtin.sexyvc.ui.rate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CommentEvent;
import com.qtin.sexyvc.ui.bean.CommonBean;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.rate.di.DaggerRateComponent;
import com.qtin.sexyvc.ui.rate.di.RateModule;
import com.qtin.sexyvc.ui.request.RateRequest;
import com.qtin.sexyvc.ui.review.ReviewActivity;
import com.qtin.sexyvc.ui.road.RoadCommentActivity;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    RatingBar ratingScore;
    @BindView(R.id.flowLayout)
    TagFlowLayout flowLayout;
    private TagAdapter tagAdapter;

    private EditText etInput;
    private Dialog tagDialog;

    private ArrayList<TagEntity> tags = new ArrayList<>();
    private ArrayList<TagEntity> normalTags = new ArrayList<>();


    private InvestorInfoBean investorInfoBean;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRateComponent.builder().appComponent(appComponent).rateModule(new RateModule(this)).build().inject(this);
    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

        tvRight.setVisibility(View.VISIBLE);
        tvRight.setTextColor(getResources().getColor(R.color.black30));
        ratingScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating>0){
                    tvRight.setSelected(true);
                    tvRight.setTextColor(getResources().getColor(R.color.barbie_pink_two));
                }else{
                    tvRight.setSelected(false);
                    tvRight.setTextColor(getResources().getColor(R.color.black30));
                }
            }
        });

        tvRight.setText(getResources().getString(R.string.commit));
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
        tvTitle.setText(getResources().getString(R.string.evaluate)+investorInfoBean.getInvestor_name());
        //ratingScore
        if (investorInfoBean.getTags() != null) {
            tags.addAll(investorInfoBean.getTags());
        }

        TagEntity addTag=new TagEntity();
        addTag.setTag_name("+ 添加");
        addTag.setTag_id(ConstantUtil.SPECIAL_ID);
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
                if(tags.get(position).getTag_id()== ConstantUtil.SPECIAL_ID){
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
        mPresenter.queryNormalQuestion();
    }

    @Override
    public void showLoading() {
        showDialog("正在提交");
    }

    @Override
    public void hideLoading() {
        dialogDismiss();
    }

    @Override
    public void showMessage(String message) {
        UiUtils.showToastShort(this,message);
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
                finish();
                break;
            case R.id.tvRight:
                if(tvRight.isSelected()){
                    int score= (int) ratingScore.getRating();
                    ArrayList<String> tem=new ArrayList<>();

                    if(tags!=null&&!tags.isEmpty()){
                        for(TagEntity entity:tags){
                            if(entity.isSelected()){
                                tem.add(entity.getTag_name());
                            }
                        }
                    }
                    if(investorInfoBean!=null){
                        RateRequest request=new RateRequest();
                        request.setFund_id(investorInfoBean.getFund_id());
                        request.setInvestor_id(investorInfoBean.getInvestor_id());
                        request.setScore(score*2);
                        request.setTags(tem);
                        mPresenter.rateInvestor(request);
                    }
                }

                break;
        }
    }

    public void showOptionDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_tag_dialog, null);
        etInput = (EditText) view.findViewById(R.id.etInput);
        //etInputQuestion.setHint(hint);
        View tvAdd = view.findViewById(R.id.tvAdd);
        View normalContainer=view.findViewById(R.id.normalContainer);
        if(normalTags==null||normalTags.isEmpty()){
            normalContainer.setVisibility(View.GONE);
        }else{
            normalContainer.setVisibility(View.VISIBLE);
            final TagFlowLayout flowLayout= (TagFlowLayout) view.findViewById(R.id.flowLayout);

            TagAdapter tagAdapter = new TagAdapter<TagEntity>(normalTags) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity s) {
                    TextView tv = (TextView) LayoutInflater.from(RateActivity.this).inflate(R.layout.item_normal_question, flowLayout, false);
                    AutoUtils.auto(tv);
                    tv.setText("+ "+s.getTag_name());
                    return tv;
                }
            };
            flowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    Iterator<Integer> it=selectPosSet.iterator();
                    if (it.hasNext()){
                        TagEntity tagEntity=normalTags.get(it.next());
                        etInput.setText(tagEntity.getTag_name());
                        etInput.setSelection(tagEntity.getTag_name().length());
                    }

                }
            });
            flowLayout.setMaxSelectCount(1);
            flowLayout.setAdapter(tagAdapter);
        }

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInput.getText().toString().trim();
                if (StringUtil.isBlank(content)) {
                    showMessage("标签不能为空");
                    return;
                }

                if(!tags.isEmpty()){
                    for(TagEntity tagEntity:tags){
                        if(content.equals(tagEntity.getTag_name())){
                            showMessage("该标签已存在");
                            return;
                        }
                    }
                }



                tagDialog.dismiss();
                TagEntity tagEntity=new TagEntity();
                tagEntity.setSelected(true);
                tagEntity.setCustom(true);
                tagEntity.setTag_name(content);
                tagEntity.setCustom(true);
                tags.add(tags.size()-1,tagEntity);

                int customNum=0;
                for(TagEntity entity:tags){
                    if(entity.isCustom()){
                        customNum++;
                    }
                }
                if(customNum>=3){
                    tags.remove(tags.size()-1);
                }
                tagAdapter.notifyDataChanged();
            }
        });
        tagDialog = new Dialog(this);
        tagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        tagDialog.setContentView(view);
        Window inputWindow = tagDialog.getWindow();
        WindowManager.LayoutParams params = inputWindow.getAttributes();
        inputWindow.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);

        //inputWindow.setFlags( WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        inputWindow.setGravity(Gravity.BOTTOM);
        inputWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inputWindow.setWindowAnimations(R.style.dialog_fade_animation);
        inputWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tagDialog.setCanceledOnTouchOutside(true);
        tagDialog.show();

        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        etInput.setFocusable(true);
                        InputMethodManager inputMethodManager = (InputMethodManager) etInput.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    }
                });
    }

    @Override
    public void rateSuccess(int score) {
        //发送事件，改变状态
        CommentEvent entity=new CommentEvent();
        entity.setScore(score);
        EventBus.getDefault().post(entity,ConstantUtil.SCORE_SUCCESS);
        //进入写点评页面
        investorInfoBean.setHas_score(1);
        investorInfoBean.setScore_value(score);
        Bundle bundle=new Bundle();

        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,investorInfoBean);
        if(investorInfoBean.getIntent()==ConstantUtil.INTENT_ROAD_COMMENT){
            bundle.putInt(ConstantUtil.INTENT_INDEX,0);
            gotoActivity(RoadCommentActivity.class, bundle);
        }else{
            gotoActivity(ReviewActivity.class,bundle);
        }

        //销毁自己
        Observable.just(1)
                .delay(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        finish();
                    }
                });
    }

    @Override
    public void queryNormalQuestionsSuccess(CommonBean commonBean) {
        if(commonBean!=null&&commonBean.getTags()!=null){
            for(int i=0;i<commonBean.getTags().size();i++){
                TagEntity tagEntity=new TagEntity();
                tagEntity.setTag_name(commonBean.getTags().get(i));
                normalTags.add(tagEntity);
            }
        }
    }
}
