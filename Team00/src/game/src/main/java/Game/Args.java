package Game;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")

public class Args {
    @Parameter(names = {"--enemiesCount", "-e"}) int enemiesCount;
    @Parameter(names = {"--wallsCount", "-w"}) int wallsCount;
    @Parameter(names = {"--size", "-s"}) int size;
    @Parameter(names = {"--profile", "-p"}) String profile;

    public void checkArgs() {
        try {
            if ((size * size - enemiesCount - wallsCount - 2 < 0)
                    || (profile == null)) {
                throw new IllegalParameterException("Illegal parameters");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
