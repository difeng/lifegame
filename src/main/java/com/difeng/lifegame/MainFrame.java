package com.difeng.lifegame;
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
/**
 * 
 * @author difeng
 *
 */
public class MainFrame {
    public static void main(String[] args) {
		JFrame life = new JFrame();
		Canvas canvas = new Canvas();
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    life.getContentPane().add(canvas,BorderLayout.CENTER);
	    life.setUndecorated(true);
	    //设置全屏显示
	    gd.setFullScreenWindow(life);
	    //添加程序退出的事件监听
	    life.addKeyListener(new KeyAdapter() {
	    	@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					System.exit(0);
				}
			}
		});
	    new Thread(canvas).start();
    }
}
