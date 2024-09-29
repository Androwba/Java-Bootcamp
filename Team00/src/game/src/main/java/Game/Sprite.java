package Game;

import static com.diogonunes.jcolor.Attribute.*;

import com.diogonunes.jcolor.Attribute;

public class Sprite {
    protected int xCoord;
    protected int yCoord;
    protected String sign;
    protected Attribute colorRGB;

    public Sprite(int xCoord, int yCoord, String sign, String color) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.sign = sign;
        this.colorRGB = BACK_COLOR(
                Colors.getColorRBG(color)[0],
                Colors.getColorRBG(color)[1],
                Colors.getColorRBG(color)[2]);
    }

    public int getPositionX() {
        return xCoord;
    }

    public int getPositionY() {
        return yCoord;
    }

    public String getSign() {
        return sign;
    }

    public Attribute getColor() {
        return this.colorRGB;
    }

    protected boolean isCollision(Sprite object) {
        return (this.xCoord == object.getPositionX()
                && this.yCoord == object.getPositionY())
                ? true
                : false;
    }
}
