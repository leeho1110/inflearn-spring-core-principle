package hello.core.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

	private final LogDemoService logDemoService;
	private final ObjectProvider<MyLogger> myLoggerObjectProvider;

	@RequestMapping("log-demo")
	@ResponseBody
	public String logDemo(HttpServletRequest request) throws InterruptedException {
		String requestURL = request.getRequestURL().toString();

		MyLogger myLogger = myLoggerObjectProvider.getObject();
		myLogger.setRequestURL(requestURL);

		myLogger.log("controller test");
		Thread.sleep(2000);
		logDemoService.logic("testId");

		return "OK";
	}
}
