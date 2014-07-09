package sll.wg.tetris.android;


import sll.wg.tetris.Tetris;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	private Tetris tetris ;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		tetris = new Tetris() ;
		initialize(tetris, config);
		this.addContentView(LayoutInflater.from(this).inflate(R.layout.home, null), createLayoutParams());
	}
	
	public void choosePic(View view) {
		Intent intent = new Intent(this,PicSelectActivity.class) ;
		startActivityForResult(intent, 101);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data!=null&&requestCode==101&&resultCode==102) {
			String path = data.getStringExtra("path") ;
			tetris.bgStage.resetPic(path);
		}
	}
}
