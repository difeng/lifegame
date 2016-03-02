package com.difeng.lifegame;
/**
 * 
 * 细胞类
 * @author difeng
 *
 */
public class Cell {
      int  row;
      int  col;
      boolean islive;
	public Cell(int row, int col, boolean islive) {
		super();
		this.row = row;
		this.col = col;
		this.islive = islive;
	}     
}
