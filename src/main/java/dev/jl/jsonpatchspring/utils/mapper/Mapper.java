package dev.jl.jsonpatchspring.utils.mapper;

public interface Mapper {
    <S, D> D mapToObject(S source, Class<D> destination);

    <S, D> void mapProperties(S source, D destination);
}
