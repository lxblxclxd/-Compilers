package main;

import java.io.*;
import lexer.*;
import parser.Parser;

public class LexerTest {

	public static void main(String[] args) throws IOException {
		try {
			Lexer lexer = new Lexer(new BufferedReader(new FileReader("./src/main/test.txt")));
			Token t;
			while ((t = lexer.scan()) != null) {
				System.out.println(t.show());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
