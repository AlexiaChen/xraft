package in.xnnyygn.xraft.core.log;

import org.junit.Assert;
import org.junit.Test;

public class MemorySnapshotTest {

    @Test(expected = IndexOutOfBoundsException.class)
    public void testReadEmpty() {
        MemorySnapshot snapshot = new MemorySnapshot(0, 0, new byte[0]);
        snapshot.read(0, 10);
    }

    @Test
    public void testRead1() {
        MemorySnapshot snapshot = new MemorySnapshot(0, 0, "foo".getBytes());
        SnapshotChunk chunk1 = snapshot.read(0, 2);
        Assert.assertArrayEquals(new byte[]{'f', 'o'}, chunk1.toByteArray());
        Assert.assertFalse(chunk1.isLastChunk());
        SnapshotChunk lastChunk = snapshot.read(2, 2);
        Assert.assertArrayEquals(new byte[]{'o'}, lastChunk.toByteArray());
        Assert.assertTrue(lastChunk.isLastChunk());
    }

    @Test
    public void testRead2() {
        MemorySnapshot snapshot = new MemorySnapshot(0, 0, "foo,".getBytes());
        Assert.assertArrayEquals(new byte[]{'f', 'o'}, snapshot.read(0, 2).toByteArray());
        Assert.assertArrayEquals(new byte[]{'o', ','}, snapshot.read(2, 2).toByteArray());
    }

}