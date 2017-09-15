package com.qtin.sexyvc.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.Presenter;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.mvp.test.progress.LoadingDialog;
import com.qtin.sexyvc.utils.NameLengthFilter;
import com.squareup.leakcanary.RefWatcher;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by jess on 8/5/16 14:11
 * contact with jess.yan.effort@gmail.com
 */
public abstract class MyBaseFragment<P extends Presenter> extends BaseFragment<P> {
    protected CustomApplication customApplication;
    private Dialog twoButtondialog;
    private Dialog selectPhotoDialog;
    private Dialog inputDialog;

    private LoadingDialog loadingDialog;
    private Dialog oneButtonDialog;

    @Override
    protected void ComponentInject() {
        customApplication = (CustomApplication)mActivity.getApplication();
        setupFragmentComponent(customApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher watcher = CustomApplication.getRefWatcher(getActivity());//使用leakCanary检测fragment的内存泄漏
        if (watcher != null) {
            watcher.watch(this);
        }
        this.customApplication =null;

    }

    protected void showLoadingDialog(String msg){
        loadingDialog=new LoadingDialog(mActivity,msg);
        loadingDialog.show();
    }

    protected void loadingDialogDismiss(){
        if(loadingDialog!=null){
            loadingDialog.close();
        }
    }


    public void gotoActivity(Class<? extends Activity> activityClass){
        Intent intent=new Intent(mActivity,activityClass);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.activity_enter_from_right,R.anim.activity_exit_to_left);
    }

    public void gotoActivity(Class<? extends Activity> activityClass,Bundle bundle){
        Intent intent=new Intent(mActivity,activityClass);
        intent.putExtras(bundle);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.activity_enter_from_right,R.anim.activity_exit_to_left);
    }

    protected void showTwoButtonDialog(String title, String stringLeft, String stringRight, final TwoButtonListerner listerner) {

        View view = View.inflate(mActivity, R.layout.two_button_dialog, null);
        TextView tvDialogTitle= (TextView) view.findViewById(R.id.tvDialogTitle);
        TextView btnLeft= (TextView) view.findViewById(R.id.btnLeft);
        TextView btnRight= (TextView) view.findViewById(R.id.btnRight);

        tvDialogTitle.setText(title);
        btnLeft.setText(stringLeft);
        btnRight.setText(stringRight);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.leftClick();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.rightClick();
            }
        });

        AutoUtils.autoSize(view);
        twoButtondialog = new Dialog(mActivity);
        twoButtondialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        twoButtondialog.setContentView(view);
        Window regionWindow = twoButtondialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        twoButtondialog.setCanceledOnTouchOutside(true);
        twoButtondialog.show();
    }


    private Dialog comfirmDialog;
    protected void showComfirmDialog(String title,String content,String button,final ComfirmListerner listerner) {

        View view = View.inflate(mActivity, R.layout.one_button_dialog, null);
        TextView tvDialogTitle= (TextView) view.findViewById(R.id.tvDialogTitle);
        Button btnRight= (Button) view.findViewById(R.id.btnRight);
        TextView tvContent= (TextView) view.findViewById(R.id.tvContent);
        if(StringUtil.isBlank(content)){
            tvContent.setVisibility(View.GONE);
        }else{
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(content);
        }

        tvDialogTitle.setText(title);
        btnRight.setText(button);

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onComfirm();
            }
        });

        AutoUtils.autoSize(view);
        comfirmDialog = new Dialog(mActivity);
        comfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        comfirmDialog.setContentView(view);
        Window regionWindow = comfirmDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        comfirmDialog.setCanceledOnTouchOutside(true);
        comfirmDialog.show();
    }

    protected void dismissComfirmDialog(){
        if(comfirmDialog!=null&&comfirmDialog.isShowing()){
            comfirmDialog.dismiss();
        }
    }

    protected void dismissTwoButtonDialog(){
        if(twoButtondialog!=null&&twoButtondialog.isShowing()){
            twoButtondialog.dismiss();
        }
    }

    protected void showBottomDialog(String first,String second,String cancle,final SelecteListerner listerner) {

        View view = View.inflate(mActivity, R.layout.select_photo_dialog, null);
        AutoUtils.autoSize(view);
        view.findViewById(R.id.tvHint).setVisibility(View.GONE);
        view.findViewById(R.id.lineHint).setVisibility(View.GONE);

        TextView btn_report = (TextView) view.findViewById(R.id.btn_report);
        TextView btn_error = (TextView) view.findViewById(R.id.btn_error);
        TextView cancleSelected = (TextView) view.findViewById(R.id.cancleSelected);

        btn_report.setText(StringUtil.formatString(first));
        btn_error.setText(StringUtil.formatString(second));
        cancleSelected.setText(StringUtil.formatString(cancle));

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onFirstClick();
            }
        });
        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onSecondClick();
            }
        });
        cancleSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onCancle();
            }
        });
        selectPhotoDialog = new Dialog(mActivity);
        selectPhotoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPhotoDialog.setContentView(view);
        Window regionWindow = selectPhotoDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        selectPhotoDialog.setCanceledOnTouchOutside(true);
        selectPhotoDialog.show();
    }

    protected void dismissBottomDialog(){
        if(selectPhotoDialog!=null&&selectPhotoDialog.isShowing()){
            selectPhotoDialog.dismiss();
        }
    }

    protected void showInputDialog(String title, String warn,String stringLeft, String stringRight, final InputListerner listerner) {

        View view = View.inflate(mActivity, R.layout.input_dialog, null);
        TextView tvTitle= (TextView) view.findViewById(R.id.tvTitle);
        TextView tvWarn=(TextView) view.findViewById(R.id.tvWarn);
        Button btnLeft= (Button) view.findViewById(R.id.btnLeft);
        Button btnRight= (Button) view.findViewById(R.id.btnRight);
        final EditText etContent= (EditText) view.findViewById(R.id.etContent);

        //etContent.setHint(getString(R.string.char_count_limit));
        InputFilter[] filters = { new NameLengthFilter(16) };
        etContent.setFilters(filters);

        tvTitle.setText(title);
        btnLeft.setText(warn);
        btnLeft.setText(stringLeft);
        btnRight.setText(stringRight);


        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.cancle();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtil.isBlank(etContent.getText().toString())){
                    UiUtils.showToastShort(mActivity,"组名不能为空");
                    return;
                }
                listerner.onComfirm(etContent.getText().toString());
            }
        });

        AutoUtils.autoSize(view);
        inputDialog = new Dialog(mActivity);
        inputDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        inputDialog.setContentView(view);
        Window regionWindow = inputDialog.getWindow();
        regionWindow.setGravity(Gravity.CENTER);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.dialog_fade_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        inputDialog.setCanceledOnTouchOutside(true);
        inputDialog.setCancelable(false);
        inputDialog.show();
    }

    protected void dismissInputDialog(){
        if(inputDialog!=null&&inputDialog.isShowing()){
            inputDialog.dismiss();
        }
    }


    public static interface TwoButtonListerner {
        void leftClick();
        void rightClick();
    }
    public static interface ComfirmListerner {
        void onComfirm();
    }

    public static interface SelecteListerner {
        void onFirstClick();
        void onSecondClick();
        void onCancle();
    }

    public static interface InputListerner {
        void onComfirm(String content);
        void cancle();
    }

    protected void showBottomOneDialog(String optionStr,final OneButtonListerner listerner) {
        View view = View.inflate(mActivity, R.layout.bottom_one_button_dialog, null);
        TextView btnOption= (TextView) view.findViewById(R.id.btnOption);
        TextView cancleSelected= (TextView) view.findViewById(R.id.cancleSelected);
        btnOption.setText(optionStr);

        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onOptionSelected();
            }
        });

        cancleSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onCancle();
            }
        });

        AutoUtils.autoSize(view);
        oneButtonDialog = new Dialog(mActivity);
        oneButtonDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        oneButtonDialog.setContentView(view);
        Window regionWindow = oneButtonDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        oneButtonDialog.setCanceledOnTouchOutside(true);
        oneButtonDialog.show();
    }
    public static interface OneButtonListerner {
        void onOptionSelected();
        void onCancle();
    }
    protected void dismissBottomOneButtonDialog(){
        if(oneButtonDialog!=null&&oneButtonDialog.isShowing()){
            oneButtonDialog.dismiss();
        }
    }

    public static interface OnListItemClickListener{
        void onClick(int position);
    }

    private Dialog listBottomDialog;
    protected void showListBottomDialog(final String[] strings, final OnListItemClickListener listener) {

        View view = View.inflate(mActivity, R.layout.list_bottom_dialog, null);
        ListView listView= (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return strings.length;
            }

            @Override
            public Object getItem(int position) {
                return strings[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView  textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_action,parent,false);
                textView.setText(strings[position]);
                return textView;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismissListBottomDialog();
                listener.onClick(position);
            }
        });
        view.findViewById(R.id.tvCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissListBottomDialog();
            }
        });
        listBottomDialog = new Dialog(mActivity);
        listBottomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listBottomDialog.setContentView(view);
        Window regionWindow = listBottomDialog.getWindow();
        regionWindow.setGravity(Gravity.BOTTOM);
        regionWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        regionWindow.setWindowAnimations(R.style.view_animation);
        regionWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        listBottomDialog.setCanceledOnTouchOutside(true);
        listBottomDialog.show();
    }

    protected void dismissListBottomDialog(){
        if(listBottomDialog!=null&&listBottomDialog.isShowing()){
            listBottomDialog.dismiss();
        }
    }
}
