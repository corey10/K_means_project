package ImageSegmentation;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


//Colored Image Segmentation
public class kMeansPPM {

	

	public static int[][] kMeansColored(int[][] originalImage, int kInput){   
	
		
		List<Vector> dataset = new Vector<Vector>();
		double instance = 0;
		double assignedKRed = 0;
		double assignedKGreen = 0;
		double assignedKBlue = 0;

		
		for (double i=0; i<originalImage.length; i++) {
			for (double j=0; j<originalImage[0].length; j=j+3) {
				Vector<Double> pixelData = new Vector<Double>();
				
				pixelData.add(instance);
				pixelData.add((double) originalImage[(int) i][(int) j]); //R
				pixelData.add((double) originalImage[(int) i][(int) j+1]);//G
				pixelData.add((double) originalImage[(int) i][(int) j+2]);//B
				pixelData.add(i);
				pixelData.add(j);
				pixelData.add(assignedKRed);
				pixelData.add(assignedKGreen);
				pixelData.add(assignedKBlue);

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
			double r = (int) (Math.random()* 100);
			double g = (int) (Math.random()* 100);
			double b = (int) (Math.random()* 100);

			
			centroid.add(i);
			centroid.add(x);
			centroid.add(y);
			centroid.add(r);
			centroid.add(g);
			centroid.add(b);
			
			
			
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
		
		

		int[][] kMeanedImage = new int[originalImage.length][originalImage[0].length];
		int multiplier = 256/centroidNumber;
		int count = 0;
		for (int i=0; i<originalImage.length; i++) {
			for (int j=0; j<originalImage[0].length; j=j+3) {
				kMeanedImage[i][j] =(int)((double) dataset.get(count).get(6))*multiplier;
				kMeanedImage[i][j+1] =(int)((double) dataset.get(count).get(7))*multiplier;
				kMeanedImage[i][j+2] =(int)((double) dataset.get(count).get(8))*multiplier;
				count++;
			}
		}
		
		
		
		return kMeanedImage;
		

	}
	
	

	
	
	
	public static void assignClusterIntensity(List<Vector> dataset, List<Vector> centroidData, int centroidNumber) {
		double distanceR = 0;
		double distanceG = 0;
		double distanceB = 0;
		for(int i=0; i<dataset.size(); i++) {
			double minDistanceR = 1000000;
			double minDistanceG = 1000000;
			double minDistanceB = 1000000;
			for (int j=0; j<centroidNumber; j++) {
				
				double dataRedIntensity = (double) dataset.get(i).get(1);
				double dataGreenIntensity = (double) dataset.get(i).get(2);
				double dataBlueIntensity = (double) dataset.get(i).get(3);
				double centroidRedIntensity = (double) centroidData.get(j).get(3);
				double centroidGreenIntensity = (double) centroidData.get(j).get(4);
				double centroidBlueIntensity = (double) centroidData.get(j).get(5);
				
				distanceR = dataRedIntensity - centroidRedIntensity;
				if(distanceR < 0) {
					distanceR = distanceR * -1;
				}
				if(distanceR<minDistanceR) {
					minDistanceR = distanceR;
					dataset.get(i).set(6, centroidData.get(j).get(0));
				}
				
				distanceG = dataGreenIntensity - centroidGreenIntensity;
				if(distanceG < 0) {
					distanceG = distanceG * -1;
				}
				if(distanceG<minDistanceG) {
					minDistanceG = distanceG;
					dataset.get(i).set(7, centroidData.get(j).get(0));
				}
				
				distanceB = dataBlueIntensity - centroidBlueIntensity;
				if(distanceB < 0) {
					distanceB = distanceB * -1;
				}
				if(distanceB<minDistanceB) {
					minDistanceB = distanceB;
					dataset.get(i).set(8, centroidData.get(j).get(0));
				}
			}
		}
	}
	
	public static void updateCentroidIntensity(List<Vector> dataset, List<Vector> centroidData, int centroidNumber) {
		for (int j = 0; j < centroidData.size(); j++) {
			double intensitySumR = 0;
			double kmeansCountR = 0;
			for(int i = 0; i< dataset.size(); i++) {
				if((double)dataset.get(i).get(6) == j) {
					intensitySumR += (double)dataset.get(i).get(1);
					kmeansCountR++;
				}
				
			}
			double intensityCenterOfMassR = intensitySumR / kmeansCountR;

			centroidData.get(j).set(3, intensityCenterOfMassR);
		}
		
		for (int j = 0; j < centroidData.size(); j++) {
			double intensitySumG = 0;
			double kmeansCountG = 0;
			for(int i = 0; i< dataset.size(); i++) {
				if((double)dataset.get(i).get(7) == j) {
					intensitySumG += (double)dataset.get(i).get(2);
					kmeansCountG++;
				}
				
			}
			double intensityCenterOfMassG = intensitySumG / kmeansCountG;

			centroidData.get(j).set(4, intensityCenterOfMassG);
		}
		
		
		for (int j = 0; j < centroidData.size(); j++) {
			double intensitySumB = 0;
			double kmeansCountB = 0;
			for(int i = 0; i< dataset.size(); i++) {
				if((double)dataset.get(i).get(8) == j) {
					intensitySumB += (double)dataset.get(i).get(3);
					kmeansCountB++;
				}
				
			}
			double intensityCenterOfMassB = intensitySumB / kmeansCountB;

			centroidData.get(j).set(5, intensityCenterOfMassB);
		}
		
	}
	
	
	

}
