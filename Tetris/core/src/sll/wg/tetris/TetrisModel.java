package sll.wg.tetris;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TetrisModel implements Cloneable {
	public float sx ;
	public float sy ;
	public float size ;
	public Image image ;
	public boolean visible ;
	public boolean isLock ;
	public TetrisModel() {
	}
	
	public void setData(float x,float y,float size) {
		this.sx = x ;
		this.sy = y ;
		this.size = size ;
		image.setPosition(x, y);
		image.setSize(size, size);
		image.setVisible(visible);
	}
	
	public void setData(Texture myPic,int x,int y,int w,int h) {
		System.out.println("myPic:"+myPic);
		this.image.setDrawable(new TextureRegionDrawable(new TextureRegion(myPic, x, y, w, h))) ;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible ;
		image.setVisible(visible);
	}
	
	protected TetrisModel clone(Texture myPic,int x,int y,int w,int h) throws CloneNotSupportedException {
		TetrisModel model = null ;
		try{
			model = (TetrisModel) super.clone() ;
			model.image = new Image(new TextureRegion(myPic, x, y, w, h)) ;
		}catch(CloneNotSupportedException e){ 
			e.printStackTrace() ;
		}
		return model ;
	}
	
	protected TetrisModel clone(Texture myPic) throws CloneNotSupportedException {
		TetrisModel model = null ;
		try{
			model = (TetrisModel) super.clone() ;
			model.image = new Image(new TextureRegion(myPic)) ;
		}catch(CloneNotSupportedException e){ 
			e.printStackTrace() ;
		}
		return model ;
	}
	
	protected TetrisModel clone() throws CloneNotSupportedException {
		TetrisModel model = null ;
		try{
			model = (TetrisModel) super.clone() ;
			model.image = new Image() ;
		}catch(CloneNotSupportedException e){ 
			e.printStackTrace() ;
		}
		return model ;
	}
	
}
