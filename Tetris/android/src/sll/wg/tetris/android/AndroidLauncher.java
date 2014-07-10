package sll.wg.tetris.android;


import sll.wg.tetris.Tetris;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	private Tetris tetris ;
	private View view ;
	private ToggleButton sound ;
	private ToggleButton start ;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		tetris = new Tetris() ;
		initialize(tetris, config);
		view = LayoutInflater.from(this).inflate(R.layout.home, null) ;
		this.addContentView(view, createLayoutParams());
		sound = (ToggleButton) view.findViewById(R.id.sound) ;
		start = (ToggleButton) view.findViewById(R.id.start) ;
		
		sound.setOnCheckedChangeListener(listener) ;
		start.setOnCheckedChangeListener(listener) ;
	}
	
	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton btn, boolean check) {
			switch (btn.getId()) {
			case R.id.sound:
				tetris.bgStage.sound() ;
				break;
			case R.id.start:
				tetris.bgStage.paly() ;
				break;

			default:
				break;
			}
		}
	};
	
	public void choosePic(View view) {
		PicSelectActivity.AndroidLauncher = this ;
		Intent intent = new Intent(this,PicSelectActivity.class) ;
		startActivity(intent);
	}
}
