package E_Learning.Project.Mapper;

import E_Learning.Project.DTO.CompetitionDto;
import E_Learning.Project.Entity.Competition;

public interface CompetitionMapper {
    CompetitionDto toDto(Competition competition);

    Competition toEntity(CompetitionDto competitionDto);
}
