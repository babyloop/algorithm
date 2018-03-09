
public class PowerSet_ON_OFF {
	static int [] res=new int[5];
	public static void main(String[] args) {
		
		
		
//		int [] src = {1,2,3,4,5};//2의 5승개 부분집합
//		int [] src = {-3,2,4,-1,5};//2의 5승개 부분집합
		  /*           0 0 0 0 0
		   *           1 0 0 0 0
		   *           0 1 0 0 0 
		   *           1 1 0 0 0
		   *           ...
		   *           1 1 1 1 1 
		   */
		int len = 4;  // 응용 2의 16승 // 65,536개
		
		System.out.println(len);
		System.out.println(1<<len);
		
		for(int i=0; i<(1<<len); i++){//32개의 부분집합	
			
			
			for(int j=0; j<len; j++){//j=0 to 4
				
				if((i&(1<<j)) == (1<<j)){ // if(2 & (1<<1) == (1<<1) )
					System.out.println((i&(1<<j))+" "+(1<<j));
					
//					System.out.print(1+" ");// 2 
				} else {
//					System.out.print(0+" ");// 2	
				}
				
			}
			
			System.out.println();			
		}
		
		
	}
}