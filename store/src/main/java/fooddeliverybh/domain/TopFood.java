package fooddeliverybh.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="TopFood_table")
@Data
public class TopFood {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private String name;
        private Integer evalCount;
        private Float score;
        private Integer totalScore;
        private Integer orderCount;

}