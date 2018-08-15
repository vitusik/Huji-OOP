import java.util.Random;
import java.util.Scanner;

/**
 * The Player class represents a player in the Nim game, producing Moves as a response to a Board state. Each player 
 * is initialized with a type, either human or one of several computer strategies, which defines the move he 
 * produces when given a board in some state. The heuristic strategy of the player is already implemented. You are 
 * required to implement the rest of the player types according to the exercise description.
 * @author OOP course staff
 */
public class Player {

	//Constants that represent the different players.
	/** The constant integer representing the Random player type. */
	public static final int RANDOM = 1;
	/** The constant integer representing the Heuristic player type. */
	public static final int HEURISTIC = 2;
	/** The constant integer representing the Smart player type. */
	public static final int SMART = 3;
	/** The constant integer representing the Human player type. */
	public static final int HUMAN = 4;
	
	//Used by produceHeuristicMove() for binary representation of board rows.
	private static final int BINARY_LENGTH = 3;
	
	private final int playerType;
	private final int playerId;
	private Scanner scanner;
	
	/**
	 * Initializes a new player of the given type and the given id, and an initialized scanner.
	 * @param type The type of the player to create.
	 * @param id The id of the player (either 1 or 2).
	 * @param inputScanner The Scanner object through which to get user input
	 * for the Human player type. 
	 */
	public Player(int type, int id, Scanner inputScanner){		
		// Check for legal player type (we will see better ways to do this in the future).
		if (type != RANDOM && type != HEURISTIC 
				&& type != SMART && type != HUMAN){
			System.out.println("Received an unknown player type as a parameter"
					+ " in Player constructor. Terminating.");
			System.exit(-1);
		}		
		playerType = type;	
		playerId = id;
		scanner = inputScanner;
	}
	
	/**
	 * @return an integer matching the player type.
	 */	
	public int getPlayerType(){
		return playerType;
	}
	
	/**
	 * @return the players id number.
	 */	
	public int getPlayerId(){
		return playerId;
	}
	
	
	/**
	 * @return a String matching the player type.
	 */
	public String getTypeName(){
		switch(playerType){
			
			case RANDOM:
				return "Random";			    
	
			case SMART: 
				return "Smart";	
				
			case HEURISTIC:
				return "Heuristic";
				
			case HUMAN:			
				return "Human";
		}
		//Because we checked for legal player types in the
		//constructor, this line shouldn't be reachable.
		return "UnkownPlayerType";
	}
	
	/**
	 * This method encapsulates all the reasoning of the player about the game. The player is given the 
	 * board object, and is required to return his next move on the board. The choice of the move depends
	 * on the type of the player: a human player chooses his move manually; the random player should 
	 * return some random move; the Smart player can represent any reasonable strategy; the Heuristic 
	 * player uses a strong heuristic to choose a move. 
	 * @param board - a Board object representing the current state of the game.
	 * @return a Move object representing the move that the current player will play according to his strategy.
	 */
	public Move produceMove(Board board){
		
		switch(playerType){
		
			case RANDOM:
				return produceRandomMove(board);				
				    
			case SMART: 
				return produceSmartMove(board);
				
			case HEURISTIC:
				return produceHeuristicMove(board);
				
			case HUMAN:
				return produceHumanMove(board);

			//Because we checked for legal player types in the
			//constructor, this line shouldn't be reachable.
			default: 
				return null;			
		}
	}
	
	/*
	method that receives a board object and returns a random row from it, the row that's returned is
	guaranteed to have at least one legal move
	 */
    private int rowGet(Board board)
    {
        // the idea is basically to get the amount of rows in the board,create an array that has
        // (number of rows) values , and then run in a loop on all the possible rows,and inside each row
        // to check if there is at least one unmarked stick, is that stick exists then the array in index
        // t will receive the row number, at the end the method will randomize a number between 0 and t
        // and the method will return the value that is inside the t index in the array
        int amountOfRows = board.getNumberOfRows();
        int rowsWithMoves[] = new int[amountOfRows];
        int rowLength;
        int t = 0;
        int randomIndex;
        Random myRandom = new Random();
        for(int i = 1; i < amountOfRows + 1; i++){
            rowLength = board.getRowLength(i);
            for(int j = 1; j < rowLength + 1; j++){
                if(board.isStickUnmarked(i,j)){
                    rowsWithMoves[t] = i;
                    t ++;
                    break;
                }
            }
        }
        randomIndex = myRandom.nextInt(t);
        return rowsWithMoves[randomIndex];
    }
    /*
    a method that receives a board object and a row number, and returns a random left bound for the stick
    sequence, the method guarantees that the bound that will be returned is can be a part of a stick sequence
     */
    private int leftBoundGet(Board board,int row){
        // the idea is the same as in the method of the random row getter, only this time the method doesn't
        // go through all of the rows
        int rowLength = board.getRowLength(row);
        int arrayOfLeftBound[] = new int[rowLength];
        int randomIndex;
        Random myRandom = new Random();
        int j=0;
        for(int i = 1; i < rowLength+1; i++){
            if(board.isStickUnmarked(row,i)){
                arrayOfLeftBound[j] = i;
                j ++;
            }
        }
        randomIndex = myRandom.nextInt(j);
        return arrayOfLeftBound[randomIndex];
    }
    /*
    a method that receives a board object, a row number and a left bound and returns a right bound for the
    stick sequence, the method guarantees that the bound that will be returned is can be a part of a stick
    sequence
     */
    private int rightBoundGet(Board board,int row,int lBound){
        // the idea is to iterate over the row that the method received, beginning in lBound as first index
        // and iterating until the end of the sequence, which will become the rBound, and finally randomize
        // a number that is between 0 and the difference between lBound and rBound, and adding it to lBound
        Random myRandom = new Random();
        boolean endOfSequence = false;
        int rBound = lBound;
        int randomRightBound;
        while(!endOfSequence){
            if(!board.isStickUnmarked(row,rBound)){
                endOfSequence = true;
                break;
            }
            rBound ++;
        }
        randomRightBound = myRandom.nextInt(rBound - lBound) + lBound;
        return randomRightBound;
    }
    /*
    method that implements the Random player's move creation' the method receives board object
    and returns move object
     */
    private Move produceRandomMove(Board board){
        int row, lBound, rBound;
        row = this.rowGet(board);
        lBound = this.leftBoundGet(board, row);
        rBound = this.rightBoundGet(board, row, lBound);
        Move randomMove = new Move(row,lBound,rBound);
        return randomMove;
	}
	
	/*
	 method that implements the Smart player's move creation' the method receives board object
    and returns move object
	 */
	private Move produceSmartMove(Board board){
        // the idea is to count the amount of sequences in a given board and in case that the number is
        // odd removes just one stick from a sequence that has more then one stick, otherwise to remove a full
        // sequence
        int amountOfLegalSequences = 0;
        int amountOfRows = board.getNumberOfRows();
        int rowLength;
        int row = 0, lBound = 0, rBound = 0;
        for(int i = 1; i < amountOfRows + 1; i++){
            rowLength = board.getRowLength(i);
            for(int j = 1; j < rowLength; j++){
                if(board.isStickUnmarked(i,j) && !board.isStickUnmarked(i,j+1)){
                    amountOfLegalSequences ++;
                }
            }
        }
        outerLoop:
        for(int i = 1; i < amountOfRows + 1; i++){
            rowLength = board.getRowLength(i);
            for(int j = 1; j < rowLength + 1; j++) {
                if (board.isStickUnmarked(i, j)) {
                    row = i;
                    lBound = j;
                    rBound = j;
                    if (amountOfLegalSequences % 2 == 0) {
                        break outerLoop;
                    } else {
                        for (int t = j + 1; t < rowLength + 1; t++) {
                            if (!board.isStickUnmarked(i, t)) {
                                lBound ++;
                                break outerLoop;
                            }
                            rBound++;
                        }
                    }
                }
            }
        }
        Move move = new Move(row,lBound,rBound);
        return move;
	}


	/*
	a method that scans the row,left bound and right bound from the human player, and then returns the Move
	object that is created from them, the assumption is that the arguments which received from the user are
	integers
	 */
    private Move humanMove(Board board){
        int row,lBound,rBound;
        Scanner scanner1 = scanner;
        System.out.println("Enter the row number:");
        row = scanner1.nextInt();
        System.out.println("Enter the index of the leftmost stick:");
        lBound = scanner1.nextInt();
        System.out.println("Enter the index of the rightmost stick:");
        rBound = scanner1.nextInt();
        Move move = new Move(row, lBound, rBound);
        return move;
    }

    /*
    a method that asks the user whether he wants to make a move or to see the board, and in case the user's
    input wasn't 1,2 then the method alerts the player that the input ain't legal, the assumption is that the
    arguments which received from the user are integers
     */
	private Move produceHumanMove(Board board){
        Scanner scanner1 = scanner;
        int userInput = 0;
        Move move;
        while(userInput != 2){
            System.out.println("Press 1 to display the board. Press 2 to make a move:");
            userInput = scanner1.nextInt();
            if (userInput == 1) {
                System.out.println(board);
            }
            if(userInput != 1 && userInput != 2){
                System.out.println("Unknown input.");
            }
        }
        move = this.humanMove(board);
        return move;

	}


	
	/*
	 * Uses a winning heuristic for the Nim game to produce a move.
	 */
	private Move produceHeuristicMove(Board board){

		if(board == null){
			return null;
		}
	
		int numRows = board.getNumberOfRows();
		int[][] bins = new int[numRows][BINARY_LENGTH];
		int[] binarySum = new int[BINARY_LENGTH];
		int bitIndex,higherThenOne=0,totalOnes=0,lastRow=0,lastLeft=0,lastSize=0,lastOneRow=0,lastOneLeft=0;
		
		for(bitIndex = 0;bitIndex<BINARY_LENGTH;bitIndex++){
			binarySum[bitIndex] = 0;
		}
		
		for(int k=0;k<numRows;k++){
			
			int curRowLength = board.getRowLength(k+1);
			int i = 0;
			int numOnes = 0;
			
			for(bitIndex = 0;bitIndex<BINARY_LENGTH;bitIndex++){
				bins[k][bitIndex] = 0;
			}
			
			do {
				if(i<curRowLength && board.isStickUnmarked(k+1,i+1) ){
					numOnes++;
				} else {
					
					if(numOnes>0){
						
						String curNum = Integer.toBinaryString(numOnes);
						while(curNum.length()<BINARY_LENGTH){
							curNum = "0" + curNum;
						}
						for(bitIndex = 0;bitIndex<BINARY_LENGTH;bitIndex++){
							bins[k][bitIndex] += curNum.charAt(bitIndex)-'0'; //Convert from char to int
						}
						
						if(numOnes>1){
							higherThenOne++;
							lastRow = k +1;
							lastLeft = i - numOnes + 1;
							lastSize = numOnes;
						} else {
							totalOnes++;
						}
						lastOneRow = k+1;
						lastOneLeft = i;
						
						numOnes = 0;
					}
				}
				i++;
			}while(i<=curRowLength);
			
			for(bitIndex = 0;bitIndex<BINARY_LENGTH;bitIndex++){
				binarySum[bitIndex] = (binarySum[bitIndex]+bins[k][bitIndex])%2;
			}
		}
		
		
		//We only have single sticks
		if(higherThenOne==0){
			return new Move(lastOneRow,lastOneLeft,lastOneLeft);
		}
		
		//We are at a finishing state				
		if(higherThenOne<=1){
			
			if(totalOnes == 0){
				return new Move(lastRow,lastLeft,lastLeft+(lastSize-1) - 1);
			} else {
				return new Move(lastRow,lastLeft,lastLeft+(lastSize-1)-(1-totalOnes%2));
			}
			
		}
		
		for(bitIndex = 0;bitIndex<BINARY_LENGTH-1;bitIndex++){
			
			if(binarySum[bitIndex]>0){
				
				int finalSum = 0,eraseRow = 0,eraseSize = 0,numRemove = 0;
				for(int k=0;k<numRows;k++){
					
					if(bins[k][bitIndex]>0){
						eraseRow = k+1;
						eraseSize = (int)Math.pow(2,BINARY_LENGTH-bitIndex-1);
						
						for(int b2 = bitIndex+1;b2<BINARY_LENGTH;b2++){
							
							if(binarySum[b2]>0){
								
								if(bins[k][b2]==0){
									finalSum = finalSum + (int)Math.pow(2,BINARY_LENGTH-b2-1);
								} else {
									finalSum = finalSum - (int)Math.pow(2,BINARY_LENGTH-b2-1);
								}
								
							}
							
						}
						break;
					}
				}
				
				numRemove = eraseSize - finalSum;
				
				//Now we find that part and remove from it the required piece
				int numOnes=0,i=0;
				//while(numOnes<eraseSize){
				while(numOnes<numRemove && i<board.getRowLength(eraseRow)){

					if(board.isStickUnmarked(eraseRow,i+1)){
						numOnes++;
					} else {
						numOnes=0;
					}
					i++;
					
				}
				
				//This is the case that we cannot perform a smart move because there are marked
				//Sticks in the middle
				if(numOnes == numRemove){
					return new Move(eraseRow,i-numOnes+1,i-numOnes+numRemove);
				} else {
					return new Move(lastRow,lastLeft,lastLeft);
				}
				
			}
		}
		
		//If we reached here, and the board is not symmetric, then we only need to erase a single stick
		if(binarySum[BINARY_LENGTH-1]>0){
			return new Move(lastOneRow,lastOneLeft,lastOneLeft);
		}
		
		//If we reached here, it means that the board is already symmetric,
		//and then we simply mark one stick from the last sequence we saw:
		return new Move(lastRow,lastLeft,lastLeft);		
	}
	
	
}
