package sll.wg.tetris.android;

import java.util.ArrayList;
import java.util.List;

import sll.wg.tetris.FileUtil;

import com.wg.mylib.views.lisview.HorizontalListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class PicSelectActivity extends Activity {
	public static AndroidLauncher AndroidLauncher ;
	private HorizontalListView listview ;
	private PicSelectAdapter adapter ;
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
		
		image = new ImageEntity("1.jpg","ͼƬ1") ;
		images.add(image) ;
		
		image = new ImageEntity("2.jpg","ͼƬ2") ;
		images.add(image) ;
		
		image = new ImageEntity("3.jpg","ͼƬ3") ;
		images.add(image) ;
		
		image = new ImageEntity("4.jpg","ͼƬ4") ;
		images.add(image) ;
		
		image = new ImageEntity("5.jpg","ͼƬ5") ;
		images.add(image) ;
		
		image = new ImageEntity("6.jpg","ͼƬ6") ;
		images.add(image) ;
		
		image = new ImageEntity("7.jpg","ͼƬ7") ;
		images.add(image) ;
		
		return images ;
	}
	
	public void doSelect(View view) {
		FileUtil.setPic(adapter.getItem().StrPath) ;
		AndroidLauncher.finish() ;
		AndroidLauncher = null ;
		Intent intent = new Intent(this, AndroidLauncher.class) ;
		startActivity(intent) ;
		onBackPressed();
	}
	
	public void doBack(View view) {
		onBackPressed();
	}
}
