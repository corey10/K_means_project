package PowerLawTransformation;


public class powerLawTransformation {
	
	
	
	public static int[][] powerLawTranformation(int[][] pixelArray, int constantC, double gamma){
		int[][] transformedImage = new int[pixelArray.length][pixelArray[0].length];
		
		for(int i= 0; i < pixelArray.length;i++) {
			for (int j = 0; j < pixelArray[0].length; j++) {
				transformedImage[i][j] = (int)(constantC * ((Math.pow(((double)pixelArray[i][j]/constantC), gamma))));//precedence is important to get value between
			}																								//0 and 1 and then multiply by constant 255
			
		}
		return transformedImage;
	}
	
	public static void main(String[] args) {
		
	}
}
