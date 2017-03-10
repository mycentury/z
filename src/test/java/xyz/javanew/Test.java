/**
 * 
 */
package xyz.javanew;

/**
 * @Desc
 * @author wewenge.yan
 * @Date 2017年3月13日
 * @ClassName Test
 */
public class Test {

	@org.junit.Test
	public void test() {
		System.out.println(Integer.parseInt("000000", 16));
		System.out.println(Integer.parseInt("ff0000", 16));
		System.out.println(Integer.parseInt("0000FF", 16));
		System.out.println(Integer.parseInt("ffffFF", 16));
		System.out.println(Integer.parseInt("ffffFF"));
	}

}
