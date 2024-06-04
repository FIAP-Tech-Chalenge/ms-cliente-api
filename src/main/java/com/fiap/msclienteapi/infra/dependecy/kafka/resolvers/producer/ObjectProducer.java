package com.fiap.msclienteapi.infra.dependecy.kafka.resolvers.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ObjectProducer {
    private String servers;
    private String topic;
}
