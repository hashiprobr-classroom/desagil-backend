// Não é necessário entender ou modificar este arquivo.

package br.edu.insper.desagil.backend;

import br.pro.hashi.nfp.dao.Firebase;
import br.pro.hashi.nfp.rest.server.RESTServer;

public class TestBackend {
	public static void main(String[] args) {
		Factory factory = new Factory("test");

		Firebase firebase = factory.createFirebase();
		firebase.connect();

		RESTServer server = factory.createRestServer();
		boolean useTunnel = factory.useTunnel();
		server.start(useTunnel);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			server.stop();
			firebase.delete();
		}));
	}
}
