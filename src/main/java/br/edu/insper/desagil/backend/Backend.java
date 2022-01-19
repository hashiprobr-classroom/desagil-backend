// Não é necessário entender ou modificar este arquivo.

package br.edu.insper.desagil.backend;

import br.pro.hashi.nfp.dao.Firebase;
import br.pro.hashi.nfp.rest.server.RestServer;

public class Backend {
	public static void main(String[] args) {
		Builder builder = new Builder("main");
		Firebase firebase = builder.buildFirebase();
		RestServer server = builder.buildRestServer();
		boolean useTunnel = builder.useTunnel();

		firebase.connect();
		server.start(useTunnel);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			server.stop();
			firebase.disconnect();
			server.destroy();
			firebase.delete();
		}));
	}
}
