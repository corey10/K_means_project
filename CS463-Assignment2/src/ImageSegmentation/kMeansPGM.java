package ImageSegmentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import java.util.List;


//GreyScale Image Segmentation
public class kMeansPGM {
	
	
	public static int[][] kMeansGreyScale(int[][] originalImage, int kInput){   
	
			
		List<Vector> dataset = new Vector<Vector>();
		double instance = 0;
		double assignedK = 0;
		
		
		for (double i=0; i<originalImage.length; i++) {
			for (double j=0; j<originalImage[0].length; j++) {
				Vector<Double> pixelData = new Vector<Double>();
				
				pixelData.add(instance);
				pixelData.add((double) originalImage[(int) i][(int) j]);
				pixelData.add(i);
				pixelData.add(j);
				pixelData.add(assignedK);
				
				instance++;
				
				dataset.add(pixelData);
			}
		}
		
		List<Vector> centroidData = new Vector<Vector>();
		
		int centroidNumber = 0;
		centroidNumber = kInput;
		
		
		for (double i=0; i<centroidNumber; i++) {
			Vector<Double> centroid = new Vector<Double>();
			double x = (int)(Math.random()* originalImage.length);
			double y = (int) (Math.random()* originalImage[0].length);
			double z = (int) (Math.random()* 100);
			centroid.add(i);
			centroid.add(x);
			centroid.add(y);
			centroid.add(z);
			
			
			centroidData.add(centroid);
		}

		boolean centroidHasNotUpdated = false;
		
		while(!centroidHasNotUpdated) {
			assignClusterIntensity(dataset,centroidData, centroidNumber);
			List<Vector> temporaryDataset = new Vector<Vector>(centroidData);
			updateCentroidIntensity(dataset, centroidData, centroidNumber);
			assignClusterIntensity(dataset,centroidData, centroidNumber);
			centroidHasNotUpdated = centroidData.equals(temporaryDataset);
		}
		
		
//		
//		int count0 =0;
//		int count1 =0;
//		int count2 =0;
//		for(int i=0; i<dataset.size();i++) {
//			if((double)dataset.get(i).get(4) == 0) {
//				count0++;
//			}
//			if((double)dataset.get(i).get(4) == 1) {
//				count1++;
//			}
//			if((double)dataset.get(i).get(4) == 2) {
//				count2++;
//			}
//		}
//		
//		System.out.println(count0 +" "+ count1 + " "+ count2);
//			
		int[][] kMeanedImage = new int[originalImage.length][originalImage[0].length];
		int multiplier = 256/centroidNumber;
		int count = 0;
		for (int i=0; i<originalImage.length; i++) {
			for (int j=0; j<originalImage[0].length; j++) {
				kMeanedImage[i][j] =(int)((double) dataset.get(count).get(4))*multiplier;
				count++;
			}
		}
		
		
		
		return kMeanedImage;
		

	}
	
	
	public static int[][] kMeansPosition(int[][] originalImage, int kInput){

		
		List<Vector> dataset = new Vector<Vector>();
		double instance = 0;
		double assignedK = 0;
		
		
		for (double i=0; i<originalImage.length; i++) {
			for (double j=0; j<originalImage[0].length; j++) {
				Vector<Double> pixelData = new Vector<Double>();
				
				pixelData.add(instance);
				pixelData.add((double) originalImage[(int) i][(int) j]);
				pixelData.add(i);
				pixelData.add(j);
				pixelData.add(assignedK);
				
				instance++;
				
				dataset.add(pixelData);
			}
		}
		
		List<Vector> centroidData = new Vector<Vector>();
		
		int centroidNumber = 0;
		centroidNumber = kInput;
				
		
		
		for (double i=0; i<centroidNumber; i++) {
			Vector<Double> centroid = new Vector<Double>();
			double x = (int)(Math.random()* originalImage.length);
			double y = (int) (Math.random()* originalImage[0].length);
			double z = (int) (Math.random()* 100);
			centroid.add(i);
			centroid.add(x);
			centroid.add(y);
			centroid.add(z);
			
			
			centroidData.add(centroid);
		}

		boolean centroidHasNotUpdated = false;
		List<Vector> temporaryDatasetList = new Vector(centroidData);
			while(centroidHasNotUpdated == false) {
				assignClustersPosition(dataset, centroidData, centroidNumber);
				temporaryDatasetList = centroidData;
				updateCentroidPosition(dataset, centroidData, centroidNumber);
				assignClustersPosition(dataset, centroidData, centroidNumber);
				centroidHasNotUpdated = centroidData.equals(temporaryDatasetList);
				
			}

		int[][] kMeanedImage = new int[originalImage.length][originalImage[0].length];
		
		int multiplier = 256/centroidNumber;
		int count = 0;
		for (int i=0; i<originalImage.length; i++) {
			for (int j=0; j<originalImage[0].length; j++) {
				kMeanedImage[i][j] =(int)((double) dataset.get(count).get(4))*multiplier;
				count++;
			}
		}
		
		return kMeanedImage;
		
	}
	
	
	
	public static void assignClusterIntensity(List<Vector> dataset, List<Vector> centroidData, int centroidNumber) {
		double distance = 0;
		for(int i=0; i<dataset.size(); i++) {
			double minDistance = 1000000;
			for (int j=0; j<centroidNumber; j++) {
				
				double dataGScaleIntensity = (double) dataset.get(i).get(1);
				double centroidGScaleIntensity = (double) centroidData.get(j).get(3);
				
				distance = dataGScaleIntensity - centroidGScaleIntensity;
				if(distance < 0) {
					distance = distance * -1;
				}
				if(distance<minDistance) {
					minDistance = distance;
					dataset.get(i).set(4, centroidData.get(j).get(0));
				}
			}
		}
	}
	
	public static void updateCentroidIntensity(List<Vector> dataset, List<Vector> centroidData, int centroidNumber) {
		for (int j = 0; j < centroidData.size(); j++) {
			double intensitySum = 0;
			double kmeansCount = 0;
			for(int i = 0; i< dataset.size(); i++) {
				if((double)dataset.get(i).get(4) == j) {
					intensitySum += (double)dataset.get(i).get(1);
					kmeansCount++;
				}
				
			}
			double intensityCenterOfMass = intensitySum / kmeansCount;

			centroidData.get(j).set(3, intensityCenterOfMass);
		}
	}
	
	
	
	
	
	
	public static void assignClustersPosition(List<Vector> dataset, List<Vector> centroidData, int centroidNumber) {
		double distance = 0;
		
		for (int i=0; i<dataset.size(); i++) {
			double minDistance = 1000000;
			for (int j=0; j<centroidNumber; j++) {
				
				double dataX = (double) dataset.get(i).get(2);
				double dataY = (double) dataset.get(i).get(3);
				double centroidX = (double) centroidData.get(j).get(1);
				double centroidY = (double) centroidData.get(j).get(2);
				
				distance = Math.sqrt((Math.pow(dataX - centroidX, 2)) + (Math.pow(dataY - centroidY, 2)));
				if(distance < minDistance) {
					minDistance = distance;
					dataset.get(i).set(4, (centroidData.get(j).get(0)));
				}
			}
		}
	}
	
	public static void updateCentroidPosition(List<Vector> dataset, List<Vector> centroidData, int centroidNumber) {
		for (int j = 0; j < centroidData.size(); j++) {
			double xKmeansSum = 0;
			double yKmeansSum = 0;
			double kmeansCount = 0;
			for(int i = 0; i< dataset.size(); i++) {
				if((double)dataset.get(i).get(4) == j) {
					xKmeansSum += (double)dataset.get(i).get(2);
					yKmeansSum += (double)dataset.get(i).get(3);
					kmeansCount++;
				}
				
			}
			double xCenterOfMass = xKmeansSum / kmeansCount;
			double yCenterOfMass = yKmeansSum / kmeansCount;
			centroidData.get(j).set(1, xCenterOfMass);
			centroidData.get(j).set(2, yCenterOfMass);
		}
	}
	

	

}
