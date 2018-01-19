package cn.redis.service;

public interface Function<T, E>{
    public T callback(E e);
}
