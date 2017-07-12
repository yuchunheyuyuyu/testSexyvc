package com.qtin.sexyvc.ui.road;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.OnSpecialClickListener;
import com.qtin.sexyvc.ui.road.bean.OnOptionClickListener;
import com.qtin.sexyvc.ui.road.bean.OptionFirstBean;
import com.qtin.sexyvc.ui.road.bean.OptionSecondBean;
import com.qtin.sexyvc.ui.road.bean.QuestionBean;
import com.qtin.sexyvc.ui.road.di.DaggerRoadCommentComponent;
import com.qtin.sexyvc.ui.road.di.RoadCommentModule;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.utils.ConstantUtil;
import java.util.ArrayList;
import butterknife.BindView;
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
    private String title;
    private long fund_id;
    private long investor_id;
    private int index;

    private ArrayList<QuestionBean> questions;
    private ArrayList<DataTypeInterface> data=new ArrayList<>();
    private RoadQuestionAdapter mAdapter;

    private EditText etInputQuestion;
    private Dialog questionDialog;
    private EditText etInputOption;
    private Dialog optionDialog;

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
        title = getIntent().getExtras().getString(ConstantUtil.INTENT_TITLE);
        fund_id = getIntent().getExtras().getLong(ConstantUtil.INTENT_ID_FUND);
        investor_id = getIntent().getExtras().getLong(ConstantUtil.INTENT_ID_INVESTOR);
        index = getIntent().getExtras().getInt(ConstantUtil.INTENT_INDEX);

        tvTitle.setText(StringUtil.formatString(title));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new RoadQuestionAdapter(this,data);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnSpecialClickListener(new OnSpecialClickListener() {
            @Override
            public void onSpecialItem(int position) {
                if(index<questions.size()-1){
                    Bundle bundle=new Bundle();
                    bundle.putString(ConstantUtil.INTENT_TITLE,title);
                    bundle.putLong(ConstantUtil.INTENT_ID_FUND,fund_id);
                    bundle.putLong(ConstantUtil.INTENT_ID_INVESTOR,investor_id);
                    bundle.putInt(ConstantUtil.INTENT_INDEX,index+1);
                    bundle.putParcelableArrayList(ConstantUtil.INTENT_PARCELABLE_ARRAY,questions);
                    gotoActivity(RoadCommentActivity.class,bundle);
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
        });
        if(index==0){
            questions=new ArrayList<>();
            mPresenter.queryRoadQuestion();
        }else{
            questions=getIntent().getExtras().getParcelableArrayList(ConstantUtil.INTENT_PARCELABLE_ARRAY);
            QuestionBean questionBean=questions.get(index);
            data.clear();
            data.add(questionBean);
            data.addAll(questionBean.getOptions());

            mAdapter.setIndex(index);
            mAdapter.setTotal(questions.size());
            mAdapter.notifyDataSetChanged();

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
    }

    @Override
    public void startLoad(String msg) {
        showDialog(msg);
    }

    @Override
    public void endLoad() {
        dialogDismiss();
    }

    public void showQuestionDialog(final int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.add_question_dialog, null);
        etInputQuestion = (EditText) view.findViewById(R.id.etInputQuestion);
        //etInputQuestion.setHint(hint);
        View tvAddQuestion = view.findViewById(R.id.tvAddQuestion);

        tvAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInputQuestion.getText().toString();
                if (StringUtil.isBlank(content)) {
                    showMessage("问题不能为空");
                    return;
                }

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
}
