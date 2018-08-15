import java.awt.Image;
import oop.ex2.*;

/**
 * This ship pursues other ships and tries to fire at them. It will always accelerate,
 * and turn towards the nearest ship. When its angle to the nearest ship is less than 0.2 radians,
 * it will try to fire.
 */
public class AggressiveSpaceship extends SpaceShip {
    /**
     * Creates a new Aggressive spaceship
     */
    public AggressiveSpaceship(){
        super();
    }

    public void doAction(SpaceWars game){
        SpaceShip target = game.getClosestShipTo(this);
        double angleTo = this.getPhysics().angleTo(target.getPhysics());
        this.getPhysics().move(this.ACCELERATION,this.turningCalculator(angleTo));
        if(angleTo <= this.MIN_ANGLE_TO_SHOT && angleTo >= -this.MIN_ANGLE_TO_SHOT){
            this.fire(game);
        }
        this.roundCounterForShots();
        this.addEnergy(0);
    }
}
