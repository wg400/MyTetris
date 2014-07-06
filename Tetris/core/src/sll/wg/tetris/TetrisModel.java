package sll.wg.tetris;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TetrisModel {
	public float x ;
	public float y ;
	public Image image ;
	public boolean visible ;
	public boolean isLock ;
	
	public TetrisModel(float x,float y,float size) {
		TextureRegion region = new TextureRegion(new Texture("data/tetris1.png")) ;
		image = new Image(region) ;
		image.setPosition(x, y);
		image.setSize(size, size);
		image.setVisible(visible);
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible ;
		image.setVisible(visible);
	}
}
