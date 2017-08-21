package cn.bluesking.spider.commons.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码与解码操作工具类
 * 
 * @author 随心
 *
 */
public final class CodecUtil {

	/**
	 * slf4j日志配置
	 */
	private static final Logger _LOG = LoggerFactory.getLogger(CodecUtil.class);

	/**
	 * 用于快速计算整型位数的数组
	 */
	private final static int [] size_table = { 9, 99, 999, 9999, 99999, 999999, 9999999,
			99999999, 999999999, Integer.MAX_VALUE };
	
	/**
	 * 计算传入整数的位数
	 * @param x [int]传入整数
	 * @return
	 */
	private static final  int stringSize(int x) {
		for (int i = 0;; i++)
			if (x <= size_table[i])
				return i + 1;
	}
	
	/**
	 * 编码html中的特殊字符成&#xxx;格式(8200ms)
	 * @param source [String]输入数据源
	 * @return
	 */
	public static final String encodeHTML(String source) {
		char[] in = source.toCharArray();
		char[] out = new char[in.length << 3];
		int outLen = 0;
		int end = in.length;
		int value;
		int numLen = 1;
		for(int i = 0; i < end; i ++) {
			value = in[i];
			if(value < 0) { // 汉字的ASCII码小于0
				throw new IllegalArgumentException(
						" 字符" + in[i] + "对应的value值必须为正数") ;
			}
			String tmp = encodeHTMLChar(in[i]);
			if(tmp != null) {
				char[] buf = tmp.toCharArray();
				for(int k = 0; k < buf.length; k ++) {
					out[outLen ++] = buf[k];
				}
			} else {
				out[outLen ++] = '&';
				out[outLen ++] = '#';
				numLen = stringSize(value);
				for(int k = outLen + numLen - 1; k >= outLen; k --) {
					out[k] = (char) (value % 10 + '0');
					value /= 10;
				}
				outLen += numLen;
				out[outLen ++] = ';';
			}
		}
		return new String(out, 0, outLen);
	}
	
	/**
	 * 解码html中的&#xxx;格式的字符
	 * @param source [String]输入数据源
	 * @return
	 */
	public static final String decodeHTML(String source) {
		char aChar;
		char[] in = source.toCharArray();
		char[] out = new char[in.length];
		int outLen = 0;
		int off = 0;
		int end = in.length;
		int codeLen = 0; // 编码的长度
		int value = 0;
		Boolean isChar = null; // 判断编码是数字还是字母
		while(off < end) {
			aChar = in[off ++];
			out[outLen ++] = aChar;
			if(aChar == '&') {
				codeLen = 1;
			} else if(codeLen == 1 && aChar == '#') {
				codeLen = 2;
				value = 0;
				if(off < end) {
					aChar = in[off];
					if(StringUtil.isNumber(aChar)) {
						isChar = false;
					} else {
						isChar = null;
					}
				}
			} else if(codeLen == 1 && StringUtil.isLetter(aChar)) {
				codeLen = 2;
				isChar = true;
			} else if(codeLen >= 2 && !isChar && StringUtil.isNumber(aChar)) {
				codeLen ++;
				value  = value * 10 + aChar - '0';
			} else if(codeLen > 1 && isChar && StringUtil.isLetter(aChar)) {
				codeLen ++;
			} else if(codeLen > 2 && aChar == ';') {
				codeLen ++;
				outLen -= codeLen;
				// 分为特殊编码和数字编码
				if(isChar) {
					Character tmp = decodeHTMLChar(new String(in, off - codeLen, codeLen));
					if(tmp != null) {
						out[outLen ++] = tmp;
					} else {
						outLen += codeLen;
					}
				} else {
					out[outLen] = (char) value;
				}
			} else {
				codeLen = 0;
			}
		}
		return new String(out, 0, outLen);
	}
	
	/**
	 * 用于快速计算十六进制的数组
	 */
	private final static int [] sixteen_table = { 1, 16, 256, 4096};
	private final static char[] sixteen_char_table = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	/**
	 * 编码unicode
	 * @param source
	 * @return
	 */
	public static final String encodeUnicode(String source) {
		char[] in = source.toCharArray();
		char[] out = new char[in.length * 6];
		int outLen = 0;
		int end = in.length;
		int value; // 每个字符对应的十进制数
		for(int i = 0; i < end; i ++) {
			value = in[i];
			out[outLen ++] = '\\';
			out[outLen ++] = 'u';
			for(int k = 3; k >= 0; k --) {
				if(value >= sixteen_table[k]) {
					int result = value >> (k << 2);
					value -= result << (k << 2);
					out[outLen ++] = sixteen_char_table[result - 1];
				} else {
					out[outLen ++] = '0';
				}
			}
		}
		return String.valueOf(out);
	}
	
	/**
	 * 解码unicode
	 * @param source
	 * @return
	 */
	public static final String decodeUnicode(String source) {
		return loadConvert(source.toCharArray(), 0, source.length());
	}
	
	/**
	 * 把\\uXXXX格式的中文字符，转换成中文
	 * 同时把\\转义的字符保留下来
	 * @param in [char[]]输入数据
	 * @param off [int]起始位置
	 * @param len [int]转换字符长度
	 * @return [String]返回转换结果字符串
	 */
	private static final String loadConvert (char[] in, int off, int len) {
		char aChar;
		char[] out = new char[len];
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if(aChar == 'u') {
					// Read the xxxx
					int value=0;
					for (int i=0; i<4; i++) {
						aChar = in[off++];
						switch (aChar) {
							case '0': case '1': case '2': case '3': case '4':
							case '5': case '6': case '7': case '8': case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a': case 'b': case 'c':
							case 'd': case 'e': case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A': case 'B': case 'C':
							case 'D': case 'E': case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException(
											"Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char)value;
				} else {
					if (aChar == 't') aChar = '\t';
					else if (aChar == 'r') aChar = '\r';
					else if (aChar == 'n') aChar = '\n';
					else if (aChar == 'f') aChar = '\f';
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = aChar;
			}
		}
		return new String (out, 0, outLen);
	}
	
	/**
	 * html特殊字符映射表
	 */
	private static final Map<Character, String> html_encode_chars_mapper = new HashMap<Character, String>();
	private static final Map<String, Character> html_decode_chars_mapper = new HashMap<String, Character>();
	
	/**
	 * 编码html单个特殊字符(500ms)
	 * @param c [char]待编码字符
	 * @return [String]编码之后的字符串
	 */
	private static final String encodeHTMLChar(char c) {
		return html_encode_chars_mapper.get(c);
	}
	
	/**
	 * 解码html单个特殊字符(500ms)
	 * @param source [String]&xxxx;格式的字符串
	 * @return [Character]解析后的字符
	 */
	public static final Character decodeHTMLChar(String source) {
		return html_decode_chars_mapper.get(source);
	}
	
	/**
	 * 将URL编码
	 * 
	 * @param source
	 * @return
	 */
	public static final String encodeURL(String source) {
		String target;
		try {
			target = URLEncoder.encode(source, "UTF-8");
		} catch (Exception e) {
			_LOG.error("url编码失败！", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * 将URL解码
	 * 
	 * @param source
	 * @return
	 */
	public static final String decodeURL(String source) {
		String target;
		try {
			target = URLDecoder.decode(source, "UTF-8");
		} catch (Exception e) {
			_LOG.error("url解码失败！", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * MD5加密
	 * @param source
	 * @return
	 */
	public static final String md5(String source) {
		return DigestUtils.md5Hex(source);
	}

	/**
	 * html特殊字符映射表
	 */
	private static final String[][] special_char_table = { { "&Alpha;", "Α" }, { "&Beta;", "Β" }, { "&Gamma;", "Γ" },
			{ "&Delta;", "Δ" }, { "&Epsilon;", "Ε" }, { "&Zeta;", "Ζ" }, { "&Eta;", "Η" }, { "&Theta;", "Θ" },
			{ "&Iota;", "Ι" }, { "&Kappa;", "Κ" }, { "&Lambda;", "Λ" }, { "&Mu;", "Μ" }, { "&Nu;", "Ν" },
			{ "&Xi;", "Ξ" }, { "&Omicron;", "Ο" }, { "&Pi;", "Π" }, { "&Rho;", "Ρ" }, { "&Sigma;", "Σ" },
			{ "&Tau;", "Τ" }, { "&Upsilon;", "Υ" }, { "&Phi;", "Φ" }, { "&Chi;", "Χ" }, { "&Psi;", "Ψ" },
			{ "&Omega;", "Ω" }, { "&alpha;", "α" }, { "&beta;", "β" }, { "&gamma;", "γ" }, { "&delta;", "δ" },
			{ "&epsilon;", "ε" }, { "&zeta;", "ζ" }, { "&eta;", "η" }, { "&theta;", "θ" }, { "&iota;", "ι" },
			{ "&kappa;", "κ" }, { "&lambda;", "λ" }, { "&mu;", "μ" }, { "&nu;", "ν" }, { "&xi;", "ξ" },
			{ "&omicron;", "ο" }, { "&pi;", "π" }, { "&rho;", "ρ" }, { "&sigmaf;", "ς" }, { "&sigma;", "σ" },
			{ "&tau;", "τ" }, { "&upsilon;", "υ" }, { "&phi;", "φ" }, { "&chi;", "χ" }, { "&psi;", "ψ" },
			{ "&omega;", "ω" }, { "&thetasym;", "ϑ" }, { "&upsih;", "ϒ" }, { "&piv;", "ϖ" }, { "&bull;", "•" },
			{ "&hellip;", "…" }, { "&prime;", "′" }, { "&Prime;", "″" }, { "&oline;", "‾" }, { "&frasl;", "⁄" },
			{ "&weierp;", "℘" }, { "&image;", "ℑ" }, { "&real;", "ℜ" }, { "&trade;", "™" }, { "&alefsym;", "ℵ" },
			{ "&larr;", "←" }, { "&uarr;", "↑" }, { "&rarr;", "→" }, { "&darr;", "↓" }, { "&harr;", "↔" },
			{ "&crarr;", "↵" }, { "&lArr;", "⇐" }, { "&uArr;", "⇑" }, { "&rArr;", "⇒" }, { "&dArr;", "⇓" },
			{ "&hArr;", "⇔" }, { "&forall;", "∀" }, { "&part;", "∂" }, { "&exist;", "∃" }, { "&empty;", "∅" },
			{ "&nabla;", "∇" }, { "&isin;", "∈" }, { "&notin;", "∉" }, { "&ni;", "∋" }, { "&prod;", "∏" },
			{ "&sum;", "∑" }, { "&minus;", "−" }, { "&lowast;", "∗" }, { "&radic;", "√" }, { "&prop;", "∝" },
			{ "&infin;", "∞" }, { "&ang;", "∠" }, { "&and;", "∧" }, { "&or;", "∨" }, { "&cap;", "∩" }, { "&cup;", "∪" },
			{ "&int;", "∫" }, { "&there4;", "∴" }, { "&sim;", "∼" }, { "&cong;", "≅" }, { "&asymp;", "≈" },
			{ "&ne;", "≠" }, { "&equiv;", "≡" }, { "&le;", "≤" }, { "&ge;", "≥" }, { "&sub;", "⊂" }, { "&sup;", "⊃" },
			{ "&nsub;", "⊄" }, { "&sube;", "⊆" }, { "&supe;", "⊇" }, { "&oplus;", "⊕" }, { "&otimes;", "⊗" },
			{ "&perp;", "⊥" }, { "&sdot;", "⋅" }, { "&lceil;", "⌈" }, { "&rceil;", "⌉" }, { "&lfloor;", "⌊" },
			{ "&rfloor;", "⌋" }, { "&loz;", "◊" }, { "&spades;", "♠" }, { "&clubs;", "♣" }, { "&hearts;", "♥" },
			{ "&diams;", "♦" }, { "&nbsp;", " " }, { "&iexcl;", "¡" }, { "&cent;", "¢" }, { "&pound;", "£" },
			{ "&curren;", "¤" }, { "&yen;", "¥" }, { "&brvbar;", "¦" }, { "&sect;", "§" }, { "&uml;", "¨" },
			{ "&copy;", "©" }, { "&ordf;", "ª" }, { "&laquo;", "«" }, { "&not;", "¬" }, { "&shy;", "­" },
			{ "&reg;", "®" }, { "&macr;", "¯" }, { "&deg;", "°" }, { "&plusmn;", "±" }, { "&sup2;", "²" },
			{ "&sup3;", "³" }, { "&acute;", "´" }, { "&micro;", "µ" }, { "&quot;", "\"" }, { "&lt;", "<" },
			{ "&gt;", ">" } };
	
	static {
		// 构建html特殊字符映射表
		for(int i = 0; i < special_char_table.length; i ++) {
			// 编码映射
			html_encode_chars_mapper.put(special_char_table[i][1].charAt(0), special_char_table[i][0]);
			// 解码映射
			html_decode_chars_mapper.put(special_char_table[i][0], special_char_table[i][1].charAt(0));
		}
	}
	
}
