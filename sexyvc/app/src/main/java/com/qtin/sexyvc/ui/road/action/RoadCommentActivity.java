package com.qtin.sexyvc.ui.road.action;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.Gson;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.CommentEvent;
import com.qtin.sexyvc.ui.bean.CommonBean;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.InvestorInfoBean;
import com.qtin.sexyvc.ui.bean.OnSpecialClickListener;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.TagEntity;
import com.qtin.sexyvc.ui.road.action.bean.AddQuestionBean;
import com.qtin.sexyvc.ui.road.action.bean.OnOptionClickListener;
import com.qtin.sexyvc.ui.road.action.bean.OptionFirstBean;
import com.qtin.sexyvc.ui.road.action.bean.OptionSecondBean;
import com.qtin.sexyvc.ui.road.action.bean.QuestionBean;
import com.qtin.sexyvc.ui.road.action.bean.RoadRequest;
import com.qtin.sexyvc.ui.road.action.di.DaggerRoadCommentComponent;
import com.qtin.sexyvc.ui.road.action.di.RoadCommentModule;
import com.qtin.sexyvc.ui.road.action.success.SuccessActivity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.user.project.my.bean.ProjectEntity;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class RoadCommentActivity extends MyBaseActivity<RoadCommentPresent> implements RoadCommentContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private InvestorInfoBean investorInfoBean;
    private int index;

    private ArrayList<QuestionBean> questions;
    private ArrayList<DataTypeInterface> data=new ArrayList<>();
    private RoadQuestionAdapter mAdapter;

    private EditText etInputQuestion;
    private Dialog questionDialog;
    private EditText etInputOption;
    private Dialog optionDialog;
    private EditText etInputAnswer;
    private Dialog answerDialog;

    private Dialog submitDialog;
    private Dialog stageDialog;

    private ArrayList<TagEntity> normalQuestions=new ArrayList<>();


    protected ArrayList<FilterEntity> stageData = new ArrayList<>();
    public static final int TYPE_STAGE = 0x002;//阶段
    private TagAdapter stageAdapter;
    private FilterEntity filterEntity=new FilterEntity();
    private boolean isHasComfirm=false;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscriber(tag = ConstantUtil.ROAD_SUCCESS, mode = ThreadMode.MAIN)
    public void onReceive(CommentEvent commentEvent){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRoadCommentComponent.builder().appComponent(appComponent).roadCommentModule(new RoadCommentModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.common_list_activity;
    }

    @Override
    protected void initData() {
        //初始化轮次数据
        filterEntity.setType_id(28);
        filterEntity.setType_name("种子轮");

        investorInfoBean = getIntent().getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);
        index = getIntent().getExtras().getInt(ConstantUtil.INTENT_INDEX);

        tvTitle.setText(getResources().getString(R.string.evaluate)+investorInfoBean.getInvestor_name());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new RoadQuestionAdapter(this,data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnSpecialClickListener(new OnSpecialClickListener() {
            @Override
            public void onSpecialItem(int position) {
                if(index<questions.size()-1){
                    Bundle bundle=new Bundle();
                    bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,investorInfoBean);
                    bundle.putInt(ConstantUtil.INTENT_INDEX,index+1);
                    bundle.putParcelableArrayList(ConstantUtil.INTENT_PARCELABLE_ARRAY,questions);
                    bundle.putParcelableArrayList(ConstantUtil.INTENT_STRING_ARRAY,normalQuestions);
                    gotoActivity(RoadCommentActivity.class,bundle);
                }else{
                    showSubmitDialog();
                }
            }
        });
        mAdapter.setOnOptionClickListener(new OnOptionClickListener() {
            @Override
            public void addOption(int position) {
                showOptionDialog(position);
            }

            @Override
            public void addQuestion(int position) {
                showQuestionDialog(position);
            }

            @Override
            public void addAnswer(int optionPosition,int addedQuestionPosition) {
                showAnswerDialog(optionPosition,addedQuestionPosition);
            }
        });
        //获取投资阶段
        mPresenter.getType("common_stage", TYPE_STAGE);
        if(index==0){
            questions=new ArrayList<>();
            mPresenter.queryNormalQuestion();
            mPresenter.queryRoadQuestion();
        }else{
            questions=getIntent().getExtras().getParcelableArrayList(ConstantUtil.INTENT_PARCELABLE_ARRAY);
            normalQuestions=getIntent().getExtras().getParcelableArrayList(ConstantUtil.INTENT_STRING_ARRAY);
            QuestionBean questionBean=questions.get(index);
            data.clear();
            data.add(questionBean);
            data.addAll(questionBean.getOptions());

            mAdapter.setIndex(index);
            mAdapter.setTotal(questions.size());
            mAdapter.notifyDataSetChanged();
        }

        if(mPresenter.getUserInfo()!=null&&mPresenter.getUserInfo().getU_auth_type()==ConstantUtil.AUTH_TYPE_FOUNDER
                &&index==questions.size()-1){
            mPresenter.queryMyProject();
        }
    }

    private void uploadAnswers(long stage_id,int is_anon){
        //提交题目
        //第一层
        RoadRequest request=new RoadRequest();
        request.setStage_id(stage_id);
        request.setIs_anon(is_anon);

        request.setInvestor_id(investorInfoBean.getInvestor_id());
        request.setFund_id(investorInfoBean.getFund_id());
        ArrayList<RoadRequest.AnswerItem> answers=new ArrayList<RoadRequest.AnswerItem>();

        //第二层
        for(QuestionBean bean:questions){
            RoadRequest.AnswerItem item=new RoadRequest.AnswerItem();

            item.setQuestion_id(bean.getQuestion_id());
            //循环搜索被选中的选项
            for(OptionFirstBean firstOption:bean.getOptions()){
                if(firstOption.isSelected()){
                    //选项id
                    item.setOption_id(firstOption.getOption_id());
                    //自定义的问题
                    if(firstOption.getAddQuestions()==null){
                        item.setAdd_questions(new ArrayList<AddQuestionBean>());
                    }else{
                        item.setAdd_questions(firstOption.getAddQuestions());
                    }
                    //二级问题的选项
                    ArrayList<OptionSecondBean> sub_options=new ArrayList<OptionSecondBean>();
                    if(firstOption.getLink_question()!=null&&firstOption.getLink_question().getOptions()!=null){
                        for(OptionSecondBean secondBean:firstOption.getLink_question().getOptions()){
                            if(secondBean.isSelected()){
                                sub_options.add(secondBean);
                            }
                        }
                    }
                    item.setSub_options(sub_options);
                    //二级问题的id
                    if(firstOption.getLink_question()!=null){
                        item.setSub_questionid(firstOption.getLink_question().getQuestion_id());
                    }
                    break;
                }
            }
            answers.add(item);
        }
        String answerJson=new Gson().toJson(answers);
        request.setAnswers(answerJson);
        mPresenter.uploadAnswers(request);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
    }

    @Override
    public void queryFail() {

    }

    @Override
    public void querySuccess(BaseListEntity<QuestionBean> listEntity) {
        questions=listEntity.getItems();
        QuestionBean questionBean=questions.get(index);
        data.clear();
        data.add(questionBean);
        data.addAll(questionBean.getOptions());

        mAdapter.setIndex(index);
        mAdapter.setTotal(questions.size());
        mAdapter.notifyDataSetChanged();
        investorInfoBean.setHas_roadshow(1);

    }

    @Override
    public void startLoad(String msg) {
        showDialog(msg);
    }

    @Override
    public void endLoad() {
        dialogDismiss();
    }

    @Override
    public void onUploadAnswersSuccess() {

        mPresenter.changeRoadStatus();
        investorInfoBean.setHas_roadshow(1);

        Bundle bundle=new Bundle();
        bundle.putParcelable(ConstantUtil.INTENT_PARCELABLE,investorInfoBean);
        gotoActivity(SuccessActivity.class,bundle);
        Observable.just(1)
                .delay(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        EventBus.getDefault().post(new CommentEvent(ConstantUtil.ROAD_SUCCESS),ConstantUtil.ROAD_SUCCESS);
                    }
                });
    }

    @Override
    public void queryNormalQuestionsSuccess(CommonBean commonBean) {
        if(commonBean!=null&&commonBean.getQuestions()!=null){
            for(String s:commonBean.getQuestions()){
                TagEntity tagEntity=new TagEntity();
                tagEntity.setTag_name(s);
                normalQuestions.add(tagEntity);
            }
        }
    }

    @Override
    public void requestTypeBack(int type, ArrayList<FilterEntity> list) {
        switch (type) {
            case TYPE_STAGE:
                stageData.clear();
                stageData.addAll(list);
                break;
        }
    }

    @Override
    public void queryProjectSuccess(ProjectEntity entity) {
        if(entity!=null&&entity.getList()!=null&&!entity.getList().isEmpty()){
            ProjectBean bean=entity.getList().get(0);
            if(!isHasComfirm&&bean.getLast_stage_id()!=0){
                filterEntity.setType_id(bean.getLast_stage_id());
            }
        }
    }


    public void showQuestionDialog(final int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.add_question_dialog, null);
        etInputQuestion = (EditText) view.findViewById(R.id.etInputQuestion);
        //etInputQuestion.setHint(hint);
        View tvAddQuestion = view.findViewById(R.id.tvAddQuestion);
        View normalQuestionContainer=view.findViewById(R.id.normalQuestionContainer);
        final TagFlowLayout flowLayout= (TagFlowLayout) view.findViewById(R.id.flowLayout);

        if(normalQuestions==null||normalQuestions.isEmpty()){
            normalQuestionContainer.setVisibility(View.GONE);
        }else{
            normalQuestionContainer.setVisibility(View.VISIBLE);
            final TagAdapter tagAdapter = new TagAdapter<TagEntity>(normalQuestions) {
                @Override
                public View getView(FlowLayout parent, int position, TagEntity tagEntity) {
                    View view = LayoutInflater.from(RoadCommentActivity.this).inflate(R.layout.item_normal_question, flowLayout, false);
                    TextView tv= (TextView) view.findViewById(R.id.tvContent);
                    if(tagEntity.isSelected()){
                        tv.setSelected(true);
                    }else{
                        tv.setSelected(false);
                    }
                    tv.setText(tagEntity.getTag_name());
                    return view;
                }
            };
            flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {

                    if(!normalQuestions.get(position).isSelected()){
                        for(TagEntity entity:normalQuestions){
                            entity.setSelected(false);
                        }
                        normalQuestions.get(position).setSelected(true);
                        tagAdapter.notifyDataChanged();
                        etInputQuestion.setText(normalQuestions.get(position).getTag_name());
                        etInputQuestion.setSelection(normalQuestions.get(position).getTag_name().length());
                    }
                    return false;
                }
            });
            flowLayout.setMaxSelectCount(1);
            flowLayout.setAdapter(tagAdapter);
        }


        tvAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInputQuestion.getText().toString();
                if (StringUtil.isBlank(content)) {
                    showMessage("问题不能为空");
                    return;
                }
                questionDialog.dismiss();
                if(data.get(position) instanceof OptionFirstBean){
                    OptionFirstBean optionBean= (OptionFirstBean) data.get(position);
                    if(optionBean.getAddQuestions()==null){
                        optionBean.setAddQuestions(new ArrayList<AddQuestionBean>());
                    }
                    AddQuestionBean bean=new AddQuestionBean();
                    bean.setTitle(content);
                    optionBean.getAddQuestions().add(bean);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        questionDialog = new Dialog(this);
        questionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        questionDialog.setContentView(view);
        Window inputWindow = questionDialog.getWindow();
        WindowManager.LayoutParams params = inputWindow.getAttributes();
        inputWindow.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);

        //inputWindow.setFlags( WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        inputWindow.setGravity(Gravity.BOTTOM);
        inputWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inputWindow.setWindowAnimations(R.style.dialog_fade_animation);
        inputWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        questionDialog.setCanceledOnTouchOutside(true);
        questionDialog.show();

        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        etInputQuestion.setFocusable(true);
                        InputMethodManager inputMethodManager = (InputMethodManager) etInputQuestion.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    }
                });
    }

    public void showOptionDialog(final int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.add_option_dialog, null);
        etInputOption = (EditText) view.findViewById(R.id.etInputOption);
        //etInputQuestion.setHint(hint);
        View tvAddOption = view.findViewById(R.id.tvAddOption);

        tvAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInputOption.getText().toString();
                if (StringUtil.isBlank(content)) {
                    showMessage("选项不能为空");
                    return;
                }
                optionDialog.dismiss();
                if(data.get(position) instanceof OptionFirstBean){
                    OptionFirstBean optionBean= (OptionFirstBean) data.get(position);
                    if(optionBean.getLink_question().getOptions()==null){
                        optionBean.getLink_question().setOptions(new ArrayList<OptionSecondBean>());
                    }
                    //添加选项
                    OptionSecondBean secondBean=new OptionSecondBean();
                    secondBean.setOption_content(content);
                    secondBean.setOption_id(0);
                    secondBean.setSelected(true);

                    //单选还是多选
                    if(optionBean.getLink_question().getMulti_select()==0){
                        for(OptionSecondBean optionSecondBean:optionBean.getLink_question().getOptions()){
                            optionSecondBean.setSelected(false);
                        }
                    }
                    optionBean.getLink_question().getOptions().add(secondBean);
                    mAdapter.notifyDataSetChanged();
                }

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

    public void showAnswerDialog(final int optionPosition,final int addedQuestionPosition) {
        View view = LayoutInflater.from(this).inflate(R.layout.add_answer_dialog, null);
        etInputAnswer = (EditText) view.findViewById(R.id.etInputAnsewer);
        etInputAnswer.setHint("输入你的答案");
        if(data.get(optionPosition) instanceof OptionFirstBean){
            OptionFirstBean optionBean= (OptionFirstBean) data.get(optionPosition);
            //添加答案
            AddQuestionBean questionBean=optionBean.getAddQuestions().get(addedQuestionPosition);
            String oldAnswer=StringUtil.formatString(questionBean.getAnswer());
            etInputAnswer.setText(oldAnswer);
            etInputAnswer.setSelection(oldAnswer.length());
        }
        View tvAddAnswer = view.findViewById(R.id.tvAddAnswer);

        tvAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInputAnswer.getText().toString();
                if (StringUtil.isBlank(content)) {
                    showMessage("答案不能为空");
                    return;
                }
                answerDialog.dismiss();
                if(data.get(optionPosition) instanceof OptionFirstBean){
                    OptionFirstBean optionBean= (OptionFirstBean) data.get(optionPosition);
                    //添加答案
                    AddQuestionBean questionBean=optionBean.getAddQuestions().get(addedQuestionPosition);
                    questionBean.setAnswer(content);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        answerDialog = new Dialog(this);
        answerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        answerDialog.setContentView(view);
        Window inputWindow = answerDialog.getWindow();
        WindowManager.LayoutParams params = inputWindow.getAttributes();
        inputWindow.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);

        //inputWindow.setFlags( WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        inputWindow.setGravity(Gravity.BOTTOM);
        inputWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inputWindow.setWindowAnimations(R.style.dialog_fade_animation);
        inputWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        answerDialog.setCanceledOnTouchOutside(true);
        answerDialog.show();

        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        etInputAnswer.setFocusable(true);
                        InputMethodManager inputMethodManager = (InputMethodManager) etInputAnswer.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    }
                });
    }

    /**
     * 两个按钮的dialog
     */
    protected void showSubmitDialog() {
        isHasComfirm=true;

        View view = View.inflate(this, R.layout.submit_dialog, null);
        final TextView tvStage= (TextView) view.findViewById(R.id.tvStage);

        for(FilterEntity entity:stageData){
            if(entity.getType_id()==filterEntity.getType_id()){
                tvStage.setText(entity.getType_name());
                break;
            }
        }

        view.findViewById(R.id.btnLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAnswers(filterEntity.getType_id(),1);
            }
        });
        view.findViewById(R.id.btnRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAnswers(filterEntity.getType_id(),0);
            }
        });
        view.findViewById(R.id.llStage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStageDialog(new OnStageClick() {
                    @Override
                    public void onClick() {
                        tvStage.setText(filterEntity.getType_name());
                    }
                });
            }
        });

        AutoUtils.autoSize(view);
        submitDialog = new Dialog(this);
        submitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        submitDialog.setContentView(view);
        Window regionWindow = submitDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        submitDialog.setCanceledOnTouchOutside(true);
        submitDialog.show();
    }

    public static interface OnStageClick{
        void onClick();
    }

    /**
     * 显示行业的dialog
     */
    private void showStageDialog(final OnStageClick onStageClick) {
        View view = LayoutInflater.from(this).inflate(R.layout.domain_dialog, null);
        final StageHolder holder = new StageHolder(view);
        holder.tvDialogTitle.setText(getString(R.string.finance_stage));
        holder.tvCancleDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissStageDialog();
            }
        });

        holder.tvComfirmDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissStageDialog();
                onStageClick.onClick();
            }
        });
        stageAdapter = new TagAdapter<FilterEntity>(stageData) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                View view = LayoutInflater.from(RoadCommentActivity.this).inflate(R.layout.item_filter, holder.flowLayout, false);
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

                    for(FilterEntity entity:stageData){
                        entity.setSelected(false);
                    }
                    filterEntity=stageData.get(position);
                    stageData.get(position).setSelected(true);
                    stageAdapter.notifyDataChanged();
                }
                return true;
            }
        });
        for(FilterEntity entity:stageData){
            if(entity.getType_id()==filterEntity.getType_id()){
                entity.setSelected(true);
                break;
            }
        }

        holder.flowLayout.setAdapter(stageAdapter);
        holder.flowLayout.setMaxSelectCount(1);
        stageDialog = new Dialog(this);
        stageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        stageDialog.setContentView(view);
        Window regionWindow = stageDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        stageDialog.setCanceledOnTouchOutside(true);
        stageDialog.show();
    }

    private void dismissStageDialog() {
        if (stageDialog != null && stageDialog.isShowing()) {
            stageDialog.dismiss();
        }
    }

    static class StageHolder {
        @BindView(R.id.tvDialogTitle)
        TextView tvDialogTitle;
        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;
        @BindView(R.id.tvCancleDomain)
        TextView tvCancleDomain;
        @BindView(R.id.tvComfirmDomain)
        TextView tvComfirmDomain;

        StageHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
