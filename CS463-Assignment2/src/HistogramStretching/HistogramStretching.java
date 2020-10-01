/**
 * @Author: Alexandre Charbonneau
 * Takes an array to convert with new bounds, in our application these will range from 0 to 255 most of the time
 * @return a new array with stretched values from the original
 */


package HistogramStretching;

public class HistogramStretching {
	public static int[][] stretch(int[][] A, int upperBound, int lowerBound){
		int minValue = A[0][0];
		int maxValue = A[0][0];
		double bMinusa = upperBound - lowerBound;

		int[][] B = new int[A.length][A[0].length];
		
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (A[i][j] < minValue)
					minValue = A[i][j];
				if (A[i][j] > maxValue)
					maxValue = A[i][j];	
			}
		}
		
		double dMinusc = maxValue - minValue;
				
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				B[i][j] = (int) ((A[i][j] - minValue) * (bMinusa / dMinusc) + lowerBound);
			}
		}
		
		return B;
	}
	
	public static int[][] inverse(int[][] A, int upperbound){
		int[][]B = new int[A.length][A[0].length];
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				B[i][j] = Math.abs(A[i][j] - upperbound);
			}
		}
		return B;
	}
}
