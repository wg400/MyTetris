package sll.wg.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileUtil {
	public static char[][] getCharArray() {
		FileHandle file = Gdx.files.internal("data/poetry.txt") ;
		String text = file.readString();
		char[][] charArr = new char[20][10] ;
		String[] temp = text.split("\n") ;
		for (int i = 0; i < temp.length; i++) {
			charArr[i] = temp[i].toCharArray() ;
		}
		return charArr ;
	}
}
