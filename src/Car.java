import java.util.ArrayList;

public class Car {
	private int x;
	private int y;
	String carNumber;
	private int floor;
	private int plate;

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getPlate() {
		return plate;
	}

	public void setPlate(int plate) {
		this.plate = plate;
	}

	public Car(String carNumber) {
		this.x = 73;
		this.y = 67;
		this.carNumber = carNumber;

	}

	void right() {
		x++;
	}

	void down() {
		y++;
	}

	void left() {
		x--;
	}

	void up() {
		y--;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
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

}
