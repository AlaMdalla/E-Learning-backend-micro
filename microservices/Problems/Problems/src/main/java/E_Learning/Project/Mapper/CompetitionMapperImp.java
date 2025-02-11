package E_Learning.Project.Mapper;

import E_Learning.Project.DTO.CompetitionDto;
import E_Learning.Project.Entity.Competition;

public class CompetitionMapperImp implements CompetitionMapper{
    @Override
    public CompetitionDto toDto(Competition competition) {
        CompetitionDto competitionDto = new CompetitionDto.Builder()
                .id(competition.getId())
                .title(competition.getTitle())
                .description(competition.getDescription())
                .prices(competition.getPrices())
                .build();

        return competitionDto;

    }

    @Override
    public Competition toEntity(CompetitionDto competitionDto) {
        return null;
    }
}
