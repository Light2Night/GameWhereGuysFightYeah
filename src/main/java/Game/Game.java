package Game;

import Game.CharacterGetters.*;
import Game.Characters.*;
import Game.Event.GameEventsAggregate;
import Game.Teams.Team;
import Helpers.IdGenerator;
import Helpers.SafeInput;

import java.util.ArrayList;

public class Game {
    //region Fields
    private ArrayList<GameUnit> units;

    private Boolean gameIsOn;

    private TeamAccessor alliesAccessor;
    private TeamAccessor enemiesAccessor;
    private UnitsAccessor unitsAccessor;
    private CompositeAccessor compositeAccessor;

    private GameCycle cycle;

    private int selectedUnitIndex = 1;

    private int currentUnitIndex = 1;

    private Team human;
    private Team ai;

    private GameEventsAggregate events;
    //endregion

    public Game() {
        events = new GameEventsAggregate();
    }

    //region Setters
    public void setSelectedUnitIndex(int selectedUnitIndex) {
        this.selectedUnitIndex = selectedUnitIndex;
        events.selectedIndexChanged();
    }

    public void setCurrentUnitId(int currentUnitIndex) {
        this.currentUnitIndex = currentUnitIndex;
        events.currentIndexChanged();
    }
    //endregion

    //region Getters
    public ArrayList<GameUnit> getUnits() {
        return units;
    }

    public GameUnit getCurrentUnit() {
        return unitsAccessor.getUnitByIndex(currentUnitIndex);
    }

    public int getSelectedUnitIndex() {
        return selectedUnitIndex;
    }

    public int getCurrentUnitIndex() {
        return currentUnitIndex;
    }

    public GameEventsAggregate getEvents() {
        return events;
    }
    //endregion

    private void reset() {
        gameIsOn = true;

        units = new ArrayList<>();

        human = new Team(1, PlayerTypes.Human, "Human");
        ai = new Team(2, PlayerTypes.AI, "AI");

        alliesAccessor = new TeamAccessor(units, human);
        enemiesAccessor = new TeamAccessor(units, ai);
        unitsAccessor = new UnitsAccessor(units);
        compositeAccessor = new CompositeAccessor(alliesAccessor, enemiesAccessor, unitsAccessor);
        cycle = new GameCycle(compositeAccessor);

        testInitialize();
    }

    private void testInitialize() {
        addAlly(UnitTypes.BARBARIAN);
        addAlly(UnitTypes.MAGICIAN);
        addAlly(UnitTypes.HEALER);

        addEnemy(UnitTypes.BARBARIAN);
        addEnemy(UnitTypes.MAGICIAN);
        addEnemy(UnitTypes.HEALER);
    }

    public void addAlly(UnitTypes type) {
        units.add(createUnit(type, human));
    }

    public void addEnemy(UnitTypes type) {
        units.add(createUnit(type, ai));
    }

    private GameUnit createUnit(UnitTypes type, Team team) {
        return switch (type) {
            case BARBARIAN -> new Barbarian(compositeAccessor, team, IdGenerator.getId());
            case MAGICIAN -> new Magician(compositeAccessor, team, IdGenerator.getId());
            case HEALER -> new Healer(compositeAccessor, team, IdGenerator.getId());
            default -> throw new IllegalArgumentException();
        };
    }

    private int inputUnitsQuantity(String message, int max) {
        int quantity;
        do {
            System.out.println(message);
            quantity = SafeInput.getInt();
        } while (!(1 <= quantity && quantity <= max));
        return quantity;
    }

    public void start() {
        reset();
    }

//    private void makeCycle() {
//        executeEffectsForAll();
//        removeDeadUnits();
//
//        if (checkTheEnd()) {
//            return;
//        }
//
//        for (int i = 0; i < unitsAccessor.getQuantity(); i++) {
//            printFrame();
//            GameUnit currentUnit = unitsAccessor.getUnitByIndex(i);
//            System.out.printf("Зараз хід юнітом - %s\n", currentUnit.toString());
//
//            if (i < alliesAccessor.getQuantity()) {
//                //currentUnit.Move(compositeAccessor);
//            } else {
//                //currentUnit.MoveAI(compositeAccessor);
//            }
//
//            i -= getQuantityOfUnitsWhichWillBeDeleted(i);
//            setCurrentUnitId(unitsAccessor.getUnitByIndex(i).getId());
//            removeDeadUnits();
//
//            if (checkTheEnd()) {
//                return;
//            }
//        }
//    }

    public void next() {
        removeDeadUnits();
        int id = cycle.next();

        while (enemiesAccessor.containsId(id)) {
            enemiesAccessor.getUnitById(id).moveAI();

            id = cycle.next();
            removeDeadUnits();
        }

        setCurrentUnitId(id);

        events.moveCompleted();
    }

    public GameUnit getUnitById(int id) {
        //System.out.println(id);
        for (GameUnit unit : units) {
            if (unit.getId() == id) {
                return unit;
            }
        }
        return null;
    }

    private Boolean checkTheEnd() {
        if (!isEnd()) {
            return false;
        }

        if (alliesAccessor.getQuantity() == 0) {
            events.gameEnd(new GameEndInfo(ai));
        } else {
            events.gameEnd(new GameEndInfo(human));
        }

        gameIsOn = false;
        return true;
    }

    private int getQuantityOfUnitsWhichWillBeDeleted(int currentIndex) {
        int quantity = 0;
        for (int i = 0; i <= currentIndex; i++) {
            if (!unitsAccessor.getUnitByIndex(i).isAlive()) quantity++;
        }
        return quantity;
    }

    private void executeEffectsForAll() {
        units.forEach(GameUnit::executeEffects);
    }

    private String getFilledString(char symbol, int count) {
        return String.valueOf(symbol).repeat(count);
    }

    private void removeDeadUnits() {
        units.removeIf(unit -> !unit.isAlive());
    }

    private Boolean isEnd() {
        return alliesAccessor.getQuantity() == 0 || enemiesAccessor.getQuantity() == 0;
    }
}