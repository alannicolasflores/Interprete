package Administrador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
//C:\Users\ALANS\Documents\Interprete\Interprete\Interprete\prueba.txt
public class Interprete {
    static boolean existenErrores = false;

    public static void main(String[] args) throws IOException {
        TablaSimbolos tablaSimbolos = new TablaSimbolos();

        if (args.length > 1) {
            System.out.println("Uso correcto: interprete [script]");
            System.exit(64);
        } else if (args.length == 1) {
            ejecutarArchivo(args[0], tablaSimbolos);
        } else {
            ejecutarPrompt(tablaSimbolos);
        }
    }

    private static void ejecutarArchivo(String path, TablaSimbolos tablaSimbolos) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ejecutar(new String(bytes, Charset.defaultCharset()), tablaSimbolos);

        if (existenErrores) {
            System.exit(65);
        }
    }

    private static void ejecutarPrompt(TablaSimbolos tablaSimbolos) throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print(">>> ");
            String linea = reader.readLine();
            if (linea == null) {
                break;
            }
            ejecutar(linea, tablaSimbolos);
            existenErrores = false;
        }
    }

    private static void ejecutar(String source, TablaSimbolos tablaSimbolos) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        /*for(Token token : tokens){
            System.out.println(token);
        }*/

        Parser parser = new Parser(tokens);
        parser.parse();

        GeneradorPostfija gpf = new GeneradorPostfija(tokens);

        List<Token> postfija = gpf.convertir();
        for(Token token : postfija){
            System.out.println(token);
        }

        GeneradorAST gast = new GeneradorAST(postfija, tablaSimbolos);

        Arbol programa = gast.generarAST();
        programa.recorrer();
    }

    static void error(int linea, String mensaje) {
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje) {
        System.err.println("[linea " + linea + "] Error " + donde + ": " + mensaje);
        existenErrores = true;
    }
}
