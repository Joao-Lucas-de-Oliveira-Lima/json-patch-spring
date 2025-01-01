package dev.jl.jsonpatchspring.utils.mapper;

import org.springframework.stereotype.Component;

@Component
public class ModelMapper implements Mapper {
    private final org.modelmapper.ModelMapper mapper;

    public ModelMapper() {
        mapper = new org.modelmapper.ModelMapper();
        mapper.getConfiguration().setPropertyCondition(mappingContext -> mappingContext.getSource() != null);
    }

    @Override
    public <S, D> D mapToObject(S source, Class<D> destination) {
        return mapper.map(source, destination);
    }

    @Override
    public <S, D> void mapProperties(S source, D destination) {
        mapper.map(source, destination);
    }
}
