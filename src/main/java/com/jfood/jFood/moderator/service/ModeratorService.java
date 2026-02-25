package com.jfood.jFood.moderator.service;

import com.jfood.jFood.moderator.dto.CreateModeratorDto;
import com.jfood.jFood.moderator.dto.ResponseModeratorDto;
import com.jfood.jFood.moderator.dto.UpdateModeratorDto;
import com.jfood.jFood.moderator.model.Moderator;

import java.util.List;

public interface ModeratorService {
    ResponseModeratorDto createModerator(CreateModeratorDto createModeratorDto);
    List<Moderator> getModerators();
    void deleteModerator(long moderatorId);
    ResponseModeratorDto updateModerator(UpdateModeratorDto updateModeratorDto);
}
