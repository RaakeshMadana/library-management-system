package library;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public List<Greeting> greeting(@RequestParam(value="name", defaultValue="World") String name, @RequestParam(value="name1", defaultValue="World1") String name1) {
		Greeting greeting1 = new Greeting(counter.incrementAndGet(), String.format(template, name));
		Greeting greeting2 = new Greeting(counter.incrementAndGet(), String.format(template, name1));
		List<Greeting> greetings = new ArrayList<Greeting>();
		greetings.add(greeting1);
		greetings.add(greeting2);
		return greetings;
	}

}
