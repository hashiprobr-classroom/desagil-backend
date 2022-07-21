// NÃO É NECESSÁRIO ENTENDER OU MODIFICAR ESTE ARQUIVO.

package br.edu.insper.desagil.backend;

import java.io.IOException;

import br.pro.hashi.nfp.dao.Firebase;
import br.pro.hashi.nfp.rest.server.RESTServer;

public class Backend {
	public static void main(String[] args) throws IOException {
		Factory factory = new Factory("main");

		Firebase firebase = factory.buildFirebase();
		RESTServer server = factory.buildServer();
		boolean useTunnel = factory.useTunnel();

		firebase.connect();
		server.start(useTunnel);

		System.out.println(server.getUrl());

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			server.stop();
			firebase.delete();
		}));
	}
}
