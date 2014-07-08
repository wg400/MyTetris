package sll.wg.tetris;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BgStage extends Stage {
	int col = 10;
	int line = 20;
	static long score ;
	static Label scoreLabel ;
	Label hiscoreLabel ;
	float topH ;
	float bottomH ;
	float startTopW ;
	float startTopH ;
	static float scoreLabelX = 120 ;
	static float scoreLabelY = 115 ;
	float hiscoreLabelY = 200 ;
	ImageButton button_l ;
	ImageButton button_r ;
	ImageButton button_d ;
	ImageButton button_c ;
	Image topbg ;
	TetrisModel[][] tetrisModels ;
	public static TetrisModel[][] nextModels ;
	public static Model[] models ;
	
	static boolean showStartAnim = true ;
	static boolean isStart = false ;
	static int isPause = -1 ;
	long curTime ;
	static long delay = 10 ;
	static int index = 19 ;
	static int modelSize = 19 ;
	static Random random = new Random(modelSize) ;
	public static int modelIndex ;
	static int nextIndex ;
	static boolean visible = true ;
	
	long clickTime ;
	int clickdelay = 200 ;
	int witchPress = 0; //1¡¢left 2¡¢ right 3¡¢down
	
	static boolean canPlaySond = true ;
	Sound buttonSound ;
	Sound overSound ;
	static Sound removeSound ;
	Sound rotateSound ;
	public BgStage() {
		super() ;
		init() ;
	}

	private void init() {
		buttonSound = Gdx.audio.newSound(Gdx.files.internal("data/audio/button.ogg")) ;
		overSound = Gdx.audio.newSound(Gdx.files.internal("data/audio/over.ogg")) ;
		removeSound = Gdx.audio.newSound(Gdx.files.internal("data/audio/remove.ogg")) ;
		rotateSound = Gdx.audio.newSound(Gdx.files.internal("data/audio/rotate.ogg")) ;
		
		buttonSound.setLooping(buttonSound.loop(), false) ;
		overSound.setLooping(overSound.loop(), false) ;
		removeSound.setLooping(removeSound.loop(), false) ;
		rotateSound.setLooping(rotateSound.loop(), false) ;
		
		TextureRegion topbgregion = new TextureRegion(new Texture("data/top_bg.png")) ;
		TextureRegion bgregion = new TextureRegion(new Texture("data/bg.png")) ;
		Texture button_n = new Texture("data/button_n.png") ;
		Texture button_f = new Texture("data/button_f.png") ;
		TextureRegionDrawable drawable_n = new TextureRegionDrawable(new TextureRegion(button_n)) ;
		TextureRegionDrawable drawable_f = new TextureRegionDrawable(new TextureRegion(button_f)) ;
		topbg = new Image(topbgregion) ;
		Image bg = new Image(bgregion) ;
		button_l = new ImageButton(drawable_n,drawable_f) ;
		button_r = new ImageButton(drawable_n,drawable_f) ;
		button_d = new ImageButton(drawable_n,drawable_f) ;
		button_c = new ImageButton(drawable_n,drawable_f) ;
		startTopW = topbg.getWidth() ;
		startTopH = topbg.getHeight() ;
		
		topH = Gdx.graphics.getWidth()*startTopH/startTopW ;
		bottomH = Gdx.graphics.getHeight()-topH ;
		topbg.setSize(Gdx.graphics.getWidth(), topH);
		topbg.setPosition(0, bottomH);
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bg.setPosition(0, 0);
		button_l.setSize(bottomH*0.5f, bottomH*0.5f);
		button_r.setSize(bottomH*0.5f, bottomH*0.5f);
		button_d.setSize(bottomH*0.5f, bottomH*0.5f);
		button_c.setSize(bottomH*0.8f, bottomH*0.8f);
		button_l.setPosition(bottomH*0.05f, bottomH*0.45f);
		button_r.setPosition(bottomH*0.85f, bottomH*0.45f);
		button_d.setPosition(bottomH*0.45f, bottomH*0.05f);
		button_c.setPosition(Gdx.graphics.getWidth()-bottomH*0.85f, bottomH*0.1f);
		button_l.addListener(listener) ;
		button_r.addListener(listener) ;
		button_d.addListener(listener) ;
		button_c.addListener(listener) ;
		topbg.addListener(listener) ;
		this.addActor(bg);
		this.addActor(topbg);
		this.addActor(button_l);
		this.addActor(button_r);
		this.addActor(button_d);
		this.addActor(button_c);
		
		initTetris() ;
		initLabel() ;
	}
	
	private void initTetris() {
		float startx = 50 ;
		float starty = 95 ;
		float startw = 35 ;
		float paddingh = 2.4f ;
		float paddingv = 2.6f ;
		float paddingh1 = 21f ;
		curTime = System.currentTimeMillis() ;
		tetrisModels = new TetrisModel[line][col] ;
		models = new Model[modelSize] ;
		startx = Gdx.graphics.getWidth()*startx/startTopW ; 
		starty = Gdx.graphics.getHeight() - topH*starty/startTopH ;
		startw = Gdx.graphics.getWidth()*startw/startTopW ;
		paddingh = Gdx.graphics.getWidth()*paddingh/startTopW ;
		paddingh1 = Gdx.graphics.getWidth()*paddingh1/startTopW ;
		paddingv = topH*paddingv/startTopH ;
		
		TetrisModel tetrisModel = new TetrisModel() ;
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < col; j++) {
				try {
					tetrisModels[i][j] = tetrisModel.clone() ;
					tetrisModels[i][j].setData(startx+(startw+paddingh)*j, starty-(startw+paddingv)*i,startw) ;
					this.addActor(tetrisModels[i][j].image);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
//				tetrisModels[i][j] = new TetrisModel(startx+(startw+paddingh)*j, starty-(startw+paddingv)*i,startw) ;
//				this.addActor(tetrisModels[i][j].image);
			}
		}
		
		nextModels = new TetrisModel[4][4] ;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				try {
					nextModels[i][j] = tetrisModel.clone() ;
					nextModels[i][j].setData(tetrisModels[5+i][col-1].x+startw+paddingh1+(startw+paddingh)*j, tetrisModels[5+i][col-1].y,startw) ;
					this.addActor(nextModels[i][j].image);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		
		models[0] = new ModelRect(tetrisModels) ;
		models[1] = new ModelT1(tetrisModels) ;
		models[2] = new ModelT2(tetrisModels) ;
		models[3] = new ModelT3(tetrisModels) ;
		models[4] = new ModelT4(tetrisModels) ;
		models[5] = new ModelL1(tetrisModels) ;
		models[6] = new ModelL2(tetrisModels) ;
		models[7] = new ModelL3(tetrisModels) ;
		models[8] = new ModelL4(tetrisModels) ;
		models[9] = new ModelL5(tetrisModels) ;
		models[10] = new ModelL6(tetrisModels) ;
		models[11] = new ModelL7(tetrisModels) ;
		models[12] = new ModelL8(tetrisModels) ;
		models[13] = new ModelN1(tetrisModels) ;
		models[14] = new ModelN2(tetrisModels) ;
		models[15] = new ModelN3(tetrisModels) ;
		models[16] = new ModelN4(tetrisModels) ;
		models[17] = new ModelH(tetrisModels) ;
		models[18] = new ModelV(tetrisModels) ;
	}
	
	private void initLabel() {
		float w = 100 ;
		float h = 30 ;
		w = Gdx.graphics.getWidth()*w/startTopW ; 
		scoreLabelX = Gdx.graphics.getWidth()*scoreLabelX/startTopW ;
		h = topH*h/startTopH ;
		scoreLabelY = topH*scoreLabelY/startTopH ;
		hiscoreLabelY = topH*hiscoreLabelY/startTopH ;
		BitmapFont font = new BitmapFont() ;
		scoreLabel = new Label("", new LabelStyle(font,font.getColor())) ;
		hiscoreLabel = new Label("", new LabelStyle(font,font.getColor())) ;
		scoreLabel.setPosition(Gdx.graphics.getWidth()-scoreLabelX, Gdx.graphics.getHeight()-scoreLabelY) ;
		scoreLabel.setSize(w, h) ;
//		scoreLabel.setFontScale(0.7f) ;
		scoreLabel.setColor(Color.BLACK) ;
		this.addActor(scoreLabel);
		hiscoreLabel.setPosition(Gdx.graphics.getWidth()-scoreLabelX, Gdx.graphics.getHeight()-hiscoreLabelY) ;
		hiscoreLabel.setSize(w, h) ;
//		hiscoreLabel.setFontScale(0.7f) ;
		hiscoreLabel.setColor(Color.BLACK) ;
		this.addActor(hiscoreLabel);
	}
	
	private void setLineVisible(int index,boolean visible) {
		for (int j = 0; j < col; j++) {
			tetrisModels[index][j].setVisible(visible);
			if (!visible) {
				tetrisModels[index][j].isLock = false;
			}
		}
	}
	
	public void showStartAnim() {
		long time = System.currentTimeMillis() ;
		if (witchPress!=0&&time-clickTime>clickdelay) {
			clickTime = time ;
			clickdelay = clickdelay>40?clickdelay-40:clickdelay ;
			// ¼ÓËÙ
			if (isPause==0) {
				switch (witchPress) {
				case 1:
					models[modelIndex].moveleft();
					break;
				case 2:
					models[modelIndex].moveRight();
					break;
				case 3:
					models[modelIndex].moveDown();
					break;
				default:
					break;
				}
			}
		}
		if (time-curTime>delay) {
			curTime = time ;
			if (index<0) {
				if (visible) {
					playSound(overSound) ;
					index = 19 ;
					visible = false ;
				} else {
					if (!isStart) {
						setStartPage() ;
					} else {
						if (isPause==0) {
							models[modelIndex].moveDown();
						}
					}
				}
			}
			if (index>=0) {
				setLineVisible(index,visible) ;
				index-- ;
			}
		}
	}
	
	private void setStartPage() {
		// 5 
		tetrisModels[0][1].image.setVisible(true);
		tetrisModels[0][2].image.setVisible(true);
		tetrisModels[0][3].image.setVisible(true);
		tetrisModels[1][1].image.setVisible(true);
		tetrisModels[2][1].image.setVisible(true);
		tetrisModels[2][2].image.setVisible(true);
		tetrisModels[2][3].image.setVisible(true);
		tetrisModels[3][3].image.setVisible(true);
		tetrisModels[4][3].image.setVisible(true);
		tetrisModels[4][2].image.setVisible(true);
		tetrisModels[4][1].image.setVisible(true);
		
		// 2 
		tetrisModels[6][1].image.setVisible(true);
		tetrisModels[6][2].image.setVisible(true);
		tetrisModels[6][3].image.setVisible(true);
		tetrisModels[7][3].image.setVisible(true);
		tetrisModels[8][3].image.setVisible(true);
		tetrisModels[8][2].image.setVisible(true);
		tetrisModels[8][1].image.setVisible(true);
		tetrisModels[9][1].image.setVisible(true);
		tetrisModels[10][1].image.setVisible(true);
		tetrisModels[10][2].image.setVisible(true);
		tetrisModels[10][3].image.setVisible(true);
		
		// 0 
		tetrisModels[12][1].image.setVisible(true);
		tetrisModels[12][2].image.setVisible(true);
		tetrisModels[12][3].image.setVisible(true);
		tetrisModels[13][3].image.setVisible(true);
		tetrisModels[14][3].image.setVisible(true);
		tetrisModels[15][3].image.setVisible(true);
		tetrisModels[16][3].image.setVisible(true);
		tetrisModels[16][2].image.setVisible(true);
		tetrisModels[16][1].image.setVisible(true);
		tetrisModels[15][1].image.setVisible(true);
		tetrisModels[14][1].image.setVisible(true);
		tetrisModels[13][1].image.setVisible(true);
		
		// 4 
		tetrisModels[0][6].image.setVisible(true);
		tetrisModels[1][6].image.setVisible(true);
		tetrisModels[2][6].image.setVisible(true);
		tetrisModels[2][7].image.setVisible(true);
		tetrisModels[2][8].image.setVisible(true);
		tetrisModels[1][8].image.setVisible(true);
		tetrisModels[0][8].image.setVisible(true);
		tetrisModels[3][8].image.setVisible(true);
		tetrisModels[4][8].image.setVisible(true);
		
		// 0 
		tetrisModels[6][6].image.setVisible(true);
		tetrisModels[6][7].image.setVisible(true);
		tetrisModels[6][8].image.setVisible(true);
		tetrisModels[7][8].image.setVisible(true);
		tetrisModels[8][8].image.setVisible(true);
		tetrisModels[9][8].image.setVisible(true);
		tetrisModels[10][8].image.setVisible(true);
		tetrisModels[10][7].image.setVisible(true);
		tetrisModels[10][6].image.setVisible(true);
		tetrisModels[9][6].image.setVisible(true);
		tetrisModels[8][6].image.setVisible(true);
		tetrisModels[7][6].image.setVisible(true);
		
		// 0 
		tetrisModels[12][6].image.setVisible(true);
		tetrisModels[12][7].image.setVisible(true);
		tetrisModels[12][8].image.setVisible(true);
		tetrisModels[13][8].image.setVisible(true);
		tetrisModels[14][8].image.setVisible(true);
		tetrisModels[15][8].image.setVisible(true);
		tetrisModels[16][8].image.setVisible(true);
		tetrisModels[16][7].image.setVisible(true);
		tetrisModels[16][6].image.setVisible(true);
		tetrisModels[15][6].image.setVisible(true);
		tetrisModels[14][6].image.setVisible(true);
		tetrisModels[13][6].image.setVisible(true);
	}
	
	private void clearScreen() {
		for (int i = 0; i < line; i++) {
			setLineVisible(i,false) ;
		}
	}
	
	public static void setModelIndex() {
//		BgStage.modelIndex = Math.abs(random.nextInt())%modelSize ;
		BgStage.modelIndex = nextIndex ;
		nextIndex = (int) (Math.random()*modelSize) ;
		models[nextIndex].drawNext() ;
//		BgStage.modelIndex = (int) (Math.random()*4) + 9 ;
//		BgStage.modelIndex = 9 ;
		System.out.println("BgStage.modelIndex:"+BgStage.modelIndex);
	}
	
	public static void setScore(int line) {
		int temp = 0 ;
		switch (line) {
		case 0:
			temp = 0 ;
			break;
		case 1:
			temp = 10 ;
			break;
		case 2:
			temp = 30 ;
			break;
		case 3:
			temp = 60 ;
			break;
		case 4:
			temp = 100 ;
			break;

		default:
			break;
		}
		score += temp ;
		scoreLabel.setText(score+"") ;
	}
	
	public static void reset() {
		showStartAnim = true ;
		isStart = false ;
		isPause = -1 ;
		delay = 10 ;
		index = 19 ;
		visible = true ;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				nextModels[i][j].setVisible(false) ;
			}
		}
		score = 0 ;
		scoreLabel.setText("") ; 
	}
	
	public void playSound(Sound sound) {
		if (canPlaySond) {
			sound.play() ;
		}
	}
	
	public static void playSound() {
		if (canPlaySond) {
			removeSound.play() ;
		}
	}
	
	private InputListener listener = new InputListener() {
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			System.out.println("touchDown");
			if (event.getListenerActor() == button_l) {
				witchPress = 1 ;
				playSound(buttonSound) ;
			} else if (event.getListenerActor() == button_r) {
				witchPress = 2 ;
				playSound(buttonSound) ;
			} else if (event.getListenerActor() == button_c) {
				models[modelIndex].change() ;
				playSound(rotateSound) ;
			} else if (event.getListenerActor() == topbg) {
				playSound(buttonSound) ;
				isStart = true ;
				if (isPause == -1) {
					nextIndex = (int) (Math.random()*modelSize) ;
					setModelIndex() ;
					isPause = 0 ;
					delay = 400 ;
					clearScreen() ;
					curTime = System.currentTimeMillis() ;
				} else if(isPause ==0) {
					isPause = 1 ;
				} else {
					isPause = 0 ;
				}
			}else if (event.getListenerActor() == button_d) {
				witchPress = 3 ;
				playSound(buttonSound) ;
			}
			return true;
		};
		
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			System.out.println("touchUp");
			witchPress = 0 ;
			clickTime = 0 ;
			clickdelay = 200 ;
		};
	} ;
	
	public void dispose() {
		super.dispose() ;
		System.out.println("dispose");
		buttonSound.dispose() ;
		removeSound.dispose() ;
		rotateSound.dispose() ;
		overSound.dispose() ;
		score = 0 ;
		scoreLabel = null ;
		scoreLabelX = 120 ;
		scoreLabelY = 115 ;
		nextModels = null ;
		models = null ;
		showStartAnim = true ;
		isStart = false ;
		isPause = -1 ;
		delay = 10 ;
		index = 19 ;
		modelSize = 19 ;
		modelIndex = 0 ;
		nextIndex = 0 ;
		visible = true ;
		canPlaySond = true ;
		removeSound = null ;
	};
}
