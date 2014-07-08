package sll.wg.tetris;

import java.util.ArrayList;

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
	
	public Model(TetrisModel[][] tetrisModels) {
		this.i = 1;
		this.j = 4;
		this.tetrisModels = tetrisModels ;
	}
	
	public Model() {
		super();
	}
	
	public void reset() {
		this.i = 1 ;
		this.j = 4 ;
	}

	public void moveleft() {}
	
	public void moveRight() {}

	public void moveDown() {}
	
	public void disappear(ArrayList<Integer> disappearList) {
		int size = disappearList.size() ;
		System.out.println(disappearList.toString());
		if (size>0) {
			int col = tetrisModels[0].length ;
			for (int k=0;k<size;k++) {
				System.out.println(disappearList.get(k).toString());
				for (int i = disappearList.get(k); i > 0; i--) {
					for (int j = 0; j < col; j++) {
						tetrisModels[i][j].setVisible(tetrisModels[i-1][j].visible) ;
						tetrisModels[i][j].isLock = tetrisModels[i-1][j].isLock ;
					}
				}
				setLineVisible(0,false) ;
			}
			BgStage.setScore(size) ;
			BgStage.playSound() ;
		}
	}
	
	private void setLineVisible(int index,boolean visible) {
		int col = tetrisModels[index].length ;
		for (int j = 0; j < col; j++) {
			tetrisModels[index][j].setVisible(visible);
			if (!visible) {
				tetrisModels[index][j].isLock = false;
			}
		}
	}
	
	public void moveModel(Model model,boolean visible) {} ;
	public void change() {
		if (i<18&&j>0&&j<9&&!tetrisModels[i-1][j-1].isLock&&!tetrisModels[i-1][j].isLock
				&&!tetrisModels[i-1][j+1].isLock&&!tetrisModels[i][j-1].isLock
				&&!tetrisModels[i][j].isLock&&!tetrisModels[i][j+1].isLock
				&&!tetrisModels[i+1][j-1].isLock&&!tetrisModels[i+1][j].isLock
				&&!tetrisModels[i+1][j+1].isLock) {
			moveModel(this,false) ;
			doChange() ;
			BgStage.models[BgStage.modelIndex].i = this.i ;
			BgStage.models[BgStage.modelIndex].j = this.j ;
			BgStage.models[BgStage.modelIndex].moveModel(BgStage.models[BgStage.modelIndex],true) ;
			this.reset() ;
		}
	}
	public void doChange() {} ;
	
	public void drawNext() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				BgStage.nextModels[i][j].setVisible(false) ;
			}
		}
		doDrawNext() ;
	} ;
	
	public void doDrawNext() {} ;
	
	public void checkDisappear() {
		int line = tetrisModels.length ;
		int col = tetrisModels[0].length ;
		boolean isAllShow = true ;
		ArrayList<Integer> disappearList = new ArrayList<Integer>() ;
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < col; j++) {
				if (!tetrisModels[i][j].visible) {
					isAllShow = false ;
				}
			}
			if (isAllShow) {
				disappearList.add(i) ;
			}
			isAllShow = true ;
		}
		disappear(disappearList) ;
	}
}
