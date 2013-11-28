package de.uni_stuttgart.ipvs.ids.clocks.lamport;

import java.io.IOException;
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

	// private final static double maximal_drift_rate;

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
				Thread.sleep(delta);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
