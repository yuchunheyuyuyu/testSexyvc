package com.qtin.sexyvc.mvp.test.upload;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.di.component.DaggerUploadComponent;
import com.qtin.sexyvc.di.module.UploadModule;
import com.qtin.sexyvc.mvp.contract.UploadContract;
import com.qtin.sexyvc.mvp.presenter.UploadPresenter;
import java.io.File;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/3/1.
 */

public class UploadActivity extends MyBaseActivity<UploadPresenter> implements UploadContract.View {

    @BindView(R.id.ivPicture)
    ImageView ivPicture;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    //调用系统相册或者相机
    private final int ALBUM_REQUEST_CODE = 0x004;
    private final int CROP_REQUEST_CODE = 0x006;
    //裁减过后的照片地址
    private String cropedPhoto;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUploadComponent.builder()
                .appComponent(appComponent)
                .uploadModule(new UploadModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.upload_activity;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.btnSelectPicture, R.id.btnStartUpload, R.id.btnPauseUpload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSelectPicture:
                goToAlbum();
                break;
            case R.id.btnStartUpload:
                if(cropedPhoto!=null){
                    mPresenter.getToken(cropedPhoto);
                }
                break;
            case R.id.btnPauseUpload:


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ALBUM_REQUEST_CODE:
                try{
                    Uri selectedImage = data.getData();
                    String[] filePathColumns={MediaStore.Images.Media.DATA};
                    Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String picturePath= c.getString(columnIndex);
                    c.close();
                    Uri uri = Uri.fromFile(new File(picturePath));
                    cropPhoto(uri);
                    //获取图片并显示
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case CROP_REQUEST_CODE:
                ivPicture.setImageURI(Uri.parse(cropedPhoto));
                break;
        }
    }

    public void goToAlbum(){
        Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, ALBUM_REQUEST_CODE);
    }

    //裁减
    private void cropPhoto(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//可裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        cropedPhoto= DataHelper.getCacheFile(this)+ File.separator+System.currentTimeMillis()+".png";
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(cropedPhoto)));
        intent.putExtra("return-data", false);//若为false则表示不返回数据
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    @Override
    public void showProgress(int percent) {
        progressBar.setProgress(percent);
    }
}
