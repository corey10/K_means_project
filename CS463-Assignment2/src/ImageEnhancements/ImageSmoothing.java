package ImageEnhancements;

import java.util.Arrays;

public class ImageSmoothing {
	
	
	private static int[][] averagingMask = 	{ 	{1, 1, 1}, 
												{1, 1, 1}, 
												{1, 1, 1} 
											};
												
												
		public static int[][] averagingSmooth(int[][] originalImage){   //smooths image using averaging mask
				

	
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
	 
				 
	 }															//**Filling Border corner pixels**
		 				borderedImage[0][0] = borderedImage[1][1];  // top left corner
		 				borderedImage[0][columns+1] = borderedImage[1][columns]; //top right corner
		 				borderedImage[rows+1][0] = borderedImage[rows][1];  //bottom left corner
		 				borderedImage[rows+1][columns+1] = borderedImage[rows][columns]; //bottom right cornerr
		 	
		 		
		 				
		 				
		 																	//GETTING Average of each pixel... GIVES 0's as BORDER
		 				int[][] averagedBorderImage = new int[originalImage.length + 2][originalImage[0].length + 2];
		 				
		 				for (int i = 0; i<rows+2; i++) {                                
		 					 for (int j = 0; j<columns+2; j++) {
		 						 if ( i < rows && j <columns)
		 						 { 
		 						 averagedBorderImage[i+1][j+1] = (((borderedImage[i][j]*averagingMask[0][0])+
		 								(borderedImage[i][j+1]*averagingMask[0][1])+(borderedImage[i][j+2]*averagingMask[0][2])+
		 								(borderedImage[i+1][j]*averagingMask[1][0])+(borderedImage[i+2][j]*averagingMask[2][0])+
		 								(borderedImage[i+2][j+1]*averagingMask[2][1])+(borderedImage[i+2][j+2]*averagingMask[2][2])+
		 								(borderedImage[i+1][j+1]*averagingMask[1][1])+(borderedImage[i+1][j+2]*averagingMask[1][2])
		 							))/9;
		 											 
		 						 
		 					 }
		 				
		 			 }
		 		}	
		 				
		 				int[][] finalAveragedImage = new int[originalImage.length][originalImage[0].length];
		 		
				 for (int i = 0; i<rows; i++) {                                
					 for (int j = 0; j<columns; j++) {
						
						 finalAveragedImage[i][j] = averagedBorderImage[i+1][j+1];
					 }
			
					 }
			
			
				 
				 
				 
				 
			//	 for (int i = 0; i<rows; i++) {                                
			//		 for (int j = 0; j<columns; j++) {
						
			//		 System.out.print(finalAveragedImage[i][j]);
			//		 System.out.print(" ");
					 
				//	 }
				//	 System.out.println();
				//	 }
		
		return finalAveragedImage;
		
		}
	
	
	
	
	
	
	
	
	


}




