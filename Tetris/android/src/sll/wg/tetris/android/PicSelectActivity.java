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
import com.wg.mylib.utils.StringUtil;
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
		if (!StringUtil.isEmpty(FileUtil.getPic())&&!FileUtil.getPic().equals("tetris.jpg")) {
			String[] temp = FileUtil.getPic().split("\\.") ;
			adapter.selectIndex = Integer.parseInt(temp[0])-1 ;
		}
		listview = (HorizontalListView) findViewById(R.id.listview) ;
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int position,
					long id) {
				if (adapter.selectIndex != position) {
					adapter.selectIndex = position ;
					adapter.notifyDataSetChanged() ;
					FileUtil.setPic(adapter.getItem().StrPath) ;
					AndroidLauncher.finish() ;
					AndroidLauncher = null ;
					Intent intent = new Intent(PicSelectActivity.this, AndroidLauncher.class) ;
					startActivity(intent) ;
				}
				onBackPressed();
			}
		});
	}
	
	private List<ImageEntity> initImages() {
		List<ImageEntity> images = new ArrayList<ImageEntity>() ;
		ImageEntity image = null ;
		
		image = new ImageEntity("1.jpg","ͼƬ1") ;
		images.add(image) ;
		
		image = new ImageEntity("2.jpg","ͼƬ2") ;
		images.add(image) ;
		
		return images ;
	}
	
//	public void doSelect(View view) {
//		if (adapter.getItem() != null) {
//			FileUtil.setPic(adapter.getItem().StrPath) ;
//		}
//		AndroidLauncher.finish() ;
//		AndroidLauncher = null ;
//		Intent intent = new Intent(this, AndroidLauncher.class) ;
//		startActivity(intent) ;
//		onBackPressed();
//	}
	
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
			TipsUtil.show(this, "δ��⵽�洢��!") ;
		}
	}
	
	public void doBack(View view) {
		onBackPressed();
	}
	
	/**
	 * �ж�SD���Ƿ����
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
				FileUtil.setPic(filePath) ;
				AndroidLauncher.finish() ;
				AndroidLauncher = null ;
				Intent intent = new Intent(this, AndroidLauncher.class) ;
				startActivity(intent) ;
				onBackPressed();
			}
		}
	}
}
