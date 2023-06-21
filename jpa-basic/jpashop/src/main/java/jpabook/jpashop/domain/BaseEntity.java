package jpabook.jpashop.domain;

import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

        private String createdBy;
        private String createdDate;
        private String lastModifiedBy;
        private String lastModifiedDate;
}
