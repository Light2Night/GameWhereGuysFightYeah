package Game;

import Game.Effects.Factories.EffectFactory;
import Game.Effects.SharedDatas.EffectSharedData;
import Game.Events.Aggregates.EffectEventsAggregate;
import Game.Events.Aggregates.UnitEventsAggregate;
import Game.Events.Arguments.Actions.ActionInfo;
import Game.Events.Arguments.GameEndInfo;
import Game.Events.Arguments.UnitId;
import Game.Events.Handlers.OnAction;
import Exceptions.GameIsNotStartedException;
import Game.Events.Aggregates.GameEventsAggregate;
import Game.Events.Handlers.OnCycleLeft;
import Game.Events.Handlers.OnUnitDied;
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

public class Game {
    //region Fields
    private final ArrayList<GameUnit> units;
    private final UnitsManager unitsManager;

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

        units = new ArrayList<>();
        sessionStatisticBuilder = new SessionStatisticBuilder();
        events = new GameEventsAggregate();
        cycleActions = new ArrayList<>();

        human = new Team(1, PlayerTypes.Human, "Human");
        ai = new Team(2, PlayerTypes.AI, "AI");

        events.CycleLeftEvent.addHandler(new OnCycleLeft(units, cycleActions, sessionStatisticBuilder));

        TeamAccessor alliesAccessor = new TeamAccessor(units, human);
        TeamAccessor enemiesAccessor = new TeamAccessor(units, ai);
        UnitsAccessor unitsAccessor = new UnitsAccessor(units);
        compositeAccessor = new CompositeAccessor(alliesAccessor, enemiesAccessor, unitsAccessor);

        cycle = new GameCycle(compositeAccessor, events);

        unitsManager = new UnitsManager(units, cycle, sessionStatisticBuilder);


        UnitEventsAggregate unitEvents = new UnitEventsAggregate();
        unitEvents.ActionPerformedEvent.addHandler(new OnAction(cycleActions));
        unitEvents.UnitDiedEvent.addHandler(new OnUnitDied(unitsManager));

        EffectEventsAggregate effectsEvents = new EffectEventsAggregate();

        EffectSharedData effectSharedData = new EffectSharedData(sessionStatisticBuilder, effectsEvents);
        EffectFactory effectFactory = new EffectFactory(effectSharedData);

        alliesFactory = new UnitFactory(compositeAccessor, human, unitEvents, sessionStatisticBuilder, effectFactory);
        enemyFactory = new UnitFactory(compositeAccessor, ai, unitEvents, sessionStatisticBuilder, effectFactory);
    }

    //region Setters
    public void setSelectedUnitIndex(@Nullable Integer selectedUnitIndex) {
        this.selectedUnitIndex = selectedUnitIndex;
        events.SelectedIndexChangedEvent.invoke(new UnitId(this.selectedUnitIndex));
    }

    public void setCurrentUnitIndex(@Nullable Integer currentUnitIndex) {
        this.currentUnitIndex = currentUnitIndex;
        events.CurrentIndexChangedEvent.invoke(new UnitId(this.currentUnitIndex));
    }
    //endregion

    //region Getters
    public ArrayList<GameUnit> getUnits() {
        return units;
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
        setCurrentUnitIndex(unitId);
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
        Integer id = cycle.next();

        if (checkTheEndAndFinishGameIfNeed()) {
            return;
        }

        if (!gameIsOn) {
            throw new GameIsNotStartedException();
        }

        setCurrentUnitIndex(id);
        events.MoveCompletedEvent.invoke();
    }

    public Boolean checkTheEndAndFinishGameIfNeed() {
        if (!isEnd()) return false;

        events.GameEndEvent.invoke(new GameEndInfo(getTeamWinner(), sessionStatisticBuilder.build()));

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
}