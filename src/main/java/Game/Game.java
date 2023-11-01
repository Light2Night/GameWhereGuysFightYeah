package Game;

import Game.CharacterGetters.*;
import Game.Characters.Barbarian;
import Game.Characters.Healer;
import Game.Characters.Magician;
import Game.Characters.GameUnit;
import Game.Event.Eventable;
import Helpers.SafeInput;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Game {
    private ArrayList<GameUnit> allies;
    private ArrayList<GameUnit> enemies;

    private Boolean gameIsOn;

    private AlliesAccessor alliesAccessor;
    private EnemiesAccessor enemiesAccessor;
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
    public void setSelectedUnitIndex(int selectedUnitIndex) {
        this.selectedUnitIndex = selectedUnitIndex;
        if (selectedIndexChanged != null) selectedIndexChanged.onEvent();
    }

    public void setCurrentUnitId(int currentUnitIndex) {
        this.currentUnitIndex = currentUnitIndex;
        if (currentIndexChanged != null) currentIndexChanged.onEvent();
    }

    public void setSelectedIndexChanged(Eventable selectedIndexChanged) {
        this.selectedIndexChanged = selectedIndexChanged;
    }

    public void setCurrentIndexChanged(Eventable currentIndexChanged) {
        this.currentIndexChanged = currentIndexChanged;
    }

    public void setMoveCompleted(@Nullable Eventable moveCompleted) {
        this.moveCompleted = moveCompleted;
    }

    // Getters
    public ArrayList<GameUnit> getAllies() {
        return allies;
    }

    public ArrayList<GameUnit> getEnemies() {
        return enemies;
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

    // Methods
    private void reset() {
        gameIsOn = true;

        allies = new ArrayList<>();


        enemies = new ArrayList<>();


        alliesAccessor = new AlliesAccessor(allies);
        enemiesAccessor = new EnemiesAccessor(enemies);
        unitsAccessor = new UnitsAccessor(allies, enemies);
        compositeAccessor = new CompositeAccessor(alliesAccessor, enemiesAccessor, unitsAccessor);
        cycle = new GameCycle(compositeAccessor);

        allies.add(new Barbarian(compositeAccessor, 1));
        allies.add(new Magician(compositeAccessor, 2));
        allies.add(new Healer(compositeAccessor, 3));

        enemies.add(new Barbarian(compositeAccessor, 4));
        enemies.add(new Magician(compositeAccessor, 5));
        enemies.add(new Healer(compositeAccessor, 6));
    }

    private int inputUnitsQuantity(String message, int max) {
        int quantity;
        do {
            System.out.println(message);
            quantity = SafeInput.getInt();
        } while (!(1 <= quantity && quantity <= max));
        return quantity;
    }

    public GameUnit getUnitById(int id) {
        System.out.println(id);
        for (GameUnit unit : allies) {
            if (unit.getId() == id) {
                return unit;
            }
        }
        for (GameUnit unit : enemies) {
            if (unit.getId() == id) {
                return unit;
            }
        }
        return null;
    }

    private ArrayList<GameUnit> createTeam(int quantity) {
        ArrayList<GameUnit> units = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            int selectedType;
            do {
                System.out.printf("Вибір клас для юніта %d:\n", i + 1);
                System.out.println("1. Варвар");
                System.out.println("2. Маг");
                System.out.println("3. Цілитель");
                selectedType = SafeInput.getInt();
            } while (!(1 <= selectedType && selectedType <= 3));

            GameUnit unit = switch (selectedType) {
                case 1 -> new Barbarian(compositeAccessor, 0);
                case 2 -> new Magician(compositeAccessor, 0);
                case 3 -> new Healer(compositeAccessor, 0);
                default -> throw new IndexOutOfBoundsException();
            };

            units.add(unit);
        }

        return units;
    }

    private void createTeams() {
        int quantity = inputUnitsQuantity("Введіть кількість ваших юнітів (до 10 штук)", 10);
        System.out.println("Вкажіть типи для союзних юнітів");
        allies.addAll(createTeam(quantity));

        quantity = inputUnitsQuantity("Введіть кількість ворожих юнітів (до 10 штук)", 10);
        System.out.println("Вкажіть типи для ворожих юнітів");
        enemies.addAll(createTeam(quantity));
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

            if (i < allies.size()) {
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

    private Boolean checkTheEnd() {
        if (!isEnd()) {
            return false;
        }

        printFrame();
        if (allies.isEmpty()) {
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
        allies.forEach(GameUnit::executeEffects);
        enemies.forEach(GameUnit::executeEffects);
    }

    private void printFrame() {
        final String separator = "-----------------------------------------------------------------";

        System.out.println(separator);
        for (int i = 0; i < Math.max(allies.size(), enemies.size()); i++) {
            int number = i + 1;
            String ally = getUnitInfo(alliesAccessor, number, i);
            String enemy = getUnitInfo(enemiesAccessor, number + allies.size(), i);

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
        allies.removeIf(ally -> !ally.isAlive());
        enemies.removeIf(ally -> !ally.isAlive());
    }

    private Boolean isEnd() {
        return allies.isEmpty() || enemies.isEmpty();
    }
}