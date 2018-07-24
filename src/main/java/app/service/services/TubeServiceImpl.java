package app.service.services;

import app.domain.entities.Tube;
import app.domain.models.service.TubeServiceModel;
import app.errors.InvalidTubeException;
import app.service.repositories.TubeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TubeServiceImpl implements TubeService {

    private final TubeRepository tubeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TubeServiceImpl(TubeRepository tubeRepository, ModelMapper modelMapper) {
        this.tubeRepository = tubeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void uploadTube(TubeServiceModel tubeServiceModel) {
        Tube tube = this.modelMapper.map(tubeServiceModel, Tube.class);

        this.tubeRepository.save(tube);
    }

    @Override
    public TubeServiceModel extractTubeById(String id) {
        Tube tubeFromDb = this.tubeRepository.findById(id).orElse(null);

        if (tubeFromDb == null) {
            throw new InvalidTubeException();
        }

        return this.modelMapper.map(tubeFromDb, TubeServiceModel.class);
    }

    @Override
    public List<TubeServiceModel> extractAllTubes() {
        List<Tube> tubesFromDb = this.tubeRepository.findAll();
        List<TubeServiceModel> tubeServiceModels = new ArrayList<>();

        for (Tube tube : tubesFromDb) {
            tubeServiceModels.add(this.modelMapper.map(tube, TubeServiceModel.class));
        }

        return tubeServiceModels;
    }

    @Override
    public List<TubeServiceModel> extractAllTubes(String username) {
        return this.extractAllTubes().stream().filter(t -> t.getUploader().getUsername().equals(username)).collect(Collectors.toList());
    }
}
