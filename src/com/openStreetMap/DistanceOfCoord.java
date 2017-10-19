package com.openStreetMap;

public class DistanceOfCoord {

	/**
	 * @param args
	 * x1,y1),(x2,y2)
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Double distance = GetDistance(116.447862,39.915816,116.447869,39.931332);

		//Double distance = GetDistance(116.447862,1.915816,116.447869,1.931332);
		//Double distance1 = GetDistance(116.447862,80.915816,116.447869,80.931332);
		
		System.out.println(distance);
	}
	
	private static double EARTH_RADIUS = 6378137;
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}

	/**
	 * 
	 * @param lng1  A
	 * @param lat1	A
	 * @param lng2	B
	 * @param lat2	B
	 * @return distanceof A,B
	 */
	public static double GetDistance(double lng1, double lat1, double lng2, double lat2)
	{
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);

	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	  // s = Math.round(s * 10000) / 10000;
	   return s;
	}

}
