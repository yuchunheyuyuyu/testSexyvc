package com.qtin.sexyvc.ui.user.modify;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.user.modify.di.DaggerModifyComponent;
import com.qtin.sexyvc.ui.user.modify.di.ModifyModule;
import com.qtin.sexyvc.ui.widget.ClearableEditText;
import com.qtin.sexyvc.ui.widget.PhoneEditText;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ls on 17/4/26.
 */
public class ModifyActivity extends MyBaseActivity<ModifyPresent> implements ModifyContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.etContent)
    ClearableEditText etContent;
    @BindView(R.id.singLineContainer)
    LinearLayout singLineContainer;
    @BindView(R.id.tvCountDown)
    TextView tvCountDown;
    @BindView(R.id.etIntroduce)
    EditText etIntroduce;
    @BindView(R.id.introduceContainer)
    RelativeLayout introduceContainer;
    @BindView(R.id.etEmail)
    ClearableEditText etEmail;
    @BindView(R.id.etEmailAlternate)
    ClearableEditText etEmailAlternate;
    @BindView(R.id.emailContainer)
    LinearLayout emailContainer;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.etPhoneBackup)
    PhoneEditText etPhoneBackup;
    @BindView(R.id.phoneContainer)
    LinearLayout phoneContainer;
    @BindView(R.id.etPhone2)
    PhoneEditText etPhone2;
    @BindView(R.id.etPhoneBackup2)
    PhoneEditText etPhoneBackup2;
    @BindView(R.id.phoneContainer2)
    LinearLayout phoneContainer2;
    @BindView(R.id.etCompany)
    ClearableEditText etCompany;
    @BindView(R.id.etTitle)
    ClearableEditText etTitle;
    @BindView(R.id.positionContainer)
    LinearLayout positionContainer;
    private String hint;
    //来源于个人中心
    public static final int MODIFY_NICK = 0x012;
    public static final int MODIFY_INTRODUCE = 0x013;
    public static final int MODIFY_EMAIL = 0x014;
    public static final int MODIFY_PHONE = 0x015;
    public static final int MODIFY_POSITION = 0x020;

    //来源于添加项目
    public static final int MODIFY_PROJECT_NAME = 0x016;
    public static final int MODIFY_PROJECT_INTRODUCE = 0x017;

    //来源于关注详情
    public static final int MODIFY_CONCERN_TELPHONE = 0x018;
    public static final int MODIFY_CONCERN_EMAIL = 0x019;
    public static final int MODIFY_CONCERN_WECAHT = 0x01a;
    public static final int MODIFY_CONCERN_REMARK = 0x01b;


    public static final String MODIFY_INTENT = "modify_type";
    public static final String MODIFY_INTENT_VALUE1 = "modify_value1";
    public static final String MODIFY_INTENT_VALUE2 = "modify_value2";
    public static final String MODIFY_INTENT_VALUE3 = "modify_value3";


    //带进来的值
    private String value1;
    private String value2;

    //联系人id,用于编辑联系人
    private long contact_id;

    private int modifyType;
    //输入框内的值
    private String nick;
    private String u_email;
    private String u_backup_email;
    private String u_signature;
    private String u_phone;
    private String u_backup_phone;

    private int wordNumber = 140;

    private String project_name;
    private String project_introduce;

    private String contact_phone;//投资人电话
    private String contact_backup_phone;//投资人电话

    private String contact_email;
    private String contact_backup_email;
    private String contact_wechat;
    private String contact_remark;

    private String u_company;
    private String u_title;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerModifyComponent.builder().appComponent(appComponent).modifyModule(new ModifyModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.modify_activity;
    }

    @Override
    protected void initData() {
        modifyType = getIntent().getExtras().getInt(MODIFY_INTENT);

        tvRight.setVisibility(View.VISIBLE);

        //编辑联系人需要contact_id
        if (modifyType == MODIFY_CONCERN_TELPHONE || modifyType == MODIFY_CONCERN_EMAIL ||
                modifyType == MODIFY_CONCERN_WECAHT || modifyType == MODIFY_CONCERN_REMARK) {
            contact_id = getIntent().getExtras().getLong("contact_id");
        }

        if (modifyType == MODIFY_POSITION) {
            positionContainer.setVisibility(View.VISIBLE);
            value1 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE1);
            value2 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE2);
            etCompany.setText(StringUtil.formatString(value1));
            etCompany.setSelection(StringUtil.formatString(value1).length());
            etTitle.setText(StringUtil.formatString(value2));
            etTitle.setSelection(StringUtil.formatString(value2).length());

            etTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

            tvRight.setText(getResources().getString(R.string.comfirm));
            tvTitle.setText(getResources().getString(R.string.title_institution));
        } else if (modifyType == MODIFY_CONCERN_TELPHONE) {

            phoneContainer2.setVisibility(View.VISIBLE);
            value1 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE1);
            value2 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE2);

            etPhone2.setText(StringUtil.formatString(value1));
            etPhone2.setSelection(etPhone2.getText().toString().length());

            etPhoneBackup2.setText(StringUtil.formatString(value2));
            etPhoneBackup2.setSelection(etPhoneBackup2.getText().toString().length());

            tvTitle.setText(getResources().getString(R.string.title_phone));

        } else if (modifyType == MODIFY_NICK || modifyType == MODIFY_PROJECT_NAME || modifyType == MODIFY_CONCERN_WECAHT) {
            singLineContainer.setVisibility(View.VISIBLE);
            value1 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE1);
            String nick = StringUtil.formatString(value1);
            etContent.setText(nick);
            etContent.setSelection(nick.length());

            if (modifyType == MODIFY_NICK) {
                etContent.setHint(getResources().getString(R.string.nick));
                tvTitle.setText(getResources().getString(R.string.title_nick));
                etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
            } else if (modifyType == MODIFY_PROJECT_NAME) {
                etContent.setHint(getResources().getString(R.string.project_name));
                tvTitle.setText(getResources().getString(R.string.project_name));
                tvRight.setText(getResources().getString(R.string.comfirm));
                etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            } else if (modifyType == MODIFY_CONCERN_WECAHT) {
                etContent.setHint(getResources().getString(R.string.we_chat));
                tvTitle.setText(getResources().getString(R.string.we_chat));
                etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
            }

        } else if (modifyType == MODIFY_EMAIL || modifyType == MODIFY_CONCERN_EMAIL) {
            emailContainer.setVisibility(View.VISIBLE);
            value1 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE1);
            value2 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE2);

            etEmail.setText(StringUtil.formatString(value1));
            etEmail.setSelection(StringUtil.formatString(value1).length());

            etEmailAlternate.setText(StringUtil.formatString(value2));
            etEmailAlternate.setSelection(StringUtil.formatString(value2).length());


            tvTitle.setText(getResources().getString(R.string.title_email));
        } else if (modifyType == MODIFY_INTRODUCE || modifyType == MODIFY_PROJECT_INTRODUCE || modifyType == MODIFY_CONCERN_REMARK) {
            value1 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE1);
            etIntroduce.setText(StringUtil.formatString(value1));
            etIntroduce.setSelection(StringUtil.formatString(value1).length());

            introduceContainer.setVisibility(View.VISIBLE);

            if (modifyType == MODIFY_INTRODUCE) {
                etIntroduce.setHint(getResources().getString(R.string.hint_introduce));
                tvTitle.setText(getResources().getString(R.string.title_introduce));
            } else if (modifyType == MODIFY_PROJECT_INTRODUCE) {
                wordNumber = 140;
                tvRight.setText(getResources().getString(R.string.comfirm));
                tvTitle.setText(getResources().getString(R.string.project_introduce));
                etIntroduce.setHint(getResources().getString(R.string.hint_introduce_project));
            } else if (modifyType == MODIFY_CONCERN_REMARK) {
                wordNumber = 140;
                tvTitle.setText(getResources().getString(R.string.remarks));
                etIntroduce.setHint(getResources().getString(R.string.remarks));
            }


            tvCountDown.setText(String.format(getResources().getString(R.string.input_count), "" + (wordNumber - StringUtil.formatString(value1).length())));

            etIntroduce.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int last = wordNumber;
                    if (s != null && s.toString() != null) {
                        last = wordNumber - s.toString().length();
                    }
                    tvCountDown.setText(String.format(getResources().getString(R.string.input_count), "" + last));
                }
            });
        } else if (modifyType == MODIFY_PHONE) {
            tvTitle.setText(getResources().getString(R.string.title_phone));
            phoneContainer.setVisibility(View.VISIBLE);
            value1 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE1);
            value2 = getIntent().getExtras().getString(MODIFY_INTENT_VALUE2);

            tvPhone.setText(StringUtil.getFormatPhone(value1));
            etPhoneBackup.setText(StringUtil.formatString(value2));
            etPhoneBackup.setSelection(etPhoneBackup.getText().toString().length());
        }
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
        UiUtils.showToastShort(this, message);
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

                if(modifyType==MODIFY_POSITION){
                    u_company=etCompany.getText().toString();
                    u_title=etTitle.getText().toString();
                    if (StringUtil.isBlank(u_company)) {
                        showMessage("机构不能为空");
                        return;
                    }

                    if (StringUtil.isBlank(u_title)) {
                        showMessage("职务不能为空");
                        return;
                    }
                    editSuccess();

                }else if (modifyType == MODIFY_NICK) {
                    nick = etContent.getText().toString().trim();
                    if (StringUtil.isBlank(nick)) {
                        showMessage("昵称不能为空");
                        return;
                    }
                    mPresenter.editNick(nick);
                } else if (modifyType == MODIFY_EMAIL) {
                    u_email = etEmail.getText().toString().trim();
                    u_backup_email = etEmailAlternate.getText().toString().trim();

                    if (!StringUtil.isEmail(u_email)) {
                        showMessage("邮箱格式不合法");
                        return;
                    }
                    if (!StringUtil.isBlank(u_backup_email)&&!StringUtil.isEmail(u_backup_email)) {
                        showMessage("备用邮箱格式不合法");
                        return;
                    }
                    mPresenter.editEmail(u_email, u_backup_email);

                } else if (modifyType == MODIFY_INTRODUCE) {
                    u_signature = etIntroduce.getText().toString().trim();
                    if (StringUtil.isBlank(u_signature)) {
                        showMessage("自我介绍不能为空");
                        return;
                    }
                    mPresenter.editSignature(u_signature);
                } else if (modifyType == MODIFY_PHONE) {
                    u_phone = tvPhone.getText().toString().trim();
                    u_backup_phone = etPhoneBackup.getPhoneText().trim();
                    if (!etPhoneBackup.isMobileNO()) {
                        //showMessage("手机格式不合法");
                        finish();
                        return;
                    }
                    mPresenter.editPhone(u_backup_phone);
                } else if ((modifyType == MODIFY_PROJECT_NAME)) {
                    project_name = etContent.getText().toString().trim();
                    if (StringUtil.isBlank(project_name)) {
                        showMessage("项目名称不能为空");
                        return;
                    }
                    editSuccess();

                } else if (modifyType == MODIFY_PROJECT_INTRODUCE) {
                    project_introduce = etIntroduce.getText().toString().trim();
                    if (StringUtil.isBlank(project_introduce)) {
                        showMessage("项目介绍不能为空");
                        return;
                    }
                    editSuccess();
                } else if (modifyType == MODIFY_CONCERN_TELPHONE) {
                    contact_phone = etPhone2.getPhoneText().trim();
                    contact_backup_phone = etPhoneBackup2.getPhoneText().trim();
                    if (!etPhone2.isMobileNO()) {
                        showMessage("手机格式不合法");
                        return;
                    }

                    if (!StringUtil.isBlank(contact_backup_phone)&&!etPhoneBackup2.isMobileNO()) {
                        showMessage("备用手机格式不合法");
                        return;
                    }
                    mPresenter.editContactPhone(contact_id, contact_phone, contact_backup_phone);

                } else if (modifyType == MODIFY_CONCERN_EMAIL) {
                    contact_email = etEmail.getText().toString().trim();
                    contact_backup_email = etEmailAlternate.getText().toString().trim();

                    if (!StringUtil.isEmail(contact_email)) {
                        showMessage("邮箱格式不合法");
                        return;
                    }
                    if (!StringUtil.isBlank(contact_backup_email)&&!StringUtil.isEmail(contact_backup_email)) {
                        showMessage("备用邮箱格式不合法");
                        return;
                    }
                    mPresenter.editContactEmail(contact_id, contact_email, contact_backup_email);

                } else if (modifyType == MODIFY_CONCERN_WECAHT) {
                    contact_wechat = etContent.getText().toString().trim();
                    if (StringUtil.isBlank(contact_wechat)) {
                        showMessage("微信不能为空");
                        return;
                    }
                    mPresenter.editContactWechat(contact_id, contact_wechat);

                } else if (modifyType == MODIFY_CONCERN_REMARK) {
                    contact_remark = etIntroduce.getText().toString().trim();
                    if (StringUtil.isBlank(contact_remark)) {
                        showMessage("备注内容不能为空");
                        return;
                    }
                    mPresenter.editContactRemark(contact_id, contact_remark);
                }
                break;
        }
    }

    @Override
    public void editSuccess() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Intent intent = new Intent();
                        if (modifyType == MODIFY_NICK) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, nick);
                        } else if (modifyType == MODIFY_EMAIL) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, u_email);
                            intent.putExtra(MODIFY_INTENT_VALUE2, u_backup_email);
                        } else if (modifyType == MODIFY_INTRODUCE) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, u_signature);
                        } else if (modifyType == MODIFY_PHONE) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, u_phone);
                            intent.putExtra(MODIFY_INTENT_VALUE2, u_backup_phone);

                        } else if (modifyType == MODIFY_PROJECT_INTRODUCE) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, project_introduce);
                        } else if (modifyType == MODIFY_PROJECT_NAME) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, project_name);

                        } else if (modifyType == MODIFY_CONCERN_TELPHONE) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, contact_phone);
                            intent.putExtra(MODIFY_INTENT_VALUE2, contact_backup_phone);
                        } else if (modifyType == MODIFY_CONCERN_EMAIL) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, contact_email);
                            intent.putExtra(MODIFY_INTENT_VALUE2, contact_backup_email);
                        } else if (modifyType == MODIFY_CONCERN_WECAHT) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, contact_wechat);
                        } else if (modifyType == MODIFY_CONCERN_REMARK) {
                            intent.putExtra(MODIFY_INTENT_VALUE1, contact_remark);
                        }else if(modifyType==MODIFY_POSITION){
                            intent.putExtra(MODIFY_INTENT_VALUE1, u_company);
                            intent.putExtra(MODIFY_INTENT_VALUE2, u_title);
                        }
                        setResult(0, intent);
                        finish();
                    }
                });
    }
}
