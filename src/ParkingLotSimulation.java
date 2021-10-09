import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ParkingLotSimulation extends JPanel {
	private static final int BOX_WIDTH = 847; // 전체 폭
	private static final int BOX_HEIGHT = 304; // 전체 높이
	private int removeIndex;
	private int removePlate;
	static int i = -1;
	PCB pcb = new PCB();
	private Flow patten = new Flow();
	private ArrayList<Lift> liftArr = new ArrayList<Lift>();
	private ArrayList<Car> carArr = new ArrayList<Car>();
	PlateLocation plateLocation = new PlateLocation();
	URL carURL = ParkingLotSimulation.class.getClassLoader().getResource("car.PNG");
	URL parkingLotURL = ParkingLotSimulation.class.getClassLoader().getResource("parkingLot.PNG");
	URL doorURL = ParkingLotSimulation.class.getClassLoader().getResource("door.PNG");
	URL liftURL = ParkingLotSimulation.class.getClassLoader().getResource("lift.PNG");
	private ImageIcon backgroundImg = new ImageIcon(parkingLotURL);
	private ImageIcon carImg = new ImageIcon(carURL);
	private ImageIcon doorImg = new ImageIcon(doorURL);
	private ImageIcon liftImg = new ImageIcon(liftURL);
	private static boolean inIng = false;
	private static boolean outIng = false;
	Door door = new Door();
	private OutPatten outPatten = new OutPatten();

	public ParkingLotSimulation() {
		liftArr.add(new Lift(62));
		liftArr.add(new Lift(409));
		liftArr.add(new Lift(755));
		InProcess inProcess = new InProcess();
		Flow flow = new Flow();
		FullMap fullMap = new FullMap();
		this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
		JTextField carNum = new JTextField(4);
		JButton in = new JButton("입고");
		ActionListener inListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (inIng == false && outIng == false &&
				// 같은 번호가 없어야 함
				plateLocation.reduplication(carArr, carNum.getText()) &&
				// 번호가 4자리여야 함
				carNum.getText().length() == 4 &&
				// 50대까지만 수용 가능
				carArr.size() < 50) {

					inIng = true;
					flow.setFlow(1);
					carArr.add(new Car(carNum.getText()));

				}

			}
		};
		in.addActionListener(inListener);

		JButton out = new JButton("출고");
		ActionListener outListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (inIng == false && outIng == false && plateLocation.reduplication(carArr, carNum.getText()) == false
						&&
				// 번호가 4자리여야 함
				carNum.getText().length() == 4) {
					outIng = true;
					flow.setFlow(1);

				}

			}
		};
		out.addActionListener(outListener);
		add(carNum);
		add(in);
		add(out);
		class MyThread extends Thread {
			public void run() {
				while (true) {
					// 입고
					if (inIng == true) {
						switch (flow.getFlow()) {
						case 1:
							liftArr.get(1).LiftY(95, flow);
							break;
						case 2:
							door.openDoor(door, flow);
							break;
						case 3:
							// 입구까지 이동
							inProcess.entrance(carArr.get(carArr.size() - 1), flow);
							break;
						case 4:
							door.closeDoor(door, flow);
							break;
						case 5:
							// 층까지 이동
							inProcess.floorY(carArr.get(carArr.size() - 1), plateLocation, flow, liftArr.get(1));
							break;
						case 6:
							// plate 까지 이동
							inProcess.plateX(carArr.get(carArr.size() - 1), plateLocation, flow);
							break;

						case 7:
							flow.setFlow(0);
							break;
						}

						if (flow.getFlow() == 0) {
							plateLocation.plateInspection(plateLocation, carArr);
							inIng = false;
						}
					}
					// 출고
					if (outIng == true) {
						switch (flow.getFlow()) {
						case 1:
							flow.setFlow(2);
							break;
						case 2:
							outPatten.carIndexSet(carArr);
							removeIndex = outPatten.removeCarSearch(carArr, carNum.getText());
							removePlate = carArr.get(removeIndex).getPlate();
							if (carArr.get(removeIndex).getFloor() == 5) {
								patten.setFlow(4);
							}
							if (removePlate == 5 || removePlate == 7) {
								flow.setFlow(3);
								pcb.setRe(0);
							} else if (removePlate == 4) {
								flow.setFlow(4);
								pcb.setRe(1);
								pcb.setCount(5);
							} else if (removePlate == 3) {
								flow.setFlow(4);
								pcb.setRe(2);
								pcb.setCount(4);
							} else if (removePlate == 2) {
								flow.setFlow(4);
								pcb.setRe(2);
								pcb.setCount(3);
							} else if (removePlate == 1) {
								flow.setFlow(4);
								pcb.setRe(2);
								pcb.setCount(2);
							} else if (removePlate == 8) {
								flow.setFlow(4);
								pcb.setRe(1);
								pcb.setCount(7);
							} else if (removePlate == 9) {
								flow.setFlow(4);
								pcb.setRe(2);
								pcb.setCount(8);
							} else if (removePlate == 10) {
								flow.setFlow(4);
								pcb.setRe(2);
								pcb.setCount(9);
							} else if (removePlate == 11) {
								flow.setFlow(4);
								pcb.setRe(2);
								pcb.setCount(10);
							}
							break;
						case 3:
							flow.setFlow(-1);
							break;
						case 4:
							// 반복되는 부분
							pcb.setFloor(carArr.get(removeIndex).getFloor());
							if (pcb.getCount() < 6) {
								pcb.setOrder(pcb.getOrder() - 1);
							} else if (pcb.getCount() > 6) {
								pcb.setOrder(pcb.getOrder() + 1);
							}

							pcb.setPlate(pcb.getOrder());
							if (patten.getFlow() != 4) {
								patten.setFlow(1);
							}

							if (patten.getFlow() == 4) {
								if (pcb.getCount() < 6) {
									flow.setFlow(-2);
								} else if (pcb.getCount() > 6) {
									flow.setFlow(-22);
								}
							} else if (pcb.getCount() < 6) {
								flow.setFlow(-3);
							} else if (pcb.getCount() > 6) {
								flow.setFlow(-33);
							}

							if (pcb.getOrder() == pcb.getCount()) {
								pcb.setRe(1);
							}
							break;
						case 5:
							door.closeDoor(door, flow);
							break;
						case 6:
							flow.setFlow(0);
							break;
						case -1:
							outPatten.pattenRemove(removeIndex, fullMap, carArr, flow, patten, liftArr.get(1), door);
							break;
						// 주차한 차 앞에 다른 차가 있을 때
						case -2:
							if (patten.getFlow() > 3) {
								outPatten.upperMove(carArr, pcb, plateLocation, flow, patten, liftArr.get(1));
							} else {
								outPatten.upperMove(carArr, pcb, plateLocation, flow, patten, liftArr.get(0));
							}
							break;
						case -3:
							if (patten.getFlow() > 3) {
								outPatten.underMove(carArr, pcb, plateLocation, flow, patten, liftArr.get(0));
							} else {
								outPatten.underMove(carArr, pcb, plateLocation, flow, patten, liftArr.get(1));
							}
							break;
						// 주차한 차 앞에 다른 차가 있을 때 + 밑이 꽉 차있을 때
						case -4:
							outPatten.pushRight(carArr, pcb, plateLocation, flow, patten, liftArr.get(1));
							break;
						case -5:
							outPatten.pushLeft(carArr, pcb, plateLocation, flow, patten, liftArr.get(0));
							break;
						case -22:
							if (patten.getFlow() > 3) {
								outPatten.upperMove_1(carArr, pcb, plateLocation, flow, patten, liftArr.get(1));
							} else {
								outPatten.upperMove_1(carArr, pcb, plateLocation, flow, patten, liftArr.get(2));
							}
							break;
						case -33:
							if (patten.getFlow() > 3) {
								outPatten.underMove_1(carArr, pcb, plateLocation, flow, patten, liftArr.get(2));
							} else {
								outPatten.underMove_1(carArr, pcb, plateLocation, flow, patten, liftArr.get(1));
							}
							break;
						// 주차한 차 앞에 다른 차가 있을 때 + 밑이 꽉 차있을 때
						case -44:
							outPatten.pushRight_1(carArr, pcb, plateLocation, flow, patten, liftArr.get(2));
							break;
						case -55:
							outPatten.pushLeft_1(carArr, pcb, plateLocation, flow, patten, liftArr.get(1));
						}
						if (flow.getFlow() == 0) {
							// 종료
							pcb.setUnder(0);

							//
							pcb.setOrder(6);
							patten.setFlow(0);
							plateLocation.plateInspection(plateLocation, carArr);
							outIng = false;
						}
					}
					repaint();
					try {
						Thread.sleep(1);
					} catch (InterruptedException ex) {
					}
				}
			}
		}
		Thread t = new MyThread();
		t.start();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImg.getImage(), 0, 50, null);

		for (int j = 0; j < carArr.size(); j++) {
			g.drawImage(carImg.getImage(), carArr.get(j).getX(), carArr.get(j).getY(), null);
			g.drawString(carArr.get(j).getCarNumber(), carArr.get(j).getX() + 10, carArr.get(j).getY() + 15);
		}
		for (int j = 0; j < liftArr.size(); j++) {
			g.drawImage(liftImg.getImage(), liftArr.get(j).getX(), liftArr.get(j).getY(), null);
		}

		g.drawImage(doorImg.getImage(), 398, door.getY(), null);

		setOpaque(false);
		super.paintComponent(g);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("ParkingLotSimulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new ParkingLotSimulation());
		frame.pack();
		frame.setVisible(true);
	}
}