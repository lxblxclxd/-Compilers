package lexer;

public class Tag {
	public final static int 
		BASIC	=257,	BREAK	=258,	DO		=259,	ELSE	=260,	FALSE	=261,
		ID		=262,	IF		=263,	INDEX	=264,	MINUS	=265,	INT		=266,
		FLOAT	=267,	TEMP	=268,	TRUE	=269,	WHILE	=270,	OPER	=271,
		COMP	=272,	ERROR	=273,	STR		=274,	SYMBOL	=275;
	static String getTag(int tag){
		switch (tag) {
		case ID:
			return "ID";
		case INT:
			return "INT";
		case FLOAT:
			return "FLOAT";
		case OPER:
			return "OPER";
		case COMP:
			return "COMP";
		case STR:
			return "STR";
		case SYMBOL:
			return "SYMBOL";
		case IF:
			return "IF";
		case ELSE:
			return "ELSE";
		case BASIC:
			return "BASIC";
		default:
			return "Syntax error on token";
		}
	}
}
