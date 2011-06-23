package net.sf.orcc.cal;

import junit.framework.Assert;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@SuppressWarnings("all")
@RunWith(XtextRunner.class)
@InjectWith(CalInjectorProvider.class)
public class ParserTest {

	@Inject
	private ParseHelper<AstEntity> parser;

	@Test
	public void testParsing() throws Exception {
		AstEntity entity = parser.parse("actor A() ==> : end");
		Assert.assertEquals("A", entity.getName());
	}

}
