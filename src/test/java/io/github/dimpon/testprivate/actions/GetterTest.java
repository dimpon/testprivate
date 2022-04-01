package io.github.dimpon.testprivate.actions;

import io.github.dimpon.testprivate.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GetterTest {

  ////test generic value////
  interface GetValue<V> {
    V getValue();
  }

  @Test
  void getPrivateStringGenerics() {
    ValueContainer<String> o = new ValueContainer<>("hello");
    @SuppressWarnings("unchecked")
    GetValue<String> obj = API.lookupPrivatesIn(o).usingInterface(GetValue.class);
    Assertions.assertEquals("hello", obj.getValue());
  }

  @Test
  void getPrivateListGenerics() {
    ArrayList arrayList = new ArrayList();
    ValueContainer<ArrayList> o = new ValueContainer<>(arrayList);
    @SuppressWarnings("unchecked")
    GetValue<List> obj = API.lookupPrivatesIn(o).usingInterface(GetValue.class);
    Assertions.assertEquals(arrayList, obj.getValue());
  }

  private static class ValueContainer<V> {
    private V value;

    ValueContainer(V value) {
      this.value = value;
    }
  }

  ////test List////
  interface GetList {
    List getMyList();
  }

  @Test
  void getPrivateArrayList() {
    ArrayList arrayList = new ArrayList();
    ListContainer o = new ListContainer(arrayList);
    GetList obj = API.lookupPrivatesIn(o).usingInterface(GetList.class);
    Assertions.assertEquals(arrayList, obj.getMyList());
  }

  private static class ListContainer {
    private ArrayList myList;

    ListContainer(ArrayList value) {
      this.myList = value;
    }
  }

  ////test String////
  interface GetString {
    String getSecretString();
  }

  @Test
  void getPrivateString() {
    StringContainer o = new StringContainer("hello");
    GetString obj = API.lookupPrivatesIn(o).usingInterface(GetString.class);
    Assertions.assertEquals("hello", obj.getSecretString());
  }

  private static class StringContainer {
    private String secretString;

    StringContainer(String value) {
      this.secretString = value;
    }
  }

  ////test boolean////
  interface GetBoolean {
    Boolean isFinished();

    boolean isNotFinished();
  }

  @Test
  void getPrivateBoolean() {
    BooleanContainer o = new BooleanContainer();
    GetBoolean obj = API.lookupPrivatesIn(o).usingInterface(GetBoolean.class);
    Assertions.assertTrue(obj.isFinished());
    Assertions.assertFalse(obj.isNotFinished());
  }

  private static class BooleanContainer {
    private Boolean finished = Boolean.TRUE;
    private boolean notFinished = false;
  }

  ////test static////
  interface GetStatic {
    String getNameForAll();
  }

  @Test
  void getPrivateStatic() {
    GetStatic obj = API.lookupPrivatesIn(StaticStringContainer.class).usingInterface(GetStatic.class);
    Assertions.assertEquals("Name For All", obj.getNameForAll());
  }

  private static class StaticStringContainer {
    private static String nameForAll = "Name For All";
  }

  ////test superclass////
  interface GetHumusOrHamon {
    String getHumus();

    String getHamon();
  }

  @Test
  void getPrivateFromSuperclass() {
    GetHumusOrHamon obj = API.lookupPrivatesIn(new NoHumus())
        .lookupInSuperclass()
        .usingInterface(GetHumusOrHamon.class);
    Assertions.assertEquals("It's a humus", obj.getHumus());
    Assertions.assertEquals("My hamon is here", obj.getHamon());
  }

  private static class HumusHere {
    private String humus = "It's a humus";
    private String hamon = "My hamon is far";
  }

  private static class NoHumus extends HumusHere {
    private String hamon = "My hamon is here";
  }
}
