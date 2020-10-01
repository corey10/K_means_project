

package UserInterface;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import EdgeDetection.EdgeDetection;
import HistogramStretching.HistogramStretching;
import ImageEnhancements.ImageSmoothing;
import ImageEnhancements.MedianFilter;
import ImageFeatures.ImageFeatures;
import ImageSegmentation.kMeansPGM;
import ImageSegmentation.kMeansPPM;
import NoiseFilter.NoiseFilter;
import ObjectGrouping.ObjectGrouping;
import ObjectLabelling.ObjectLabelling;
import PowerLawTransformation.powerLawTransformation;
import SignificantObjects.ObjectDetails;


public class UserInterface {
	
	public enum ColorMode {GRAYSCALE, BINARY, LABELS, COLOR, GROUPS};
	
	private ColorMode toDisplay;
	
	private JFrame mainWindow;
	private JPanel mainPanel;
	private MyJPanel drawingBoard;
	private JPanel fileLoadArea, topLineArea, holder2, holder3, buttonArea, buttonArea2, kHolder;;
	private JButton submit, JButtonkMeansPPM, JButtonkMeansPGM, JButtonPGMLocation, JButtonGetK, JButtonPGMTexture;
	private JLabel JLinstructions, JGreyscale, JColor, JEnterK;
	private JTextField fileInputField, kInput;
	private String filename;
	private File imageFile;
	
	private int[][] convertedMatrix;	//the original matrix acquired from the image file
	private int[][] kMeansPGMLocation;        //after performing k means on grey images
	private int[][] kMeansMatrixPGM;        // after performing k means on color 
	private int[][] kMeansMatrixPPM; 
	private int[][] kMeansPGMTexture;
	 
	
	
	private ObjectDetails[] objectList;

	
	private int erode1count = 0;
	private int erode2count = 0; 
	private int dilatecount = 0;
	
	private Toolkit tk;
	private Dimension screensize;

	
	public UserInterface() {
		tk = Toolkit.getDefaultToolkit();
		screensize = tk.getScreenSize();
	
	
		
		
		JButtonkMeansPPM = new JButton("Using Color");  //color
		JButtonkMeansPGM = new JButton("Using Intensity Values");  //grey
		JButtonPGMLocation = new JButton("Using (X,Y) Coordinates"); //grey
		JButtonPGMTexture = new JButton("Using Texture");
		
		
		JGreyscale= new JLabel("Greyscale Images [.PGM]");
		JColor = new JLabel("Colored Images [.PPM]");
		JEnterK = new JLabel("Number Of Clusters [K]");
	
		kHolder = new JPanel();
		kHolder.setLayout(new FlowLayout());
		

		
		kInput = new JTextField(5);
		kInput.setPreferredSize(new Dimension(2, 20));
		kInput.setText("4");
		
		
		
	
		JButtonGetK = new JButton("Input K");
		JButtonGetK.setPreferredSize(new Dimension(75, 15));
		
		
		
		
		
		kHolder.add(JButtonGetK);
		kHolder.add(kInput);
		
		buttonArea = new JPanel();	
		buttonArea.setLayout(new GridLayout(15,1,1,5));	
		buttonArea.add(JColor);
		buttonArea.add(JButtonkMeansPPM);
		//buttonArea.add(JButtonPPMLocation);
		buttonArea.add(JGreyscale);
		buttonArea.add(JButtonkMeansPGM);
		buttonArea.add(JButtonPGMLocation);
		buttonArea.add(JButtonPGMTexture);
		buttonArea.add(JEnterK);
		buttonArea.add(kHolder);
		
		JButtonGetK.setEnabled(false);
		JButtonkMeansPPM.setEnabled(false);
		JButtonkMeansPGM.setEnabled(false);
		JButtonPGMLocation.setEnabled(false);
		JButtonPGMTexture.setEnabled(false);
		
		
		
		
		
		
		JButtonGetK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.parseInt(kInput.getText());
					//filteredMatrix2 = powerLawTransformation.powerLawTranformation(filteredMatrix2, 255, Double.parseDouble(gammaInput.getText()));
				//	toDisplay = ColorMode.GRAYSCALE;
				//	displayMatrix(filteredMatrix2, toDisplay);
					System.out.print(Integer.parseInt(kInput.getText()));
				}
				catch(NumberFormatException e1){
					kInput.setText("Error");
				}
			}
		});

		
		

		
		filename = new String();
		fileInputField = new JTextField(40);
		fileInputField.setPreferredSize(new Dimension(80, 35));
		fileInputField.setText("src\\Resources\\Images\\plane.ppm");
		
		
		submit = new JButton("OPEN");
		submit.setPreferredSize(new Dimension(80, 35));
		
	
	
	
		Hashtable<Integer, JLabel> labelTable = new Hashtable();
		labelTable.put(0, new JLabel("0"));
		labelTable.put(255, new JLabel("255"));
	
		
		
		
		

		JButtonkMeansPPM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				kMeansMatrixPPM = kMeansPPM.kMeansColored(kMeansMatrixPPM,Integer.parseInt(kInput.getText()));
				toDisplay = ColorMode.COLOR;
				displayMatrix(kMeansMatrixPPM, toDisplay);
			}
		});
	
		
	
		JButtonkMeansPGM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				kMeansMatrixPGM = kMeansPGM.kMeansGreyScale(kMeansMatrixPGM,Integer.parseInt(kInput.getText()));
				toDisplay = ColorMode.GRAYSCALE;                                        //change to color for color images
				displayMatrix(kMeansMatrixPGM, toDisplay);
			}
		});
	
		JButtonPGMLocation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				kMeansPGMLocation = kMeansPGM.kMeansPosition(kMeansPGMLocation,Integer.parseInt(kInput.getText()));
				toDisplay = ColorMode.GRAYSCALE;                                        //change to color for color images
				displayMatrix(kMeansPGMLocation, toDisplay);
			}
		});
	
		JButtonPGMTexture.addActionListener(new ActionListener() {
			
			
				
				@Override
				public void actionPerformed(ActionEvent e) {
					kMeansPGMTexture = EdgeDetection.getEdges(kMeansPGMTexture);
					kMeansPGMTexture = HistogramStretching.stretch(kMeansPGMTexture, 255, 0);
				//	kMeansPGMTexture = HistogramStretching.inverse(kMeansPGMTexture, 255);
					kMeansPGMTexture = kMeansPGM.kMeansGreyScale(kMeansPGMTexture, Integer.parseInt(kInput.getText()));
					toDisplay = ColorMode.GRAYSCALE;
					displayMatrix(kMeansPGMTexture, toDisplay);
				}
		});
		
		
		
		
		
		JLinstructions = new JLabel("Please enter the image file path");
		
		mainWindow = new JFrame();
		mainWindow.setTitle("Image segmentation based on K-means");
		mainPanel = new JPanel();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
		mainWindow.add(mainPanel);
		mainWindow.setMaximumSize(new Dimension(screensize.width - 10, screensize.height - 10));
		mainWindow.setPreferredSize(new Dimension(750, 700));
		mainPanel.setLayout(new BorderLayout());
		mainWindow.pack();
		
		topLineArea = new JPanel();

		fileLoadArea = new JPanel();
		
		fileLoadArea.add(fileInputField);
		fileLoadArea.add(submit);
	
		topLineArea.add(JLinstructions);
		topLineArea.add(fileLoadArea);

		mainPanel.add(topLineArea, BorderLayout.PAGE_START);
		mainPanel.add(buttonArea, BorderLayout.LINE_END);
		
		


		
		
		

	
	
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!fileInputField.getText().equals("")) {
					if (imageFile != null)
						mainPanel.remove(drawingBoard);	//gets rid of any previous image
					JLinstructions.setText("...loading image...");
					filename = fileInputField.getText();
					imageFile = new File(filename);
					if (imageFile.exists() == true) {
						JLinstructions.setText("File loaded successfully!");
						convertImage(imageFile);
						JButtonkMeansPPM.setEnabled(true);
						JButtonkMeansPGM.setEnabled(true);
						JButtonGetK.setEnabled(true);
						JButtonPGMLocation.setEnabled(true);
						JButtonPGMTexture.setEnabled(true);
						
						
					}
					else {
						imageFile = null;
						JLinstructions.setText("File not found");
					}
				}
			}
		
		});
		
	}
	
	
	
	
	
	public int[][] convertImage(File f) {
		
		int heigth;
		int width;
		int ColorModeFromFile = 0;
		String fileColorMode = "P3";
		String fileGreyMode = "P2";
		String testMode;
		try {
			java.util.Scanner scanner = new Scanner(f);	
			testMode = scanner.nextLine();
			if(testMode.equals(fileColorMode)) {        //if true, we are loading a colored image 
			toDisplay = ColorMode.COLOR;
			width = scanner.nextInt();
			heigth = scanner.nextInt();
			ColorModeFromFile = scanner.nextInt();
			double ratiow = width / screensize.width + 1.5;
			double ratioh = heigth / screensize.height + 1.5;
			
			int ratioWRounded = (int) ratiow;
			int ratioHRounded = (int) ratioh;
			width = width*3;
			
			int biggestRatio;
			
			if (ratioWRounded > ratioHRounded)
				biggestRatio = ratioWRounded;
			else
				biggestRatio = ratioHRounded;
			
			convertedMatrix = new int[heigth / biggestRatio][width / biggestRatio];
			kMeansPGMTexture = new int[heigth / biggestRatio][width / biggestRatio];
			kMeansPGMLocation = new int[heigth / biggestRatio][width / biggestRatio];
			kMeansMatrixPGM = new int[heigth / biggestRatio][width / biggestRatio];
			kMeansMatrixPPM = new int[heigth / biggestRatio][width / biggestRatio];
		
			for (int i = 0; i < convertedMatrix.length; i ++) {
				for (int j = 0; j < convertedMatrix[0].length; j ++) {
					if (scanner.hasNext()) {
						convertedMatrix[i][j] = scanner.nextInt();
					
					}
					
					else {
						JLinstructions.setText("This image file is not of a supported format");
						break;
					}
				}
			}
			mainWindow.setMinimumSize(new Dimension (750, 700));
			mainWindow.setMaximumSize(new Dimension(screensize.width - 50, screensize.height - 50));
			mainWindow.setPreferredSize(new Dimension(convertedMatrix[0].length/3 + 100 + buttonArea.getSize().width, screensize.height - 500));
			scanner.close();
			
			
			}else if(testMode.equals(fileGreyMode)) {   //else we are loading a GREYSCALE Image
				
				
				toDisplay = ColorMode.GRAYSCALE;
				
				width = scanner.nextInt();
				heigth = scanner.nextInt();
				ColorModeFromFile = scanner.nextInt();
				
				double ratiow = width / screensize.width + 1.5;
				double ratioh = heigth / screensize.height + 1.5;
				
				int ratioWRounded = (int) ratiow;
				int ratioHRounded = (int) ratioh;
				
				int biggestRatio;
				
				if (ratioWRounded > ratioHRounded)
					biggestRatio = ratioWRounded;
				else
					biggestRatio = ratioHRounded;
				
				
				convertedMatrix = new int[heigth / biggestRatio][width / biggestRatio];
				kMeansPGMTexture = new int[heigth / biggestRatio][width / biggestRatio];
				kMeansPGMLocation = new int[heigth / biggestRatio][width / biggestRatio];
				kMeansMatrixPGM = new int[heigth / biggestRatio][width / biggestRatio];
				kMeansMatrixPPM = new int[heigth / biggestRatio][width / biggestRatio];
				
				
				for (int i = 0; i < convertedMatrix.length; i ++) {
					for (int j = 0; j < convertedMatrix[0].length; j ++) {
						if (scanner.hasNext()) {
							convertedMatrix[i][j] = scanner.nextInt();
						}
						else {
							JLinstructions.setText("This image file is not of a supported format");
							break;
						}
					}
				}
				mainWindow.setMinimumSize(new Dimension (750, 700));
				mainWindow.setMaximumSize(new Dimension(screensize.width - 50, screensize.height - 50));
				mainWindow.setPreferredSize(new Dimension(convertedMatrix[0].length + 100 + buttonArea.getSize().width, screensize.height - 500));
				scanner.close();
			}		
				
		}

		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < convertedMatrix.length; i++) {
			for (int j = 0; j < convertedMatrix[0].length; j++) {
				kMeansMatrixPGM[i][j] = convertedMatrix[i][j];
				kMeansMatrixPPM[i][j] = convertedMatrix[i][j];
				kMeansPGMTexture[i][j] = convertedMatrix[i][j];
				kMeansPGMLocation[i][j] = convertedMatrix[i][j];
		}
	}
		
		
		
	
		mainWindow.pack();
		displayMatrix(convertedMatrix, toDisplay);
		return convertedMatrix;
	}
	
	
	
	
	

	

	
	public void displayMatrix(int[][]A, ColorMode mode) {
		if (drawingBoard != null) {
			mainPanel.remove(drawingBoard);
		}
		drawingBoard = new MyJPanel(this);
		mainPanel.add(drawingBoard, BorderLayout.CENTER);
		drawingBoard.setCOLORA(A);
		drawingBoard.setGREYA(A);
		
		drawingBoard.setMode(mode);
		mainWindow.repaint();
		mainWindow.pack();
	}
	
	public ObjectDetails[] getObjectDetails() {
		return objectList;
	}
}
