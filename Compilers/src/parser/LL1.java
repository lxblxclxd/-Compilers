package parser;

import java.io.IOException;

import lexer.Tag;
import lexer.Token;

public class LL1 {
	Parser parser;

	public LL1(Parser p) {
		parser = p;
	}

	public boolean step(Parser parser) throws IOException {
		parser.production = parser.list.pop();
		Token token = parser.look;
		switch (parser.production) {
		case "program":
			if (token.toString().equals("{")) {
				product("block");
				return false;
			}
			break;
		case "block":
			if (token.toString().equals("{")) {
				product("{", "decls", "stmts", "}");
				return true;
			}
			break;
		case "decls":
			if (token.tag == Tag.BASIC) {
				product("decl", "decls");
				return false;
			} else if (token.toString().equals("{") || token.tag == Tag.ID || token.tag == Tag.IF) {
				reduce();
				return false;
			}
			break;
		case "decl":
			if (token.tag == Tag.BASIC) {
				product("type", "ID", ";");
				return false;
			}
		case "type":
			if (token.tag == Tag.BASIC) {
				product("BASIC");
				return true;
			}
			break;
		case "stmts":
			if (token.toString().equals("{") || token.tag == Tag.ID || token.tag == Tag.IF) {
				product("stmt", "stmts");
				return false;
			} else if (token.toString().equals("}")) {
				reduce();
				return false;
			}
			break;
		case "stmt":
			if (token.toString().equals("{")) {
				product("block");
				return false;
			} else if (token.toString().equals("}")) {
				product("block");
				return false;
			} else if (token.tag == Tag.ID) {
				product("loc", "=", "expr", ";");
				return false;
			} else if (token.tag == Tag.IF) {
				product("IF", "(", "bool", ")", "stmt", "stmt'");
				return false;
			}
		case "stmt'":
			if (token.tag == Tag.ELSE) {
				product("ELSE", "stmt");
				return false;
			} else if (token.toString().equals("{") || token.tag == Tag.ID || token.tag == Tag.IF)
				reduce();
		case "loc":
			if (token.tag == Tag.ID) {
				product("ID");
				return true;
			}
			break;
		case "bool":
			if (token.toString().equals("(") || token.toString().equals("!") || token.tag == Tag.INT
					|| token.tag == Tag.FLOAT || token.tag == Tag.TRUE || token.tag == Tag.FALSE
					|| token.tag == Tag.ID) {
				product("join", "bool'");
				return false;
			}
			break;
		case "bool'":
			if (token.toString().equals(")")) {
				reduce();
				return false;
			} else { if (token.toString().equals("||"))
				product("||", "join", "bool'");
			return true;
			}
		case "join":
			if (token.toString().equals("(") || token.toString().equals("!") || token.tag == Tag.INT
					|| token.tag == Tag.FLOAT || token.tag == Tag.TRUE || token.tag == Tag.FALSE
					|| token.tag == Tag.ID) {
				product("rel", "join'");
				return false;
			}
			break;
		case "join'":
			if (token.toString().equals(")")||token.toString().equals("||")) {
				reduce();
				return false;
			} else { if (token.toString().equals("&&"))
				product("&&", "rel", "join'");
			return true;
			}
		case "rel":
			if (token.toString().equals("(") || token.toString().equals("!") || token.tag == Tag.INT
					|| token.tag == Tag.FLOAT || token.tag == Tag.TRUE || token.tag == Tag.FALSE
					|| token.tag == Tag.ID) {
				product("expr", "rel'");
				return false;
			}
			break;
		case "rel'":
			if (token.toString().equals(")")
					||token.toString().equals("||")
					||token.toString().equals("&&")) {
				reduce();
				return false;
			} else { if (token.tag == Tag.COMP)
				product(token.toString(), "expr");
			return true;
			}
		case "expr":
			if (token.toString().equals("(") || token.toString().equals("!") || token.tag == Tag.INT
					|| token.tag == Tag.FLOAT || token.tag == Tag.TRUE || token.tag == Tag.FALSE
					|| token.tag == Tag.ID) {
				product("term", "expr'");
				return false;
			}
			break;
		case "expr'":
			if (token.toString().equals(")") || token.toString().equals("&&") || token.toString().equals("||")
					|| token.tag == Tag.COMP) {
				reduce();
				return false;
			} else {
				if (token.toString().equals("+"))
					product("+", "term", "expr");
				else if (token.toString().equals("-"))
					product("-", "term", "expr");
				return true;
			}
		case "term":
			if (token.toString().equals("(") || token.toString().equals("!") || token.tag == Tag.INT
					|| token.tag == Tag.FLOAT || token.tag == Tag.TRUE || token.tag == Tag.FALSE
					|| token.tag == Tag.ID) {
				product("unary", "term'");
				return false;
			}
			break;
		case "term'":
			if (token.toString().equals(")") || token.toString().equals("&&") || token.toString().equals("||")
					|| token.tag == Tag.COMP) {
				reduce();
				return false;
			} else if (token.toString().equals("+"))
				product("+", "term", "expr");
			else if (token.toString().equals("-"))
				product("-", "term", "expr");
			break;
		case "unary":
			if (token.toString().equals("(") || token.tag == Tag.INT || token.tag == Tag.FLOAT || token.tag == Tag.TRUE
					|| token.tag == Tag.FALSE || token.tag == Tag.ID) {
				product("factor");
				return false;
			} else if (token.toString().equals("!"))
				product("!", "factor");
			else if (token.toString().equals("-"))
				product("-", "factor");
			break;
		case "factor":
			if (token.tag == Tag.ID)
				product("loc");
			else {
				if (token.toString().equals("("))
					product("(", "bool", ")");
				else if (token.tag == Tag.INT)
					product("INT");
				else if (token.tag == Tag.FLOAT)
					product("FLOAT");
				else if (token.tag == Tag.TRUE)
					product("TRUE");
				else if (token.tag == Tag.FALSE)
					product("FALSE");
				return true;
			}
			break;
		default: {
			parser.list.push(parser.production);
			parser.production = null;
			return true;
		}
		}
		return false;
	}

	private void reduce() throws IOException {
		parser.production += " -> " + " ¦Å";
	}

	private void addProduct(String s) {
		parser.production += s;
		parser.list.push(s);
	}

	private void addProduct(String s1, String s2) {
		parser.production += s1 + " " + s2;
		parser.list.push(s2);
		parser.list.push(s1);
	}

	private void addProduct(String s1, String s2, String s3) {
		parser.production += s1 + " " + s2 + " " + s3;
		parser.list.push(s3);
		parser.list.push(s2);
		parser.list.push(s1);
	}

	private void addProduct(String s1, String s2, String s3, String s4) {
		parser.production += s1 + " " + s2 + " " + s3 + " " + s4;
		parser.list.push(s4);
		parser.list.push(s3);
		parser.list.push(s2);
		parser.list.push(s1);
	}

	private void addProduct(String s1, String s2, String s3, String s4, String s5, String s6) {
		parser.production += s1 + " " + s2 + " " + s3 + " " + s4 + " " + s5 + " " + s6;
		parser.list.push(s6);
		parser.list.push(s5);
		parser.list.push(s4);
		parser.list.push(s3);
		parser.list.push(s2);
		parser.list.push(s1);
	}

	private void product(String s) {
		parser.production += " -> ";
		addProduct(s);
	}

	private void product(String s1, String s2) {
		parser.production += " -> ";
		addProduct(s1, s2);
	}

	private void product(String s1, String s2, String s3) {
		parser.production += " -> ";
		addProduct(s1, s2, s3);
	}

	private void product(String s1, String s2, String s3, String s4) {
		parser.production += " -> ";
		addProduct(s1, s2, s3, s4);
	}

	private void product(String s1, String s2, String s3, String s4, String s5, String s6) {
		parser.production += " -> ";
		addProduct(s1, s2, s3, s4, s5, s6);
	}
}
