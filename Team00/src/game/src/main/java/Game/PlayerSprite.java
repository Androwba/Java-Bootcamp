package Game;

import java.util.Scanner;

public class PlayerSprite extends Sprite {
    private Scanner scan;
    private GameMap gameMap;

    public PlayerSprite(GameMap gameMap, int xCoord,
                        int yCoord, String sign, String color) {
        super(xCoord, yCoord, sign, color);
        this.scan = new Scanner(System.in);
        this.gameMap = gameMap;
    }

    public void move() {
        String direction = null;
        boolean wrongDir = true;

        while (wrongDir) {
            direction = this.scan.nextLine();
            switch (direction) {
                case "w":
                    if (this.isAvailable(this.xCoord, this.yCoord - 1)) {
                        this.yCoord--;
                    }
                    wrongDir = false;
                    break;
                case "a":
                    if (this.isAvailable(this.xCoord - 1, this.yCoord)) {
                        this.xCoord--;
                    }
                    wrongDir = false;
                    break;
                case "s":
                    if (this.isAvailable(this.xCoord, this.yCoord + 1)) {
                        this.yCoord++;
                    }
                    wrongDir = false;
                    break;
                case "d":
                    if (this.isAvailable(this.xCoord + 1, this.yCoord)) {
                        this.xCoord++;
                    }
                    wrongDir = false;
                    break;
                case "9":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong direction!");
                    break;
            }
        }
    }

    private boolean isAvailable(int xCoord, int yCoord) {
        boolean result = true;
        if ((xCoord < 0 || xCoord >= this.gameMap.getSize())
                || (yCoord < 0 || yCoord >= this.gameMap.getSize())) {
            result = false;
        } else if (this.gameMap.getMap()[yCoord][xCoord] != null) {
            if (this.gameMap.getMap()[yCoord][xCoord]
                    .getClass().getSimpleName().equals("WallSprite")) {
                result = false;
            }
        }
        return result;
    }
}
