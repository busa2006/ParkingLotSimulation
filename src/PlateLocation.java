import java.util.ArrayList;

public class PlateLocation {
	private Plate plateArr[] = new Plate[50];
	private int w[] = { 136, 191, 242, 298, 349, 486, 539, 592, 645, 700 };
	private int h[] = { 122, 146, 172, 195, 219 };

	PlateLocation() {
		int k = 0;
		for (int i = 0; i < h.length; i++) {
			for (int j = 0; j < w.length; j++) {
				plateArr[k++] = new Plate(w[j], h[i]);
			}
		}
	}

	public int getW(int i) {
		return w[i];
	}

	public int getH(int i) {
		return h[i];
	}

	public Plate getPlateArr(int i) {
		return plateArr[i];
	}

	// ���� ������ �� ��ȯ
	public int vaildFloor() {
		int fullCount = 0;
		int floor;
		for (floor = 0; floor < 5; floor++) {
			for (int i = 0; i < 10; i++) {
				if (plateArr[(floor * 10) + i].isFull()) {
					fullCount++;
				}
			}
			if (fullCount < 10) {
				return floor;
			}
			fullCount = 0;

		}

		return -1;

	}

	// ���� ������ Plate ������ X�� �Ÿ� ��ȯ
	public int vaildPlate(PlateLocation plateLocation) {
		int floor = plateLocation.vaildFloor();
		for (int i = 0; i < 5; i++) {
			if (plateLocation.getPlateArr((floor * 10) + i).isFull() == false) {
				// plateLocation.getPlateArr((floor*10) + i).setFull(true);
				return plateLocation.getW(i);
			}
		}
		for (int i = 9; i > 4; i--) {
			if (plateLocation.getPlateArr((floor * 10) + i).isFull() == false) {
				// plateLocation.getPlateArr((floor*10) + i).setFull(true);
				return plateLocation.getW(i);
			}
		}
		return -1;
	}

	// ���� ������ Plate ������ X�� �Ÿ� ��ȯ
	public int vaildPlate(int floor, PlateLocation plateLocation) {

		for (int i = 0; i < 5; i++) {
			if (plateLocation.getPlateArr((floor * 10) + i).isFull() == false) {
				// plateLocation.getPlateArr((floor*10) + i).setFull(true);
				// System.out.println(plateLocation.getW(i));
				return plateLocation.getW(i);
			}
		}

		for (int i = 9; i > 4; i--) {
			if (plateLocation.getPlateArr((floor * 10) + i).isFull() == false) {
				// plateLocation.getPlateArr((floor*10) + i).setFull(true);
				// System.out.println(plateLocation.getW(i));
				return plateLocation.getW(i);
			}
		}
		return -1;
	}

	public int vaildPlateReverse(PlateLocation plateLocation) {

		for (int i = 5; i < 10; i++) {
			if (plateLocation.getPlateArr((40) + i).isFull() == false) {
				// plateLocation.getPlateArr((floor*10) + i).setFull(true);
				// System.out.println(plateLocation.getW(i));
				return plateLocation.getW(i);
			}
		}
		return -1;
	}

	// ���� ������ Plate�� �ε��� ��ȯ
	public int vaildPlate(PlateLocation plateLocation, int index) {
		int floor = plateLocation.vaildFloor();
		for (int i = 0; i < 5; i++) {
			if (plateLocation.getPlateArr((floor * 10) + i).isFull() == false) {
				// plateLocation.getPlateArr((floor*10) + i).setFull(true);
				return (floor * 10) + i;
			}
		}
		for (int i = 9; i > 4; i--) {
			if (plateLocation.getPlateArr((floor * 10) + i).isFull() == false) {
				// plateLocation.getPlateArr((floor*10) + i).setFull(true);
				return (floor * 10) + i;
			}
		}
		return -1;
	}

	public int vaildPlateReverse(PlateLocation plateLocation, ArrayList<Car> carArr) {

		for (int i = 0; i < carArr.size(); i++) {
			if (carArr.get(i).getFloor() == 5 && carArr.get(i).getPlate() == 6) {
				return carArr.get(i).getX();
			}
		}
		for (int i = 4; i < -1; i--) {
			if (plateLocation.getPlateArr(40 + i).isFull() == false) {
				// plateLocation.getPlateArr((floor*10) + i).setFull(true);
				return plateLocation.getW(i);
			}
		}
		return -1;
	}

	// �Ȱ��� ��ȣ �ִ��� Ȯ��
	public boolean reduplication(PlateLocation plateLocation, String carNumber) {
		for (int i = 0; i < 50; i++) {
			if (plateLocation.getPlateArr(i).getCarNumber().equals(carNumber)) {
				return false;
			}
		}
		return true;
	}

	// �Ȱ��� ��ȣ �ִ��� Ȯ��
	public boolean reduplication(ArrayList<Car> carArr, String carNumber) {
		for (int i = 0; i < carArr.size(); i++) {
			if (carArr.get(i).getCarNumber().equals(carNumber)) {
				return false;
			}
		}
		return true;
	}

	// ������ plate�� ��ġ�ϴ��� ����
	public void plateInspection(PlateLocation plateLocation, ArrayList<Car> carArr) {
		for (int i = 0; i < 50; i++) {
			plateLocation.getPlateArr(i).setFull(false);
			for (int j = 0; j < carArr.size(); j++) {

				if (plateLocation.getPlateArr(i).getX() == carArr.get(j).getX()
						&& plateLocation.getPlateArr(i).getY() == carArr.get(j).getY()) {
					plateLocation.getPlateArr(i).setCarNumber(carArr.get(j).getCarNumber());
					plateLocation.getPlateArr(i).setFull(true);
				}

			}
		}

	}

}
/*
 * (72,136),(72,191),(72,242)(72,298)(72,349)
 * (72,486)(72,539)(72,592)(72,645)(72,700)
 * (96,136)(96,191)(96,242)(96,298)(96,349)
 * (96,486)(96,539)(96,592)(96,645)(96,700)
 * (122,136)(122,191)(122,242)(122,298)(122,349)
 * (122,486)(122,539)(122,592)(122,645)(122,700)
 * (145,136)(145,191)(145,242)(145,298)(145,349)
 * (145,486)(145,539)(145,592)(145,645)(145,700)
 * (169,136)(169,191)(169,242)(169,298)(169,349)
 * (169,486)(169,539)(169,592)(169,645)(169,700)
 */