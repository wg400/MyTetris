package sll.wg.tetris;

public class ModelV extends Model  {
	// 竖直一字
	public ModelV(TetrisModel[][] tetrisModels) {
		super(tetrisModels);
	}

	public void moveleft() {
		if (this.j>0) {
			this.j-- ;
			if (tetrisModels[i-1][j].isLock || tetrisModels[i][j].isLock || tetrisModels[i+1][j].isLock || tetrisModels[i+2][j].isLock) {
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
		if (this.j<9) {
			this.j++ ;
			if (tetrisModels[i-1][j].isLock || tetrisModels[i][j].isLock || tetrisModels[i+1][j].isLock || tetrisModels[i+2][j].isLock) {
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
		if (this.i<17) {
			this.i++ ;
			if (tetrisModels[i+2][j].isLock) {
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
			// 游戏结束
			BgStage.reset();
		} else {
			tetrisModels[i-1][j].isLock = true ;
			tetrisModels[i][j].isLock = true ;
			tetrisModels[i+1][j].isLock = true ;
			tetrisModels[i+2][j].isLock = true ;
			this.reset();
			BgStage.setModelIndex() ;
			checkDisappear() ;
		}
	}
	
	public void moveModel(Model model,boolean visible) {
		tetrisModels[i-1][j].setVisible(visible);
		tetrisModels[i][j].setVisible(visible);
		tetrisModels[i+1][j].setVisible(visible);
		tetrisModels[i+2][j].setVisible(visible);
	}
	
	public void change() {
		if (j>0&&j<4) {
			if (!tetrisModels[i-1][j-1].isLock&&!tetrisModels[i][j-1].isLock
					&&!tetrisModels[i+1][j-1].isLock&&!tetrisModels[i+2][j-1].isLock
					&&!tetrisModels[i-1][j+1].isLock&&!tetrisModels[i][j+1].isLock
					&&!tetrisModels[i+1][j+1].isLock&&!tetrisModels[i+2][j+1].isLock
					&&!tetrisModels[i-1][j+2].isLock&&!tetrisModels[i][j+2].isLock
					&&!tetrisModels[i+1][j+2].isLock&&!tetrisModels[i+2][j+2].isLock) {
				moveModel(this,false) ;
				BgStage.modelIndex = 17 ;
				BgStage.models[BgStage.modelIndex].i = this.i+1 ;
				BgStage.models[BgStage.modelIndex].j = this.j ;
				BgStage.models[BgStage.modelIndex].moveModel(BgStage.models[BgStage.modelIndex],true) ;
				this.reset() ;
			}
		} else if (j<9) {
			if (!tetrisModels[i-1][j-1].isLock&&!tetrisModels[i][j-1].isLock
					&&!tetrisModels[i+1][j-1].isLock&&!tetrisModels[i+2][j-1].isLock
					&&!tetrisModels[i-1][j+1].isLock&&!tetrisModels[i][j+1].isLock
					&&!tetrisModels[i+1][j+1].isLock&&!tetrisModels[i+2][j+1].isLock
					&&!tetrisModels[i-1][j-2].isLock&&!tetrisModels[i][j-2].isLock
					&&!tetrisModels[i+1][j-2].isLock&&!tetrisModels[i+2][j-2].isLock) {
				moveModel(this,false) ;
				BgStage.modelIndex = 17 ;
				BgStage.models[BgStage.modelIndex].i = this.i+1 ;
				BgStage.models[BgStage.modelIndex].j = this.j-1 ;
				BgStage.models[BgStage.modelIndex].moveModel(BgStage.models[BgStage.modelIndex],true) ;
				this.reset() ;
			}
		}
	}
	
	public void doDrawNext() {
		BgStage.nextModels[0][1].setVisible(true) ;
		BgStage.nextModels[1][1].setVisible(true) ;
		BgStage.nextModels[2][1].setVisible(true) ;
		BgStage.nextModels[3][1].setVisible(true) ;
	} ;
}
