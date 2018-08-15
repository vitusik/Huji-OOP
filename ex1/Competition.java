import java.util.Scanner;

/**
 * The Competition class represents a Nim competition between two players, consisting of a given
 * number of rounds.
 * It also keeps track of the number of victories of each player.
 */
public class Competition {
    private boolean msg;
    private Player player1;
    private Player player2;
    private int num_wins1 =0;
    private int num_wins2 =0;



    public Competition(Player player1, Player player2, boolean displayMessage){
        this.msg = displayMessage;
        this.player1 = player1;
        this.player2 = player2;
        }

    /*
    method that returns the amount of a wins a Player has, the method receives
    a player position which is 1 or 2 and returns the amount of wins that corresponds
    with that player id, if the position that was given isn't 1,2 then
    the method returns -1
     */
    public int getPlayerScore(int playerPosition){
        if(playerPosition == 1){
            return this.num_wins1;
        }
        else if(playerPosition == 2){
            return this.num_wins2;
        }
        else{
            return -1;
        }
    }

    /*
    a method that receives a player position which is 1,2 and adds score to
     the wanted player
     */
    private void addScore(int playerPosition){
        if(playerPosition == 1){
            this.num_wins1 += 1;
        }
        else if(playerPosition == 2){
            this.num_wins2 +=1;
        }
    }


	/*
	 * Returns the integer representing the type of the player; returns -1 on bad
	 * input.
	 */
	private static int parsePlayerType(String[] args,int index){
		try{
			return Integer.parseInt(args[index]);
		} catch (Exception E){
			return -1;
		}
	}

	/*
	 * Returns the integer representing the type of player 2; returns -1 on bad
	 * input.
	 */
	private static int parseNumberOfGames(String[] args){
		try{
			return Integer.parseInt(args[2]);
		} catch (Exception E){
			return -1;
		}
	}

    /*
    method that receives Player and Board objects and int that represents a player 1,2
    the method then user the player object to create a move object, which then uses by the board object
    if the winning criteria was met then the method will return true, otherwise false
     */
    private boolean playerMove(Player player,Board board,int playerTurn){
        Move move = null;
        int moveCheck = 0;
        while(moveCheck != 1){
            if(playerTurn%2 == 0) {
                move = this.player1.produceMove(board);
            }
            else{
                move = this.player2.produceMove(board);
            }
            moveCheck = board.markStickSequence(move);
            // I need to perform a check to the Move object legality
            if(moveCheck != 1){
                System.out.println("Invalid move. Enter another:");
            }
        }
        if(this.msg){
            System.out.println("Player " +player.getPlayerId()+" made the move:"+move);
        }
        // in case the amount of unmarked sticks is 0 then this round is finished and the player that made
        // the before last move gets a point
        if (board.getNumberOfUnmarkedSticks() == 0) {
            if(playerTurn%2 == 0) {
                this.addScore(2);
            }
            else{
                this.addScore(1);
            }

            return true;
        }
        else{
            return false;
        }
    }
    /*
    method that simulates a number of rounds in a nim game, the amount of numbers determined by the argument
    that the method receives
     */
    public void playMultipleRounds(int numberOfRounds){
        int counter = 0;
        String p1 = this.player1.getTypeName();
        String p2 = this.player2.getTypeName();
        System.out.println("Starting a Nim competition of " + numberOfRounds + " rounds between a " + p1 +
                " player " + "and a " + p2 +" player.");
        while(counter < numberOfRounds){
            Board board = new Board();
            // when the competition object was initialized the msg field determines whether some messages
            // would be printed out
            if(this.msg){
                System.out.println("Welcome to the sticks game!");
            }
            boolean win = false;
            int playerTurn = 0;
            while(!win) {
                if(msg){
                    System.out.println("Player " + (playerTurn +1) + ", it is now your turn!");
                }
                if(playerTurn%2 ==0){
                    win = this.playerMove(this.player1,board,playerTurn);
                }
                else{
                    win = this.playerMove(this.player2,board,playerTurn);
                }
                playerTurn +=1;
                playerTurn %=2;
                if(win && msg){

                    System.out.println("Player "+(playerTurn+1)+" won!");
                }
            }
            counter += 1;
        }
        System.out.println("The results are " + this.getPlayerScore(1) + ":" + this.getPlayerScore(2));

    }

	/**
	 * The method runs a Nim competition between two players according to the three user-specified arguments.
	 * (1) The type of the first player, which is a positive integer between 1 and 4: 1 for a Random computer
	 *     player, 2 for a Heuristic computer player, 3 for a Smart computer player and 4 for a human player.
	 * (2) The type of the second player, which is a positive integer between 1 and 4.
	 * (3) The number of rounds to be played in the competition.
	 * @param args an array of string representations of the three input arguments, as detailed above.
	 */
	public static void main(String[] args) {

		int p1Type = parsePlayerType(args,0);
		int p2Type = parsePlayerType(args,1);
		int numGames = parseNumberOfGames(args);
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player(p1Type,1,scanner);
        Player player2 = new Player(p2Type,2,scanner);
        boolean msg;
        if(p1Type == 4 || p2Type == 4){
            // if one of the players is human then there is need to print out messages in the game
            msg = true;
        }
        else{
            msg = false;
        }
        Competition competition = new Competition(player1,player2,msg);
        competition.playMultipleRounds(numGames);
	}

}
