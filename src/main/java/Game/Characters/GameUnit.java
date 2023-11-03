package Game.Characters;

import Game.CharacterGetters.CompositeAccessor;
import Game.CharacterGetters.UnitsAccessor;
import Game.Effects.Effectable;
import Game.Exceptions.InvalidActionException;
import Game.Move;
import Game.Teams.Team;

import java.util.ArrayList;

public abstract class GameUnit {
    protected final CompositeAccessor accessor;
    private final int id;
    private String name;
    private final int maxHp;
    private int hp;
    private ArrayList<Effectable> effects;
    private Team team;

    public int getId() {
        return id;
    }

    public GameUnit(CompositeAccessor accessor, Team team, int id, String name, int hp) {
        this.accessor = accessor;
        this.team = team;
        this.id = id;
        this.name = name;
        maxHp = this.hp = hp;
        effects = new ArrayList<>();
    }

    protected int inputUnitNumber(UnitsAccessor unitsAccessor) {
        int number;
        while (true) {
            number = 1; //SafeInput.getInt();
            if (0 < number && number <= unitsAccessor.getQuantity()) {
                break;
            }
            System.out.println("Некоректний номер юніта");
        }
        return number;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public Team getTeam() {
        return team;
    }

    public ArrayList<Effectable> getEffects() {
        return new ArrayList<>(effects);
    }

    public void takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("damage");

        hp -= Math.min(hp, damage);
    }

    public void heal(int newHp) {
        if (!isAlive()) return;

        hp = Math.min(maxHp, hp + newHp);
    }

    public Boolean isAlive() {
        return hp != 0;
    }

    public void takeEffect(Effectable effect) {
        effects.add(effect);
    }

    public void executeEffects() {
        for (Effectable effect : effects) {
            effect.effect(this);
        }
        effects.removeIf(e -> e.getCyclesLeft() == 0);
    }

    public abstract void move(Move move) throws InvalidActionException;

    public abstract void moveAI();

    protected void sleep(long milliseconds) {
//        try {
//            Thread.sleep(milliseconds);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public String toString() {
        return String.format("%d | %-8s | %3s♥ | Ефектів: %1d", id, name, hp, effects.size());
    }
}
