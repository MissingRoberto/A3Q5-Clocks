package de.uni_stuttgart.ipvs.ids.clocks.cristian;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import de.uni_stuttgart.ipvs.ids.clocks.Clock;
import de.uni_stuttgart.ipvs.ids.communicationLib.VSDatagramSocket;

public class Timeserver implements Runnable {

	private Clock clock;
	private VSDatagramSocket serverSocket;

	public Timeserver(Clock clock, InetSocketAddress address) {

		this.clock = clock;
		serverSocket = new VSDatagramSocket(address);

	}

	public void run() {
		// TODO: Implement me

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		while (true) {
			DatagramPacket rp = new DatagramPacket(receiveData,
					receiveData.length);
			serverSocket.receive(rp);

			sendData = ("" + clock.getTime()).getBytes();
			DatagramPacket sp = new DatagramPacket(sendData, sendData.length,
					rp.getAddress(), rp.getPort());
			try {
				serverSocket.send(sp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("server to " + (new String(rp.getData())) + " "
					+ clock.getTime());
		}
	}

}
