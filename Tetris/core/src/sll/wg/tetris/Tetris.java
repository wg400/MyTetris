package sll.wg.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tetris extends ApplicationAdapter {
	BgStage bgStage ;
	
	@Override
	public void create () {
		bgStage = new BgStage() ;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.input.setInputProcessor(bgStage);
		bgStage.act();
		bgStage.draw();
		bgStage.showStartAnim();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		bgStage.dispose() ;
	}
}
