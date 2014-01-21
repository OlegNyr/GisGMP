import org.springframework.context.support.GenericXmlApplicationContext;
import ru.nyrk.gisgmp.core.ParsingTest;

public class App {
    public static void main(String[] args) throws Exception {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:app-context.xml");
        ctx.refresh();

        ctx.getBean("parsingTest", ParsingTest.class).run();


    }
}
