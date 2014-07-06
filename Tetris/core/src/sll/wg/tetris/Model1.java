package sll.wg.tetris;

public class Model1 extends Model  {
	// ·½¿é
	public Model1(int i, int j) {
		super(i, j);
	}
	
	public Model1(TetrisModel[][] tetrisModels) {
		super(0, 4,tetrisModels);
	}
	
	public void reset() {
		this.i = 0 ;
		this.j = 4 ;
	}

	public Model getPoint1() {
		return new Model(this.i,this.j) ;
	}
	
	public Model getPoint2() {
		return new Model(this.i+1,this.j) ;
	}
	
	public Model getPoint3() {
		return new Model(this.i,this.j+1) ;
	}
	
	public Model getPoint4() {
		return new Model(this.i+1,this.j+1) ;
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
		tetrisModels[i][j].isLock = true ;
		tetrisModels[i][j+1].isLock = true ;
		tetrisModels[i+1][j].isLock = true ;
		tetrisModels[i+1][j+1].isLock = true ;
		this.reset();
	}
	
	public void moveModel(Model model,boolean visible) {
		tetrisModels[i][j].setVisible(visible);
		tetrisModels[i][j+1].setVisible(visible);
		tetrisModels[i+1][j].setVisible(visible);
		tetrisModels[i+1][j+1].setVisible(visible);
	}
}
