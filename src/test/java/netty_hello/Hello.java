package netty_hello;

import com.blade.Blade;
import com.blade.event.EventType;
import com.blade.mvc.http.EmptyBody;
import com.blade.security.web.csrf.CsrfMiddleware;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author biezhi
 * 2017/6/5
 */
public class Hello {

    public static void main(String[] args) {
        Blade.me()
//                .devMode(false)
//                .environment(Const.ENV_KEY_NETTY_WORKERS, Runtime.getRuntime().availableProcessors())
                .get("/hello", ctx -> ctx.text("Hello World."))
                .get("/error", ctx -> {
                    int a = 1 / 0;
                    ctx.text("Hello World.");
                })
                .post("/hello", ctx -> ctx.text("Hello World."))
                .put("/hello", ctx -> ctx.text("Hello World."))
                .delete("/hello", ctx -> ctx.text("Hello World."))
                .get("/download", ctx -> {
                    try {
                        ctx.response().download("hello.txt", new File("/Users/biezhi/workspace/projects/java/blade/src/test/resources/static/a.txt"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .before("/*", ctx -> {
                    System.out.println("Before...");
                })
                .get("/rand", ctx -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ctx.body(EmptyBody.empty());
                })
//                .use(new CsrfMiddleware())
                .event(EventType.ENVIRONMENT_CHANGED, new ConfigChanged())
                .start(Hello.class, args);
    }
}
