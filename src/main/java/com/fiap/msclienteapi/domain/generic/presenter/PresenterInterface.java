package com.fiap.msclienteapi.domain.generic.presenter;

import com.fiap.msclienteapi.domain.generic.output.OutputInterface;

import java.util.Map;

public interface PresenterInterface {
    Map<String, Object> toArray();

    OutputInterface getOutput();
}
