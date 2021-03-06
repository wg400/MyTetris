package sll.wg.tetris;

public class ModelL6 extends Model  {
	// L��
	public ModelL6(TetrisModel[][] tetrisModels) {
		super(tetrisModels);
	}

	public void moveleft() {
		if (this.j>1) {
			this.j-- ;
			if (tetrisModels[i-1][j-1].isLock || tetrisModels[i][j].isLock || tetrisModels[i+1][j].isLock) {
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
			if (tetrisModels[i-1][j].isLock || tetrisModels[i][j].isLock || tetrisModels[i+1][j].isLock) {
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
		if (this.i<20) {
			this.i++ ;
			if (tetrisModels[i-1][j-1].isLock || tetrisModels[i+1][j].isLock) {
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
			// ��Ϸ����
			BgStage.reset();
		} else {
			tetrisModels[i-1][j-1].isLock = true ;
			tetrisModels[i-1][j].isLock = true ;
			tetrisModels[i][j].isLock = true ;
			tetrisModels[i+1][j].isLock = true ;
			this.reset();
			BgStage.setModelIndex() ;
			checkDisappear() ;
		}
	}
	
	public void moveModel(Model model,boolean visible) {
		tetrisModels[i-1][j-1].setVisible(visible);
		tetrisModels[i-1][j].setVisible(visible);
		tetrisModels[i][j].setVisible(visible);
		tetrisModels[i+1][j].setVisible(visible);
	}
	
	public void doChange() {
		BgStage.modelIndex = 11 ;
	}
	
	public void doDrawNext() {
		BgStage.nextModels[0][0].setVisible(true) ;
		BgStage.nextModels[0][1].setVisible(true) ;
		BgStage.nextModels[1][1].setVisible(true) ;
		BgStage.nextModels[2][1].setVisible(true) ;
	} ;
}
