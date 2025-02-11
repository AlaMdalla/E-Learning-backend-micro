package E_Learning.Project.DTO;

import java.util.List;

public class CompetitionDto {
    private int id;
    private String title;
    private  String description;
    private List<String> prices;
    private CompetitionDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.prices = builder.prices;
    }
    public static class Builder {
        private int id;
        private String title;
        private  String description;
        private List<String> prices;



        public Builder id(int id){
            this.id=id;
            return  this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder prices(List<String> prices) {
            this.prices = prices;
            return this;
        }

        public CompetitionDto build() {
            return new CompetitionDto(this  );
        }

    }
}
