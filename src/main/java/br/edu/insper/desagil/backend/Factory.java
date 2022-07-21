// NÃO É NECESSÁRIO ENTENDER OU MODIFICAR ESTE ARQUIVO.

package br.edu.insper.desagil.backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.pro.hashi.nfp.dao.Firebase;
import br.pro.hashi.nfp.dao.FirebaseManager;
import br.pro.hashi.nfp.rest.server.RESTServer;
import br.pro.hashi.nfp.rest.server.RESTServerFactory;

public class Factory {
	private final Properties properties;

	public Factory(String name) throws IOException {
		String path = "%s.properties".formatted(name);
		InputStream stream = new FileInputStream(path);
		this.properties = new Properties();
		this.properties.load(stream);
	}

	private String get(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			throw new IllegalArgumentException("Property %s does not exist".formatted(key));
		}
		return value.strip();
	}

	public Firebase buildFirebase() {
		String path = get("credentials");
		FirebaseManager manager = Firebase.manager();
		return manager.getFromCredentials(path);
	}

	public RESTServer buildServer() {
		String endpointPrefix = "br.edu.insper.desagil.backend.endpoint";
		String converterPrefix = "br.edu.insper.desagil.backend.converter";
		int port = Integer.parseInt(get("port"));
		RESTServerFactory factory = RESTServer.factory();
		return factory.build(endpointPrefix, converterPrefix, port);
	}

	public boolean useTunnel() {
		return Boolean.parseBoolean(get("tunnel"));
	}
}
