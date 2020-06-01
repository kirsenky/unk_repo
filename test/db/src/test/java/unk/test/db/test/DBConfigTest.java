package unk.test.db.test;

import org.junit.*;

import unk.test.db.DBConfig;

public class DBConfigTest {

  @Test
  public void load_test() {
    DBConfig conf = DBConfig.getConfig();
    Assert.assertEquals(conf.getUsr(), "AAAA");
    Assert.assertEquals(conf.getPword(), "BBBB");
    Assert.assertEquals(conf.getDatabaseName(), "CCCC");
  }
}