package com.yue.ygateway.filter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class YBloomFilter {
    // 默认位图的大小
    private static int def_size = 1 << 30;
    // 位图
    private static BitSet bitset;
    // 哈希函数数量
    private static int hashnum = 2;
    // 两个哈希算法
    private MessageDigest MD5;
    private MessageDigest SHA1;
    public YBloomFilter() throws NoSuchAlgorithmException {
        bitset = new BitSet(def_size);
        MD5 = MessageDigest.getInstance("MD5");
        SHA1 = MessageDigest.getInstance("SHA-1");
        add("127.0.0.1");
    }
    public void add(String url) {
        int hash1 = hashmd5(url);
        int hash2 = hashsha1(url);
        bitset.set(hash1);
        bitset.set(hash2);
    }
    private int hashmd5(String url) {
        byte[] bytes = MD5.digest(url.getBytes());
        int hash = 0;
        for (int i = 0; i < 4; i++) {
            hash += ((int)bytes[i] % def_size);
        }
        return Math.abs(hash);
    }
    private int hashsha1(String url) {
        byte[] bytes = SHA1.digest(url.getBytes());
        int hash = 0;
        for (int i = 0; i < 4; i++) {
            hash += ((int)bytes[i] % def_size);
        }
        return Math.abs(hash);

    }
    public boolean contains(String url) {
        int hash1 = hashmd5(url);
        int hash2 = hashsha1(url);
        return bitset.get(hash1) && bitset.get(hash2);
    }
}
