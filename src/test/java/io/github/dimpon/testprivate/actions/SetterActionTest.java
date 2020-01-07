package io.github.dimpon.testprivate.actions;
import static io.github.dimpon.testprivate.API.cast;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.dimpon.testprivate.TestprivateException;

class SetterActionTest {

	@Test
	void setPrivateField() {
		SomePOJOClass o = new SomePOJOClass();
		PlaysWithPrivateField obj = cast(o).toInterface(PlaysWithPrivateField.class);

		Assertions.assertThrows(TestprivateException.class, () -> obj.putValue("some"), "setter must be started from 'set'");

		Assertions.assertThrows(TestprivateException.class, () -> obj.setVal("some"), "method setValue must have void");

		Assertions.assertThrows(TestprivateException.class, () -> obj.setVal(), "setter must have 1 argument");

		Assertions.assertThrows(TestprivateException.class, () -> obj.setVal("1", "2"), "setter must have 1 argument");

		Assertions.assertThrows(TestprivateException.class, () -> obj.setValue("some"), "method setValue does not exist");
	}

	@Test
	void setSubtypeToPrivateField() {
		SomePOJOClass o = new SomePOJOClass();
		SetupArrayList obj = cast(o).toInterface(SetupArrayList.class);
		ArrayList arrayList = new ArrayList();
		obj.setMyList(arrayList);

		Assertions.assertEquals(arrayList,o.readMyList());
	}

	interface PlaysWithPrivateField {
		String setVal(String v);

		void setVal(String v1, String v2);

		void setVal();

		String setValue(String v);

		String putValue(String v);
	}

	interface SetupArrayList {
		void setMyList(ArrayList list);
	}

	static class SomePOJOClass {
		private String val;
		private List myList;

		public List readMyList() {
			return myList;
		}

		public String readVal() {
			return val;
		}

		public void readVal(Integer i) {

		}

		public void readBook() {

		}
	}
}
