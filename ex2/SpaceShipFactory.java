import oop.ex2.*;

/**
 * class that creates a list of the spaceships participating in the current game
 */
public class SpaceShipFactory {
    /**
     * creates a list of spaceships
     * @param args the command line arguments
     * @return list of spaceships, each member of the list is created based of the argument that
     * was given in the command line
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip [] shipList = new SpaceShip[args.length];
        int j = 0;
        for(String i : args){
            char t = i.charAt(0);
            switch (t){
                case 'h': shipList[j] = new HumanSpaceship();
                    j++;
                    break;
                case 'a': shipList[j] = new AggressiveSpaceship();
                    j++;
                    break;
                case 'r': shipList[j] = new RunnerSpaceship();
                    j++;
                    break;
                case 'b': shipList[j] = new BasherSpaceship();
                    j++;
                    break;
                case 'd': shipList[j] = new DrunkardSpaceship();
                    j++;
                    break;
                case 's': shipList[j] = new SpecialSpaceship();
                    j++;
                    break;
                default: shipList[j] = null;
                    j++;
                    break;
            }

        }


        return shipList;
    }
}
