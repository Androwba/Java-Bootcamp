package edu.school21.printer;

import edu.school21.renderer.Renderer;
import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String message) {
        String dateTime = LocalDateTime.now().toString();
        renderer.render(dateTime + " " + message);
    }
}
