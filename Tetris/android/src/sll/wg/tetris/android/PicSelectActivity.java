package sll.wg.tetris.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sll.wg.tetris.FileUtil;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.wg.mylib.utils.ImageUtil;
import com.wg.mylib.utils.LogUtil;
import com.wg.mylib.utils.TipsUtil;
import com.wg.mylib.views.lisview.HorizontalListView;

public class PicSelectActivity extends Activity {
	public static AndroidLauncher AndroidLauncher ;
	private HorizontalListView listview ;
	private PicSelectAdapter adapter ;
	
	protected String savePath = "file:///sdcard/tetris.jpg" ;
	protected String capturePath = "file:///sdcard/tetris_capture.jpg" ;
	protected File uploadFile ;
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	protected int zoomW = 1 ;
	protected int zoomH = 2 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piclist);
		adapter = new PicSelectAdapter(this) ;
		adapter.images = initImages() ;
		listview = (HorizontalListView) findViewById(R.id.listview) ;
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int position,
					long id) {
				adapter.selectIndex = position ;
				adapter.notifyDataSetChanged() ;
			}
		});
	}
	
	private List<ImageEntity> initImages() {
		List<ImageEntity> images = new ArrayList<ImageEntity>() ;
		ImageEntity image = null ;
		
		image = new ImageEntity("1.jpg","图片1") ;
		images.add(image) ;
		
		image = new ImageEntity("2.jpg","图片2") ;
		images.add(image) ;
		
		return images ;
	}
	
	public void doSelect(View view) {
		if (adapter.getItem() != null) {
			FileUtil.setPic(adapter.getItem().StrPath) ;
		}
		AndroidLauncher.finish() ;
		AndroidLauncher = null ;
		Intent intent = new Intent(this, AndroidLauncher.class) ;
		startActivity(intent) ;
		onBackPressed();
	}
	
	public void album(View view) {
		ImageUtil.getPicFromCaptureOrLocal(this, null, false, FLAG_CHOOSE_IMG);
	}
	
	public void takePhoto(View view) {
		if (isSDExist()) {
			String status = Environment.getExternalStorageState();
			if (status.equals(Environment.MEDIA_MOUNTED)) {
				try {
					ImageUtil.getPicFromCaptureOrLocal(this, Uri.parse(capturePath), true,
							FLAG_CHOOSE_PHONE) ;
				} catch (ActivityNotFoundException e) {
				}
			}
		} else {
			TipsUtil.show(this, "未检测到存储卡!") ;
		}
	}
	
	public void doBack(View view) {
		onBackPressed();
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
			if (data != null) {
				String filePath = "tetris.jpg" ;
//				uploadFile = new File(File.separator + "mnt" + File.separator + "sdcard" + File.separator + filePath) ;
//				if (uploadFile.exists()) {
//					uploadFile.delete() ;
//				}
//				try {
//					uploadFile.createNewFile() ;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				
//				decodeUriAsFile(uploadFile,Uri.parse(savePath)) ;
//				Bitmap bitmap = decodeUriAsBitmap(Uri.parse(savePath)) ;
//				LogUtil.info("bitmap:"+bitmap) ;
//				ImageUtil.saveBitmap(uploadFile, bitmap) ;
				FileUtil.setPic(filePath) ;
				AndroidLauncher.finish() ;
				AndroidLauncher = null ;
				Intent intent = new Intent(this, AndroidLauncher.class) ;
				startActivity(intent) ;
				onBackPressed();
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
	
	private void decodeUriAsFile(File file,Uri uri){
		if (file.exists()) {
			file.delete() ;
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int bytesum = 0;
		int byteread = 0; 
		byte[] buffer = new byte[1024];
		int length;
		InputStream inStream;
		try {
			inStream = getContentResolver().openInputStream(uri);
			while ( (byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; //字节数 文件大小 
				fOut.write(buffer, 0, byteread);                
			}   
			fOut.flush();
			inStream.close(); 
			fOut.close() ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
