package com.qtin.sexyvc.ui.user.photo;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.UploadPhotoUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.user.photo.di.DaggerPhotoComponent;
import com.qtin.sexyvc.ui.user.photo.di.PhotoModule;
import com.qtin.sexyvc.utils.CommonUtil;
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
import uk.co.senab.photoview.PhotoView;

/**
 * Created by ls on 17/4/26.
 */
public class PhotoActivity extends MyBaseActivity<PhotoPresent> implements PhotoContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.photoView)
    PhotoView photoView;

    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private String url;

    private Dialog selectPhotoDialog;
    // 调用系统相册或者相机
    private final int CAMERA_REQUEST_CODE = 0x002;
    private final int ALBUM_REQUEST_CODE = 0x004;
    // 拍照地址
    private String path;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerPhotoComponent.builder().appComponent(appComponent).photoModule(new PhotoModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.photo_activity;
    }

    @Override
    protected void initData() {
        url=getIntent().getExtras().getString(ConstantUtil.INTENT_URL);
        tvTitle.setText(getResources().getString(R.string.title_vertify_position));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.upload));

        mImageLoader = customApplication.getAppComponent().imageLoader();
        //加载图片
        mImageLoader.loadImage(customApplication, GlideImageConfig
                .builder()
                .url(CommonUtil.getAbsolutePath(url))
                .imageView(photoView)
                .build());
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
                showPhotoDialog();
                break;
        }
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
                    File file=new File(picturePath);
                    if(file!=null&&file.exists()){
                        Bitmap bitmapOriginal = UploadPhotoUtil.getUpLoadImage(
                                picturePath, BaseApplication.screenSize.x,
                                BaseApplication.screenSize.y, true);
                        String newPath = picturePath + "r.png";
                        OutputStream stream = new FileOutputStream(newPath);
                        bitmapOriginal.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                        DeviceUtils.recycle(bitmapOriginal);
                        mPresenter.getQiNiuToken(newPath);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CAMERA_REQUEST_CODE:
                try {
                    if (path != null) {
                        String newPath = path;
                        int angle = DeviceUtils.readPictureDegree(path);

                        newPath = path + "r.png";
                        Bitmap bitmapOriginal = UploadPhotoUtil.getUpLoadImage(
                                path, BaseApplication.screenSize.x,
                                BaseApplication.screenSize.y, true);

                        OutputStream stream = new FileOutputStream(newPath);
                        if (angle != 0) {
                            Bitmap bm = DeviceUtils.rotaingImageView(angle, bitmapOriginal);
                            bm.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                            DeviceUtils.recycle(bitmapOriginal);
                            DeviceUtils.recycle(bm);
                        } else {
                            bitmapOriginal.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                            DeviceUtils.recycle(bitmapOriginal);
                        }

                        File file = new File(newPath);
                        if (file != null && file.exists()) {
                            mPresenter.getQiNiuToken(newPath);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    /**
     * 相册弹出框
     */
    private void showPhotoDialog() {

        View view = View.inflate(this, R.layout.select_photo_dialog, null);
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


    @Override
    public void uploadSuccess(String url) {
        Intent intent=new Intent();
        intent.putExtra(ConstantUtil.INTENT_URL,url);
        mImageLoader.loadImage(customApplication, GlideImageConfig
                .builder()
                .url(CommonUtil.getAbsolutePath(url))
                .imageView(photoView)
                .build());
        setResult(0,intent);
        showComfirmDialog("已完成提交", "我们的工作人员将在 3 个工作日内审核", "好", new ComfirmListerner() {
            @Override
            public void onComfirm() {
                dismissComfirmDialog();
                finish();

            }
        });
    }
}
