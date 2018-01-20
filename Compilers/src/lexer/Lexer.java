package lexer;

import java.io.*;
import java.util.*;
import symbols.*;

public class Lexer {
	private BufferedReader bufferedReader;
	public static int line = 1;
	String linestr="";
	int pos = 0;
	char peek = ' ';
	Hashtable<String, Word> words = new Hashtable<String, Word>();
	public int state;
	public int tag;
	public StringBuffer buffer;

	void reserve(Word w) {
		words.put(w.lexeme, w);
	}

	public Lexer(BufferedReader br) {
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));
		reserve(Word.True);
		reserve(Word.False);
		reserve(Type.Bool);	reserve(Type.Char);
		reserve(Type.Int);	reserve(Type.Float);
		reserve(new Word("Syntax error on token ", Tag.ERROR));
		bufferedReader = br;
	}

	public boolean readch() throws IOException {
		if(pos==linestr.length()) {
			linestr = bufferedReader.readLine();
			if(linestr==null) {
				peek='\0';
				return false;
			}
			pos=0;
		}
		peek = linestr.charAt(pos);
		pos++;
		return true;
	}
	
	public boolean nextState() {
		state = DFA.getNextState(this);
		if(state!=3)
			buffer.append(peek);
		if(state>0)
			return true;
		else
			return false;
	}

	public Token scan() throws IOException {
		state = 0;
		tag = 0;
		buffer = new StringBuffer();
		boolean flag=true;
		for (;; flag=readch()) {
			if (peek == ' ' || peek == '\t')
				continue;
			else if (peek == '\n')
				line++;
			else
				break;
		}
		if(!flag) {
			return null;
		}

		
		while (nextState()) {
			if(state == 2 || state == 3)
				break;
			else
				readch();
		}
		if(state==2) {
			peek=' ';
			return getToken();
		}
		if(state==3||state==-1) {
			return getToken();
		}
		return null;
	}

	private Token getToken() {
		String s=buffer.toString();
		if(tag==Tag.INT) 
			return new IntegerMy(Integer.parseInt(s));
		else if(tag==Tag.FLOAT) 
			return new FloatMy(Float.parseFloat(s));
		else if(tag==Tag.STR) 
			return new StringMy(s);
		else if(tag==Tag.OPER||tag==Tag.SYMBOL) 
			return new Word(s, tag);
		else{
			Word word=(Word)words.get(s);
			if(word!=null) return word;
			word=new Word(s, tag);
			words.put(s, word);
			return word;
		}
	}

}
