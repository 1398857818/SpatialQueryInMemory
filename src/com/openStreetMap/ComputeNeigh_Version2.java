package com.openStreetMap;

import java.util.HashSet;

public class ComputeNeigh_Version2 {

	public static HashSet<String> getNeighGeoHash(double lngitude, double latitude, int circleNum, int Precision) {

		HashSet<String> hs = new HashSet<String>();

		String original = GeoHashCompute.encode(lngitude,latitude).substring(0,Precision);
		hs.add(original);

		double unit =  0.0000001;

		double unit_horizontal = unit;
		double unit_vertical = unit;

		String temp_left = original;
		double lng_left = lngitude;
		double lat_left = latitude;
		int find_left = circleNum ;
		int ii = 0;
		double[] aver_h = new double[10];
		while(find_left>0)
		{
			lng_left=lng_left-unit_horizontal;
			String left_neighbour = GeoHashCompute.encode(lng_left,lat_left).substring(0,Precision);
			if(!left_neighbour.equals(temp_left))
			{
				temp_left = left_neighbour;
				hs.add(temp_left);
				find_left--;

				if(ii<10)
				{
					aver_h[ii] = lng_left;
					unit_horizontal = computAverage(aver_h,ii);
					ii++;
				}
				else if(ii==10)
					aver_h = null;


				String temp_up = temp_left;
				double lng_up = lng_left;
				double lat_up = lat_left;
				int find_up = circleNum ;
				double[] average_v = new double[10];
				int i = 0;
				while(find_up>0)
				{
					lat_up = lat_up + unit_vertical;
					String up_neighbour = GeoHashCompute.encode(lng_up,lat_up).substring(0,Precision);
					if(!up_neighbour.equals(temp_up))
					{
						temp_up = up_neighbour;
						hs.add(temp_up);
						find_up--;

						if(i<10)
						{
							average_v[i] = lat_up;
							unit_vertical = computAverage(average_v,i);
							i++;
						}
						else if(i==10)
							average_v = null;


					}
				}

				String temp_down = temp_left;
				double lng_down = lng_left;
				double lat_down = lat_left;
				int find_down = circleNum;
				while(find_down>0)
				{
					lat_down = lat_down - unit_vertical;
					String down_neighbour = GeoHashCompute.encode(lng_down,lat_down).substring(0,Precision);
					if(!down_neighbour.equals(temp_down))
					{
						temp_down = down_neighbour;
						hs.add(temp_down);
						find_down--;
					}
				}

			}
		}


		String temp_right = original;
		double lng_right = lngitude;
		double lat_right= latitude;
		int find_right = circleNum;
		unit_horizontal = unit;
		int iia = 0;
		double[] aver_h_r = new double[10];
		while(find_right>0)
		{
			lng_right=lng_right + unit;// unit_horizontal;
			String right_neighbour = GeoHashCompute.encode(lng_right,lat_right).substring(0,Precision);
			if(!right_neighbour.equals(temp_right))
			{
				temp_right = right_neighbour;
				hs.add(temp_right);
				find_right--;


				if(iia<10)
				{
					aver_h_r[iia] = lng_right;
					unit_horizontal = computAverage(aver_h_r,iia);
					iia++;
				}
				else if(iia==10)
					aver_h_r = null;

				String temp_up = temp_right;
				double lng_up = lng_right;
				double lat_up = lat_right;
				int find_up = circleNum ;
				while(find_up>0)
				{
					lat_up = lat_up + unit_vertical;
					String up_neighbour = GeoHashCompute.encode(lng_up,lat_up).substring(0,Precision);
					if(!up_neighbour.equals(temp_up))
					{
						temp_up = up_neighbour;
						hs.add(temp_up);
						find_up--;
					}
				}

				String temp_down = temp_right;
				double lng_down = lng_right;
				double lat_down = lat_right;
				int find_down = circleNum;
				while(find_down>0)
				{
					lat_down = lat_down - unit_vertical;
					String down_neighbour = GeoHashCompute.encode(lng_down,lat_down).substring(0,Precision);
					if(!down_neighbour.equals(temp_down))
					{
						temp_down = down_neighbour;
						hs.add(temp_down);
						find_down--;
					}
				}
			}
		}

		String temp_o_up = original;
		double lng_o_up = lngitude;
		double lat_o_up = latitude;
		int find_o_up = circleNum ;
		while(find_o_up>0)
		{
			lat_o_up = lat_o_up + unit_vertical;
			String o_up_neighbour = GeoHashCompute.encode(lng_o_up,lat_o_up).substring(0,Precision);
			if(!o_up_neighbour.equals(temp_o_up))
			{
				temp_o_up = o_up_neighbour;
				hs.add(temp_o_up);
				find_o_up--;
			}
		}

		String temp_o_down = original;
		double lng_o_down = lngitude;
		double lat_o_down = latitude;
		int find_o_down = circleNum ;
		while(find_o_down>0)
		{
			lat_o_down = lat_o_down - unit_vertical;
			String o_down_neighbour = GeoHashCompute.encode(lng_o_down,lat_o_down).substring(0,Precision);
			if(!o_down_neighbour.equals(temp_o_down))
			{
				temp_o_down = o_down_neighbour;
				hs.add(temp_o_down);
				find_o_down--;
			}
		}

		return hs;
	}


	private static double computAverage(double[] average, int i) {
		// TODO Auto-generated method stub
		if(i == 0)
			return 0.0000001;
		else
		{
			double sum = 0;
			for (int j = 1; j <= i; j++) {
				sum += average[j]-average[j-1];
			}
			return Math.abs(sum/i);
		}
	}


	public static void main(String[] args) {
		long st = System.currentTimeMillis();
		HashSet<String> hh = ComputeNeigh_Version2.getNeighGeoHash(177.1882380,-80.0000000,1,6);
		System.out.println(System.currentTimeMillis()-st+"ms");

		for(String s : hh)
		{
			System.out.println(s);
		}
		System.out.println("size"+hh.size());
	}
}
