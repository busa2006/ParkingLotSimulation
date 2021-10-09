
public class InProcess {

	// �Ա����� �̵�
	void entrance(Car car, Flow flow) {
		// 423 ��� ����Ʈ
		if (car.getX() < 423) {
			car.right();

			if (car.getX() == 423) {
				flow.setFlow(4);
			}
		}
	}

	// ���� ������ ������ �̵�
	void floorY(Car car, PlateLocation plateLocation, Flow flow, Lift lift) {
		if (car.getY() < plateLocation.getH(plateLocation.vaildFloor())) {
			car.down();
			lift.down();
			if (car.getY() == plateLocation.getH(plateLocation.vaildFloor())) {
				flow.setFlow(6);

			}
		}

	}

	// ���� ������ Plate���� �̵�
	void plateX(Car car, PlateLocation plateLocation, Flow flow) {
		if (car.getX() > plateLocation.vaildPlate(plateLocation)) {
			car.left();
		} else if (car.getX() < plateLocation.vaildPlate(plateLocation)) {
			car.right();
		} else if (car.getX() == plateLocation.vaildPlate(plateLocation)) {
			flow.setFlow(7);

			plateLocation.getPlateArr(plateLocation.vaildPlate(plateLocation, 0)).setFull(true);
			plateLocation.getPlateArr(plateLocation.vaildPlate(plateLocation, 0)).setCarNumber(car.getCarNumber());
		}
	}

}
