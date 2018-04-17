package uni.master.entity;

public class ColorXY {
    public final int x, y;

    public ColorXY(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "ColorXY{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
