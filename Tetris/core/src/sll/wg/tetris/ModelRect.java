package sll.wg.tetris;

public class ModelRect extends Model  {
	// ·½¿é
	public ModelRect(TetrisModel[][] tetrisModels) {
		super(0, 4,tetrisModels);
	}
	
	public void reset() {
		this.i = 0 ;
		this.j = 4 ;
	}
	
	public void moveleft() {
		if (this.j>0) {
			this.j-- ;
			if (tetrisModels[i][j].isLock || tetrisModels[i+1][j].isLock) {
				this.j++ ;
			} else {
				this.j++ ;
				moveModel(this,false) ;
				this.j-- ;
				moveModel(this,true) ;
			}
		}
	}
	
	public void moveRight() {
		if (this.j<8) {
			this.j++ ;
			if (tetrisModels[i][j+1].isLock || tetrisModels[i+1][j+1].isLock) {
				this.j-- ;
			} else {
				this.j-- ;
				moveModel(this,false) ;
				this.j++ ;
				moveModel(this,true) ;
			}
		}
	}

	public void moveDown() {
		if (this.i<18) {
			this.i++ ;
			if (tetrisModels[i+1][j].isLock || tetrisModels[i+1][j+1].isLock) {
				this.i-- ;
				lock() ;
			} else {
				this.i-- ;
				moveModel(this,false) ;
				this.i++ ;
				moveModel(this,true) ;
			}
		} else {
			lock() ;
		}
	}
	
	public void lock() {
		moveModel(this,true) ;
		if (i==1&&j==4) {
			// ÓÎÏ·½áÊø
			BgStage.reset();
		} else {
			tetrisModels[i][j].isLock = true ;
			tetrisModels[i][j+1].isLock = true ;
			tetrisModels[i+1][j].isLock = true ;
			tetrisModels[i+1][j+1].isLock = true ;
			this.reset();
			BgStage.setModelIndex() ;
			checkDisappear() ;
		}
	}
	
	public void moveModel(Model model,boolean visible) {
		tetrisModels[i][j].setVisible(visible);
		tetrisModels[i][j+1].setVisible(visible);
		tetrisModels[i+1][j].setVisible(visible);
		tetrisModels[i+1][j+1].setVisible(visible);
	}
	
	public void change() {}
	
	public void doDrawNext() {
		BgStage.nextModels[1][1].setVisible(true) ;
		BgStage.nextModels[1][2].setVisible(true) ;
		BgStage.nextModels[2][1].setVisible(true) ;
		BgStage.nextModels[2][2].setVisible(true) ;
	} ;
}
