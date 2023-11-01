package Game.Characters;

import Game.CharacterGetters.CompositeAccessor;
import Game.CharacterGetters.UnitsAccessor;
import Game.Effects.Effectable;
import Helpers.SafeInput;

import java.util.ArrayList;

public abstract class GameUnit {
    private String name;
    private final int maxHp;
    private int hp;
    private ArrayList<Effectable> effects;

    public GameUnit(String name, int hp) {
        this.name = name;
        maxHp = this.hp = hp;
        effects = new ArrayList<>();
    }

    protected int inputUnitNumber(UnitsAccessor unitsAccessor) {
        int number;
        while (true) {
            number = SafeInput.getInt();
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

    public void takeDamage(int damage) {
        if (damage < 0)
            throw new IllegalArgumentException("damage");

        hp -= Math.min(hp, damage);
    }

    public void heal(int newHp) {
        if (!isAlive())
            return;

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

    public abstract void Move(CompositeAccessor accessor);

    public abstract void MoveAI(CompositeAccessor accessor);

    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "%-8s | %3s♥ | Ефектів: %1d",
                name, hp, effects.size()
        );
    }
}
