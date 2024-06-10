import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class Cassino {


// Declaring ANSI_RESET so that we can reset the color
public static final String ANSI_RESET = "\u001B[0m";

public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";


private static int score = 10000;

public static void main(String[] args) {

Scanner option = new Scanner(System.in);
boolean isTheFirstPlay = true;

generateLogo();

do{
int number1 = 0;
int number2 = 0;
int number3 = 0;
if(score == 0) {
System.out.println(ANSI_RED + "YOU DON'T HAVE MORE CREDITS, BUY MORE CREDITS AND PLAY AGAIN" + System.lineSeparator()+ ANSI_RESET);
break;
}
if (isTheFirstPlay){
System.out.println(ANSI_YELLOW + "Deseja jogar? S ou N" + System.lineSeparator());
if(option.nextLine().toUpperCase().equals("S")) {
toPlay(number1, number2, number3);
}else {
break;
}
}else if(!isTheFirstPlay) {
toPlay(number1, number2, number3);
}else if (option.nextLine().toUpperCase().equals("S")){
toPlay(number1, number2, number3);
}
isTheFirstPlay = false;
System.out.println(ANSI_YELLOW + "Deseja jogar novamente? S ou N" + System.lineSeparator());
option  = new Scanner("S");
}while(option.nextLine().toUpperCase().equals("S"));
option.close();
}

private static void toPlay(int number1, int number2, int number3) {
//System.out.println(ANSI_YELLOW + "Iniciando jogo..." + System.lineSeparator());
       
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
String spaces = generateSpaces(screenSize);
Double amountSpaces = screenSize.getWidth() / 10;
System.out.println(ANSI_YELLOW + "Jogando..." + System.lineSeparator());
System.out.println(ANSI_GREEN + "  SCORE: " + score + System.lineSeparator() + ANSI_RESET);
        spaces = spaces.substring(0,60);
for(int i = 0; i < 100; i++ ) {
// gerar um número aleatório de 0 a 99
number1 = generateFirstNumber();
number2 = generateSecondNumber();
number3 = generateThirdNumber();

System.out.print("\r"+spaces+number1 +"|"+ number2 +"|"+ number3);
try {
Thread.sleep(100); // Espera um pouco para que você possa ver a mudança
} catch (InterruptedException e) {
e.printStackTrace();
}              
}
System.out.println("\n");
String message = "YOU LOSE";
if(number1 == number2
&& number1 == number3 ){
//&& number2 != number3 ){
message = "YOU WIN";
score += 10;
gerarResultado(message);
}else {
score -= 10;
gerarResultado(message);
}
}

private static String generateSpaces(Dimension screenSize) {
String spaces = screenSize.getWidth()+"";
StringBuilder sb = new StringBuilder();
for(int i =0; i< screenSize.getWidth(); i++) {
sb.append(" ");
}
return sb.toString();
}

private static int generateThirdNumber() {
Random random = new Random();
int number3 = random.nextInt(100);
return number3;
}

private static int generateSecondNumber() {
Random random = new Random();
int number2 = random.nextInt(100);
return number2;
}

private static int generateFirstNumber() {
Random random = new Random();
int number1 = random.nextInt(100);
return number1;
}

public static void gerarResultado(String message) {
Thread blinkThread = new Thread(() -> {
try {
while (true) {
System.out.print(ANSI_RED + "\r" + message + ANSI_RESET);
Thread.sleep(500); // tempo em milissegundos
System.out.print("\r              ");
Thread.sleep(500); // tempo em milissegundos
}
} catch (InterruptedException e) {
// e.printStackTrace();
}
});

blinkThread.start();

// Mantém o programa em execução
try {
Thread.sleep(5000); // tempo total de execução em milissegundos
} catch (InterruptedException e) {
e.printStackTrace();
}
blinkThread.interrupt();
}

private static void generateLogo() {

int width = 130;
int height = 30;
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
Graphics g = image.getGraphics();
g.setFont(new Font("SansSerif", Font.BOLD, 15));
Graphics2D graphics = (Graphics2D) g;
graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
graphics.drawString("Douglas's Casino", 5, 18);
for (int y = 0; y < height; y++) {
StringBuilder sb = new StringBuilder();
for (int x = 0; x < width; x++) {
sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");
}
if (sb.toString().trim().isEmpty()) {
continue;
}
System.out.println(ANSI_GREEN + sb);
}
}

}
