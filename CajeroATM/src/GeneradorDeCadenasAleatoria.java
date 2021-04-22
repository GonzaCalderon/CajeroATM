import java.util.Random;

public class GeneradorDeCadenasAleatoria {

    public void generateRandomText(int cantidad) {
        char[] chars = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        System.out.print(output);
    }

    public static void main(String[] args) {
        GeneradorDeCadenasAleatoria test = new GeneradorDeCadenasAleatoria();
        for (int i = 0; i < 30000; i++){
            test.generateRandomText(8);
            System.out.print(",");
            test.generateRandomText(4);
            System.out.print(",");
            test.generateRandomText(11);
            System.out.print("\n");
        }
    }
}

