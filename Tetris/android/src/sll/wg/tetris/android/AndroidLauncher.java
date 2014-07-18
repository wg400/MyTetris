package sll.wg.tetris.android;


import java.util.Timer;
import java.util.TimerTask;

import sll.wg.tetris.Tetris;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	private Tetris tetris ;
	private View view ;
	public ImageView startView ;
	public LinearLayout endView ;
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
		startView = (ImageView) view.findViewById(R.id.startView) ;
		endView = (LinearLayout) view.findViewById(R.id.endView) ;
		
		sound.setOnCheckedChangeListener(listener) ;
		start.setOnCheckedChangeListener(listener) ;
		
		Handler handler = new Handler() ;
		handler.postDelayed(new Runnable() {
			@SuppressLint("ResourceAsColor")
			@Override
			public void run() {
				startView.setVisibility(View.INVISIBLE) ;
				endView.setVisibility(View.VISIBLE) ;
				view.setBackgroundColor(android.R.color.transparent) ;
			}
		}, 1500) ;
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
