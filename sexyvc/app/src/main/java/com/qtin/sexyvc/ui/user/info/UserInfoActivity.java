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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
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
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.user.info.di.DaggerUserInfoComponent;
import com.qtin.sexyvc.ui.user.info.di.UserInfoModule;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.utils.CommonUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
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
    @BindView(R.id.ivIdentity)
    ImageView ivIdentity;

    public static final String INTENT_USER="user_info";
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

    private boolean isUpdateAvatar;//是上传头像还是上传验证

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
        userInfo=getIntent().getExtras().getParcelable(INTENT_USER);

        tvTitle.setText(getResources().getString(R.string.title_user_info));

        setValue(userInfo);
    }

    private void setValue(UserInfoEntity entity){

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
        tvName.setText(StringUtil.isBlank(entity.getU_nickname())?getResources().getString(R.string.nick_defalut):entity.getU_nickname());
        //性别
        if(entity.getU_gender()==1){
            tvSex.setText(getResources().getString(R.string.sex_male));
        }else{
            tvSex.setText(getResources().getString(R.string.sex_female));
        }
        //自我介绍
        tvMyDescription.setText(StringUtil.isBlank(entity.getU_signature())?getResources()
                .getString(R.string.input_defalut):entity.getU_signature());

        //手机号码
        String mobile=entity.getU_phone();
        if(StringUtil.isBlank(mobile)){
            mobile=getResources().getString(R.string.input_defalut);
        }else{
            mobile=mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        tvMobile.setText(mobile);
        //邮箱
        tvEmail.setText(StringUtil.isBlank(entity.getU_email())?
                getResources().getString(R.string.input_defalut):entity.getU_email());

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

    @OnClick({R.id.ivLeft, R.id.avatarContainer, R.id.nickContainer, R.id.sexContainer, R.id.descriptionContainer, R.id.mobileContainer, R.id.emailContainer, R.id.positionContainer, R.id.identifyContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.avatarContainer:
                isUpdateAvatar=true;
                showPhotoDialog(true);
                break;
            case R.id.nickContainer:
                Bundle nick=new Bundle();
                nick.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_NICK);
                nick.putString(ModifyActivity.MODIFY_INTENT_VALUE1,userInfo.getU_nickname());
                gotoActivityForResult(ModifyActivity.class,nick,ModifyActivity.MODIFY_NICK);
                break;
            case R.id.sexContainer:
                chooseSexDialog();
                break;
            case R.id.descriptionContainer:
                Bundle introduce=new Bundle();
                introduce.putString(ModifyActivity.MODIFY_INTENT_VALUE1,userInfo.getU_signature());
                introduce.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_INTRODUCE);
                gotoActivityForResult(ModifyActivity.class,introduce,ModifyActivity.MODIFY_INTRODUCE);
               break;
            case R.id.mobileContainer:
                Bundle mobile=new Bundle();
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE1,userInfo.getU_phone());
                mobile.putString(ModifyActivity.MODIFY_INTENT_VALUE2,userInfo.getU_backup_phone());
                mobile.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_PHONE);
                gotoActivityForResult(ModifyActivity.class,mobile,ModifyActivity.MODIFY_PHONE);
                break;
            case R.id.emailContainer:
                Bundle email=new Bundle();
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE1,userInfo.getU_email());
                email.putString(ModifyActivity.MODIFY_INTENT_VALUE2,userInfo.getU_backup_email());
                email.putInt(ModifyActivity.MODIFY_INTENT,ModifyActivity.MODIFY_EMAIL);
                gotoActivityForResult(ModifyActivity.class,email,ModifyActivity.MODIFY_EMAIL);
                break;
            case R.id.positionContainer:
                break;
            case R.id.identifyContainer:
                isUpdateAvatar=false;
                showPhotoDialog(false);
                break;
        }
    }

    /**
     * 性别
     */
    private void chooseSexDialog() {

        View view = View.inflate(this, R.layout.choose_sex_dialog, null);
        AutoUtils.autoSize(view);

        View btn_report =view.findViewById(R.id.btn_report);
        View btn_error =view.findViewById(R.id.btn_error);
        View cancleSelected =view.findViewById(R.id.cancleSelected);
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
        if(isAvatar){
            view.findViewById(R.id.tvHint).setVisibility(View.GONE);
            view.findViewById(R.id.lineHint).setVisibility(View.GONE);
        }

        View btn_report =view.findViewById(R.id.btn_report);
        View btn_error =view.findViewById(R.id.btn_error);
        View cancleSelected =view.findViewById(R.id.cancleSelected);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ModifyActivity.MODIFY_NICK:
                if(data!=null){
                    String nick=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                    userInfo.setU_nickname(nick);
                    tvName.setText(nick);
                }
                break;
            case ModifyActivity.MODIFY_INTRODUCE:
                if(data!=null){
                    String sign=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                    userInfo.setU_signature(sign);
                    tvMyDescription.setText(sign);
                }
                break;
            case ModifyActivity.MODIFY_EMAIL:
                if(data!=null){
                    String u_email=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                    String u_backup_email=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE2));
                    userInfo.setU_email(u_email);
                    userInfo.setU_backup_email(u_backup_email);
                    tvEmail.setText(u_email);
                }
                break;
            case ModifyActivity.MODIFY_PHONE:
                if(data!=null){
                    String u_phone=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1));
                    String u_backup_phone=StringUtil.formatString(data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE2));
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
                    Uri uri = Uri.fromFile(new File(picturePath));
                    cropPhoto(uri);
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
                if(data!=null){
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
        if(isUpdateAvatar){
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
    }

    @Override
    public void editSexSuccess(int u_gender) {
        if(u_gender==0){
            tvSex.setText(getResources().getString(R.string.sex_female));
        }else{
            tvSex.setText(getResources().getString(R.string.sex_male));
        }
    }
}
