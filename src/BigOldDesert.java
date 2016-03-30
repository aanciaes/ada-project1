public class BigOldDesert {

	public static final char PLAIN = '_';
	public static final char CANNOE = 'c';
	public static final char PLANK = 'p';
	public static final char BALLON = 'b';
	public static final char LAKE = 'L';
	public static final char PIT = 'P';
	public static final char MOUNTAIN = 'M';

	private String [] problem;
	private int currentPos;

	public BigOldDesert (String [] problem){
		this.problem = problem;
		currentPos=0;
	}

	public int solve() {
		if(currentPos<problem.length){
			if(problem[currentPos++].charAt(0)==PLAIN){
				return 1 + solve();
			}
			
			if(problem[currentPos++].charAt(0)==CANNOE){
				return Math.min(1+solve(), 2 + solve());
			}
		}
		return 0;
	}
}
