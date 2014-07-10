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
	
	public static void setScore(long score) {
		FileHandle file = Gdx.files.external("score.txt") ;
		if (!file.exists()) {
			file.file() ;
		}
		file.writeString(score+"", false) ;
	}
	
	public static String getPic() {
		FileHandle file = Gdx.files.external("pic.txt") ;
		if (!file.exists()) {
			return "1.jpg" ;
		} else {
			String text = file.readString();
			return text ;
		}
	}
	
	public static void setPic(String txt) {
		FileHandle file = Gdx.files.external("pic.txt") ;
		if (!file.exists()) {
			file.file() ;
		}
		file.writeString(txt, false) ;
	}
}
