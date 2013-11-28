package de.uni_stuttgart.ipvs.ids.clocks.lamport;

import java.net.DatagramPacket;

import de.uni_stuttgart.ipvs.ids.clocks.Clock;
import de.uni_stuttgart.ipvs.ids.communicationLib.VSDatagramSocket;

public class TimeReceiver implements Runnable {

	private Clock clock;
	private VSDatagramSocket receiverSocket;
	private final static long minimum_delay = 110;

	public TimeReceiver(Clock clock, VSDatagramSocket socket) {
		this.clock = clock;
		receiverSocket = socket;
	}

	public void run() {
		// TODO: Implement me!

		byte[] receiveData = new byte[1024];

		while (true) {
			DatagramPacket rp = new DatagramPacket(receiveData,
					receiveData.length);
			receiverSocket.receive(rp);

			long tm = new Long((new String(rp.getData()).trim()));
			
			clock.setTime(Math.max(clock.getTime(), tm + minimum_delay));
			
		}

	}

}
