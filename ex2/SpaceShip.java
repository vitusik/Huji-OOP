import java.awt.Image;
import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. 
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 *  a base class for the other spaceships or any other option you will choose.
 *  
 * @author oop
 */
public abstract class SpaceShip{

    private int hp, energy, maxEnergy;
    private final static int STARTING_HP = 20, STARTING_ENERGY = 200;
    private final static int SHOT_COST = 20, SHIELD_COST = 3, ROUNDS_PER_SHOT = 8;
    private final static int MAX_ENERGY_INCREASE_SHIELDED = 20;
    private final static int HP_LOSS_PER_HIT = 1, CURR_ENERGY_INCREASE_SHIELDED = 20;
    private final static int ENERGY_LOSS_NO_SHIELD =10;
    private final static int TELEPORT_COST = 150;
    /** acceleration modifier for the PC controlled spaceships */
    public final boolean ACCELERATION = true;
    /** the minimal angle between two ships so one will fire upon the other */
    public final double MIN_ANGLE_TO_SHOT = 0.2;
    /** the minimal distance between two ships so one will activate its shield */
    public final double MIN_DISTANCE_TO_SHIELD = 0.2;
    /** the minimal distance between two ships so one will teleport */
    public final double MIN_DISTANCE_TO_TELEPORT = 0.2;
    /** the minimal angle between two ships so one will teleport */
    public final double MIN_ANGLE_TO_TELEPORT = 0.2;
    private Image image;
    private Image noShield;
    private Image shielded;
    private SpaceShipPhysics physics;
    private boolean canFire;
    private int roundCount;
    public SpaceShip(){
        this.hp = STARTING_HP;
        this.energy = STARTING_ENERGY;
        this.maxEnergy = STARTING_ENERGY;
        this.physics = new SpaceShipPhysics();
        this.setImageUnShielded(GameGUI.ENEMY_SPACESHIP_IMAGE);
        this.setImageShielded(GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD);
        this.setImage(noShield);
        this.canFire = true;
        this.roundCount = 0;

    }


    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){
        if(this.image == this.shielded){
            this.maxEnergy += MAX_ENERGY_INCREASE_SHIELDED;
            this.addEnergy(CURR_ENERGY_INCREASE_SHIELDED);
        }
        else{
            this.hp -= HP_LOSS_PER_HIT;
            this.addEnergy(-ENERGY_LOSS_NO_SHIELD);
            this.energyCheck();
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        this.hp = STARTING_HP;
        this.maxEnergy = STARTING_ENERGY;
        this.energyCheck();
        this.physics = new SpaceShipPhysics();
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        if(this.hp == 0){
            return true;
        }
        return false;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.physics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if(this.image == this.noShield){
            this.maxEnergy -= ENERGY_LOSS_NO_SHIELD;
            this.hp -= HP_LOSS_PER_HIT;
            this.energyCheck();
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if(this.getEnergy() >= TELEPORT_COST){
            this.physics = new SpaceShipPhysics();
            this.addEnergy(-TELEPORT_COST);
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public Image getImage(){
        return this.image;
    }

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
       if(this.energy >= SHOT_COST && this.canFire){
           game.addShot(this.physics);
           this.canFire = false;
           this.addEnergy(-SHOT_COST);
       }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if(this.getEnergy() >= SHIELD_COST){
            this.addEnergy(-SHIELD_COST);
            this.setImage(shielded);
        }
        else {
            this.setImage(noShield);
        }
    }

    /**
    Checks if the new max energy level of the ship is smaller then the current energy level
     */
    private void energyCheck(){
        if(this.energy > this.maxEnergy){
            this.energy = this.maxEnergy;
        }
    }

    /**
     * sets the image for a given spaceship
     *
     * @param image the image that we want to set for a given spaceship
     */
    protected void setImage(Image image){
        this.image = image;
    }

    /**
     * method that returns the private field canFire
     * @return True\False
     */
    protected boolean canFire(){
        return this.canFire;
    }

    /**
     * method that counts rounds after a ship has fired a shot
     * after enough rounds it can shoot again
     */
    protected void roundCounterForShots(){
        if(!this.canFire){
            this.roundCount ++;
            if(this.roundCount == ROUNDS_PER_SHOT){
                this.roundCount = 0;
                this.canFire = true;
            }
        }
    }

    /**
     * method that adds energy to the ship
     *
     * @param change the addition to the energy, can be negative
     */
    protected void addEnergy(int change){
        if(change == 0){
            this.energy ++;
        }
        else {
            this.energy += change;
        }
    }

    /**
     * method that changes the shielded image of our spaceship
     *
     * @param image the shielded image that we want for our shielded spaceship
     */
    protected void setImageShielded(Image image){
        this.shielded = image;
    }

    /**
     * method that returns the shielded image of our spaceship
     *
     * @return the shielded image of the given spaceship
     */
    protected Image getImageShielded(){
        return this.shielded;
    }

    /**
     * method that changes the unshielded image of our spaceship
     *
     * @param image the unshielded image that we want for our shielded spaceship
     */
    protected void setImageUnShielded(Image image){
        this.noShield = image;
    }

    /**
     * method that returns the shielded image of our spaceship
     *
     * @return the shielded image of the given spaceship
     */
    protected Image getImageUnShielded(){
        return this.noShield;
    }

    /**
     * an hp setter for the spaceship
     *
     * @param hp the desired hp
     */
    protected void setHP(int hp){
        this.hp = hp;
    }

    /**
     * an hp getter
     *
     * @return the amount of hp the ship has
     */
    protected int getHp(){
        return this.hp;
    }

    /**
     * an energy setter
     *
     * @param energy the desired energy level
     */
    protected void setEnergy(int energy){
        this.energy = energy;
    }

    /**
     * an energy getter
     *
     * @return the current energy level of the spaceship
     */
    protected int getEnergy(){
        return this.energy;
    }

    /**
     * a max energy setter
     *
     * @param energy the desired max energy level
     */
    protected void setMaxEnergy(int energy){
        this.maxEnergy = energy;
    }

    /**
     * a max energy getter
     *
     * @return the current max energy level of the spaceship
     */
    protected int getMaxEnergy(){
        return this.maxEnergy;
    }

    /**
     * a method that calculates the correct turn based on the angle that it receives
     * @param angle the angle between two ships
     * @return 1,0,-1 which each represent turn right,left or no turn at all
     */
    protected int turningCalculator(double angle){
        if (angle < 0.0) {
            return -1;
        } else if (angle > 0.0) {
            return 1;
        } else {
            return 0;
        }
    }

}
