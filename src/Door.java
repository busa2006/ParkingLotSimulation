
public class Door {
	int x;
	int y;
	// final static int X = 398;
	final static int close = 53;
	final static int open = 10;

	Door() {
		this.y = close;
	}

	public static int getClose() {
		return close;
	}

	public static int getOpen() {
		return open;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void up() {
		y--;
	}

	public void down() {
		y++;
	}

	public void openDoor(Door door, Flow flow) {
		if (door.getY() > open) {
			door.up();
		} else {
			flow.setFlow(flow.getFlow() + 1);

		}
	}

	public void closeDoor(Door door, Flow flow) {
		if (door.getY() < close) {
			door.down();
		} else {
			flow.setFlow(flow.getFlow() + 1);

		}
	}
}
