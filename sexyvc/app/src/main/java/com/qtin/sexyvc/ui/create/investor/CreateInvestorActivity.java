package com.qtin.sexyvc.ui.create.investor;

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
import com.qtin.sexyvc.ui.create.investor.di.CreateInvestorModule;
import com.qtin.sexyvc.ui.create.investor.di.DaggerCreateInvestorComponent;
import com.qtin.sexyvc.ui.follow.set.SetGroupActivity;
import com.qtin.sexyvc.ui.request.CreateInvestorRequest;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
public class CreateInvestorActivity extends MyBaseActivity<CreateInvestorPresent> implements CreateInvestorContract.View {

    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvFundName)
    TextView tvFundName;
    @BindView(R.id.tvInvestorTitle)
    TextView tvInvestorTitle;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvRemark)
    TextView tvRemark;
    @BindView(R.id.ivAnonymous)
    ImageView ivAnonymous;

    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private CreateInvestorRequest request=new CreateInvestorRequest();

    private Dialog selectPhotoDialog;
    // 调用系统相册或者相机
    private final int CAMERA_REQUEST_CODE = 0x002;
    private final int ALBUM_REQUEST_CODE = 0x004;
    private final int CROP_REQUEST_CODE = 0x006;
    // 裁减过后的照片地址
    private String cropedPhoto;
    // 拍照地址
    private String path;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCreateInvestorComponent.builder().appComponent(appComponent).createInvestorModule(new CreateInvestorModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.create_investor_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getResources().getString(R.string.title_create_investor));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.create_and_concern));
        mImageLoader = customApplication.getAppComponent().imageLoader();
        ivAnonymous.setSelected(true);
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

    @Override
    public void onCreateSuccess(long contact_id) {
        Bundle bundle = new Bundle();
        bundle.putLong(ConstantUtil.INTENT_ID, contact_id);
        bundle.putInt(ConstantUtil.INTENT_TYPE_SET_GROUP, ConstantUtil.TYPE_SET_GROUP_CONTACT);
        gotoActivity(SetGroupActivity.class, bundle);

        Observable.just(1)
                .delay(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    public void call(Integer integer) {
                        finish();
                    }
                });
    }

    @Override
    public void uploadAvatarSuccess(String avatar) {
        if(!StringUtil.isBlank(avatar)){
            request.setInvestor_avatar(avatar);
        }

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
    public void startRefresh(String msg) {
        showDialog(msg);
    }

    @Override
    public void endRefresh() {
        dialogDismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
            case ConstantUtil.TYPE_CREATE_INVESTOR_NAME:
                if(data!=null){
                    String name=data.getExtras().getString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE);
                    request.setInvestor_name(name);
                    tvName.setText(StringUtil.formatString(name));
                }
                break;
            case ConstantUtil.TYPE_CREATE_INVESTOR_FUND:
                if(data!=null){
                    String fund=data.getExtras().getString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE);
                    request.setFund_name(fund);
                    tvFundName.setText(StringUtil.formatString(fund));
                }
                break;
            case ConstantUtil.TYPE_CREATE_INVESTOR_TITLE:
                if(data!=null){
                    String title=data.getExtras().getString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE);
                    request.setTitle(title);
                    tvInvestorTitle.setText(StringUtil.formatString(title));
                }
                break;
            case ConstantUtil.TYPE_CREATE_INVESTOR_PHONE:
                if(data!=null){
                    String phone=data.getExtras().getString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE);
                    request.setPhone(phone);
                    tvPhone.setText(StringUtil.formatString(phone));
                }
                break;
            case ConstantUtil.TYPE_CREATE_INVESTOR_EMAIL:
                if(data!=null){
                    String email=data.getExtras().getString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE);
                    request.setEmail(email);
                    tvEmail.setText(StringUtil.formatString(email));
                }
                break;
            case ConstantUtil.TYPE_CREATE_INVESTOR_REMARK:
                if(data!=null){
                    String remark=data.getExtras().getString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE);
                    request.setRemark(remark);
                    tvRemark.setText(StringUtil.formatString(remark));
                }
                break;

        }
    }

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.avatarContainer, R.id.nameContainer,
              R.id.fundContainer, R.id.titleContainer, R.id.phoneContainer,
              R.id.emailContainer, R.id.remarkContainer,R.id.ivAnonymous})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAnonymous:
                if(ivAnonymous.isSelected()){
                    ivAnonymous.setSelected(false);
                }else{
                    ivAnonymous.setSelected(true);
                }
                break;
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                if(StringUtil.isBlank(request.getInvestor_name())){
                    showMessage("投资人姓名不能为空");
                    return;
                }

                if(StringUtil.isBlank(request.getFund_name())){
                    showMessage("所在机构不能为空");
                    return;
                }
                if(StringUtil.isBlank(request.getTitle())){
                    showMessage("职务不能为空");
                    return;
                }
                mPresenter.createInvestor(request);
                break;
            case R.id.avatarContainer:
                showPhotoDialog();
                break;
            case R.id.nameContainer:
                Bundle name=new Bundle();
                name.putInt(ConstantUtil.INTENT_CREATE_INVESTOR_TYPE,ConstantUtil.TYPE_CREATE_INVESTOR_NAME);
                name.putString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE, StringUtil.formatString(request.getInvestor_name()));
                gotoActivityForResult(CreateInvestorInfoActivity.class,name,ConstantUtil.TYPE_CREATE_INVESTOR_NAME);
                break;
            case R.id.fundContainer:
                Bundle fund=new Bundle();
                fund.putInt(ConstantUtil.INTENT_CREATE_INVESTOR_TYPE,ConstantUtil.TYPE_CREATE_INVESTOR_FUND);
                fund.putString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE, StringUtil.formatString(request.getFund_name()));
                gotoActivityForResult(CreateInvestorInfoActivity.class,fund,ConstantUtil.TYPE_CREATE_INVESTOR_FUND);
                break;
            case R.id.titleContainer:
                Bundle title=new Bundle();
                title.putInt(ConstantUtil.INTENT_CREATE_INVESTOR_TYPE,ConstantUtil.TYPE_CREATE_INVESTOR_TITLE);
                title.putString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE, StringUtil.formatString(request.getTitle()));
                gotoActivityForResult(CreateInvestorInfoActivity.class,title,ConstantUtil.TYPE_CREATE_INVESTOR_TITLE);
                break;
            case R.id.phoneContainer:
                Bundle phone=new Bundle();
                phone.putInt(ConstantUtil.INTENT_CREATE_INVESTOR_TYPE,ConstantUtil.TYPE_CREATE_INVESTOR_PHONE);
                phone.putString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE, StringUtil.formatString(request.getPhone()));
                gotoActivityForResult(CreateInvestorInfoActivity.class,phone,ConstantUtil.TYPE_CREATE_INVESTOR_PHONE);
                break;
            case R.id.emailContainer:
                Bundle email=new Bundle();
                email.putInt(ConstantUtil.INTENT_CREATE_INVESTOR_TYPE,ConstantUtil.TYPE_CREATE_INVESTOR_EMAIL);
                email.putString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE, StringUtil.formatString(request.getEmail()));
                gotoActivityForResult(CreateInvestorInfoActivity.class,email,ConstantUtil.TYPE_CREATE_INVESTOR_EMAIL);
                break;
            case R.id.remarkContainer:
                Bundle remark=new Bundle();
                remark.putInt(ConstantUtil.INTENT_CREATE_INVESTOR_TYPE,ConstantUtil.TYPE_CREATE_INVESTOR_REMARK);
                remark.putString(ConstantUtil.INTENT_CREATE_INVESTOR_OLD_VALUE, StringUtil.formatString(request.getRemark()));
                gotoActivityForResult(CreateInvestorInfoActivity.class,remark,ConstantUtil.TYPE_CREATE_INVESTOR_REMARK);
                break;
        }
    }
    /**
     * 相册弹出框
     */
    private void showPhotoDialog() {

        View view = View.inflate(this, R.layout.select_photo_dialog, null);
        AutoUtils.autoSize(view);
        view.findViewById(R.id.tvHint).setVisibility(View.GONE);
        view.findViewById(R.id.lineHint).setVisibility(View.GONE);

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
}
