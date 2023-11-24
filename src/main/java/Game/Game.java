package Game;

import Game.Effects.Factories.EffectFactory;
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
import Game.Units.Getters.CompositeAccessor;
import Game.Units.Getters.TeamAccessor;
import Game.Units.Getters.UnitsAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Game {
    //region Fields
    private final ArrayList<GameUnit> units;

    private Boolean gameIsOn;

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

        events.setCycleLeftEvent(new OnCycleLeft(units, cycleActions, sessionStatisticBuilder));

        human = new Team(1, PlayerTypes.Human, "Human");
        ai = new Team(2, PlayerTypes.AI, "AI");

        TeamAccessor alliesAccessor = new TeamAccessor(units, human);
        TeamAccessor enemiesAccessor = new TeamAccessor(units, ai);
        UnitsAccessor unitsAccessor = new UnitsAccessor(units);
        compositeAccessor = new CompositeAccessor(alliesAccessor, enemiesAccessor, unitsAccessor);

        cycle = new GameCycle(compositeAccessor, events);

        UnitEventsAggregate unitEvents = new UnitEventsAggregate();
        unitEvents.setActionPerformedEvent(new OnAction(cycleActions));
        EffectFactory effectFactory = new EffectFactory(sessionStatisticBuilder);

        alliesFactory = new UnitFactory(compositeAccessor, human, unitEvents, sessionStatisticBuilder, effectFactory);
        enemyFactory = new UnitFactory(compositeAccessor, ai, unitEvents, sessionStatisticBuilder, effectFactory);
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
        cycle.resetFields();
        cycleActions.clear();
        sessionStatisticBuilder.reset();
    }

    public void addUnit(GameUnit unit) {
        units.add(unit);
    }

    public void next() throws GameIsNotStartedException {
        if (checkTheEndAndFinishGameIfNeed()) {
            return;
        }

        if (!gameIsOn) {
            throw new GameIsNotStartedException();
        }

        Integer id = cycle.next();
        setCurrentUnitId(id);
        events.moveCompleted();
    }

    public Boolean checkTheEndAndFinishGameIfNeed() {
        removeDeadUnits();
        if (!isEnd()) {
            return false;
        }

        events.gameEnd(new GameEndInfo(getTeamWinner(), sessionStatisticBuilder.build()));

        gameIsOn = false;
        return true;
    }

    private Boolean isEnd() {
        return compositeAccessor.getAlliesAccessor().getQuantity() == 0 || compositeAccessor.getEnemiesAccessor().getQuantity() == 0;
    }

    private Team getTeamWinner() {
        if (compositeAccessor.getAlliesAccessor().getQuantity() == 0) {
            return ai;
        } else {
            return human;
        }
    }

    private void removeDeadUnits() {
        List<GameUnit> deadUnits = units.stream().filter(u -> !u.isAlive()).toList();
        deadUnits.forEach(sessionStatisticBuilder::setDied);
        units.removeAll(deadUnits);
    }
}