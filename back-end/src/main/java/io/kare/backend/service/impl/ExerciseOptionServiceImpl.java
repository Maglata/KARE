package io.kare.backend.service.impl;

import io.kare.backend.entity.*;
import io.kare.backend.mapper.ExerciseOptionMapper;
import io.kare.backend.payload.data.*;
import io.kare.backend.repository.ExerciseOptionRepository;
import io.kare.backend.service.ExerciseOptionService;
import java.util.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class ExerciseOptionServiceImpl implements ExerciseOptionService {
	private final ExerciseOptionRepository exerciseOptionRepository;
	private final ExerciseOptionMapper exerciseOptionMapper;

	public ExerciseOptionServiceImpl(ExerciseOptionRepository exerciseOptionRepository,
		ExerciseOptionMapper exerciseOptionMapper
	) {
		this.exerciseOptionRepository = exerciseOptionRepository;
		this.exerciseOptionMapper = exerciseOptionMapper;
	}

	@Override
	public void save(List<WorkoutExercisePayload> exercisePayloads, List<ExerciseEntity> exercises, WorkoutEntity workout) {
		this.exerciseOptionRepository.saveAll(this.exerciseOptionMapper.toExerciseOptionEntities(
			exercisePayloads,
			exercises,
			workout
		));
	}

	@Override
	public void update(
		List<WorkoutExercisePayload> exercisePayloads,
		List<ExerciseEntity> exercises,
		WorkoutEntity workout
	) {
		List<ExerciseOptionEntity> existingEntities = this.exerciseOptionRepository.findByWorkoutIdAndExerciseIds(
			workout.getId(),
			exercises.stream()
				.map(ExerciseEntity::getId)
				.toList()
		);
		this.exerciseOptionRepository.deleteAll(existingEntities);
		this.exerciseOptionRepository.saveAll(this.exerciseOptionMapper.toExerciseOptionEntities(
			exercisePayloads,
			exercises,
			workout
		));
	}

	@Override
	public List<ExerciseOptionEntity> findAllByWorkoutIds(List<String> ids, UserEntity user) {
		return this.exerciseOptionRepository.findAllByWorkoutIdsAndUser(ids, user);
	}

	@Override
	public ExerciseOptionEntity mapToEntity(WorkoutFullExercisePayload exercisePayload, ExerciseEntity exercise, WorkoutEntity workoutEntity) {
		return this.exerciseOptionMapper.toExerciseOptionEntity(exercisePayload, exercise, workoutEntity);
	}

	@Override
	public List<ExerciseOptionEntity> save(List<ExerciseOptionEntity> exerciseOptionEntities) {
		return this.exerciseOptionRepository.saveAll(exerciseOptionEntities);
	}

	@Override
	public void removeExerciseOptionsByWorkout(WorkoutEntity workout) {
		List<ExerciseOptionEntity> entities = this.exerciseOptionRepository.findAllByWorkout(workout);
		this.exerciseOptionRepository.deleteAll(entities);
	}
}
