import java.awt.Image;
import oop.ex2.*;

/**
 * this spaceship has double starting point and double energy points, its behavior is pretty smart
 * upon choosing its target the ship will fire on the target,
 * use its shields and even teleport in case its hp is very low.
 */
public class SpecialSpaceship extends SpaceShip {
    private final int MIN_HP_TO_TELEPORT = 3;
    public SpecialSpaceship(){
        super();
        this.setHP(this.getHp()*2);
        this.setEnergy(this.getEnergy()*2);
        this.setMaxEnergy(this.getMaxEnergy()*2);
    }

    public void doAction(SpaceWars game){
        this.setImage(this.getImageUnShielded());
        SpaceShip target = game.getClosestShipTo(this);
        double angleTo = this.getPhysics().angleTo(target.getPhysics());
        double distanceFromTarget = this.getPhysics().distanceFrom(target.getPhysics());
        if(angleTo < MIN_ANGLE_TO_TELEPORT && distanceFromTarget < MIN_ANGLE_TO_TELEPORT &&
                this.getHp() < MIN_HP_TO_TELEPORT){
            this.teleport();
        }
        int turn;
        if(angleTo > 0.0){
            turn = 1;
        }
        else if(angleTo < 0.0){
            turn = -1;
        }
        else{
            turn = 0;
        }
        this.getPhysics().move(ACCELERATION,turn);
        if(distanceFromTarget < MIN_DISTANCE_TO_SHIELD){
            this.shieldOn();
        }
        if(angleTo < MIN_ANGLE_TO_SHOT && angleTo > -MIN_ANGLE_TO_SHOT){
            this.fire(game);
        }
        this.roundCounterForShots();
        this.addEnergy(0);
    }
}
