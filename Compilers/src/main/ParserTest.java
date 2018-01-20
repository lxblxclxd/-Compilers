package main;

import java.io.*;

import lexer.*;
import parser.*;

public class ParserTest {
		public static void main(String[] args) throws IOException {
			try {
				Lexer lexer = new Lexer(new BufferedReader(new FileReader("./src/main/test.txt")));
				Parser parser = new Parser(lexer);
				parser.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

}
