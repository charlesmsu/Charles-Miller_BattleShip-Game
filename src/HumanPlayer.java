import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.util.Map.Entry;

public class HumanPlayer implements IPlayer{
    private String name;
    private Coordinate shot;
    protected OceanGrid oceanGrid = new OceanGrid();
    protected TargetGrid targetGrid = new TargetGrid();

    private List<Coordinate> shotList = new ArrayList<Coordinate>();
    
    
    protected ShipFactory aiShipFactory;

    public HumanPlayer(String name) {
        this.name = name;
        
    }

    public void getShipFactory(int option) {
        if(option == 1){ this.aiShipFactory = new AutomaticShipFactory(); }
        if(option == 2){ this.aiShipFactory = new ManualShipFactory();}
    }

    @Override
    public void printOceanGrid(){
        this.oceanGrid.printGrid();
    }

    @Override
    public void printTargetGrid(){
        this.targetGrid.printGrid();
    }

    public void setOceanGrid(OceanGrid grid){
        this.oceanGrid = grid;
    }

    public String getName(){
        return name;
    }
    
   
    // row and column Position bool, random do and while return

   
    @Override
    public void placeShips(){
        for (Ship ship : aiShipFactory.getShips()) {
            oceanGrid.placeShip(ship);
        }

    }


    
    @Override
    public Coordinate takeShot() {
        
        while (true){

        String input = ConsoleHelper.getInput("Take a Shot> ");
            try {
                shot  = new Coordinate(input);
                printTargetGrid();
                printOceanGrid();
                shotList.add(shot);
            } catch (Exception e){
                System.out.println("Invalid Shot");
               // explain to user of an invalid shot
               //continue while loop 
               continue;
            }
            
            if (targetGrid.isShotValid(shot)){
                return shot;
                
            }else {
            System.out.printf("You have already taken the Shot at %s%n", input);
            
            }
        }
    }
    @Override
    public ShotResult receiveShot(Coordinate shot) {
        return oceanGrid.receiveShot(shot);
    }
    @Override
    public void receiveShotResult(Coordinate shot, ShotResult result) {
        targetGrid.receiveShotResult(result, shot);
        
    }

    public Grid getOceanGrid() {
        return oceanGrid;
    }


    @Override
    public boolean shipsAreSunk() {
        if( aiShipFactory.numberOfSunkShips() == 5 ){
            return true;}
            else {
                return false;
            }
    }

    @Override
    public String getRecentSunkShip() {
        return "The Rear Admiral has sunk your ship!";
    }


}
