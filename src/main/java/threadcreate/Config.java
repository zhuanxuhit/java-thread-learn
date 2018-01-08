package threadcreate;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zhuanxu
 */
@Configuration
@ComponentScan("threadcreate")
@EnableAsync
public class Config {
}
