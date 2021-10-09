
public class Lift extends FullMap {
	int x;
	int y;

	// final static int X = 398;
	Lift(int x) {
		this.x = x;
		this.y = 244;
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

	public void LiftY(Car car, Flow flow) {
		if (this.getY() > car.getY() + 28) {
			this.up();
		} else if (this.getY() < car.getY() + 28) {
			this.down();
		} else {
			flow.setFlow(flow.getFlow() + 1);
		}
	}

	public void LiftY(int Y, Flow flow) {
		if (this.getY() > Y) {
			this.up();
		} else if (this.getY() < Y) {
			this.down();
		} else {
			flow.setFlow(flow.getFlow() + 1);
		}
	}
}
