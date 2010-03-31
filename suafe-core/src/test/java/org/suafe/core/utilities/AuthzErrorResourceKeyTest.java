package org.suafe.core.utilities;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Test;

public class AuthzErrorResourceKeyTest {
	@Test
	public void testAuthzErrorResourceKey() {
		try {
			final Class<?> c = AuthzErrorResourceKey.class;

			for (final Field f : c.getFields()) {
				if (f.getType().equals(AuthzErrorResourceKey.class)) {
					final AuthzErrorResourceKey key = (AuthzErrorResourceKey) f.get(null);

					assertNotNull("Unable to load resource with key \"" + key.toString() + "\"", AuthzResources
							.getString(key));
				}
			}
		}
		catch (final IllegalArgumentException e) {
			fail("IllegalArgumentException caught");
		}
		catch (final IllegalAccessException e) {
			fail("IllegalAccessException caught");
		}
	}
}
