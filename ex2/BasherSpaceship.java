import java.awt.Image;
import oop.ex2.*;


/**
 * This ship attempts to collide with other ships. It will always accelerate, and will
 * constantly turn towards the closest ship. If it gets within a distance of 0.2 units from another
 * ship, it will attempt to turn on its shields.
 */
public class BasherSpaceship extends SpaceShip {
    public BasherSpaceship(){
        super();
    }
    public void doAction(SpaceWars game){
        this.setImage(this.getImageUnShielded());
        SpaceShip target = game.getClosestShipTo(this);
        double angleTo = this.getPhysics().angleTo(target.getPhysics());
        this.getPhysics().move(ACCELERATION,this.turningCalculator(angleTo));
        double distanceFromTarget = this.getPhysics().distanceFrom(target.getPhysics());
        if(distanceFromTarget < this.MIN_DISTANCE_TO_SHIELD){
            this.shieldOn();
        }
        this.addEnergy(0);
    }
}
