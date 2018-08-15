import java.awt.Image;
import oop.ex2.*;
import java.util.Random;

/**
 * the driver of this spaceship had a little bit too much, his behavior isn't predictable
 * at each round he has a chance of losing his keys for his spaceship, therefore he won't be
 * able to nothing until he finds his keys, also there is a chance that the driver will
 * decide to space drift in circles.
 */
public class DrunkardSpaceship extends SpaceShip {
    private boolean acceleration, noKeys, goingForASpin;
    private int turn, roundCount;
    private final int CHANCE_TO_LOST_KEYS = 1;
    private final int ROUNDS_TO_FIND_KEYS = 10;
    private final int ROUNDS_SPINNING = 50;
    private final int CHANCE_TO_TELEPORT = 5;
    private final int CHANCE_TO_SHIELD = 35;
    private final int CHANCE_TO_SHOT = 95;
    /**
     * Creates a new Drunkard spaceship
     */
    public DrunkardSpaceship(){
        super();
        this.acceleration = false;
        this.turn = -1;
        this.noKeys = false;
        this.goingForASpin = true;
        this.roundCount = 0;
    }
    private Random myRandom = new Random();

    /**
     * method that simulates the loss of the keys by the driver, upon finding them he becomes enraged
     * and decides next round to begin spinning around
     */
    private void keysFinder(){
        if(myRandom.nextInt(100) <= CHANCE_TO_LOST_KEYS && !this.goingForASpin) {
            this.noKeys = true;
            this.goingForASpin = true;
        }
        if(this.noKeys){
            this.roundCount ++;
            if(this.roundCount == ROUNDS_TO_FIND_KEYS){
                this.noKeys = false;
                this.roundCount = 0;
            }
        }
    }

    /**
     * method that makes the driver to start spinning around and firing his rockets
     *
     * @param game the game object to which this ship belongs.
     */
    private void spinningAround(SpaceWars game){
        this.getPhysics().move(ACCELERATION,1);
        this.roundCount ++;
        if(this.roundCount == ROUNDS_SPINNING){
            this.roundCount = 0;
            this.goingForASpin = false;
        }
        this.fire(game);
    }

    /**
     * the driver has come to his senses and decides to find the nearest ship and go after it
     *
     * @param game the game object to which this ship belongs.
     */
    private void seriousMove(SpaceWars game) {
        SpaceShip target = game.getClosestShipTo(this);
        double angleTo = this.getPhysics().angleTo(target.getPhysics());
        double distanceFromTarget = this.getPhysics().distanceFrom(target.getPhysics());
        int randomAcceleration = myRandom.nextInt(2);
        if (myRandom.nextInt(100) <= CHANCE_TO_TELEPORT) {
            this.teleport();
        }
        switch (randomAcceleration) {
            case 0:
                this.acceleration = false;
                break;
            case 1:
                this.acceleration = true;
                break;
        }
        this.getPhysics().move(acceleration, this.turningCalculator(angleTo));
        if (distanceFromTarget <= this.MIN_DISTANCE_TO_SHIELD) {
            if (myRandom.nextInt(100) <= CHANCE_TO_SHIELD) {
                this.shieldOn();
            }
        }
        if (angleTo < MIN_ANGLE_TO_SHOT && angleTo > -MIN_ANGLE_TO_SHOT) {
            if (myRandom.nextInt(100) <= CHANCE_TO_SHOT) {
                this.fire(game);
            }
        }
    }

    public void doAction(SpaceWars game){
        this.setImage(this.getImageUnShielded());
        this.keysFinder();
        if(!this.noKeys && this.goingForASpin) {
            this.spinningAround(game);
        }
        else {
            this.seriousMove(game);
        }
        this.roundCounterForShots();
        this.addEnergy(0);
    }


}
