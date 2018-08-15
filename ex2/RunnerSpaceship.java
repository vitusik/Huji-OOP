import java.awt.Image;
import oop.ex2.*;


/**
 * This spaceship attempts to run away from the fight. It will always accelerate, and
 * will constantly turn away from the closest ship. If the nearest ship is closer than 0.2 units,
 * and if its angle to the Runner is less than 0.2 radians, the Runner feels threatened and will
 * attempt to teleport.
 */
public class RunnerSpaceship extends SpaceShip {
    /**
     * Creates a new Runner spaceship
     */
    public RunnerSpaceship(){
        super();
    }

    public void doAction(SpaceWars game){
        SpaceShip enemy = game.getClosestShipTo(this);
        double angleTo = this.getPhysics().angleTo(enemy.getPhysics());
        double distanceFromTarget = this.getPhysics().distanceFrom(enemy.getPhysics());
        if(angleTo < this.MIN_ANGLE_TO_TELEPORT && distanceFromTarget < this.MIN_DISTANCE_TO_TELEPORT){
            this.teleport();
        }
        this.getPhysics().move(this.ACCELERATION,-this.turningCalculator(angleTo));
        this.addEnergy(0);
    }
}
