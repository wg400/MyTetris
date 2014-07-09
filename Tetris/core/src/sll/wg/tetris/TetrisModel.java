package sll.wg.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TetrisModel  implements Cloneable {
	public float x ;
	public float y ;
//	public TextButton image ;
	public Image image ;
//	public TextButtonStyle style ;
	public boolean visible ;
	public boolean isLock ;
	
	public TetrisModel() {
//		TextureRegion region = new TextureRegion(new Texture("data/tetris2.png")) ;
//		BitmapFont font = new BitmapFont(Gdx.files.internal("data/sll.fnt"),
//	               Gdx.files.internal("data/sll.png"), false);
//		font.setScale(0.5f) ;
//		TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(region)) ;
//		style = new TextButtonStyle(d, d, d, font) ;
//		image = new TextButton("", style) ;
		image = new Image() ;
		image.setVisible(visible) ;
	}
	
	public void setData(float x,float y,float size) {
		this.x = x ;
		this.y = y ;
		image.setPosition(x, y);
		image.setSize(size, size);
		image.setVisible(visible);
	}
	
	public void setData(TextureRegion region) {
		this.image.setDrawable(new TextureRegionDrawable(region)) ;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible ;
		image.setVisible(visible);
	}
	
//	public void setText(String text) {
//		image.setText(text) ;
//	}
	
	protected TetrisModel clone(TextureRegion region) throws CloneNotSupportedException {
		TetrisModel model = null ;
		try{
			model = (TetrisModel) super.clone() ;
			model.image = new Image(region) ;
		}catch(CloneNotSupportedException e){ 
			e.printStackTrace() ;
		}
		return model ;
	}
	
}
