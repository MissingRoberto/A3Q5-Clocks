package de.uni_stuttgart.ipvs.ids.clocks.cristian;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import de.uni_stuttgart.ipvs.ids.clocks.Clock;
import de.uni_stuttgart.ipvs.ids.communicationLib.VSDatagramSocket;

public class Timeclient implements Runnable {

	private Clock clock;
	private VSDatagramSocket clientSocket;
	private String name;

	private final static long delta = 1000;

	private final static double maximal_drift_rate = 0.17;

	private final static long tau = (long) (((double) delta) / (maximal_drift_rate * 2));

	InetSocketAddress server_address = new InetSocketAddress("localhost", 4000);

	public Timeclient(Clock clock, InetSocketAddress address, String name) {
		this.clock = clock;
		clientSocket = new VSDatagramSocket(address);
		this.name = name;
		System.out.println(tau);
	}

	public void run() {
		// TODO: Implement me!

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		while (true) {

			sendData = name.getBytes();
			DatagramPacket sp = new DatagramPacket(sendData, sendData.length,
					server_address.getAddress(), server_address.getPort());
			try {
				clientSocket.send(sp);

				DatagramPacket rp = new DatagramPacket(receiveData,
						receiveData.length);

				long t0 = clock.getTime();
				clientSocket.receive(rp);
				long cm = new Long((new String(rp.getData()).trim()));
				long t1 = clock.getTime();
				long delay = (t1 - t0) / 2;
				long currenttime = cm + delay;

				// Check the velocity of the clock. If it is faster we set t0.

				if (clock.getTime() <= cm) {
					clock.setTime(currenttime);
				} else {
					clock.setTime(t0);
				}

				System.out.println("clock " + name + " " + currenttime
						+ " delay " + delay);

				Thread.sleep(tau);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
