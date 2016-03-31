public class BigOldDesert {

	public static final double INFINITY = Double.POSITIVE_INFINITY;

	public static final char PLAIN = '_';
	public static final char CANNOE = 'c';
	public static final char PLANK = 'p';
	public static final char BALLON = 'b';
	public static final char LAKE = 'L';
	public static final char PIT = 'P';
	public static final char MOUNTAIN = 'M';

	public static final int NOTHING = 0;
	public static final int CANNOE_INT = 1;
	public static final int PLANK_INT = 2;
	public static final int BALLON_INT = 3;

	private String [] problem;
	private int [] [] table;

	public BigOldDesert (String [] problem){
		this.problem = problem;
		table = new int [problem.length][4];
	}

	public double solveRecursively(double carrying, int currentPos) {
		if(currentPos<problem.length){
			if(problem[currentPos].charAt(0)==PLAIN){
				if(carrying==NOTHING){
					currentPos++;
					return 1+solveRecursively(NOTHING, currentPos);
				}
				else{
					currentPos++;
					return Math.min((2+solveRecursively(NOTHING, currentPos)), (3+solveRecursively(carrying, currentPos)));
				}	
			}
			if(problem[currentPos].charAt(0)==CANNOE){
				if(carrying==NOTHING){
					currentPos++;
					return Math.min((1 + solveRecursively(NOTHING, currentPos)), (2 + solveRecursively(CANNOE_INT, currentPos)));
				}
				else{
					currentPos++;
					return Math.min(3 + solveRecursively(carrying, currentPos), Math.min(3 + solveRecursively(CANNOE_INT, currentPos), 2 + solveRecursively(NOTHING, currentPos)));
				}
			}
			if(problem[currentPos].charAt(0)==PLANK){
				if(carrying==NOTHING){
					currentPos++;
					return Math.min(1 + solveRecursively(NOTHING, currentPos), 2 + solveRecursively(PLANK_INT, currentPos));
				}
				else{
					currentPos++;
					return Math.min(3 + solveRecursively(carrying, currentPos), Math.min(3 + solveRecursively(PLANK_INT, currentPos), 2 + solveRecursively(NOTHING, currentPos)));
				}
			}
			if(problem[currentPos].charAt(0)==BALLON){
				if(carrying==NOTHING){
					currentPos++;
					return Math.min(1 + solveRecursively(NOTHING, currentPos), 2 + solveRecursively(BALLON_INT, currentPos));
				}
				else{
					currentPos++;
					return Math.min(3 + solveRecursively(carrying, currentPos), Math.min(3 + solveRecursively(BALLON_INT, currentPos), 2 + solveRecursively(NOTHING, currentPos)));
				}
			}
			if(problem[currentPos].charAt(0)==LAKE){
				if(carrying==NOTHING)
					return INFINITY;
				else{
					if(carrying==PLANK_INT)
						return 5 + solveRecursively(PLANK_INT, ++currentPos);
					if(carrying==CANNOE_INT){
						return 4 + solveRecursively(CANNOE_INT, ++currentPos);
					}
					if(carrying==BALLON_INT)
						return 6 + solveRecursively(BALLON_INT, ++currentPos);
				}
			}
			if(problem[currentPos].charAt(0)==PIT){
				if(carrying==NOTHING || carrying == CANNOE_INT)
					return INFINITY;
				else{
					if(carrying==PLANK_INT)
						return 5 + solveRecursively(PLANK_INT, ++currentPos);
					if(carrying==BALLON_INT)
						return 6 + solveRecursively(BALLON_INT, ++currentPos);
				}
			}
			if(problem[currentPos].charAt(0)==MOUNTAIN){
				if(carrying==NOTHING || carrying==PLANK_INT || carrying== CANNOE_INT)
					return INFINITY;
				else{
					if(carrying==BALLON_INT)
						return 6 + solveRecursively(BALLON_INT, ++currentPos);
				}
			}
		}
		return 0;
	}

	public int resolveIterative () {
		for (int i=0; i<problem.length;i++){
			fillLine(i);
			//printLine(i);
		}
		//printTable();
		return getMinimumOffLine(problem.length-1);
	}

	private void printLine(int i) {
		for(int z=0;z<4;z++)
			System.out.print(table[i][z] + " | ");
		System.out.println();

	}

	public void fillLine (int currentPos) {
		switch(problem[currentPos].charAt(0)){
		case PLAIN:
			handlePlain(currentPos);
			break;
		case CANNOE:
			handleCannoe(currentPos);
			break;
		case PLANK:
			handlePlank(currentPos);
			break;
		case BALLON:
			handleBallon(currentPos);
			break;
		case LAKE:
			handleLake(currentPos);
			break;
		case PIT:
			handlePit(currentPos);
			break;
		case MOUNTAIN:
			handleMountain(currentPos);
			break;
		default:
			System.out.println("ERROR");
			break;
		}
	}

	private void handleMountain(int currentPos) {
		for(int z=0; z<4; z++){
			switch(z){
			case NOTHING:
				table[currentPos][z]=0;
				break;
			case CANNOE_INT:
				table[currentPos][z]=0;
				break;
			case PLANK_INT:
				table[currentPos][z]=0;
				break;
			case BALLON_INT:
				if(table[currentPos-1][z]!=0)
					table[currentPos][z]=table[currentPos-1][z] + 6;
				else{
					table[currentPos][z]=getMinimumOffLine(currentPos-1)+6;
				}
				break;
			}
		}

	}

	private void handlePit(int currentPos) {
		for(int z=0; z<4; z++){
			switch(z){
			case NOTHING:
				table[currentPos][z]=0;
				break;
			case CANNOE_INT:
				table[currentPos][z]=0;
				break;
			case PLANK_INT:
				if(table[currentPos-1][z]!=0)
					table[currentPos][z]=table[currentPos-1][z] + 5;
				else{
					table[currentPos][z]=0;
				}
				break;
			case BALLON_INT:
				if(table[currentPos-1][z]!=0)
					table[currentPos][z]=table[currentPos-1][z] + 6;
				else{
					table[currentPos][z]=0;
				}
				break;
			}
		}

	}


	private void handleLake(int currentPos) {
		for(int z=0; z<4; z++){
			switch(z){
			case NOTHING:
				table[currentPos][z]=0;
				break;
			case CANNOE_INT:
				if(table[currentPos-1][z]!=0)
					table[currentPos][z]=table[currentPos-1][z] + 4;
				else{
					table[currentPos][z]=0;
				}
				break;
			case PLANK_INT:
				if(table[currentPos-1][z]!=0)
					table[currentPos][z]=table[currentPos-1][z] + 5;
				else{
					table[currentPos][z]=0;
				}
				break;
			case BALLON_INT:
				if(table[currentPos-1][z]!=0)
					table[currentPos][z]=table[currentPos-1][z] + 6;
				else{
					table[currentPos][z]=0;
				}
				break;
			}
		}

	}

	private void handleBallon(int currentPos) {
		for(int z=0;z<4;z++){
			switch(z){
			case NOTHING:
				if(currentPos==0)
					table[currentPos][z]=1;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 1;
					else{
						table[currentPos][z]=getMinimumOffLine(currentPos-1)+2;
					}
				}
				break;
			case CANNOE_INT:
				if(currentPos==0)
					table[currentPos][z]=0;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 3;
					else{
						table[currentPos][z]=0;
					}
				}
				break;
			case PLANK_INT:
				if(currentPos==0)
					table[currentPos][z]=0;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 3;
					else{
						table[currentPos][z]=0;
					}
				}
				break;
			case BALLON_INT:
				if(currentPos==0)
					table[currentPos][z]=2;
				else{
					if(table[currentPos-1][z]!=0)
						if(table[currentPos-1][0]!=0)
							table[currentPos][z]=table[currentPos-1] [0] + 2;
						else
							table[currentPos][z]=getMinimumOffLine(currentPos-1) + 3;
					else{
						if(hadArtifacts(currentPos))
							table[currentPos][z]=getMinimumOffLine(currentPos-1)+3;
						else
							table[currentPos][z]=getMinimumOffLine(currentPos-1)+2;
					}
				}
				break;
			}
		}

	}

	private void handlePlank(int currentPos) {
		for(int z=0;z<4;z++){
			switch(z){
			case NOTHING:
				if(currentPos==0)
					table[currentPos][z]=1;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 1;
					else{
						table[currentPos][z]=getMinimumOffLine(currentPos-1)+2;
					}
				}
				break;
			case CANNOE_INT:
				if(currentPos==0)
					table[currentPos][z]=0;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 3;
					else{
						table[currentPos][z]=0;
					}
				}
				break;
			case PLANK_INT:
				if(currentPos==0)
					table[currentPos][z]=2;
				else{
					if(table[currentPos-1][z]!=0)
						if(table[currentPos-1][0]!=0)
							table[currentPos][z]=table[currentPos-1] [0] + 2;
						else
							table[currentPos][z]=table[currentPos-1][z] + 3;
					else{
						if(hadArtifacts(currentPos))
							table[currentPos][z]=getMinimumOffLine(currentPos-1)+3;
						else
							table[currentPos][z]=getMinimumOffLine(currentPos-1)+2;
					}
				}
				break;
			case BALLON_INT:
				if(currentPos==0)
					table[currentPos][z]=0;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 3;
					else{
						table[currentPos][z]=0;
					}
				}
				break;
			}
		}

	}

	private void handleCannoe(int currentPos) {
		for(int z=0;z<4;z++){
			switch(z){
			case NOTHING:
				if(currentPos==0)
					table[currentPos][z]=1;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 1;
					else{
						table[currentPos][z]=getMinimumOffLine(currentPos-1)+2;
					}
				}
				break;
			case CANNOE_INT:
				if(currentPos==0)
					table[currentPos][z]=2;
				else{
					if(table[currentPos-1][z]!=0)
						if(table[currentPos-1][0]!=0)
							table[currentPos][z]=table[currentPos-1] [0] + 2;
						else
							table[currentPos][z]=table[currentPos-1][z] + 3;
					else{
						if(hadArtifacts(currentPos))
							table[currentPos][z]=getMinimumOffLine(currentPos-1)+3;
						else
							table[currentPos][z]=getMinimumOffLine(currentPos-1)+2;
					}
				}
				break;
			case PLANK_INT:
				if(currentPos==0)
					table[currentPos][z]=0;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 3;
					else{
						table[currentPos][z]=0;
					}
				}
				break;
			case BALLON_INT:
				if(currentPos==0)
					table[currentPos][z]=0;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 3;
					else{
						table[currentPos][z]=0;
					}
				}
				break;
			}
		}

	}

	private int getMinimumOffLine(int currentPos) {
		int x=Integer.MAX_VALUE;
		for(int i=0; i<4;i++){
			if(table[currentPos][i]<x && table[currentPos][i]!=0)
				x=table[currentPos][i];
		}
		return x;
	}

	public void handlePlain(int currentPos) {
		for(int z=0; z<4;z++){
			if(z==0){
				if(currentPos==0)
					table[currentPos][z]=1;
				else{
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z]+1;
					else
						table[currentPos][z]=getMinimumOffLine(currentPos-1)+2;
				}
			}else{
				if(currentPos==0)
					table[currentPos][z]=0;
				else{
					if(table[currentPos-1][z]!=0)
						if(currentPos!=problem.length-1)
							table[currentPos][z]=table[currentPos-1][z]+3;
						else
							table[currentPos][z]=table[currentPos-1][z]+2;
					else
						table[currentPos][z]=0;
				}
			}
		}
	}

	private boolean hadArtifacts(int currentPos) {
		for(int z=1;z<4;z++){
			if(table[currentPos-1][z]!=0 && table[currentPos-1][0]==0)
				return true;
		}
		return false;
	}

	public void printTable() {
		for(int i = 0; i<problem.length;i++){
			for(int z = 0;z<4; z++){
				System.out.print(table[i][z] + " | ");
			}
			System.out.println();
		}
	}
}
