package Game;

import Game.CharacterGetters.*;
import Game.Characters.Barbarian;
import Game.Characters.Healer;
import Game.Characters.Magician;
import Game.Characters.GameUnit;
import Helpers.UIAdapter;
import Helpers.SafeInput;

import java.util.ArrayList;

public class Game {
    private ArrayList<GameUnit> allies;
    private ArrayList<GameUnit> enemies;
    private Boolean gameIsOn;
    private AlliesAccessor alliesAccessor;
    private EnemiesAccessor enemiesAccessor;
    private UnitsAccessor unitsAccessor;
    private CompositeAccessor compositeAccessor;

    public UIAdapter uiAdapter = new UIAdapter(); // Адаптер для UI. Вважай ніби це API для UI

    private void reset() {
        gameIsOn = true;

        allies = new ArrayList<>();

        enemies = new ArrayList<>();

        alliesAccessor = new AlliesAccessor(allies);
        enemiesAccessor = new EnemiesAccessor(enemies);
        unitsAccessor = new UnitsAccessor(allies, enemies);
        compositeAccessor = new CompositeAccessor(alliesAccessor, enemiesAccessor, unitsAccessor);
        uiAdapter.setCompositeAccessor(compositeAccessor);
    }

    private int inputUnitsQuantity(String message, int max) {
        int quantity;
        do {
            System.out.println(message);
            quantity = SafeInput.getInt();
        } while (!(1 <= quantity && quantity <= max));
        return quantity;
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
                case 1 -> new Barbarian();
                case 2 -> new Magician();
                case 3 -> new Healer();
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
        createTeams();

        while (gameIsOn) {
            MakeCycle();
        }
    }

    private void MakeCycle() {
        executeEffectsForAll();
        removeDeadUnits();

        if (checkTheEnd()) {
            return;
        }

        for (int i = 0; i < unitsAccessor.getQuantity(); i++) {
            printFrame();
            GameUnit currentUnit = unitsAccessor.getUnit(i);
            System.out.printf("Зараз хід юнітом - %s\n", currentUnit.toString());

            if (i < allies.size()) {
                currentUnit.Move(compositeAccessor);
            } else {
                currentUnit.MoveAI(compositeAccessor);
            }

            i -= getQuantityOfUnitsWhichWillBeDeleted(i);
            removeDeadUnits();

            if (checkTheEnd()) {
                return;
            }
        }
    }

    private int currentUnitIndex = 0;
    public void next() {
        executeEffectsForAll();
        removeDeadUnits();
        uiAdapter.updateAllies(allies);
        uiAdapter.updateEnemies(enemies);

        currentUnitIndex++;
        if (currentUnitIndex >= unitsAccessor.getQuantity()) {
            currentUnitIndex = 0;
        }
        uiAdapter.updateCurrentUnit(unitsAccessor.getUnit(currentUnitIndex));
        if (!uiAdapter.isCurrentUnitAlly()) {
            uiAdapter.getCurrentUnit().getValue().MoveAI(compositeAccessor);
        }
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
            if (!unitsAccessor.getUnit(i).isAlive()) quantity++;
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
            return String.format("%1d. %s", number, getter.getUnit(index).toString());
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