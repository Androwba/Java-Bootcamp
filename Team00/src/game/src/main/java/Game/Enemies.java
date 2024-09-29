package Game;

public class Enemies {
    private EnemySprite[] enemies;

    public Enemies(EnemySprite[] enemies) {
        this.enemies = enemies;
    }

    public boolean isCollision(Sprite object) {
        boolean result = false;
        for (EnemySprite es : enemies) {
            if(es.isCollision(object)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void move() {
        for (EnemySprite es : enemies) {
            es.move();
        }
    }
}
