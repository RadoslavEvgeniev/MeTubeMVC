package app.service.services;

import app.domain.models.service.TubeServiceModel;

import java.util.List;

public interface TubeService {

    void uploadTube(TubeServiceModel tubeServiceModel);

    TubeServiceModel extractTubeById(String id);

    List<TubeServiceModel> extractAllTubes();

    List<TubeServiceModel> extractAllTubes(String username);
}
