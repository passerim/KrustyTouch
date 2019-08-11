package testprogetto;

import java.awt.Point;
import java.util.ArrayList;

public class Sequencer {
	
	private ArrayList<Integer> pointsDirections = new ArrayList<Integer>();
	private Point[] points;
	
	public Sequencer(ArrayList<Point> inputPoints) {
		int i = 0;
		this.points = new Point[inputPoints.size()];
		for (Point p : inputPoints) {
			points[i] = p;
			i++;
		}
	}
	
	public Integer[] computeSequence() {
		Point lastP = null;
	    int cost = 0;
	    for (int p = 1; p<points.length; p++) {
	    	int dir = -1;
	    	double thetaDeg = computeAngle(points[p-1], points[p]);
    		if ((thetaDeg < 360. && thetaDeg > 360. - 45./2.) || (thetaDeg >= 0. && thetaDeg <  45./2.)) dir = 0;
    		for (int a = 1; a < 8; a++) {
    			if ( ((45.*a - 45./2.)) <= thetaDeg && thetaDeg  < ((45.*a + 45./2.)) ) dir = a;
    		}
    		if (dir == -1) {
    			throw new IllegalArgumentException("Direction not recognized!");
    		}
    		if (p == 1) {
    			pointsDirections.add(dir);
    			lastP = points[p];
    			continue;
    		}
    		if (dir == pointsDirections.get(pointsDirections.size()-1)) {
    			continue;
    		}
    		thetaDeg = computeAngle(lastP, points[p]);
    		int prevDir = pointsDirections.get(pointsDirections.size()-1);
    		if ((thetaDeg < 360. && thetaDeg > 360. - 45./2.) || (thetaDeg >= 0. && thetaDeg <  45./2.)) {
    			continue;
    		} else if ((45.*prevDir - 45./2.) <= thetaDeg && thetaDeg < (45.*prevDir + 45./2.)) {
				continue;
			} else {
				String currString = pointsDirections.toString();
				currString = currString.replace("[", "");
				currString = currString.replace("]", "");
				currString = currString.replace(", ", "");
				String nextString = new String();
				nextString = currString.concat(Integer.toString(dir));
				int gain = computeLevenshteinDistance(currString, nextString);
				cost += distMod8(prevDir, dir);
				if (cost > gain) {
					cost = 0;
					pointsDirections.add(dir);
					lastP = points[p];
				}
			}
	    }
		return listToArray(pointsDirections);
	}
	
	private Integer[] listToArray(ArrayList<Integer> directions) {
		Integer[] intArray = new Integer[directions.size()]; 
		int i = 0;
		for (Integer d : directions) {
			intArray[i] = d;
			i++;
		}
		return intArray;
	}

	private int distMod8(int a,int b)
	{
	    int diff = Math.abs( b - a );
	    return ( diff < 4 ) ? diff : 8 - diff;
	}
	
	private int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        
                                                                                 
        for (int i = 0; i <= lhs.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= rhs.length(); j++)                                 
            distance[0][j] = j;                                                  
                                                                                 
        for (int i = 1; i <= lhs.length(); i++)                                 
            for (int j = 1; j <= rhs.length(); j++)                             
                distance[i][j] = Math.min(                                        
                        distance[i - 1][j] + 1, Math.min(                                
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1)));
                                                                                 
        return distance[lhs.length()][rhs.length()];                           
    }
	
	private double computeAngle(Point prec, Point succ) {
		int deltaX = prec.x - succ.x;
		int deltaY = prec.y - succ.y;
		double thetaDeg = Math.toDegrees(Math.atan2(deltaY, deltaX));
		if (thetaDeg < 0) thetaDeg =   180 + (180+thetaDeg);
		return thetaDeg;
	}

}
