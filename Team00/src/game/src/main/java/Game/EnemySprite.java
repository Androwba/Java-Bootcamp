package Game;

public class EnemySprite extends Sprite {
    private GameMap gameMap;
    private int previousX;
    private int previousY;

    public EnemySprite(GameMap gameMap, int xCoord, int yCoord, String sign, String color) {
        super(xCoord, yCoord, sign, color);
        this.gameMap = gameMap;
        this.previousX = xCoord;
        this.previousY = yCoord;
    }

    public void move() {
        Point enemyPos = new Point(this.xCoord, this.yCoord);
        Point playerPos = new Point(gameMap.getPlayer().getPositionX(), gameMap.getPlayer().getPositionY());
        Point nextMove = ChaseLogic.getNextMove(enemyPos, playerPos, gameMap.getCharMap());

        if (isAvailable(nextMove.x, nextMove.y)) {
            this.previousX = this.xCoord;
            this.previousY = this.yCoord;
            this.xCoord = nextMove.x;
            this.yCoord = nextMove.y;
        }
    }

    private boolean isAvailable(int xCoord, int yCoord) {
        if ((xCoord < 0 || xCoord >= this.gameMap.getSize()) || (yCoord < 0 || yCoord >= this.gameMap.getSize())) {
            return false;
        }
        Sprite[][] map = this.gameMap.getMap();
        if (map[yCoord][xCoord] != null) {
            String spriteName = map[yCoord][xCoord].getClass().getSimpleName();
            if (spriteName.equals("WallSprite") || spriteName.equals("EnemySprite") || spriteName.equals("TargetSprite")) {
                return false;
            }
        }
        for (EnemySprite enemy : this.gameMap.getEnemies()) {
            if (enemy != this && enemy.getPositionX() == xCoord && enemy.getPositionY() == yCoord) {
                return false;
            }
        }
        return true;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }
}
