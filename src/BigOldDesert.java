import java.util.concurrent.SynchronousQueue;

public class BigOldDesert {

	public static final double INFINITY = Double.POSITIVE_INFINITY;

	public static final char PLAIN = '_';
	public static final char CANNOE = 'c';
	public static final char PLANK = 'p';
	public static final char BALLON = 'b';
	public static final char LAKE = 'L';
	public static final char PIT = 'P';
	public static final char MOUNTAIN = 'M';

	public static final double NOTHING = 0;
	public static final double CANNOE_INT = 1;
	public static final double PLANK_INT = 2;
	public static final double BALLON_INT = 3;

	private String [] problem;
	public int calls;

	public BigOldDesert (String [] problem){
		this.problem = problem;
	}

	public double solve(double carrying, int currentPos) {
		calls++;
		if(currentPos<problem.length){
			if(problem[currentPos].charAt(0)==PLAIN){
				if(carrying==NOTHING){
					currentPos++;
					return 1+solve(NOTHING, currentPos);
				}
				else{
					currentPos++;
					return Math.min((2+solve(NOTHING, currentPos)), (3+solve(carrying, currentPos)));
				}	
			}
			if(problem[currentPos].charAt(0)==CANNOE){
				if(carrying==NOTHING){
					currentPos++;
					return Math.min((1 + solve(NOTHING, currentPos)), (2 + solve(CANNOE_INT, currentPos)));
				}
				else{
					currentPos++;
					return Math.min(3 + solve(carrying, currentPos), Math.min(3 + solve(CANNOE_INT, currentPos), 2 + solve(NOTHING, currentPos)));
				}
			}
			if(problem[currentPos].charAt(0)==PLANK){
				if(carrying==NOTHING){
					currentPos++;
					return Math.min(1 + solve(NOTHING, currentPos), 2 + solve(PLANK_INT, currentPos));
				}
				else{
					currentPos++;
					return Math.min(3 + solve(carrying, currentPos), Math.min(3 + solve(PLANK_INT, currentPos), 2 + solve(NOTHING, currentPos)));
				}
			}
			if(problem[currentPos].charAt(0)==BALLON){
				if(carrying==NOTHING){
					currentPos++;
					return Math.min(1 + solve(NOTHING, currentPos), 2 + solve(BALLON_INT, currentPos));
				}
				else{
					currentPos++;
					return Math.min(3 + solve(carrying, currentPos), Math.min(3 + solve(BALLON_INT, currentPos), 2 + solve(NOTHING, currentPos)));
				}
			}
			if(problem[currentPos].charAt(0)==LAKE){
				if(carrying==NOTHING)
					return INFINITY;
				else{
					if(carrying==PLANK_INT)
						return 5 + solve(PLANK_INT, ++currentPos);
					if(carrying==CANNOE_INT){
						return 4 + solve(CANNOE_INT, ++currentPos);
					}
					if(carrying==BALLON_INT)
						return 6 + solve(BALLON_INT, ++currentPos);
				}
			}
			if(problem[currentPos].charAt(0)==PIT){
				if(carrying==NOTHING || carrying == CANNOE_INT)
					return INFINITY;
				else{
					if(carrying==PLANK_INT)
						return 5 + solve(PLANK_INT, ++currentPos);
					if(carrying==BALLON_INT)
						return 6 + solve(BALLON_INT, ++currentPos);
				}
			}
			if(problem[currentPos].charAt(0)==MOUNTAIN){
				if(carrying==NOTHING || carrying==PLANK_INT || carrying== CANNOE_INT)
					return INFINITY;
				else{
					if(carrying==BALLON_INT)
						return 6 + solve(BALLON_INT, ++currentPos);
				}
			}
		}
		return 0;
	}
}
