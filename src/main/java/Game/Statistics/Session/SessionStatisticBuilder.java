package Game.Statistics.Session;

import Game.Effects.EffectTypes;
import Game.Teams.Team;
import Game.Units.Characters.GameUnit;
import ViewModels.Statistics.Session.FullSessionStatisticVm;
import Game.Statistics.Session.DataCollectors.UnitStatisticCollector;
import ViewModels.Statistics.Session.SessionStatisticVm;
import ViewModels.Statistics.Session.TeamStatisticVm;
import ViewModels.Statistics.Session.UnitStatisticVm;

import java.util.Hashtable;
import java.util.Map;

public class SessionStatisticBuilder implements ISessionStatisticBuilder {
    private Map<GameUnit, UnitStatisticCollector> unitStatistics;
    private int cyclesCount;

    public SessionStatisticBuilder() {
        reset();
    }

    public void reset() {
        unitStatistics = new Hashtable<>();
        cyclesCount = 0;
    }

    @Override
    public FullSessionStatisticVm build() {
        Map<GameUnit, UnitStatisticVm> userStatisticVms = getUserStatisticVms();
        Map<Team, TeamStatisticVm> teamStatisticVms = getTeamStatisticVms(userStatisticVms);
        SessionStatisticVm sessionStatistic = getSessionStatisticVm(teamStatisticVms);

        return new FullSessionStatisticVm(userStatisticVms, teamStatisticVms, sessionStatistic);
    }

    private SessionStatisticVm getSessionStatisticVm(Map<Team, TeamStatisticVm> teamStatisticVms) {
        SessionStatisticVm vm = new SessionStatisticVm();

        for (Map.Entry<Team, TeamStatisticVm> entry : teamStatisticVms.entrySet()) {
            vm.Damage += entry.getValue().Damage;
            vm.Heal += entry.getValue().Heal;
            vm.ReceivedDamage += entry.getValue().ReceivedDamage;
            vm.ReceivedHeal += entry.getValue().ReceivedHeal;
            vm.DeadUnits.addAll(entry.getValue().DeadUnits);
            vm.ImposedEffects.addAll(entry.getValue().ImposedEffects);
            vm.ReceivedEffects.addAll(entry.getValue().ReceivedEffects);
        }

        vm.CyclesCount = cyclesCount;

        return vm;
    }

    private Map<Team, TeamStatisticVm> getTeamStatisticVms(Map<GameUnit, UnitStatisticVm> unitStatisticVms) {
        Map<Team, TeamStatisticVm> teamStatisticVms = new Hashtable<>();

        for (Map.Entry<GameUnit, UnitStatisticVm> entry : unitStatisticVms.entrySet()) {
            Team team = entry.getKey().getTeam();
            if (!teamStatisticVms.containsKey(team)) {
                teamStatisticVms.put(team, new TeamStatisticVm());
            }

            TeamStatisticVm teamVm = teamStatisticVms.get(team);
            UnitStatisticVm unitVm = entry.getValue();

            teamVm.Damage += unitVm.Damage;
            teamVm.Heal += unitVm.Heal;
            teamVm.ReceivedDamage += unitVm.ReceivedDamage;
            teamVm.ReceivedHeal += unitVm.ReceivedHeal;
            if (unitVm.IdDied) {
                teamVm.DeadUnits.add(entry.getKey());
            }
            teamVm.ImposedEffects.addAll(unitVm.ImposedEffects);
            teamVm.ReceivedEffects.addAll(unitVm.ReceivedEffects);
        }

        return teamStatisticVms;
    }

    private Map<GameUnit, UnitStatisticVm> getUserStatisticVms() {
        Map<GameUnit, UnitStatisticVm> unitStatisticVms = new Hashtable<>();
        for (Map.Entry<GameUnit, UnitStatisticCollector> entry : unitStatistics.entrySet()) {
            unitStatisticVms.put(entry.getKey(), new UnitStatisticVm(entry.getValue()));
        }
        return unitStatisticVms;
    }

    private UnitStatisticCollector getStatistic(GameUnit unit) {
        if (!unitStatistics.containsKey(unit)) {
            unitStatistics.put(unit, new UnitStatisticCollector());
        }

        return unitStatistics.get(unit);
    }

    @Override
    public IUnitStatisticCollector addDamage(GameUnit unit, int damage) {
        getStatistic(unit).addDamage(damage);
        return this;
    }

    @Override
    public IUnitStatisticCollector addHeal(GameUnit unit, int heal) {
        getStatistic(unit).addHeal(heal);
        return this;
    }

    @Override
    public IUnitStatisticCollector addReceivedDamage(GameUnit unit, int receivedDamage) {
        getStatistic(unit).addReceivedDamage(receivedDamage);
        return this;
    }

    @Override
    public IUnitStatisticCollector addReceivedHeal(GameUnit unit, int receivedHeal) {
        getStatistic(unit).addReceivedHeal(receivedHeal);
        return this;
    }

    @Override
    public IUnitStatisticCollector setDied(GameUnit unit) {
        getStatistic(unit).setDied();
        return this;
    }

    @Override
    public IUnitStatisticCollector addImposedEffect(GameUnit unit, EffectTypes effect) {
        getStatistic(unit).addImposedEffect(effect);
        return this;
    }

    @Override
    public IUnitStatisticCollector addReceivedEffect(GameUnit unit, EffectTypes effect) {
        getStatistic(unit).addReceivedEffect(effect);
        return this;
    }

    @Override
    public ICycleStatisticCollector addCycle() {
        cyclesCount++;
        return this;
    }
}
