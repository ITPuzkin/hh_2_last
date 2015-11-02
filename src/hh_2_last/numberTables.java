package hh_2_last;

public class numberTables {
	
	public static int[][] table;
	
	public numberTables(int k){
		table = getMultTable(k);
	}
	
	public static int[][] getMultTable(int k){
		int[][] temp = new int[k][k];		
		for(int i=0;i<k;i++){
			for(int j=0;j<k;j++){
				temp[i][j] = i*j;
			}
		}		
		return temp;
	}
	
	public int get(int i,int j){
		return table[i][j];
	}

}
