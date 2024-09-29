package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.client.Client;

@Parameters(separators = "=")
public class Main {

    @Parameter(names = "--server-port", description = "--server-port=8081", required = true)
    private Integer serverPort;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(main)
                .build();

        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            jCommander.usage();
            System.exit(1);
        }

        String host = "localhost";
        int port = main.serverPort;

        Client client = new Client();
        client.start(host, port);
    }
}
