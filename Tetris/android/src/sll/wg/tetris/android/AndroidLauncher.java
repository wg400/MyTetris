package sll.wg.tetris.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import sll.wg.tetris.FileUtil;
import sll.wg.tetris.Tetris;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		FileUtil.newFile() ;
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Tetris(), config);
	}
}
