package sll.wg.tetris;

public class ModelN2 extends Model  {
	// L��
	public ModelN2(TetrisModel[][] tetrisModels) {
		super(tetrisModels);
	}

	public void moveleft() {
		if (this.j>1) {
			this.j-- ;
			if (tetrisModels[i][j-1].isLock || tetrisModels[i+1][j].isLock) {
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
			if (tetrisModels[i][j].isLock || tetrisModels[i+1][j+1].isLock) {
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
			if (tetrisModels[i][j-1].isLock || tetrisModels[i+1][j].isLock || tetrisModels[i+1][j+1].isLock) {
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
			tetrisModels[i][j-1].isLock = true ;
			tetrisModels[i][j].isLock = true ;
			tetrisModels[i+1][j+1].isLock = true ;
			tetrisModels[i+1][j].isLock = true ;
			this.reset();
			BgStage.setModelIndex() ;
			checkDisappear() ;
		}
	}
	
	public void moveModel(Model model,boolean visible) {
		tetrisModels[i][j-1].setVisible(visible);
		tetrisModels[i][j].setVisible(visible);
		tetrisModels[i+1][j+1].setVisible(visible);
		tetrisModels[i+1][j].setVisible(visible);
	}
	
	public void doChange() {
		BgStage.modelIndex = 13 ;
	}
	
	public void doDrawNext() {
		BgStage.nextModels[0][1].setVisible(true) ;
		BgStage.nextModels[1][1].setVisible(true) ;
		BgStage.nextModels[0][0].setVisible(true) ;
		BgStage.nextModels[1][2].setVisible(true) ;
	} ;
}
