package sll.wg.tetris;

public class Model {
	public int i ;
	public int j ;
	public TetrisModel[][] tetrisModels ;
	
	public Model(int i, int j,TetrisModel[][] tetrisModels) {
		super();
		this.i = i;
		this.j = j;
		this.tetrisModels = tetrisModels ;
	}
	
	public Model(int i, int j) {
		super();
		this.i = i;
		this.j = j;
	}

	public void moveleft() {
		if (this.j>0) {
			this.j-- ;
		}
	}
	
	public void moveRight() {
		if (this.j<9) {
			this.j++ ;
		}
	}

	public void moveDown() {
		if (this.i<19) {
			this.i++ ;
		}
	}
	
	public Model getPoint1() {
		return this ;
	}
	
	public Model getPoint2() {
		return this ;
	}
	
	public Model getPoint3() {
		return this ;
	}
	
	public Model getPoint4() {
		return this ;
	}
}
