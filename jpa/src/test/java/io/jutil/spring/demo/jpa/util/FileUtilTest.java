package io.jutil.spring.demo.jpa.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-02-17
 */
public class FileUtilTest {

    @Test
    public void testGetLastFolder() {
        Assertions.assertEquals("a", FileUtil.getLastFolder("a"));
        Assertions.assertEquals("a", FileUtil.getLastFolder("/a"));
        Assertions.assertEquals("a", FileUtil.getLastFolder("a.z"));
        Assertions.assertEquals("a", FileUtil.getLastFolder("/b/a.z"));
        Assertions.assertEquals("abc", FileUtil.getLastFolder("/b/abc.zip"));
        Assertions.assertEquals("efg", FileUtil.getLastFolder("/c/d/z/y/x/efg.zip"));
    }

    @Test
    public void testGetFilename() {
        Assertions.assertEquals("a", FileUtil.getFilename("/a"));
        Assertions.assertEquals("a.b", FileUtil.getFilename("/a.b"));
        Assertions.assertEquals("b", FileUtil.getFilename("/a/b"));
        Assertions.assertEquals("b.c", FileUtil.getFilename("/a/b.c"));
    }

    @Test
    public void testGetRelativeDir() {
        Assertions.assertEquals("/", FileUtil.getRelativeDir("/a", ""));
        Assertions.assertEquals("/a", FileUtil.getRelativeDir("/a/b", ""));
        Assertions.assertEquals("/b", FileUtil.getRelativeDir("/a/b/c", "/a"));
        Assertions.assertEquals("/b/c", FileUtil.getRelativeDir("/a/b/c/d", "/a"));
        Assertions.assertEquals("/c/d", FileUtil.getRelativeDir("/a/b/c/d/e", "/a/b"));
        Assertions.assertEquals("/b", FileUtil.getRelativeDir("/a/b/c", "/a/x"));
    }

    @Test
    public void testConcat() {
        Assertions.assertEquals("/", FileUtil.concat());
        Assertions.assertEquals("/a", FileUtil.concat("/", "/a"));
        Assertions.assertEquals("/a", FileUtil.concat("a"));
        Assertions.assertEquals("/a/b", FileUtil.concat("a", "b"));
        Assertions.assertEquals("/a/b", FileUtil.concat("/a/", "/b/"));
        Assertions.assertEquals("/a/b/c/d", FileUtil.concat("a/b", "c", "/d//"));
    }

}
