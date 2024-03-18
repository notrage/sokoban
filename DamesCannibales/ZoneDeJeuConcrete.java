import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ZoneDeJeuConcrete extends ZoneDeJeuAbstraite {
	public ZoneDeJeuConcrete(int height, int width) {
		super(height, width);
	}

	public ZoneDeJeuConcrete(String arg) {
		throw new UnsupportedOperationException();
	}
}
