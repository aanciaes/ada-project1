public class BigOldDesert {

	public static final double INFINITY = Double.POSITIVE_INFINITY;

	//char constants representing the various types of paths and artifacts
	public static final char PLAIN = '_';
	public static final char CANNOE = 'c';
	public static final char PLANK = 'p';
	public static final char BALLON = 'b';
	public static final char LAKE = 'L';
	public static final char PIT = 'P';
	public static final char MOUNTAIN = 'M';

	//int constants representing the artifacts in integer
	public static final int NOTHING = 0;
	public static final int CANNOE_INT = 1;
	public static final int PLANK_INT = 2;
	public static final int BALLON_INT = 3;

	//contains the path
	private char [] problem;

	//Table to be filled when solving the problem with iterative way (not recursive)
	private int [] [] table;

	public BigOldDesert (char [] problem){
		this.problem = problem;
		table = new int [problem.length][4];
	}

	/**
	 * Solves the problem with recurvise method. 
	 * @param carrying Double representing the artifact carried
	 * @param currentPos Integer representing the current position on the map/desert
	 * @return Double representing minimum path to cross the map/desert 
	 */
	public double solveRecursively(double carrying, int currentPos) {
		if(currentPos<problem.length){
			if(problem[currentPos]==PLAIN){
				if(carrying==NOTHING){
					currentPos++;
					return 1+solveRecursively(NOTHING, currentPos);
				}
				else{
					currentPos++;
					return Math.min((2+solveRecursively(NOTHING, currentPos)), (3+solveRecursively(carrying, currentPos)));
				}	
			}
			if(problem[currentPos]==CANNOE){
				if(carrying==NOTHING){
					currentPos++;
					return Math.min((1 + solveRecursively(NOTHING, currentPos)), (2 + solveRecursively(CANNOE_INT, currentPos)));
				}
				else{
					currentPos++;
					return Math.min(3 + solveRecursively(carrying, currentPos), Math.min(3 + solveRecursively(CANNOE_INT, currentPos), 2 + solveRecursively(NOTHING, currentPos)));
				}
			}
			if(problem[currentPos]==PLANK){
				if(carrying==NOTHING){
					currentPos++;
					return Math.min(1 + solveRecursively(NOTHING, currentPos), 2 + solveRecursively(PLANK_INT, currentPos));
				}
				else{
					currentPos++;
					return Math.min(3 + solveRecursively(carrying, currentPos), Math.min(3 + solveRecursively(PLANK_INT, currentPos), 2 + solveRecursively(NOTHING, currentPos)));
				}
			}
			if(problem[currentPos]==BALLON){
				if(carrying==NOTHING){
					currentPos++;
					return Math.min(1 + solveRecursively(NOTHING, currentPos), 2 + solveRecursively(BALLON_INT, currentPos));
				}
				else{
					currentPos++;
					return Math.min(3 + solveRecursively(carrying, currentPos), Math.min(3 + solveRecursively(BALLON_INT, currentPos), 2 + solveRecursively(NOTHING, currentPos)));
				}
			}
			if(problem[currentPos]==LAKE){
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
			if(problem[currentPos]==PIT){
				if(carrying==NOTHING || carrying == CANNOE_INT)
					return INFINITY;
				else{
					if(carrying==PLANK_INT)
						return 5 + solveRecursively(PLANK_INT, ++currentPos);
					if(carrying==BALLON_INT)
						return 6 + solveRecursively(BALLON_INT, ++currentPos);
				}
			}
			if(problem[currentPos]==MOUNTAIN){
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

	/**
	 * Solves the problem throuh a table in an iterative mode
	 * @return Integer representing minimum path to cross the map/desert
	 */
	public int resolveIterative () {
		for (int i=0; i<problem.length;i++){
			fillLine(i);
		}
		return getMinimumOffLine(problem.length-1);
	}

	/**
	 * Prints the line with given index. Commented because not crucial to assigment
	 * just testing
	 * @param i index
	 */
	/*private void printLine(int i) {
		for(int z=0;z<4;z++)
			System.out.print(table[i][z] + " | ");
		System.out.println();

	}*/

	/**
	 * Fills one line of the table. The lines represent each step of the map/desert
	 * and are filled from the beginning to the last, one by one
	 * @param currentPos Line to be filled
	 */
	public void fillLine (int currentPos) {
		//multiple types of terrain, require different handles
		switch(problem[currentPos]){
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
			handleObstacle(currentPos, LAKE);
			break;
		case PIT:
			handleObstacle(currentPos, PIT);
			break;
		case MOUNTAIN:
			handleObstacle(currentPos, MOUNTAIN);
			break;
		default:
			System.out.println("ERROR");
			break;
		}
	}

	/**
	 * Handles the filling of the line given when an obstacle is in the way
	 * @param currentPos Line to be filled
	 * @param obstacle Type of obstacle
	 */
	private void handleObstacle(int currentPos, char obstacle) {
		for(int z=0; z<4; z++){
			switch(z){
			case NOTHING:
				table[currentPos][z]=0;
				break;
			case CANNOE_INT:
				if(obstacle==LAKE){
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 4;
					else{
						table[currentPos][z]=0;
					}
				}else
					table[currentPos][z]=0;

				break;
			case PLANK_INT:
				if(obstacle==LAKE || obstacle==PIT){
					if(table[currentPos-1][z]!=0)
						table[currentPos][z]=table[currentPos-1][z] + 5;
					else{
						table[currentPos][z]=0;
					}
				}else
					table[currentPos][z]=0;
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

	/**
	 * Handles the filling of the given line when the an ballon is found
	 * @param currentPos The line to be filled
	 */
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

	/**
	 * Handles the filling of the given line when the an plank is found
	 * @param currentPos The line to be filled
	 */
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
							table[currentPos][z]=getMinimumOffLine(currentPos-1) + 3;
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

	/**
	 * Handles the filling of the given line when the an cannoe is found
	 * @param currentPos The line to be filled
	 */
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
							table[currentPos][z]=getMinimumOffLine(currentPos-1) + 3;
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

	/**
	 * Handles the filling of the given line when the an plain is found
	 * @param currentPos The line to be filled
	 */
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

	/**
	 * Checks if in the line before there were artifacts in use
	 * @param currentPos The current line. The line before this one is the one
	 * getting checked
	 * @return
	 */
	private boolean hadArtifacts(int currentPos) {
		for(int z=1;z<4;z++){
			if(table[currentPos-1][z]!=0 && table[currentPos-1][0]==0)
				return true;
		}
		return false;
	}

	/**
	 * Gets the minimum vaule off the line, not counting the value 0
	 * @param currentPos The line to be checked and retrived the minimum value
	 * @return The minimum value in the line
	 */
	private int getMinimumOffLine(int currentPos) {
		int x=Integer.MAX_VALUE;
		for(int i=0; i<4;i++){
			if(table[currentPos][i]<x && table[currentPos][i]!=0)
				x=table[currentPos][i];
		}
		return x;
	}

	/**
	 * Prints the complete and fully filled table. Commented because not crucial to assigment
	 * just testing
	 */
	/*public void printTable() {
		for(int i = 0; i<problem.length;i++){
			for(int z = 0;z<4; z++){
				System.out.print(table[i][z] + " | ");
			}
			System.out.println();
		}
	}*/
}