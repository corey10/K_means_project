/**
 * @author Alexandre Charbonneau
 * Uses 2 basic filters called horizontalEdges and verticalEdges to get the magnitude of the edges
 * in a new 2D matrix. This matrix must then be convered to have values from 0 to 255 so it can be properly
 * displayed.
 * Note: a very high value must be mapped to a very low value to represent darkness
 */


package EdgeDetection;

public class EdgeDetection {
	private static int[][] horizontalEdges = 	{ 	{1, 1, 1}, 
													{0, 0, 0}, 
													{-1, -1, -1} 
												};
	private static int[][] verticalEdges = 		{ 	{1, 0, -1}, 
													{1, 0, -1}, 
													{1, 0, -1} 
												};
	
	public static int[][] getEdges(int[][] originalImage){
		
		int[][] finalImage = new int[originalImage.length][originalImage[0].length];
		int[][] paddedImage = new int[originalImage.length + 2][originalImage[0].length + 2];
		int[][] horizEdges = new int[originalImage.length][originalImage[0].length];
		int[][] vertiEdges = new int[originalImage.length][originalImage[0].length];
		
		for (int i = 0; i < originalImage.length; i++) {
			for  (int  j = 0; j < originalImage[0].length; j++) {
				paddedImage[i + 1][j + 1] = originalImage[i][j];
			}
		}
		
		for (int i = 0; i < originalImage.length; i++) {
			for (int j = 0; j < originalImage[0].length; j++) {
				horizEdges[i][j] = 
						(horizontalEdges[0][0] * paddedImage[i - 1 + 1][j - 1 + 1]) + (horizontalEdges[0][1] * paddedImage[i - 1 + 1][j + 1]) + (horizontalEdges[0][2] * paddedImage[i - 1 + 1][j + 1 + 1])+ 
						(horizontalEdges[1][0] * paddedImage[i + 1][j - 1 + 1])     + (horizontalEdges[1][1] * paddedImage[i + 1][j + 1])     + (horizontalEdges[1][2] * paddedImage[i + 1][j + 1 + 1]) +
						(horizontalEdges[2][0] * paddedImage[i + 1 + 1][j - 1 + 1]) + (horizontalEdges[2][1] * paddedImage[i + 1 + 1][j + 1]) + (horizontalEdges[2][2] * paddedImage[i + 1 + 1][j + 1 + 1]);
				vertiEdges[i][j] = 
						(verticalEdges[0][0] * paddedImage[i - 1 + 1][j - 1 + 1]) + (verticalEdges[0][1] * paddedImage[i - 1 + 1][j + 1]) + (verticalEdges[0][2] * paddedImage[i - 1 + 1][j + 1 + 1])+ 
						(verticalEdges[1][0] * paddedImage[i + 1][j - 1 + 1])     + (verticalEdges[1][1] * paddedImage[i + 1][j + 1])     + (verticalEdges[1][2] * paddedImage[i + 1][j + 1 + 1]) +
						(verticalEdges[2][0] * paddedImage[i + 1 + 1][j - 1 + 1]) + (verticalEdges[2][1] * paddedImage[i + 1 + 1][j + 1]) + (verticalEdges[2][2] * paddedImage[i + 1 + 1][j + 1 + 1]);
			}
		}
		for (int i = 0; i < originalImage.length; i++) {
			for (int j = 0; j < originalImage[0].length; j++) {
				finalImage[i][j] = (int)(Math.sqrt((Math.pow(horizEdges[i][j], 2) + Math.pow(vertiEdges[i][j], 2))));
			}
		}
		
		return finalImage;
	}
}
