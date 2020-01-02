package io.github.dimpon.testprivate.actions;
import static io.github.dimpon.testprivate.API.cast;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetterActionTest {

	@Test
	void getPrivateString() {
		SomePOJOClass o = new SomePOJOClass("hello");
		GetString obj = cast(o).toInterface(GetString.class);
		Assertions.assertEquals("hello", obj.getVal());
	}

	@Test
	void getPrivateBoolean() {
		SomePOJOClass o = new SomePOJOClass(true);
		GetBoolean obj = cast(o).toInterface(GetBoolean.class);
		Assertions.assertEquals(true, obj.isFinished());
	}

	@Test
	void getPrivateList() {
		ArrayList arrayList = new ArrayList();
		SomePOJOClass o = new SomePOJOClass(arrayList);
		GetList obj = cast(o).toInterface(GetList.class);
		Assertions.assertEquals(arrayList, obj.getMyList());
	}

	interface GetList {
		List getMyList();
	}

	interface GetString {
		String getVal();
	}

	interface GetBoolean {
		Boolean isFinished();
	}

	static class SomePOJOClass {
		private String val;
		private ArrayList myList;
		private Boolean finished;

		public SomePOJOClass(String val) {
			this.val = val;
		}

		public SomePOJOClass(ArrayList myList) {
			this.myList = myList;
		}

		public SomePOJOClass(Boolean finished) {
			this.finished = finished;
		}
	}
}
