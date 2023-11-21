package Game;

import Game.Effects.Healling;
import Game.Effects.Poisoning;
import Game.Event.Aggregates.UnitEventsAggregate;
import Game.Event.Arguments.Actions.ActionInfo;
import Game.Event.Arguments.GameEndInfo;
import Game.Event.Handlers.OnAction;
import Exceptions.GameIsNotStartedException;
import Game.Event.Aggregates.GameEventsAggregate;
import Game.Event.Handlers.OnCycleLeft;
import Game.Statistics.Session.ISessionStatisticBuilder;
import Game.Statistics.Session.SessionStatisticBuilder;
import Game.Teams.Team;
import Game.Units.Characters.*;
import Game.Units.Factories.UnitFactory;
import Game.Units.Factories.ViewModels.BarbarianViewModel;
import Game.Units.Factories.ViewModels.HealerViewModel;
import Game.Units.Factories.ViewModels.MageViewModel;
import Game.Units.Getters.CompositeAccessor;
import Game.Units.Getters.TeamAccessor;
import Game.Units.Getters.UnitsAccessor;
import ViewModels.SessionStatisticVm;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Game {
    //region Fields
    private final ArrayList<GameUnit> units;

    private Boolean gameIsOn;

    private final TeamAccessor alliesAccessor;
    private final TeamAccessor enemiesAccessor;
    private final UnitsAccessor unitsAccessor;
    private final CompositeAccessor compositeAccessor;

    private final GameCycle cycle;

    private final ISessionStatisticBuilder sessionStatisticBuilder;

    @Nullable
    private Integer selectedUnitIndex = null;
    @Nullable
    private Integer currentUnitIndex = null;

    private final Team human;
    private final Team ai;

    private final GameEventsAggregate events;
    private final UnitEventsAggregate unitEvents;
    private final ArrayList<ActionInfo> cycleActions;

    public final UnitFactory alliesFactory;
    public final UnitFactory enemyFactory;
    //endregion

    public Game() {
        gameIsOn = false;

        sessionStatisticBuilder = new SessionStatisticBuilder();

        units = new ArrayList<>();
        events = new GameEventsAggregate();
        cycleActions = new ArrayList<>();

        events.setCycleLeftEvent(new OnCycleLeft(units, cycleActions));
        unitEvents = new UnitEventsAggregate();
        unitEvents.setActionPerformedEvent(new OnAction(cycleActions));

        human = new Team(1, PlayerTypes.Human, "Human");
        ai = new Team(2, PlayerTypes.AI, "AI");

        alliesAccessor = new TeamAccessor(units, human);
        enemiesAccessor = new TeamAccessor(units, ai);
        unitsAccessor = new UnitsAccessor(units);
        compositeAccessor = new CompositeAccessor(alliesAccessor, enemiesAccessor, unitsAccessor);

        cycle = new GameCycle(compositeAccessor, events);

        alliesFactory = new UnitFactory(compositeAccessor, human, unitEvents, sessionStatisticBuilder);
        enemyFactory = new UnitFactory(compositeAccessor, ai, unitEvents, sessionStatisticBuilder);
    }

    //region Setters
    public void setSelectedUnitIndex(@Nullable Integer selectedUnitIndex) {
        this.selectedUnitIndex = selectedUnitIndex;
        events.selectedIndexChanged();
    }

    public void setCurrentUnitId(@Nullable Integer currentUnitIndex) {
        this.currentUnitIndex = currentUnitIndex;
        events.currentIndexChanged();
    }
    //endregion

    //region Getters
    public ArrayList<GameUnit> getUnits() {
        return units;
    }

    @Nullable
    public Integer getSelectedUnitIndex() {
        return selectedUnitIndex;
    }

    @Nullable
    public Integer getCurrentUnitIndex() {
        return currentUnitIndex;
    }

    public GameEventsAggregate getEvents() {
        return events;
    }

    public ArrayList<ActionInfo> getCycleActions() {
        return cycleActions;
    }

    public GameUnit getUnitById(int id) {
        for (GameUnit unit : units) {
            if (unit.getId() == id) {
                return unit;
            }
        }
        return null;
    }
    //endregion

    public void start() {
        gameIsOn = true;

        Integer unitId = cycle.next();
        setCurrentUnitId(unitId);
        setSelectedUnitIndex(unitId);
    }

    public void reset() {
        units.clear();
        cycleActions.clear();
    }

    public void addUnit(GameUnit unit) {
        units.add(unit);
    }

    public void next() throws GameIsNotStartedException {
        if (!gameIsOn) {
            throw new GameIsNotStartedException();
        }

        removeDeadUnits();
        if (checkTheEndAndFinishGameIfNeed()) {
            return;
        }

        Integer id = cycle.next();
        setCurrentUnitId(id);

        while (enemiesAccessor.containsId(id)) {
            removeDeadUnits();
            if (checkTheEndAndFinishGameIfNeed()) {
                return;
            }

            enemiesAccessor.getUnitById(id).moveAI();
            id = cycle.next();
            setCurrentUnitId(id);
        }

        removeDeadUnits();
        if (checkTheEndAndFinishGameIfNeed()) {
            return;
        }
        events.moveCompleted();
    }

    private Boolean checkTheEndAndFinishGameIfNeed() {
        if (!isEnd()) {
            return false;
        }

        SessionStatisticVm statistic = sessionStatisticBuilder.build();
        sessionStatisticBuilder.reset();

        events.gameEnd(new GameEndInfo(getTeamWinner(), statistic));

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