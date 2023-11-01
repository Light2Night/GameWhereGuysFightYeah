package Game.Characters;

import Game.Effects.Effectable;

public interface Heallable {
    Effectable getHealingEffect();
    int getHeal();
}
