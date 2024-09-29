package edu.school21.app;

import edu.school21.preprocessor.PreProcessor;
import edu.school21.preprocessor.PreProcessorToUpperImpl;
import edu.school21.printer.Printer;
import edu.school21.printer.PrinterWithPrefixImpl;
import edu.school21.renderer.Renderer;
import edu.school21.renderer.RendererStandardImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ApplicationContext;

public class Program {
    public static void main(String[] args) {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererStandardImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Manual Prefix");
        printer.print("Hello");

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer print = context.getBean("printerWithPrefix", Printer.class);
        print.print("Hello");

        Printer printErr = context.getBean("printerWithDateTime", Printer.class);
        printErr.print("Hello");

        ((ConfigurableApplicationContext) context).close();
    }
}
