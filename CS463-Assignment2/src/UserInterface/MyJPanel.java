package UserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import SignificantObjects.ObjectDetails;
import UserInterface.UserInterface.ColorMode;

public class MyJPanel extends JPanel implements MouseListener{

	private int[][] COLORA;
	private int[][] GREYA;

	
	private UserInterface registeredUI;
	private int colorMode;
	private ObjectDetails clickedObject;
	
	public MyJPanel(UserInterface ui) {
		registeredUI = ui;
		
		addMouseListener(this);
	}
	
	
	public void setMode(UserInterface.ColorMode m) {
		if (m == UserInterface.ColorMode.COLOR)
			colorMode = 1;
		if (m == UserInterface.ColorMode.GRAYSCALE)
			colorMode = 2;
	}
	

	public void setCOLORA(int[][]COLORA) {
		this.COLORA = COLORA;
		this.setPreferredSize(new Dimension(COLORA[0].length / 3, COLORA.length));   //color
	}
		
		
		
	public void setGREYA(int[][]GREYA) {
		
		this.GREYA = GREYA;
		this.setPreferredSize(new Dimension(GREYA[0].length, GREYA.length));		//greyscale
		
		}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		 
		if (colorMode == 1) {									//color
			for (int i = 0; i < COLORA.length; i++) {
				for (int j = 0; j < COLORA[0].length; j = j + 3 ) {
					g.setColor(new Color(COLORA[i][j], COLORA[i][j+1], COLORA[i][j+2]));
					g.drawLine(j /3, i, j/ 3, i);
				}
			}
		}
		
		else if (colorMode == 2) {
			
																//grayscale
				for (int i = 0; i < GREYA[0].length; i++) {
					for (int j = 0; j < GREYA.length; j++) {
						g.setColor(new Color(GREYA[j][i], GREYA[j][i], GREYA[j][i]));
						g.drawLine(i, j, i, j);
					}
				}
			}
		}
	
	
	
		
	
	

	
	@Override
	public void mouseClicked(MouseEvent e) {
		ObjectDetails[] list;
		list = registeredUI.getObjectDetails();
		Point clickedP = e.getPoint();
		for (int i = 0; i < list.length; i++) {
			if ( (clickedP.x > list[i].getOrigin().x) && (clickedP.x < list[i].getOrigin().x + list[i].getPixelMap()[0].length - 1 ))
				if ( (clickedP.y > list[i].getOrigin().y) && (clickedP.y < list[i].getOrigin().y + list[i].getPixelMap().length - 1)) {
					if (list[i].getPixelMap()[clickedP.y - list[i].getOrigin().y][clickedP.x - list[i].getOrigin().x] != 0) {
						clickedObject = list[i];
						System.out.println(clickedObject.toString());
						this.repaint();
						break;
					}
				}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
