package code;

import java.awt.Point;

public class Coordinate {
	
	int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean reached(Object o) {
		return o.getHitbox().contains(new Point(x,y));
	}
}
