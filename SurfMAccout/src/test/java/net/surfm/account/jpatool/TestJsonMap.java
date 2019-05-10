package net.surfm.account.jpatool;

import static net.surfm.infrastructure.JsonMap.PATH_DOT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.surfm.infrastructure.JsonMap;;

@SpringBootTest
public class TestJsonMap {
	

	
	@Test
	public void testParsePathValue() {
		JsonMap testData = new JsonMap();
		testData.put("a","bcd");
		testData.put("b",1.2);
		JsonMap testChildData = new JsonMap();
		testChildData.put("ca","bcd");
		testChildData.put("cb",1.2);
		testData.put("c",testChildData);
		Map<String,Object> result = testData.parsePathValue();
		assertEquals(result.size(), 4);
		assertTrue(result.containsKey("c"+PATH_DOT+"cb"));
		assertEquals(result.get("c"+PATH_DOT+"cb"),1.2);
		
		assertEquals( testData.getByPath("c"+PATH_DOT+"cb"),1.2);
		testData.putByPath("c"+PATH_DOT+"cb", "QQ");
		assertEquals( testData.getByPath("c"+PATH_DOT+"cb"),"QQ");
		
		
		testData.putByPath("c"+PATH_DOT+"test"+PATH_DOT+"abc", "888");
		assertEquals( testData.getByPath("c"+PATH_DOT+"test"+PATH_DOT+"abc"),"888");
		
		System.out.println("show test"+testData.getByPath("c"+PATH_DOT+"test"+PATH_DOT+"abc"));
		
		
	}

}
