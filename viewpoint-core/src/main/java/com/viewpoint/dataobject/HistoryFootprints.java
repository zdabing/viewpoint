package com.viewpoint.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class HistoryFootprints {

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private Integer historyId;

        private String historyContent;

        /**
         * 事迹的开始时间排序用
         */
        private Integer startYear;

  //      private Integer endYear;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updateTime;

        private Integer sort;

}
