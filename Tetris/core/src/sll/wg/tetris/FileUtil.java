package sll.wg.tetris;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileUtil {
	public static char[][] getCharArray() {
		FileHandle file = Gdx.files.internal("data/poetry.txt") ;
		String text = file.readString();
		System.out.println(text);
		char[][] charArr = new char[20][10] ;
		String[] temp = text.split("\n") ;
		for (int i = 0; i < temp.length; i++) {
			charArr[i] = temp[i].toCharArray() ;
		}
		return charArr ;
	}
	
	public static String getScore() {
		FileHandle file = Gdx.files.external("score.txt") ;
		if (!file.exists()) {
			return "0" ;
		} else {
			String text = file.readString();
			return text ;
		}
	}
	
	public static void newFile() {
		File file = new File("/mnt/sdcard/external_sd/score.txt") ;
		try {
			if (!file.exists()) {
				file.createNewFile() ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setScore(long score) {
		FileHandle file = Gdx.files.external("score.txt") ;
		if (!file.exists()) {
			file.file() ;
		}
		file.writeString(score+"", false) ;
	}
}
