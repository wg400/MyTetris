package sll.wg.tetris;

public class ModelN3 extends Model  {
	// LÐÍ
	public ModelN3(TetrisModel[][] tetrisModels) {
		super(tetrisModels);
	}

	public void moveleft() {
		if (this.j>1) {
			this.j-- ;
			if (tetrisModels[i-1][j-1].isLock || tetrisModels[i][j-1].isLock || tetrisModels[i+1][j].isLock) {
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
			if (tetrisModels[i-1][j-1].isLock || tetrisModels[i][j].isLock || tetrisModels[i+1][j].isLock) {
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
			if (tetrisModels[i+1][j].isLock || tetrisModels[i][j-1].isLock) {
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
		tetrisModels[i-1][j-1].isLock = true ;
		tetrisModels[i][j].isLock = true ;
		tetrisModels[i][j-1].isLock = true ;
		tetrisModels[i+1][j].isLock = true ;
		this.reset();
		BgStage.setModelIndex() ;
		checkDisappear() ;
	}
	
	public void moveModel(Model model,boolean visible) {
		tetrisModels[i-1][j-1].setVisible(visible);
		tetrisModels[i][j].setVisible(visible);
		tetrisModels[i][j-1].setVisible(visible);
		tetrisModels[i+1][j].setVisible(visible);
	}
	
	public void doChange() {
		BgStage.modelIndex = 16 ;
	}
}
