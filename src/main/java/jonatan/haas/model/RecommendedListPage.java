package jonatan.haas.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="recommended_list_page")
public class RecommendedListPage {

    @Id
    @SequenceGenerator(name = "recommendedListPageSeq", sequenceName = "recommended_list_page_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendedListPageSeq")
    @NotNull
    @Column(name = "recommended_list_page_id")
    private Long recommendedListPageId;

    @NotNull
    @Column(name = "recommended_list_id")
    private Long recommendedListId;

    @NotNull
    @Column(name = "page_number")
    private Integer pageNumber;

}
