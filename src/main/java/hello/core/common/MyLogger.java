package hello.core.common;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
// HTTP Request 당 하나씩 생성되고, HTTP Request이 끝나는 시점에 소멸된다.
public class MyLogger {

	private String uuid;
	private String requestURL;

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public void log(String message) {
		System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
	}

	@PostConstruct
	public void init() {
		uuid = UUID.randomUUID().toString();
		System.out.println("[" + uuid + "] request scope bean CREATE:" + this);
	}

	@PreDestroy
	public void close() {
		System.out.println("[" + uuid + "] request scope bean CLOSE:" + this);
	}

}
