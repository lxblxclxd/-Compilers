package parser;
import java.io.*;
import java.util.*;
import inter.*;
import lexer.*;

public class Parser {
	private Lexer lexer;//����﷨�������Ĵʷ�������
	public Token look;//��ǰ���ʷ���Ԫ
	private LL1 ll1 = new LL1(this);
	public LinkedList<String> list = new LinkedList<String>();//ջ
	public String production;
	public Parser(Lexer l) throws IOException {
		lexer = l;
		move();
		list.push("$");
		list.push("program");
	}

	void move() throws IOException { look=lexer.scan(); }
	void error(String s) { throw new Error("near line " + lexer.line + ": " + s); }
	void match (String s) throws IOException {
		if( look.toString().equals(s)||look.getTag().equals(s)) {
			move();
			System.out.println("ƥ�䵽 " + s);
		}
		else {
			error("syntax error");
			list.push(s);
		}
	}
	
	public void run() throws IOException {
		boolean flag=true;
		
		while (true) {
			flag = ll1.step(this);
			if(production != null)
				System.out.println(production);
			if(flag) {
				String string = list.pop();
				if(!string.equals("$"))
					match(string);
				else
					break;
			}
			if(look == null)
				break;
		}
		System.out.println("�﷨������ɣ�");
	}
	
}
