package Game;

import Game.CharacterGetters.*;
import Game.Characters.Barbarian;
import Game.Characters.Healer;
import Game.Characters.Magician;
import Game.Characters.GameUnit;
import Game.Event.Eventable;
import Game.Teams.PlayerTypes;
import Game.Teams.Team;
import Helpers.SafeInput;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Game {
    private ArrayList<GameUnit> units;

    private Boolean gameIsOn;

    private TeamAccessor alliesAccessor;

    private TeamAccessor enemiesAccessor;
    private UnitsAccessor unitsAccessor;
    private CompositeAccessor compositeAccessor;
    private GameCycle cycle;

    private int selectedUnitIndex = 1;

    private int currentUnitIndex = 1;
    @Nullable
    private Eventable selectedIndexChanged;

    @Nullable
    private Eventable currentIndexChanged;
    @Nullable
    private Eventable moveCompleted;

    // Setters
    public ArrayList<GameUnit> getUnits() {
        return units;
    }

    public void setSelectedUnitIndex(int selectedUnitIndex) {
        this.selectedUnitIndex = selectedUnitIndex;
        if (selectedIndexChanged != null) selectedIndexChanged.onEvent();
    }

    public void setCurrentUnitId(int currentUnitIndex) {
        this.currentUnitIndex = currentUnitIndex;
        if (currentIndexChanged != null) currentIndexChanged.onEvent();
    }

    public void setSelectedIndexChanged(@Nullable Eventable selectedIndexChanged) {
        this.selectedIndexChanged = selectedIndexChanged;
    }

    public void setCurrentIndexChanged(@Nullable Eventable currentIndexChanged) {
        this.currentIndexChanged = currentIndexChanged;
    }

    public void setMoveCompleted(@Nullable Eventable moveCompleted) {
        this.moveCompleted = moveCompleted;
    }

    // Getters
    public GameUnit getCurrentUnit() {
        return unitsAccessor.getUnitByIndex(currentUnitIndex);
    }

    public int getSelectedUnitIndex() {
        return selectedUnitIndex;
    }

    public int getCurrentUnitIndex() {
        return currentUnitIndex;
    }

    // Methods
    private void reset() {
        gameIsOn = true;

        units = new ArrayList<>();

        Team human = new Team(1, PlayerTypes.Human, "Human");
        Team ai = new Team(2, PlayerTypes.AI, "AI");

        alliesAccessor = new TeamAccessor(units, human);
        enemiesAccessor = new TeamAccessor(units, ai);
        unitsAccessor = new UnitsAccessor(units);
        compositeAccessor = new CompositeAccessor(alliesAccessor, enemiesAccessor, unitsAccessor);
        cycle = new GameCycle(compositeAccessor);

        units.add(new Barbarian(compositeAccessor, human, 1));
        units.add(new Magician(compositeAccessor, human, 2));
        units.add(new Healer(compositeAccessor, human, 3));

        units.add(new Barbarian(compositeAccessor, ai, 4));
        units.add(new Magician(compositeAccessor, ai, 5));
        units.add(new Healer(compositeAccessor, ai, 6));
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

    private void makeCycle() {
        executeEffectsForAll();
        removeDeadUnits();

        if (checkTheEnd()) {
            return;
        }

        for (int i = 0; i < unitsAccessor.getQuantity(); i++) {
            printFrame();
            GameUnit currentUnit = unitsAccessor.getUnitByIndex(i);
            System.out.printf("Зараз хід юнітом - %s\n", currentUnit.toString());

            if (i < alliesAccessor.getQuantity()) {
                //currentUnit.Move(compositeAccessor);
            } else {
                //currentUnit.MoveAI(compositeAccessor);
            }

            i -= getQuantityOfUnitsWhichWillBeDeleted(i);
            setCurrentUnitId(unitsAccessor.getUnitByIndex(i).getId());
            removeDeadUnits();

            if (checkTheEnd()) {
                return;
            }
        }
    }

    public void next() {
        removeDeadUnits();
        int id = cycle.next();

        while (enemiesAccessor.containsId(id)) {
            enemiesAccessor.getUnitById(id).moveAI();

            id = cycle.next();
            removeDeadUnits();
        }

        setCurrentUnitId(id);

        if (moveCompleted != null) moveCompleted.onEvent();
    }

    public GameUnit getUnitById(int id) {
        System.out.println(id);
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

        printFrame();
        if (alliesAccessor.getQuantity() == 0) {
            System.out.println("Ви програми! Вашу групу знищили");
        } else {
            System.out.println("Ви перемогли! Вам вдалося знищити всіх противників");
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

    private void printFrame() {
        final String separator = "-----------------------------------------------------------------";

        System.out.println(separator);
        for (int i = 0; i < Math.max(alliesAccessor.getQuantity(), enemiesAccessor.getQuantity()); i++) {
            int number = i + 1;
            String ally = getUnitInfo(alliesAccessor, number, i);
            String enemy = getUnitInfo(enemiesAccessor, number + alliesAccessor.getQuantity(), i);

            System.out.printf("%s ‖ %s\n", ally, enemy);
        }
        System.out.println(separator);
    }

    private String getUnitInfo(Accessiable getter, int number, int index) {
        try {
            return String.format("%1d. %s", number, getter.getUnitByIndex(index).toString());
        } catch (IndexOutOfBoundsException ex) {
            return getFilledString(' ', 31);
        }
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