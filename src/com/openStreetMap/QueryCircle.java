package com.openStreetMap;

public class QueryCircle {
	
	public int getCircleNum(int P_arg , double range) {

		int P = P_arg;//int P = 9;
	    Double Range = range ;// Double Range = 1000.0 ;

	    Double C_lati = 39940650.00;//40075016.00;
	    Double C_long = 39940650.00;
	    
	    int OldEven = 0;
	    if(P%2 != 0)
	    {
	    	OldEven=1;
	    }
	    
	    Double BianChang = C_lati/Math.pow(2, 5*P/2+OldEven);
	    int n = ComputeQueryCircle(Range, BianChang);

	    System.out.println("\n[INFO][QueryCircle]\t"+"Range:"+Range+"\t"+ "P:"+P +"\t"+ "CircleNum:"+n);
	    System.out.println();
	    return n;
	}
	
	public  int ComputeQueryCircle(Double RangeDis, Double BianChang) {
		// TODO Auto-generated constructor stub
		
		 Double res = RangeDis/BianChang;

		 if(RangeDis%BianChang == 0)
		 {
			return (new Double(res)).intValue();
		 }
		 else if(RangeDis%BianChang != 0)
		 {
			 return (new Double(res)).intValue()+1;
		 }
		 
		 return 0;
	}
}
