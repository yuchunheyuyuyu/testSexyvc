package com.qtin.sexyvc.ui.user.info;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.utils.UploadPhotoUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.main.MainActivity;
import com.qtin.sexyvc.ui.mycase.MyCaseActivity;
import com.qtin.sexyvc.ui.request.EditTypeRequest;
import com.qtin.sexyvc.ui.user.info.di.DaggerUserInfoComponent;
import com.qtin.sexyvc.ui.user.info.di.UserInfoModule;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.ui.user.position.PositionActivity;
import com.qtin.sexyvc.ui.widget.tagview.FlowLayout;
import com.qtin.sexyvc.ui.widget.tagview.TagAdapter;
import com.qtin.sexyvc.ui.widget.tagview.TagFlowLayout;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/4/26.
 */
public class UserInfoActivity extends MyBaseActivity<UserInfoPresent> implements UserInfoContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.tvMyDescription)
    TextView tvMyDescription;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvPosition)
    TextView tvPosition;

    public static final String INTENT_USER = "user_info";
    @BindView(R.id.tvDomainNum)
    TextView tvDomainNum;
    @BindView(R.id.tvStageNum)
    TextView tvStageNum;
    @BindView(R.id.tvCaseNum)
    TextView tvCaseNum;
    @BindView(R.id.investorInfoContainer)
    LinearLayout investorInfoContainer;
    private UserInfoEntity userInfo;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    private Dialog selectPhotoDialog;
    // 调用系统相册或者相机
    private final int CAMERA_REQUEST_CODE = 0x002;
    private final int ALBUM_REQUEST_CODE = 0x004;
    private final int CROP_REQUEST_CODE = 0x006;
    // 裁减过后的照片地址
    private String cropedPhoto;
    // 拍照地址
    private String path;

    private boolean isNeedGotoMain = false;//是否需要跳到首页

    //行业轮次对话框
    private Dialog domainDialog;
    private ArrayList<FilterEntity> domainData = new ArrayList<>();
    private Dialog stageDialog;
    private ArrayList<FilterEntity> stageData = new ArrayList<>();
    private TagAdapter domainAdapter;
    private TagAdapter stageAdapter;
    public static final int TYPE_DOMAIN = 0x001;//行业
    public static final int TYPE_STAGE = 0x002;//阶段

    private final static int MAX_DOMAIN_NUM=100;
    private final static int MAX_STAGE_NUM=100;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserInfoComponent.builder().appComponent(appComponent).userInfoModule(new UserInfoModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.user_info_activity;
    }

    @Override
    protected void initData() {
        mImageLoader = customApplication.getAppComponent().imageLoader();
        userInfo = getIntent().getExtras().getParcelable(INTENT_USER);
        try {
            isNeedGotoMain = getIntent().getExtras().getBoolean("isNeedGotoMain");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvTitle.setText(getResources().getString(R.string.title_user_info));
        setValue(userInfo);
        //获取投资行业
        mPresenter.getType("common_domain", TYPE_DOMAIN);
        //获取投资阶段
        mPresenter.getType("common_stage", TYPE_STAGE);
        //mPresenter.getUserInfo();暂时不需要重新请求数据
    }

    private void setInvestorInfo(UserInfoEntity entity){
        if(userInfo.getU_auth_type()== ConstantUtil.AUTH_TYPE_INVESTOR
                &&userInfo.getU_auth_state()==ConstantUtil.AUTH_STATE_PASS){
            investorInfoContainer.setVisibility(View.VISIBLE);
            tvDomainNum.setText(""+entity.getDomain_list().size());
            tvStageNum.setText(""+entity.getStage_list().size());
            tvCaseNum.setText(""+entity.getCase_number());
        }else{
            investorInfoContainer.setVisibility(View.GONE);
        }
    }

    private void setValue(UserInfoEntity entity) {
        setInvestorInfo(entity);
        //头像
        mImageLoader.loadImage(customApplication, GlideImageConfig
                .builder()
                .errorPic(R.drawable.avatar_user_s)
                .placeholder(R.drawable.avatar_user_s)
                .url(CommonUtil.getAbsolutePath(entity.getU_avatar()))
                .transformation(new CropCircleTransformation(this))
                .imageView(ivAvatar)
                .build());

        //昵称
        tvName.setText(StringUtil.isBlank(entity.getU_nickname()) ? getResources().getString(R.string.nick_defalut) : entity.getU_nickname());
        //性别
        if (entity.getU_gender() == 1) {
            tvSex.setText(getResources().getString(R.string.sex_male));
        } else {
            tvSex.setText(getResources().getString(R.string.sex_female));
        }
        //自我介绍
        tvMyDescription.setText(StringUtil.isBlank(entity.getU_signature()) ? getResources()
                .getString(R.string.input_defalut) : entity.getU_signature());

        //手机号码
        String mobile = entity.getU_phone();
        if (StringUtil.isBlank(mobile)) {
            mobile = getResources().getString(R.string.input_defalut);
        } else {
            mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        tvMobile.setText(mobile);
        //邮箱
        tvEmail.setText(StringUtil.isBlank(entity.getU_email()) ?
                getResources().getString(R.string.input_defalut) : entity.getU_email());
        //设置职位
        setPosition(entity.getU_company(), entity.getU_title());
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

    @Override
    protected boolean isNeedFinishAnim() {
        if (isNeedGotoMain) {
            return false;
        }
        return true;
    }

    @OnClick({R.id.ivLeft, R.id.avatarContainer, R.id.nickContainer, R.id.sexContainer, R.id.descriptionContainer, R.id.mobileContainer,
            R.id.emailContainer, R.id.positionContainer,R.id.domainContainer, R.id.stageContainer, R.id.caseContainer})
    public void onClick(View view) {
        if (userInfo == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.ivLeft:
                if (isNeedGotoMain) {
                    gotoActivity(MainActivity.class);
                } else {
                    finish();
                }

                break;
            case R.id.avatarContainer:
                showPhotoDialog(true);
                break;
            case R.id.nickContainer:
                Bundle nick = new Bundle();
                nick.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_NICK);
                nick.putString(ModifyActivity.MODIFY_INTENT_VALUE1, userInfo.getU_nickname());
                gotoActivityForResult(ModifyActivity.class, nick, ModifyActivity.MODIFY_NICK);
                break;
            case R.id.sexContainer:
                chooseSexDialog();
                break;
            case R.id.descriptionContainer:
                Bundle introduce = new Bundle();
                introduce.putString(ModifyActivity.MODIFY_INTENT_VALUE1, userInfo.getU_signature());
                introduce.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_INTRODUCE);
                gotoActivityForResult(ModifyActivity.class, introduce, ModifyActivity.MODIFY_INTRODUCE);
                break;
            case R.id.mobileContainer:
                Bundle mobile = new Bundle();
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE1, userInfo.getU_phone());
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE2, userInfo.getU_backup_phone());
                mobile.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_PHONE);
                gotoActivityForResult(ModifyActivity.class, mobile, ModifyActivity.MODIFY_PHONE);
                break;
            case R.id.emailContainer:
                Bundle email = new Bundle();
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE1, userInfo.getU_email());
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE2, userInfo.getU_backup_email());
                email.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_EMAIL);
                gotoActivityForResult(ModifyActivity.class, email, ModifyActivity.MODIFY_EMAIL);
                break;
            case R.id.positionContainer:
                gotoPosition();
                break;
            case R.id.domainContainer:
                showDomainDialog();
                break;
            case R.id.stageContainer:
                showStageDialog();
                break;
            case R.id.caseContainer:
                gotoActivity(MyCaseActivity.class);
                break;
        }
    }

    private void gotoPosition() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(UserInfoActivity.INTENT_USER, userInfo);
        gotoActivityForResult(PositionActivity.class, bundle, ModifyActivity.MODIFY_POSITION);
    }

    /**
     * 性别
     */
    private void chooseSexDialog() {

        View view = View.inflate(this, R.layout.choose_sex_dialog, null);
        AutoUtils.autoSize(view);

        View btn_report = view.findViewById(R.id.btn_report);
        View btn_error = view.findViewById(R.id.btn_error);
        View cancleSelected = view.findViewById(R.id.cancleSelected);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhotoDialog != null) {
                    selectPhotoDialog.dismiss();
                }
                mPresenter.editSex(1);
            }
        });
        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhotoDialog != null) {
                    selectPhotoDialog.dismiss();
                }
                mPresenter.editSex(0);
            }
        });
        cancleSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhotoDialog != null) {
                    selectPhotoDialog.dismiss();
                }
            }
        });
        selectPhotoDialog = new Dialog(this);
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

    /**
     * 相册弹出框
     */
    private void showPhotoDialog(boolean isAvatar) {

        View view = View.inflate(this, R.layout.select_photo_dialog, null);
        AutoUtils.autoSize(view);
        if (isAvatar) {
            view.findViewById(R.id.tvHint).setVisibility(View.GONE);
            view.findViewById(R.id.lineHint).setVisibility(View.GONE);
        }

        View btn_report = view.findViewById(R.id.btn_report);
        View btn_error = view.findViewById(R.id.btn_error);
        View cancleSelected = view.findViewById(R.id.cancleSelected);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhotoDialog != null) {
                    selectPhotoDialog.dismiss();
                }
                takePhoto();

            }
        });
        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhotoDialog != null) {
                    selectPhotoDialog.dismiss();
                }
                goToAlbum();
            }
        });
        cancleSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPhotoDialog != null) {
                    selectPhotoDialog.dismiss();
                }
            }
        });
        selectPhotoDialog = new Dialog(this);
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

    private void setPosition(String u_company, String u_title) {
        String position = "";
        if (StringUtil.isBlank(u_title) && StringUtil.isBlank(u_company)) {
            position = getResources().getString(R.string.not_fill);
        } else {
            position = "" + u_company + " " + u_title;
        }
        tvPosition.setText(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ModifyActivity.MODIFY_POSITION:
                if (data != null) {
                    userInfo=data.getExtras().getParcelable(ConstantUtil.INTENT_PARCELABLE);
                    setPosition(userInfo.getU_company(), userInfo.getU_title());
                    setInvestorInfo(userInfo);
                }
                break;
            case ModifyActivity.MODIFY_NICK:
                if (data != null) {
                    String nick = StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                    userInfo.setU_nickname(nick);
                    tvName.setText(nick);
                }
                break;
            case ModifyActivity.MODIFY_INTRODUCE:
                if (data != null) {
                    String sign = StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                    userInfo.setU_signature(sign);
                    tvMyDescription.setText(sign);
                }
                break;
            case ModifyActivity.MODIFY_EMAIL:
                if (data != null) {
                    String u_email = StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                    String u_backup_email = StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE2));
                    userInfo.setU_email(u_email);
                    userInfo.setU_backup_email(u_backup_email);
                    tvEmail.setText(u_email);
                }
                break;
            case ModifyActivity.MODIFY_PHONE:
                if (data != null) {
                    String u_phone = StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                    String u_backup_phone = StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE2));
                    userInfo.setU_phone(u_phone);
                    userInfo.setU_backup_phone(u_backup_phone);
                }
                break;
            case ALBUM_REQUEST_CODE:
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    File file = new File(picturePath);
                    if (file != null && file.exists()) {
                        Uri uri = Uri.fromFile(file);
                        cropPhoto(uri);
                    }
                    // 获取图片并显示
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CAMERA_REQUEST_CODE:
                try {
                    if (path != null) {
                        String newPath = path;
                        int angle = DeviceUtils.readPictureDegree(path);
                        if (angle != 0) {
                            newPath = path + "r.png";
                            Bitmap bitmapOriginal = UploadPhotoUtil.getUpLoadImage(
                                    path, BaseApplication.screenSize.x,
                                    BaseApplication.screenSize.y, true);
                            Bitmap bm = DeviceUtils.rotaingImageView(angle, bitmapOriginal);
                            OutputStream stream = new FileOutputStream(newPath);
                            bm.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                            DeviceUtils.recycle(bitmapOriginal);
                            DeviceUtils.recycle(bm);
                        } else {
                            newPath = path;
                        }

                        File file = new File(newPath);
                        if (file != null && file.exists()) {
                            Uri uri = Uri.fromFile(file);
                            cropPhoto(uri);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }

                break;
            case CROP_REQUEST_CODE: // 裁剪
                if (data != null) {
                    mPresenter.getQiNiuToken(cropedPhoto);
                }

                break;
        }
    }

    public void takePhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.CHINA);
            String fileName = sdf.format(new Date());
            File file = new File(DataHelper.getCacheFile(getApplicationContext()), fileName);
            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
            path = DataHelper.getCacheFile(getApplicationContext()).getAbsolutePath() + File.separator + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void goToAlbum() {
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, ALBUM_REQUEST_CODE);
    }

    // 裁减
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// 可裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        cropedPhoto = DataHelper.getCacheFile(getApplicationContext()) + File.separator
                + System.currentTimeMillis() + ".png";
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(cropedPhoto)));
        intent.putExtra("return-data", false);// 若为false则表示不返回数据
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    @Override
    public void editAvatarSuccess(String avatar) {
        userInfo.setU_avatar(avatar);
        //头像
        mImageLoader.loadImage(customApplication, GlideImageConfig
                .builder()
                .errorPic(R.drawable.avatar_user_s)
                .placeholder(R.drawable.avatar_user_s)
                .url(CommonUtil.getAbsolutePath(avatar))
                .transformation(new CropCircleTransformation(this))
                .imageView(ivAvatar)
                .build());
    }

    @Override
    public void editSexSuccess(int u_gender) {
        userInfo.setU_gender(u_gender);
        if (u_gender == 0) {
            tvSex.setText(getResources().getString(R.string.sex_female));
        } else {
            tvSex.setText(getResources().getString(R.string.sex_male));
        }
    }

    @Override
    public void requestSuccess(UserInfoEntity entity) {
        if(entity!=null){
            userInfo=entity;
            setValue(userInfo);
        }
    }

    @Override
    public void requestTypeBack(int type, ArrayList<FilterEntity> list) {
        switch (type) {
            case TYPE_DOMAIN:
                domainData.clear();
                domainData.addAll(list);
                break;
            case TYPE_STAGE:
                stageData.clear();
                stageData.addAll(list);
                break;
        }
    }

    @Override
    public void editTypeSuccess(EditTypeRequest entity) {
        if(entity.getType_key().equals(ConstantUtil.TYPE_KEY_DOMAIN)){
            //更新本地用户数据
            List<FilterEntity> domain_list=new ArrayList<>();
            for(FilterEntity filterEntity:domainData){
                if(filterEntity.isSelected()){
                    domain_list.add(filterEntity);
                }
            }
            userInfo.setDomain_list(domain_list);

        }else if(entity.getType_key().equals(ConstantUtil.TYPE_KEY_STAGE)){
            //更新本地用户数据
            List<FilterEntity> stage_list=new ArrayList<>();
            for(FilterEntity filterEntity:stageData){
                if(filterEntity.isSelected()){
                    stage_list.add(filterEntity);
                }
            }
            userInfo.setStage_list(stage_list);
        }
        mPresenter.saveUsrInfo(userInfo);
        setInvestorInfo(userInfo);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (userInfo != null) {
            mPresenter.saveUsrInfo(userInfo);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isNeedGotoMain) {
                gotoActivity(MainActivity.class);
            } else {
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 显示行业的dialog
     */
    private void showDomainDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.domain_dialog, null);
        final DomainHolder holder = new DomainHolder(view);
        holder.tvDialogTitle.setText(getString(R.string.concern_domain));
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
                //构建请求数据
                EditTypeRequest request=new EditTypeRequest();
                request.setType_key(ConstantUtil.TYPE_KEY_DOMAIN);
                ArrayList<Long> type_ids=new ArrayList<Long>();
                for(FilterEntity entity:domainData){
                    if(entity.isSelected()){
                        type_ids.add(entity.getType_id());
                    }
                }
                request.setType_ids(type_ids);
                mPresenter.editType(request);
            }
        });
        domainAdapter = new TagAdapter<FilterEntity>(domainData) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                View view = LayoutInflater.from(UserInfoActivity.this).inflate(R.layout.item_filter, holder.flowLayout, false);
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
                    if(domainData.get(position).isSelected()){
                        domainData.get(position).setSelected(false);
                    }else{
                        int num=0;
                        for(FilterEntity entity:domainData){
                            if(entity.isSelected()){
                                num++;
                            }
                        }
                        if(num>=MAX_DOMAIN_NUM){
                            showMessage("最多只能选择"+MAX_DOMAIN_NUM+"个行业");
                        }else{
                            domainData.get(position).setSelected(true);
                        }
                    }
                    domainAdapter.notifyDataChanged();
                }
                return true;
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
        domainDialog.show();
    }

    private void setDomainStatus(){
        if(userInfo.getDomain_list()!=null&&!userInfo.getDomain_list().isEmpty()){

            for(FilterEntity f2:userInfo.getDomain_list()){
                for(FilterEntity f1:domainData){
                    if(f2.getType_id()==f1.getType_id()){
                        f1.setSelected(true);
                        break;
                    }
                }
            }
        }
    }

    private void dismissDomainDialog() {
        if (domainDialog != null && domainDialog.isShowing()) {
            domainDialog.dismiss();
        }
    }

    static class DomainHolder {
        @BindView(R.id.tvDialogTitle)
        TextView tvDialogTitle;
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

    /**
     * 显示行业的dialog
     */
    private void showStageDialog() {
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
                //构建请求数据
                EditTypeRequest request=new EditTypeRequest();
                request.setType_key(ConstantUtil.TYPE_KEY_STAGE);
                ArrayList<Long> type_ids=new ArrayList<Long>();
                for(FilterEntity entity:stageData){
                    if(entity.isSelected()){
                        type_ids.add(entity.getType_id());
                    }
                }
                request.setType_ids(type_ids);
                mPresenter.editType(request);
            }
        });
        stageAdapter = new TagAdapter<FilterEntity>(stageData) {
            @Override
            public View getView(FlowLayout parent, int position, FilterEntity o) {
                View view = LayoutInflater.from(UserInfoActivity.this).inflate(R.layout.item_filter, holder.flowLayout, false);
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
                    if(stageData.get(position).isSelected()){
                        stageData.get(position).setSelected(false);
                    }else{
                        int num=0;
                        for(FilterEntity entity:stageData){
                            if(entity.isSelected()){
                                num++;
                            }
                        }
                        if(num>=MAX_STAGE_NUM){
                            showMessage("最多只能选择"+MAX_STAGE_NUM+"个轮次");
                        }else{
                            stageData.get(position).setSelected(true);
                        }
                    }
                    stageAdapter.notifyDataChanged();
                }
                return true;
            }
        });

        setStageStatus();

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

    private void setStageStatus(){
        if(userInfo.getStage_list()!=null&&!userInfo.getStage_list().isEmpty()){

            for(FilterEntity f2:userInfo.getStage_list()){
                for(FilterEntity f1:stageData){
                    if(f2.getType_id()==f1.getType_id()){
                        f1.setSelected(true);
                        break;
                    }
                }
            }
        }
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
