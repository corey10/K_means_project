package ImageEnhancements;

import java.util.Arrays;

public class MedianFilter {
	
	
	public static int[][] medianFiltering(int[][] originalImage){   
		

		
	int rows = 0;
	int columns = 0;
	int[][] borderedImage = new int[originalImage.length + 2][originalImage[0].length + 2];
	rows = originalImage.length;
	columns = originalImage[0].length;
	

	 for (int i = 0; i<rows; i++) {                                
		 for (int j = 0; j<columns; j++) {	
			 borderedImage[i+1][j+1] = originalImage[i][j];	    //filling in center pixels
		 }
	 }
	 
	 for (int i = 0; i<rows; i++) {                                
		 for (int j = 0; j<columns; j++) {	           //**Filling Border pixels**
		
			 borderedImage[i+1][columns+1] = borderedImage[i+1][columns]; //right border
			 borderedImage[i+1][0] = borderedImage[i+1][1];     //left border
			 borderedImage[0][j+1] = borderedImage[1][j+1];	    //filling in top border row
			 borderedImage[rows+1][j+1] = borderedImage[rows][j+1];  //bottom border
		 }
	 }															
	 		
	 		
	 														//**Filling Border corner pixels**
	 borderedImage[0][0] = borderedImage[1][1];  // top left corner
	 borderedImage[0][columns+1] = borderedImage[1][columns]; //top right corner
	 borderedImage[rows+1][0] = borderedImage[rows][1];  //bottom left corner
	 borderedImage[rows+1][columns+1] = borderedImage[rows][columns]; //bottom right corner
	 	
	 		
	 				
	 				
	 													//GETTING median of each pixel... GIVES 0's as BORDER
	 int[][] medianBorderImage = new int[originalImage.length + 2][originalImage[0].length + 2];
	 				
	 	for (int i = 0; i<rows+2; i++) {                                
	 		for (int j = 0; j<columns+2; j++) {
	 			if ( i < rows && j <columns){ 
	 							 
	 						
	 				int [] medianArray = new int [9];	//make array to hold 9 values from mask
	 				medianArray[0] = borderedImage[i][j]; //insert each value into the array
	 				medianArray[1] = borderedImage[i][j+1];
	 				medianArray[2] = borderedImage[i][j+2];
	 				medianArray[3] = borderedImage[i+1][j];
	 				medianArray[4] = borderedImage[i+2][j];
	 				medianArray[5] = borderedImage[i+2][j+1];
	 				medianArray[6] = borderedImage[i+2][j+2];
	 				medianArray[7] = borderedImage[i+1][j+1];
	 				medianArray[8] = borderedImage[i+1][j+2];
	 				Arrays.sort(medianArray); //sort the array
	 				medianBorderImage[i+1][j+1] = medianArray[4]; //insert median value into middle pixel of 3x3 area
	 			}	
	 		}
	 	}	
	 				
	 	int[][] finalMedianImage = new int[originalImage.length][originalImage[0].length];
	 		
		for (int i = 0; i<rows; i++) {                                
			for (int j = 0; j<columns; j++) {		
				finalMedianImage[i][j] = medianBorderImage[i+1][j+1]; //taking away 0's border
			}
		}
		
		 
	
	return finalMedianImage;
	
	}
}
