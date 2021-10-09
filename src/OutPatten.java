import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class OutPatten extends FullMap {
	Flow tmp = new Flow();
	int index;
	int index2;
	ArrayList<Integer> plateList = new ArrayList<Integer>();

	public void carIndexSet(ArrayList<Car> carArr) {

		for (int i = 0; i < carArr.size(); i++) {
			for (int j = 0; j < 13; j++) {
				if (carArr.get(i).getX() == getW(j)) {
					carArr.get(i).setPlate(j);
				}
			}

			for (int j = 0; j < 6; j++) {
				if (carArr.get(i).getY() == getH(j)) {
					carArr.get(i).setFloor(j);
				}
			}

		}
	}

	public int removeCarSearch(ArrayList<Car> carArr, String carNumber) {
		for (int i = 0; i < carArr.size(); i++) {
			if (carArr.get(i).getCarNumber().equals(carNumber)) {
				return i;
			}
		}
		return -1;
	}

	public void pattenRemove(int removeIndex, FullMap fullMap, ArrayList<Car> carArr, Flow flow, Flow patten, Lift lift,
			Door door) {

		if (tmp.getFlow() == 0) {
			lift.LiftY(carArr.get(removeIndex), tmp);
		} else if (tmp.getFlow() == 1) {
			moveX(carArr.get(removeIndex), fullMap.getW(6), tmp);
		} else if (tmp.getFlow() == 2) {
			moveY(carArr.get(removeIndex), fullMap.getH(0), tmp, lift);
		} else if (tmp.getFlow() == 3) {
			door.openDoor(door, tmp);
		} else if (tmp.getFlow() == 4) {
			moveX(carArr.get(removeIndex), fullMap.getW(0), tmp);
		} else if (tmp.getFlow() == 5) {
			// patten.setFlow(0);
			carArr.remove(removeIndex);
			tmp.setFlow(0);
			flow.setFlow(5);
		}
	}

	public void underMove(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation, Flow flow, Flow patten,
			Lift lift) {
		if (tmp.getFlow() == 0) {

			index = -1;

			if (patten.getFlow() != 4) {
				for (int i = 0; i < carArr.size(); i++) {
					if (carArr.get(i).getFloor() == pcb.getFloor() && carArr.get(i).getPlate() == pcb.getPlate()) {
						index = i;
						if (plateLocation.vaildPlate(pcb.getFloor(), plateLocation) == -1) {
							tmp.setFlow(5);
						} else {
							tmp.setFlow(1);
						}
					}
				}
			} else if (patten.getFlow() == 4) {
				for (int i = 0; i < carArr.size(); i++) {
					if (carArr.get(i).getFloor() == pcb.getFloor() && carArr.get(i).getPlate() == pcb.getPlate()) {
						index = i;
						if (plateLocation.vaildPlate(4, plateLocation) == -1) {
							tmp.setFlow(5);
						} else {
							tmp.setFlow(1);
						}
					}
				}
			}

			if (index == -1) {
				tmp.setFlow(5);
			}

		}

		else if (tmp.getFlow() == 1) {
			lift.LiftY(carArr.get(index), tmp);
		} else if (tmp.getFlow() == 2) {
			if (patten.getFlow() < 4) {
				moveX(carArr.get(index), getW(6), tmp);
			} else {
				moveX(carArr.get(index), getW(0), tmp);
			}
		} else if (tmp.getFlow() == 3) {
			moveY(carArr.get(index), getH(pcb.getFloor() + 1), tmp, lift);
		} else if (tmp.getFlow() == 4) {
			moveX(carArr.get(index), plateLocation.vaildPlate(pcb.getFloor(), plateLocation), tmp);
		} else if (tmp.getFlow() == 5) {
			plateLocation.plateInspection(plateLocation, carArr);
			carIndexSet(carArr);
			tmp.setFlow(0);

			//////////////////////////////////////////
			// 시작
			if (patten.getFlow() == 1) {
				flow.setFlow(-4);
				// 끝
			} else if (patten.getFlow() == 2) {
				flow.setFlow(3);
				// 반복
			} else if (patten.getFlow() == 3) {
				flow.setFlow(4);
			} else if (patten.getFlow() == 4) {
				flow.setFlow(-2);
				patten.setFlow(5);
			}
			// 반복 되는 부분
			//////////////////////////////////////////

		}
	}

	public void underMove_1(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation, Flow flow, Flow patten,
			Lift lift) {
		System.out.println("underMove : " + pcb.getFloor() + " " + pcb.getPlate());
		if (tmp.getFlow() == 0) {

			index = -1;
			for (int i = 0; i < carArr.size(); i++) {
				if (carArr.get(i).getFloor() == pcb.getFloor() && carArr.get(i).getPlate() == pcb.getPlate()) {
					index = i;
					if (plateLocation.vaildPlate(pcb.getFloor(), plateLocation) == -1) {
						tmp.setFlow(5);
					} else {
						tmp.setFlow(1);
					}
				}
			}
			if (index == -1) {
				tmp.setFlow(5);
			}
		} else if (tmp.getFlow() == 1) {
			lift.LiftY(carArr.get(index), tmp);
		} else if (tmp.getFlow() == 2) {
			if (patten.getFlow() == 4) {
				moveX(carArr.get(index), getW(12), tmp);
			} else {
				moveX(carArr.get(index), getW(6), tmp);
			}
		} else if (tmp.getFlow() == 3) {
			moveY(carArr.get(index), getH(pcb.getFloor() + 1), tmp, lift);
		} else if (tmp.getFlow() == 4) {
			moveX(carArr.get(index), plateLocation.vaildPlate(pcb.getFloor(), plateLocation), tmp);
		} else if (tmp.getFlow() == 5) {
			plateLocation.plateInspection(plateLocation, carArr);
			carIndexSet(carArr);
			tmp.setFlow(0);

			//////////////////////////////////////////
			// 시작
			if (patten.getFlow() == 1) {
				flow.setFlow(-55);
				// 끝
			} else if (patten.getFlow() == 2) {
				flow.setFlow(3);
				// 반복
			} else if (patten.getFlow() == 3) {
				flow.setFlow(4);
			} else if (patten.getFlow() == 4) {
				flow.setFlow(-22);
				patten.setFlow(5);
			}
			// 반복 되는 부분
			//////////////////////////////////////////

		}
	}

	public void upperMove(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation, Flow flow, Flow patten,
			Lift lift) {
		System.out.println("upperMove " + pcb.getFloor() + " " + pcb.getPlate());
		if (tmp.getFlow() == 0) {
			index = -1;
			index2 = -1;
			if (patten.getFlow() < 5) {
				for (int i = 0; i < carArr.size(); i++) {
					if (carArr.get(i).getFloor() == pcb.getFloor() && carArr.get(i).getPlate() == pcb.getPlate()) {
						index = i;
						if (plateLocation.vaildPlate(pcb.getFloor() - 2, plateLocation) == -1) {
							tmp.setFlow(5);
						} else {
							tmp.setFlow(1);
						}
					}
				}
				if (index == -1) {
					tmp.setFlow(5);
				}
			} else if (patten.getFlow() == 5) {
				for (int i = 0; i < carArr.size(); i++) {
					if (carArr.get(i).getFloor() == 5 && carArr.get(i).getPlate() == 1) {
						index = i;
					}
					if (carArr.get(i).getFloor() == 5 && carArr.get(i).getPlate() == 2) {
						index2 = i;
					}
				}

				if (index == -1 && index2 != -1) {
					tmp.setFlow(1);
				} else {
					tmp.setFlow(5);
				}
			}
		} else if (tmp.getFlow() == 1) {
			lift.LiftY(carArr.get(index), tmp);
		} else if (tmp.getFlow() == 2) {
			if (patten.getFlow() > 3) {
				moveX(carArr.get(index), getW(6), tmp);
			} else {
				moveX(carArr.get(index), getW(0), tmp);
			}
		} else if (tmp.getFlow() == 3) {
			moveY(carArr.get(index), getH(pcb.getFloor() - 1), tmp, lift);
		} else if (tmp.getFlow() == 4) {
			if (patten.getFlow() != 5) {
				moveX(carArr.get(index), plateLocation.vaildPlate(pcb.getFloor() - 2, plateLocation), tmp);
			} else {
				moveX(carArr.get(index), plateLocation.vaildPlateReverse(plateLocation, carArr), tmp);
			}
		} else if (tmp.getFlow() == 5) {
			plateLocation.plateInspection(plateLocation, carArr);
			carIndexSet(carArr);
			tmp.setFlow(0);
			// removeIndex == 4
			if (patten.getFlow() == 1) {
				pcb.setFloor(pcb.getFloor() - 1);
				if (pcb.getUnder() == 0) {
					pcb.setPlate(6);
				} else if (pcb.getUnder() == 1) {
					pcb.setPlate(underMoveIndex(carArr, pcb, plateLocation));
					pcb.setUnder(0);
				}
				flow.setFlow(-3);

				//////////////////////////////////////////
				// 반복 되는 부분
				// 끝
				if (pcb.getRe() == 1) {
					patten.setFlow(2);
					// 반복
				} else if (pcb.getRe() == 2) {
					patten.setFlow(3);
				}
				// 반복 되는 부분
				//////////////////////////////////////////
			}
			if (patten.getFlow() == 4 && pcb.getRe() != 1) {
				flow.setFlow(-4); // Floor 5일때
			} else if (patten.getFlow() == 5 && pcb.getRe() != 1) {
				flow.setFlow(4); // Floor 5일때
				patten.setFlow(4);
			} else if (patten.getFlow() > 3 && pcb.getRe() == 1) {
				flow.setFlow(3);
			}
		}
	}

	public void upperMove_1(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation, Flow flow, Flow patten,
			Lift lift) {
		System.out.println("upperMove " + pcb.getFloor() + " " + pcb.getPlate());
		if (tmp.getFlow() == 0) {

			index = -1;
			index2 = -1;
			if (patten.getFlow() < 5) {
				for (int i = 0; i < carArr.size(); i++) {
					System.out.println(carArr.get(i).getFloor() + " " + carArr.get(i).getPlate());
					if (carArr.get(i).getFloor() == pcb.getFloor() && carArr.get(i).getPlate() == pcb.getPlate()) {
						index = i;
						if (plateLocation.vaildPlate(pcb.getFloor() - 2, plateLocation) == -1) {
							tmp.setFlow(5);
						} else {
							tmp.setFlow(1);
						}
					}
				}
				if (index == -1) {
					tmp.setFlow(5);
				}
			} else if (patten.getFlow() == 5) {
				for (int i = 0; i < carArr.size(); i++) {
					if (carArr.get(i).getFloor() == 5 && carArr.get(i).getPlate() == 11) {
						index = i;
					}
					if (carArr.get(i).getFloor() == 5 && carArr.get(i).getPlate() == 10) {
						index2 = i;
					}
				}

				if (index == -1 && index2 != -1) {
					tmp.setFlow(1);
				} else {
					tmp.setFlow(5);
				}
			}

		} else if (tmp.getFlow() == 1) {
			lift.LiftY(carArr.get(index), tmp);
		} else if (tmp.getFlow() == 2) {

			if (patten.getFlow() > 3) {
				moveX(carArr.get(index), getW(6), tmp);
			} else {
				moveX(carArr.get(index), getW(12), tmp);
			}

		} else if (tmp.getFlow() == 3) {
			moveY(carArr.get(index), getH(pcb.getFloor() - 1), tmp, lift);
		} else if (tmp.getFlow() == 4) {
			// moveX(carArr.get(index), plateLocation.vaildPlate(pcb.getFloor() - 2,
			// plateLocation), tmp);
			if (patten.getFlow() < 4) {
				moveX(carArr.get(index), getW(11), tmp);
			} else if (patten.getFlow() > 3) {
				moveX(carArr.get(index), plateLocation.vaildPlateReverse(plateLocation), tmp);
			}
		} else if (tmp.getFlow() == 5) {
			plateLocation.plateInspection(plateLocation, carArr);
			carIndexSet(carArr);
			tmp.setFlow(0);
			// removeIndex == 4
			if (patten.getFlow() == 1) {
				pcb.setFloor(pcb.getFloor() - 1);
				if (pcb.getUnder() == 0) {
					pcb.setPlate(6);
				} else if (pcb.getUnder() == 1) {
					pcb.setPlate(underMoveIndex_1(carArr, pcb, plateLocation));
					pcb.setUnder(0);
				}
				flow.setFlow(-33);

				//////////////////////////////////////////
				// 반복 되는 부분
				// 끝
				if (pcb.getRe() == 1) {
					patten.setFlow(2);
					// 반복
				} else if (pcb.getRe() == 2) {
					patten.setFlow(3);
				} /*
					 * else if(pcb.getRe() == 3) {
					 * 
					 * }
					 */
				// 반복 되는 부분
				//////////////////////////////////////////
			}
			if (patten.getFlow() == 4 && pcb.getRe() != 1) {
				flow.setFlow(-55); // Floor 5일때
			} else if (patten.getFlow() == 5 && pcb.getRe() != 1) {
				flow.setFlow(4); // Floor 5일때
				patten.setFlow(4);
			} else if (patten.getFlow() > 3 && pcb.getRe() == 1) {
				flow.setFlow(3);
			}
			// 탈출하면 안됨 수정해야 함
		}
	}

	public int underMoveIndex(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation) {
		for (int j = 6; j > 1; j--) {
			for (int i = 0; i < carArr.size(); i++) {
				if (pcb.getFloor() == carArr.get(i).getFloor() && carArr.get(i).getPlate() == j
						&& plateLocation.vaildPlate(pcb.getFloor(), plateLocation) != -1) {
					return j;
				}
			}
		}
		return 6;
	}

	public int underMoveIndex_1(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation) {
		for (int j = 6; j < 11; j++) {
			for (int i = 0; i < carArr.size(); i++) {
				if (pcb.getFloor() == carArr.get(i).getFloor() && carArr.get(i).getPlate() == j
						&& plateLocation.vaildPlate(pcb.getFloor(), plateLocation) != -1) {
					return j;
				}
			}
		}
		return 6;
	}

	public void pushRight(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation, Flow flow, Flow patten,
			Lift lift) {
		if (tmp.getFlow() == 0) {
			index = -1;
			for (int j = 5; j > 0; j--) {
				for (int i = 0; i < carArr.size(); i++) {
					if (pcb.getFloor() == carArr.get(i).getFloor() && j == carArr.get(i).getPlate()) {
						plateList.add(i);
					}
					if (pcb.getFloor() == carArr.get(i).getFloor() && pcb.getPlate() == carArr.get(i).getPlate()) {
						index = i;
					}
				}

			}

			if (plateList.size() == 0) {
				tmp.setFlow(7);
			} else if (index == -1) {
				tmp.setFlow(7);
			} else {
				tmp.setFlow(1);
				pcb.setUnder(1);
			}
		} else if (tmp.getFlow() == 1) {
			lift.LiftY(carArr.get(plateList.get(0)), tmp);
		} else if (tmp.getFlow() == 2) {
			moveX(carArr.get(plateList.get(0)), getW(carArr.get(plateList.get(0)).getPlate() + 1), tmp);
		} else if (tmp.getFlow() == 3) {
			if (plateList.size() > 1) {
				moveX(carArr.get(plateList.get(1)), getW(carArr.get(plateList.get(1)).getPlate() + 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 4) {
			if (plateList.size() > 2) {
				moveX(carArr.get(plateList.get(2)), getW(carArr.get(plateList.get(2)).getPlate() + 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 5) {
			if (plateList.size() > 3) {
				moveX(carArr.get(plateList.get(3)), getW(carArr.get(plateList.get(3)).getPlate() + 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 6) {
			if (plateList.size() > 4) {
				moveX(carArr.get(plateList.get(4)), getW(carArr.get(plateList.get(4)).getPlate() + 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 7) {
			plateList.clear();
			plateLocation.plateInspection(plateLocation, carArr);
			carIndexSet(carArr);
			tmp.setFlow(0);
			if (patten.getFlow() == 1) {
				pcb.setFloor(pcb.getFloor() + 1);
				flow.setFlow(-5);
			} else if (patten.getFlow() == 4) {
				pcb.setFloor(pcb.getFloor() - 1);
				flow.setFlow(-5);// Floor가 5일때
			}
			// 탈출하면 안됨 수정해야 함
		}
	}

	public void pushLeft_1(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation, Flow flow, Flow patten,
			Lift lift) {
		if (tmp.getFlow() == 0) {
			index = -1;

			for (int j = 7; j < 12; j++) {
				for (int i = 0; i < carArr.size(); i++) {
					if (pcb.getFloor() == carArr.get(i).getFloor() && j == carArr.get(i).getPlate()) {
						plateList.add(i);
					}
					if (pcb.getFloor() == carArr.get(i).getFloor() && pcb.getPlate() == carArr.get(i).getPlate()) {
						index = i;
					}
				}

			}

			if (plateList.size() == 0) {
				tmp.setFlow(7);
			} else if (index == -1) {
				tmp.setFlow(7);
			} else {
				tmp.setFlow(1);
				pcb.setUnder(1);
			}
		} else if (tmp.getFlow() == 1) {
			lift.LiftY(carArr.get(plateList.get(0)), tmp);
		} else if (tmp.getFlow() == 2) {
			moveX(carArr.get(plateList.get(0)), getW(carArr.get(plateList.get(0)).getPlate() - 1), tmp);
		} else if (tmp.getFlow() == 3) {
			if (plateList.size() > 1) {
				moveX(carArr.get(plateList.get(1)), getW(carArr.get(plateList.get(1)).getPlate() - 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 4) {
			if (plateList.size() > 2) {
				moveX(carArr.get(plateList.get(2)), getW(carArr.get(plateList.get(2)).getPlate() - 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 5) {
			if (plateList.size() > 3) {
				moveX(carArr.get(plateList.get(3)), getW(carArr.get(plateList.get(3)).getPlate() - 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 6) {
			if (plateList.size() > 4) {
				moveX(carArr.get(plateList.get(4)), getW(carArr.get(plateList.get(4)).getPlate() - 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 7) {
			plateList.clear();
			plateLocation.plateInspection(plateLocation, carArr);
			carIndexSet(carArr);
			tmp.setFlow(0);
			if (patten.getFlow() == 1) {
				pcb.setFloor(pcb.getFloor() + 1);
				flow.setFlow(-44);
			} else if (patten.getFlow() == 4) {
				pcb.setFloor(pcb.getFloor() - 1);
				flow.setFlow(-44);
			}
			// 탈출하면 안됨 수정해야 함
		}
	}

	public int plateCount(int floor, ArrayList<Car> carArr) {
		int n = 0;
		for (int i = 0; i < carArr.size(); i++) {
			if (carArr.get(i).getFloor() == floor && carArr.get(i).getPlate() < 6) {
				n++;
			}
		}
		return n;
	}

	public void pushLeft(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation, Flow flow, Flow patten,
			Lift lift) {
		if (tmp.getFlow() == 0) {
			index = -1;
			index2 = -1;
			if (patten.getFlow() != 4) {
				for (int j = 1; j < 6; j++) {
					for (int i = 0; i < carArr.size(); i++) {
						if (pcb.getFloor() == carArr.get(i).getFloor() && j == carArr.get(i).getPlate()) {
							plateList.add(i);
						}
						if (pcb.getFloor() - 1 == carArr.get(i).getFloor() && 1 == carArr.get(i).getPlate()) {
							index = i; // 위 층 첫번째 자리에 있는지 확인
						}
						if (pcb.getFloor() - 1 == carArr.get(i).getFloor() && 2 == carArr.get(i).getPlate()) {
							index2 = i; // 위 층 두번째 자리에 있는지 확인
						}

					}

				}

			} else if (patten.getFlow() == 4) {
				for (int j = 1; j < 6; j++) { // Floor 5일때
					for (int i = 0; i < carArr.size(); i++) {
						if (pcb.getFloor() == carArr.get(i).getFloor() && j == carArr.get(i).getPlate()) {
							plateList.add(i);
						}
						if (pcb.getFloor() + 1 == carArr.get(i).getFloor() && 1 == carArr.get(i).getPlate()) {
							index = i; // 아래 층 첫번째 자리에 있는지 확인
						}
						if (pcb.getFloor() + 1 == carArr.get(i).getFloor() && 2 == carArr.get(i).getPlate()) {
							index2 = i; // 아래 층 두번째 자리에 있는지 확인
						}

					}

				}
			}

			if (index == -1 && index2 != -1) {
				tmp.setFlow(1);
			} else {
				tmp.setFlow(7);
			}

		} else if (tmp.getFlow() == 1) {
			lift.LiftY(carArr.get(plateList.get(0)), tmp);
		} else if (tmp.getFlow() == 2) {
			moveX(carArr.get(plateList.get(0)), getW(carArr.get(plateList.get(0)).getPlate() - 1), tmp);
		} else if (tmp.getFlow() == 3) {
			if (plateList.size() > 1) {
				moveX(carArr.get(plateList.get(1)), getW(carArr.get(plateList.get(1)).getPlate() - 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 4) {
			if (plateList.size() > 2) {
				moveX(carArr.get(plateList.get(2)), getW(carArr.get(plateList.get(2)).getPlate() - 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 5) {
			if (plateList.size() > 3) {
				moveX(carArr.get(plateList.get(3)), getW(carArr.get(plateList.get(3)).getPlate() - 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 6) {
			if (plateList.size() > 4) {
				moveX(carArr.get(plateList.get(4)), getW(carArr.get(plateList.get(4)).getPlate() - 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 7) {
			plateList.clear();
			plateLocation.plateInspection(plateLocation, carArr);
			carIndexSet(carArr);
			tmp.setFlow(0);
			if (patten.getFlow() == 1) {
				pcb.setPlate(0);
				flow.setFlow(-2);
			} else if (patten.getFlow() == 4) {
				pcb.setPlate(0);
				flow.setFlow(-3);
			}
			// 탈출하면 안됨 수정해야 함
		}
	}

	public void pushRight_1(ArrayList<Car> carArr, PCB pcb, PlateLocation plateLocation, Flow flow, Flow patten,
			Lift lift) {
		if (tmp.getFlow() == 0) {
			index = -1;
			index2 = -1;
			if (patten.getFlow() != 4) {
				for (int j = 11; j > 6; j--) {
					for (int i = 0; i < carArr.size(); i++) {
						if (pcb.getFloor() == carArr.get(i).getFloor() && j == carArr.get(i).getPlate()) {
							plateList.add(i);
						}
						if (pcb.getFloor() - 1 == carArr.get(i).getFloor() && 11 == carArr.get(i).getPlate()) {
							index = i; // 위 층 첫번째 자리에 있는지 확인
						}
						if (pcb.getFloor() - 1 == carArr.get(i).getFloor() && 10 == carArr.get(i).getPlate()) {
							index2 = i; // 위 층 두번째 자리에 있는지 확인
						}

					}

				}
			}
			if (patten.getFlow() == 4) {
				for (int j = 11; j > 6; j--) {
					for (int i = 0; i < carArr.size(); i++) {
						if (pcb.getFloor() == carArr.get(i).getFloor() && j == carArr.get(i).getPlate()) {
							plateList.add(i);
						}
						if (pcb.getFloor() + 1 == carArr.get(i).getFloor() && 11 == carArr.get(i).getPlate()) {
							index = i; // 아래 층 첫번째 자리에 있는지 확인
						}
						if (pcb.getFloor() + 1 == carArr.get(i).getFloor() && 10 == carArr.get(i).getPlate()) {
							index2 = i; // 아래 층 두번째 자리에 있는지 확인
						}

					}

				}
			}
			if (index == -1 && index2 != -1) {
				tmp.setFlow(1);
			} else {
				tmp.setFlow(7);
			}

		} else if (tmp.getFlow() == 1) {
			lift.LiftY(carArr.get(plateList.get(0)), tmp);
		} else if (tmp.getFlow() == 2) {
			moveX(carArr.get(plateList.get(0)), getW(carArr.get(plateList.get(0)).getPlate() + 1), tmp);
		} else if (tmp.getFlow() == 3) {
			if (plateList.size() > 1) {
				moveX(carArr.get(plateList.get(1)), getW(carArr.get(plateList.get(1)).getPlate() + 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 4) {
			if (plateList.size() > 2) {
				moveX(carArr.get(plateList.get(2)), getW(carArr.get(plateList.get(2)).getPlate() + 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 5) {
			if (plateList.size() > 3) {
				moveX(carArr.get(plateList.get(3)), getW(carArr.get(plateList.get(3)).getPlate() + 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 6) {
			if (plateList.size() > 4) {
				moveX(carArr.get(plateList.get(4)), getW(carArr.get(plateList.get(4)).getPlate() + 1), tmp);
			} else {
				tmp.setFlow(7);
			}
		} else if (tmp.getFlow() == 7) {
			plateList.clear();
			plateLocation.plateInspection(plateLocation, carArr);
			carIndexSet(carArr);
			tmp.setFlow(0);
			if (patten.getFlow() == 1) {
				pcb.setPlate(12);
				flow.setFlow(-22);
			} else if (patten.getFlow() == 4) {
				pcb.setPlate(6);
				flow.setFlow(-33);
			}
			// 탈출하면 안됨 수정해야 함
		}
	}

	private void moveY(Car car, int destination, Flow flow, Lift lift) {
		if (car.getY() > destination) {
			car.up();
			lift.up();
		} else if (car.getY() < destination) {
			car.down();
			lift.down();
		} else {
			flow.setFlow(flow.getFlow() + 1);
		}

	}

	private void moveX(Car car, int destination, Flow flow) {
		if (car.getX() > destination) {
			car.left();
		} else if (car.getX() < destination) {
			car.right();
		} else {
			flow.setFlow(flow.getFlow() + 1);

		}
	}

	private void moveY(Car car, int destination, Flow flow, int i) {
		if (car.getY() > destination) {
			car.up();
		} else if (car.getY() < destination) {
			car.down();
		} else {
			flow.setFlow(i);
		}

	}

	private void moveX(Car car, int destination, Flow flow, int i) {
		if (car.getX() > destination) {
			car.left();
		} else if (car.getX() < destination) {
			car.right();
		} else {
			flow.setFlow(i);

		}
	}
}
