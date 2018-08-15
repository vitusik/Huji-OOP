import java.awt.Image;
import oop.ex2.*;

/**
 * This is the user spaceship
 */

public class HumanSpaceship extends SpaceShip {
    /**
     * Creates a new Human spaceship, each game only one can be created
     */
    public HumanSpaceship(){
        super();
        this.setImageShielded(GameGUI.SPACESHIP_IMAGE_SHIELD);
        this.setImageUnShielded(GameGUI.SPACESHIP_IMAGE);
        this.setImage(this.getImageUnShielded());
    }

    public void doAction(SpaceWars game){
        this.setImage(this.getImageUnShielded());
        GameGUI gui = game.getGUI();
        boolean acceleration = false;
        int turn = 0;
        if(gui.isTeleportPressed()){
            this.teleport();
        }
        if(gui.isUpPressed()){
            acceleration = true;
        }
        if(gui.isLeftPressed()){
            turn = 1;
        }
        else if(gui.isRightPressed()){
            turn = -1;
        }
        this.getPhysics().move(acceleration,turn);
        if(gui.isShieldsPressed()){
            this.shieldOn();
        }
        if(gui.isShotPressed()){
            this.fire(game);
        }
        this.roundCounterForShots();
        this.addEnergy(0);
    }
}
