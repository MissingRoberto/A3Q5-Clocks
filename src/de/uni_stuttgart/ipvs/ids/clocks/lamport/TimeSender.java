package de.uni_stuttgart.ipvs.ids.clocks.lamport;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.Set;

import de.uni_stuttgart.ipvs.ids.clocks.Clock;
import de.uni_stuttgart.ipvs.ids.communicationLib.VSDatagramSocket;

public class TimeSender implements Runnable {

	private Clock clock;
	private VSDatagramSocket senderSocket;
	private Set<InetSocketAddress> neighbourSet;
	private final static long delta = 1000;

	private final static double maximal_drift_rate = 0.17;

	private final static long tau = (long) (((double) delta) / (maximal_drift_rate * 2 * 2));

	public TimeSender(Clock clock, VSDatagramSocket socket,
			Set<InetSocketAddress> neighbourSet) {
		this.clock = clock;
		this.neighbourSet = neighbourSet;
		this.senderSocket = socket;

	}

	public void run() {
		// TODO: Implement me!

		byte[] sendData = new byte[1024];
		while (true) {
			try {
				sendData = (clock.getTime() + "").getBytes();
				for (InetSocketAddress neighbour : neighbourSet) {
					DatagramPacket sp = new DatagramPacket(sendData,
							sendData.length, neighbour.getAddress(),
							neighbour.getPort());

					senderSocket.send(sp);

				}
				Thread.sleep(tau);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
