package com.difeng.lifegame;

import java.awt.Dimension;
import java.awt.Toolkit;
/**
 * 配置参数类
 * @author difeng
 *
 */
public class Constant {
     static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
     public static int SCREEN_WIDTH = (int) dimension.getWidth();
     public static int SCREEN_HEIGHT = (int)dimension.getHeight();
     public static int ROW_NUM;
     public static int COL_NUM;
     public static final int CELL_WIDTH = 20;
     public static final int CELL_HEIGHT = 20;
     public static final int REFRESH_INTERVALS = 100;
}