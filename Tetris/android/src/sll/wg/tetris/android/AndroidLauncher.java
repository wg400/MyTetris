package sll.wg.tetris.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import sll.wg.tetris.BgStage;
import sll.wg.tetris.Tetris;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.wg.mylib.utils.ImageUtil;
import com.wg.mylib.utils.LogUtil;
import com.wg.mylib.utils.TipsUtil;

public class AndroidLauncher extends AndroidApplication {
	protected PopupWindow window;
	protected String savePath = "file:///sdcard/myself.jpg" ;
	protected String capturePath = "file:///sdcard/myself_temp.jpg" ;
	protected File uploadFile ;
	
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	protected int zoomW = 1 ;
	protected int zoomH = 2 ;
	public Tetris tetris ;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		FileUtil.newFile() ;
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		tetris = new Tetris() ;
		initialize(tetris, config);
//		this.addContentView(LayoutInflater.from(this).inflate(R.layout.activity_home, null), createLayoutParams ()) ;
	}
	
	public void choosePic(View view) {
		if(BgStage.isPause ==0) {
			BgStage.isPause = 1 ;
		}
		showWindow() ;
	}
	
	protected void showWindow() {
		if (isSDExist()) {
			LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View photoView = lay.inflate(R.layout.exit_dialog_from_settings, null);
			window = new PopupWindow(photoView, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			window.setAnimationStyle(R.style.AnimBottom);
			window.setFocusable(false);
			window.setTouchable(true);
			window.setOutsideTouchable(false);
			window.showAtLocation(photoView, Gravity.CENTER, 0, 0);
			window.update();
		} else {
			TipsUtil.show(this, "未检测到存储卡!") ;
		}
	}

	/**
	 * 判断SD卡是否可用
	 * 
	 * @return
	 */
	private boolean isSDExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 拍照
	 * @param v
	 */
	public void capturePhoto(View v) {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				ImageUtil.getPicFromCaptureOrLocal(this, Uri.parse(capturePath), true,
						FLAG_CHOOSE_PHONE) ;
				window.dismiss();
			} catch (ActivityNotFoundException e) {
			}
		}
	}

	/**
	 * 从本地获取图片
	 * 
	 * @param v
	 */
	public void pickPhotoFromLocal(View v) {
		ImageUtil.getPicFromCaptureOrLocal(this, null, false, FLAG_CHOOSE_IMG);
		window.dismiss();
	}

	/**
	 * 取消
	 * 
	 * @param v
	 */
	public void cancel(View v) {
		window.dismiss();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
			if (data != null) {
				Uri uri = data.getData();
				ImageUtil.startPhotoZoom(this, uri,Uri.parse(savePath), zoomW, zoomH,
						FLAG_MODIFY_FINISH);
			}
		} else if (requestCode == FLAG_CHOOSE_PHONE && resultCode == RESULT_OK) {
				ImageUtil.startPhotoZoom(this, Uri.parse(capturePath),Uri.parse(savePath)
						,zoomW, zoomH, FLAG_MODIFY_FINISH);
				
		} else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
			LogUtil.info("FLAG_MODIFY_FINISH") ;
			if (data != null) {
				LogUtil.info("decodeUriAsBitmap") ;
				Bitmap bitmap = decodeUriAsBitmap(Uri.parse(savePath)) ;
				LogUtil.info("File") ;
				uploadFile = new File(File.separator + "mnt" + File.separator + "sdcard" + File.separator + "tetris.jpg") ;
				LogUtil.info("exists") ;
				if (!uploadFile.exists()) {
					try {
						uploadFile.createNewFile() ;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				ImageUtil.saveBitmap(uploadFile, bitmap) ;
				tetris.bgStage.resetPic() ;
			}
		}
	}
	
	private Bitmap decodeUriAsBitmap(Uri uri){
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}
