package myclientsocket;

import java.util.HashMap;
import java.util.Map;

public class Cifrar {
    private static final Map <Character, Character> cifrado = new HashMap<>();

    static {
        cifrado.put('A', 'R');
        cifrado.put('B', 'T');
        cifrado.put('C', 'Q');
        cifrado.put('D', 'W');
        cifrado.put('E', 'Z');
        cifrado.put('F', 'U');
        cifrado.put('G', 'O');
        cifrado.put('H', 'S');
        cifrado.put('I', 'N');
        cifrado.put('J', 'Y');
        cifrado.put('K', 'P');
        cifrado.put('L', 'X');
        cifrado.put('M', 'V');
        cifrado.put('N', 'A');
        cifrado.put('O', 'C');
        cifrado.put('P', 'E');
        cifrado.put('Q', 'D');
        cifrado.put('R', 'B');
        cifrado.put('S', 'H');
        cifrado.put('T', 'M');
        cifrado.put('U', 'K');
        cifrado.put('V', 'I');
        cifrado.put('W', 'F');
        cifrado.put('X', 'L');
        cifrado.put('Y', 'G');
        cifrado.put('Z', 'J');
        cifrado.put('a', 'r');
        cifrado.put('b', 't');
        cifrado.put('c', 'q');
        cifrado.put('d', 'w');
        cifrado.put('e', 'z');
        cifrado.put('f', 'u');
        cifrado.put('g', 'o');
        cifrado.put('h', 's');
        cifrado.put('i', 'n');
        cifrado.put('j', 'y');
        cifrado.put('k', 'p');
        cifrado.put('l', 'x');
        cifrado.put('m', 'v');
        cifrado.put('n', 'a');
        cifrado.put('o', 'c');
        cifrado.put('p', 'e');
        cifrado.put('q', 'd');
        cifrado.put('r', 'b');
        cifrado.put('s', 'h');
        cifrado.put('t', 'm');
        cifrado.put('u', 'k');
        cifrado.put('v', 'i');
        cifrado.put('w', 'f');
        cifrado.put('x', 'l');
        cifrado.put('y', 'g');
        cifrado.put('z', 'j');
        cifrado.put('0', '5');
        cifrado.put('1', '6');
        cifrado.put('2', '7');
        cifrado.put('3', '8');
        cifrado.put('4', '9');
        cifrado.put('5', '0');
        cifrado.put('6', '1');
        cifrado.put('7', '2');
        cifrado.put('8', '3');
        cifrado.put('9', '4');
        cifrado.put('!', '@');
        cifrado.put('@', '#');
        cifrado.put('#', '$');
        cifrado.put('$', '%');
        cifrado.put('%', '&');
        cifrado.put('&', '*');
        cifrado.put('*', '!');
        cifrado.put(':', ';');
        cifrado.put(';', '.');
        cifrado.put('.', ',');
        cifrado.put(',', '?');
        cifrado.put('?', '!');
    }
    public static String cifrarMensaje(String mensaje) {
        StringBuilder mensajeCifrado = new StringBuilder();
        for (char letra : mensaje.toCharArray()) {
            char letraCifrada = cifrado.getOrDefault(letra, letra);
            mensajeCifrado.append(letraCifrada);
        }
        return mensajeCifrado.toString();
    }
}
