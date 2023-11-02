package Game;

import Game.CharacterGetters.*;
import Game.Characters.*;
import Game.Event.GameEventsAggregate;
import Game.Event.Handlers.OnCycleLeft;
import Game.Teams.Team;
import Helpers.IdGenerator;

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

    private int selectedUnitIndex;
    private int currentUnitIndex;

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
        cycle = new GameCycle(compositeAccessor, events);

        events.setCycleLeftEvent(new OnCycleLeft(units));

        testInitialize();

        int unitId = cycle.next();
        setCurrentUnitId(unitId);
        setSelectedUnitIndex(unitId);
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

    public void start() {
        reset();
    }

    public void next() {
        removeDeadUnits();
        if (checkTheEnd()) {
            events.gameEnd(new GameEndInfo(getTeamWinner()));
            return;
        }

        int id = cycle.next();
        setCurrentUnitId(id);

        while (enemiesAccessor.containsId(id)) {
            removeDeadUnits();
            if (checkTheEnd()) {
                events.gameEnd(new GameEndInfo(getTeamWinner()));
                return;
            }

            enemiesAccessor.getUnitById(id).moveAI();
            id = cycle.next();
            setCurrentUnitId(id);
        }

        removeDeadUnits();
        if (checkTheEnd()) {
            events.gameEnd(new GameEndInfo(getTeamWinner()));
            return;
        }
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

    private Boolean isEnd() {
        return alliesAccessor.getQuantity() == 0 || enemiesAccessor.getQuantity() == 0;
    }

    private Team getTeamWinner() {
        if (alliesAccessor.getQuantity() == 0) {
            return ai;
        } else {
            return human;
        }
    }

    private void removeDeadUnits() {
        units.removeIf(unit -> !unit.isAlive());
    }
}