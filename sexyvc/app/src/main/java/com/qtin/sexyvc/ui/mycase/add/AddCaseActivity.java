package com.qtin.sexyvc.ui.mycase.add;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.mycase.add.di.AddCaseModule;
import com.qtin.sexyvc.ui.mycase.add.di.DaggerAddCaseComponent;
import com.qtin.sexyvc.ui.user.modify.ModifyActivity;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.zhy.autolayout.utils.AutoUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class AddCaseActivity extends MyBaseActivity<AddCasePresent> implements AddCaseContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvName)
    TextView tvName;

    private Dialog selectPhotoDialog;
    // 调用系统相册或者相机
    private final int CAMERA_REQUEST_CODE = 0x002;
    private final int ALBUM_REQUEST_CODE = 0x004;
    private final int CROP_REQUEST_CODE = 0x006;
    // 裁减过后的照片地址
    private String cropedPhoto;
    // 拍照地址
    private String path;

    private String case_name;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerAddCaseComponent.builder().appComponent(appComponent).addCaseModule(new AddCaseModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.add_case_activity;
    }

    @Override
    protected void initData() {
        tvRight.setVisibility(View.VISIBLE);
        tvTitle.setText(getString(R.string.title_add_case));
        tvRight.setText(getString(R.string.commit));
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

    @OnClick({R.id.ivLeft, R.id.tvRight, R.id.logoContainer, R.id.nameContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                if(StringUtil.isBlank(case_name)){
                    showMessage("项目名称不能为空");
                    return;
                }
                if(cropedPhoto==null){
                    mPresenter.createCase(case_name,"");
                }else{
                    mPresenter.getQiNiuToken(cropedPhoto);
                }
                break;
            case R.id.logoContainer:
                showPhotoDialog(true);
                break;
            case R.id.nameContainer:
                Bundle nameBundle = new Bundle();
                nameBundle.putString(ModifyActivity.MODIFY_INTENT_VALUE1, StringUtil.formatString(case_name));
                nameBundle.putInt(ModifyActivity.MODIFY_INTENT, ModifyActivity.MODIFY_PROJECT_NAME);
                gotoActivityForResult(ModifyActivity.class, nameBundle, ModifyActivity.MODIFY_PROJECT_NAME);
                break;
        }
    }

    @Override
    public void onCreateSuccess(CaseBean caseBean) {
        Intent intent=new Intent();
        intent.putExtra(ConstantUtil.INTENT_PARCELABLE,caseBean);
        setResult(0,intent);
        finish();
    }

    @Override
    public void uploadPhotoSuccess(String imageUrl) {
        mPresenter.createCase(case_name,imageUrl);
    }

    @Override
    public void showProgress(String msg) {
        showDialog(msg);
    }

    @Override
    public void hideProgress() {
        dialogDismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ModifyActivity.MODIFY_PROJECT_NAME:
                if (data != null) {
                    case_name= data.getExtras().getString(ModifyActivity.MODIFY_INTENT_VALUE1);
                    tvName.setText(StringUtil.formatString(case_name));
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
                    //mPresenter.getQiNiuToken(cropedPhoto);
                    Bitmap bitmap= BitmapFactory.decodeFile(cropedPhoto);
                    ivLogo.setImageBitmap(bitmap);
                }

                break;
        }
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
