package lexer;

public class Word extends Token{
	public String lexeme = "";
	public Word(String s, int tag) {super(tag);lexeme=s;}
	public String toString() {return lexeme;}
	public String show() {return Tag.getTag(tag)+"\t:\t"+lexeme;}
	public static final Word
		True	=new Word("true"	, Tag.TRUE	),
		False	=new Word("false"	, Tag.FALSE	),
		Temp	=new Word("t"		, Tag.TEMP	);
}
