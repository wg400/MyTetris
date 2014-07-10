package sll.wg.tetris;

public class ModelH extends Model  {
	// 水平一字
	public ModelH(TetrisModel[][] tetrisModels) {
		super(0,4,tetrisModels);
	}
	
	public void reset() {
		this.i = 2 ;
		this.j = 4 ;
	}

	public void moveleft() {
		if (this.j>1) {
			this.j-- ;
			if (tetrisModels[i][j-1].isLock) {
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
		if (this.j<7) {
			this.j++ ;
			if (tetrisModels[i][j+2].isLock) {
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
		if (this.i<21) {
			this.i++ ;
			if (tetrisModels[i][j-1].isLock || tetrisModels[i][j].isLock || tetrisModels[i][j+1].isLock || tetrisModels[i][j+2].isLock) {
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
		if (i==2&&j==4) {
			// 游戏结束
			BgStage.reset();
		} else {
			tetrisModels[i][j-1].isLock = true ;
			tetrisModels[i][j].isLock = true ;
			tetrisModels[i][j+1].isLock = true ;
			tetrisModels[i][j+2].isLock = true ;
			this.reset();
			BgStage.setModelIndex() ;
			checkDisappear() ;
		}
	}
	
	public void moveModel(Model model,boolean visible) {
		tetrisModels[i][j-1].setVisible(visible);
		tetrisModels[i][j].setVisible(visible);
		tetrisModels[i][j+1].setVisible(visible);
		tetrisModels[i][j+2].setVisible(visible);
	}
	
	public void change() {
		if (i<18&&i>1&&!tetrisModels[i-2][j-1].isLock&&!tetrisModels[i-2][j].isLock
				&&!tetrisModels[i-2][j+1].isLock&&!tetrisModels[i-2][j+2].isLock
				&&!tetrisModels[i-1][j-1].isLock&&!tetrisModels[i-1][j].isLock
				&&!tetrisModels[i-1][j+1].isLock&&!tetrisModels[i-1][j+2].isLock
				&&!tetrisModels[i+1][j-1].isLock&&!tetrisModels[i+1][j].isLock
				&&!tetrisModels[i+1][j+1].isLock&&!tetrisModels[i+1][j+2].isLock) {
			moveModel(this,false) ;
			BgStage.modelIndex = 18 ;
			if (j<4) {
				BgStage.models[BgStage.modelIndex].i = this.i-1 ;
				BgStage.models[BgStage.modelIndex].j = this.j ;
			} else {
				BgStage.models[BgStage.modelIndex].i = this.i-1 ;
				BgStage.models[BgStage.modelIndex].j = this.j+1 ;
			}
			BgStage.models[BgStage.modelIndex].moveModel(BgStage.models[BgStage.modelIndex],true) ;
			this.reset() ;
		}
	}
	
	public void doDrawNext() {
		BgStage.nextModels[1][0].setVisible(true) ;
		BgStage.nextModels[1][1].setVisible(true) ;
		BgStage.nextModels[1][2].setVisible(true) ;
		BgStage.nextModels[1][3].setVisible(true) ;
	} ;
}
