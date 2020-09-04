package extension;

import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

public class OddEven {

	@UserFunction(name="extension.isOdd")
	@Description("判断数字是否为奇数")
	public boolean isOdd(@Name("number") Long number) {
		return number % 2 == 1;
	}
}
