/**
 * 
 */
package github.com.ekis.algorithms.chapter1.api;

/**
 * @author Erik
 *
 */
public enum UnionFindEnum {
	
	TINY_UF("/tinyUF.txt"),
	MEDIUM_UF("/mediumUF.txt"),
	LARGE_UF("/largeUF.txt");
	
	String value;
	
	private UnionFindEnum(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}

}
