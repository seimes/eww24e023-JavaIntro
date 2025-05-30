package at.technikum.exercises.voexercises.battleshiplight.consoleversion.board;

import at.technikum.exercises.voexercises.battleshiplight.core.ANSIIColors;
import at.technikum.exercises.voexercises.battleshiplight.core.Player;
import at.technikum.exercises.voexercises.battleshiplight.core.State;
import at.technikum.exercises.voexercises.battleshiplight.interfaces.BoardLike;

import java.awt.*;
import java.util.Random;

public class Board implements BoardLike<Field> {
    protected Field[][] fields;
    protected static final int scoreUpdate = 100;

    public Board(int size, boolean isComputer) {
        this.fields = new Field[size][size];
        this.init();
        if (isComputer) this.placeRandomShips();
    }

    protected void init() {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields.length; x++) {
                this.fields[y][x] = new Field();
            }
        }
    }

    protected void placeRandomShips() {
        for (int i = 0; i < this.fields.length; i++) {
            this.placeRandomShip();
        }
    }

    protected void placeRandomShip() {
        int freeFieldsAmount = this.getFreeFieldsAmount();
        Field[] emptyFields = new Field[freeFieldsAmount];

        int i = 0;
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields.length; x++) {
                Field field = this.fields[y][x];

                if (field.getState() == State.NO_SHIP_NO_HIT) {
                    emptyFields[i++] = field;
                }
            }
        }

        Random random = new Random();
        int randomFieldNumber = random.nextInt(emptyFields.length);
        emptyFields[randomFieldNumber].placeShip();
    }

    public int getFreeFieldsAmount() {
        int freeFieldsCounter = 0;

        for (int y = 0; y < this.fields.length; y++) {
            for (int x = 0; x < this.fields.length; x++) {
                Field field = this.fields[y][x];

                if (field.getState() == State.NO_SHIP_NO_HIT) {
                    freeFieldsCounter++;
                }
            }
        }
        return freeFieldsCounter;
    }

    public void updateStateAndScore(Point fieldCoordinate, Player player) {
        Field field = this.getFields()[fieldCoordinate.x][fieldCoordinate.y];

        if (field.getState() == State.NO_SHIP_NO_HIT) {
            field.setState(State.NO_SHIP_HIT);
            return;
        }
        if (field.getState() == State.SHIP_NO_HIT) {
            field.setState(State.SHIP_HIT);
            player.getGameState().updateScore(Board.scoreUpdate);
        }
    }

    public void print() {
        //this.printRepeatedChars('#', fields.length+2, true);
        System.out.print("   ");
        this.printNumbers(fields.length);
        System.out.println();

        for (int y = 0; y < this.fields.length; y++) {
            System.out.print(y + " ");

            for (int x = 0; x < this.fields.length; x++) {
                if (fields[y][x].getState() == State.SHIP_NO_HIT)
                    System.out.print("⛵ ");
                else if (fields[y][x].getState() == State.SHIP_HIT)
                    System.out.print("☠\uFE0F ");
                else if (fields[y][x].getState() == State.NO_SHIP_HIT) {
                    System.out.print(ANSIIColors.GRAY + "~~ " + ANSIIColors.RESET);
                } else
                    //System.out.print("\uD83C\uDF0A");
                    System.out.print(ANSIIColors.BLUE + "~~ " + ANSIIColors.RESET);
            }
            System.out.println();
        }
        //this.printRepeatedChars('#', fields.length+2, false);
        System.out.println();
    }

    protected void printRepeatedChars(char symbol, int amount, boolean trailingNewline) {
        for (int i = 0; i < amount; i++) {
            System.out.print(symbol);
        }

        if (trailingNewline) System.out.println();
    }

    protected void printNumbers(int start, int end, int step) {
        for (int i = start; i < end; i += step) {
            if (i == 0)
                System.out.printf("%d ", i);
            else
                System.out.printf(" %d ", i);
        }
    }

    protected void printNumbers(int start, int end) {
        this.printNumbers(start, end, 1);
    }

    private void printNumbers(int end) {
        this.printNumbers(0, end);
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public void resetBoard() {
        this.init();
        this.placeRandomShips();
    }

    @Override
    public Field[][] getFields() {
        return fields;
    }

    @Override
    public int getSize() {
        return fields.length;
    }
}
