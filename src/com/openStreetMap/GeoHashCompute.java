package com.openStreetMap;

public class GeoHashCompute {

	static String[] Base32 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	
	public GeoHashCompute() {
		// TODO Auto-generated constructor stub
	}
	
	public static String encode(double longitude,double latitude)
	{
		int code_length = 30;

		Double long_min = -180.0000000;
		Double long_max = 180.0000000;
		Double lat_min = -90.000000;
		Double lat_max =  90.000000;
		
/*
		double long_min = -1800000000;
		double long_max =  1800000000;
		double lat_min = -900000000;
		double lat_max = 900000000;
*/

		String GeoCoding = "";

		while(code_length>0)
		{
			code_length-=1;

			double long_middle = (long_min+long_max)/2;
			if(long_middle < longitude)
			{
				GeoCoding = GeoCoding+"1";
			    long_min = long_middle;
			}
			else
			{
				GeoCoding = GeoCoding+"0";
				long_max = long_middle;
			}

			double lat_middle = (lat_min+lat_max)/2;
			if(lat_middle < latitude)
			{
				GeoCoding = GeoCoding+"1";
		        lat_min = lat_middle;
			}
		    else
		    {
		    	GeoCoding = GeoCoding+"0";
		        lat_max = lat_middle;
			}

	    }

		String GeoHashCode = "";
		int i = 0;
		while (i < GeoCoding.length())
		{
			String temp = GeoCoding.substring(i, i + 5);
			int num = Integer.parseInt(temp, 2);
			GeoHashCode = GeoHashCode + Base32[num];		
			i = i + 5;
		}
		
		return GeoHashCode;
	}
	
	public static void main(String[] args) {
		//-900000000,1771882380
		//-900000000,1293757490
		System.out.println(GeoHashCompute.encode(1771882380,-900000000));
		System.out.println(GeoHashCompute.encode(1293757490,-900000000));
		System.out.println(GeoHashCompute.encode(1161966555,397563555));
	}
	
}
