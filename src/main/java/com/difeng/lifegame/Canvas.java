package com.difeng.lifegame;

import static com.difeng.lifegame.Constant.CELL_HEIGHT;
import static com.difeng.lifegame.Constant.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;
/**
 * 
 * 封装生命游戏的算法并负责绘制图案
 * 
 * @author difeng
 *
 */
public class Canvas extends JPanel implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//取值为1 和 2   1:当代存活的细胞      2:刚产生的下一代细胞
	int [][] flagArr;
	//活着的细胞
	List<Cell> cellList = new LinkedList<Cell>();
	//新生的细胞
	List<Cell> bornCellList = new LinkedList<Cell>();
	//死亡的细胞
	List<Cell> deadCellList = new LinkedList<Cell>();
	//方向数组
	final int [][] dir = {
			{-1,-1},{-1,0},
			{-1,1},{0,-1},
			{0,1},{1,-1},
			{1,0},{1,1},
	};
	//滑翔机发射器的图案矩阵
    final int[][] plant = {
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},	
    		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},	
    		{0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1},	
    		{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1},	
    		{1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
    		{1,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},	
    		{0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},	
    		{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    		{0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}	
    };
    
	public Canvas(){
		//设置棋盘的背景色
		setBackground(Color.black);
		//初始化棋盘上已经放置的细胞
		init();
	}
	
	/**
	 * 
	 * 初始化
	 * 
	 */
	public void init(){
		COL_NUM = SCREEN_WIDTH/CELL_WIDTH + 4;
		ROW_NUM= SCREEN_HEIGHT/CELL_HEIGHT + 4;
		flagArr = new int[ROW_NUM][COL_NUM];
		//滑翔机发射器的起始坐标
		int x = 5,y=5;
		//将滑翔机发射器的图案初始化到
		for(int i = 0;i < plant.length;i++){
			for(int j = 0;j < plant[0].length;j++){
				if(plant[i][j] == 1){
					int r = x + i;
					int col = y + j;
					if(flagArr[r][col]==0){
						Cell cel = new Cell(r, col,true);
						cellList.add(cel);
						flagArr[r][col] = 1;
					}
				}
			}
		}
	}
    /**
     * 棋盘上的所有细胞开始繁衍进化
     */
	public void evolution(){
		//清空中间计算的列表
		bornCellList.clear();
		deadCellList.clear();
		
		//遍历当代细胞列表，让细胞开始繁衍生长，计算下一代的状态
		for(Cell cel:cellList){
			//细胞生长
			cellEvolution(cel); 
		}
		
		//将下一代将要死掉的细胞标记为死亡
		for(Cell cel:deadCellList){
			flagArr[cel.row][cel.col] = 0;
		}
		
		//从活着的细胞列表中去掉在下一代将要死掉的
		cellList.removeAll(deadCellList);
		
		//将下一代新生成的细胞标记为当代存活的细胞
		for(Cell cel:bornCellList){
			flagArr[cel.row][cel.col]=1;
		}
		//将新生成的细胞加入到活着的细胞列表中
		cellList.addAll(bornCellList);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//绘制细胞棋盘
		g.setColor(new Color(33,33,33));
		for(int i=0;i<=ROW_NUM;i++){
			g.drawLine(0,i*CELL_HEIGHT,SCREEN_WIDTH,i*CELL_HEIGHT);
		}
		for(int i=0;i<=COL_NUM;i++){
			g.drawLine(i*CELL_WIDTH,0,i*CELL_WIDTH,SCREEN_HEIGHT);
		}
		//开始画细胞
		g.setColor(Color.GREEN);
		for(int i=0;i<cellList.size();i++){
			Cell cel = cellList.get(i);
			if(cel!=null){
				g.fill3DRect(cel.col*CELL_WIDTH,cel.row*CELL_HEIGHT,CELL_WIDTH,CELL_HEIGHT,true);		
			}
		}
	}
	
	/**
	 * 细胞繁衍
	 * @param cel
	 */
	public  void cellEvolution(Cell cel){
		int row = 0;
		int col = 0;
		int cellNum = 0;
		for(int i=0;i<dir.length;i++){
			row = cel.row + dir[i][0];
			col = cel.col + dir[i][1];
			if(row>-1 && row<ROW_NUM && col>-1 && col<COL_NUM){
				if(flagArr[row][col]==1){
					cellNum++;
				}else{
					//此位置周围有三个存活的细胞且此位置暂无生成的新细胞，则此位置应产生一个细胞
					if(computeRound(row,col) == 3 && flagArr[row][col] != 2){
						Cell newCell = new Cell(row, col,true);
						//标志此位置新生成一个细胞
						flagArr[row][col] = 2;
						bornCellList.add(newCell);
					}
				}  
			}
		}
		//细胞死亡判断条件
		if(cellNum < 2 || cellNum > 3){
			deadCellList.add(cel);  
		}
	}
	
	/**
	 * 计算一个细胞周围有多少个存活的细胞
	 * @param row 
	 * @param col
	 * @return
	 * 
	 */
	public  int computeRound(int row,int col){
		int cellNum = 0;
		int r=0;
		int cl=0;
		for(int i = 0;i<dir.length;i++){
			r = row + dir[i][0];
			cl =  col + dir[i][1];
			if(r > -1 && r < ROW_NUM && cl > -1 && cl < COL_NUM){
				if(flagArr[r][cl]==1){
					cellNum++;
				}
			}
		}
		return cellNum;
	}
	
	public void run() {
		while(true){
			try {
				Thread.sleep(REFRESH_INTERVALS);
				//重新绘制
				repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//开始下一代繁衍
			evolution();
		}
	}
}