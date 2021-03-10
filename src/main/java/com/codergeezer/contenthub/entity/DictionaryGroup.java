package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_dictionary_group")
public class DictionaryGroup extends BaseEntity {

    private Long dictionaryGroupId;

    private String dictionaryGroupCode;

    private String dictionaryGroupName;

    @Id
    @Column(name = "dictionary_group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getDictionaryGroupId() {
        return dictionaryGroupId;
    }

    public void setDictionaryGroupId(Long dictionaryGroupId) {
        this.dictionaryGroupId = dictionaryGroupId;
    }

    @Basic
    @Column(name = "dictionary_group_code")
    public String getDictionaryGroupCode() {
        return dictionaryGroupCode;
    }

    public void setDictionaryGroupCode(String dictionaryGroupCode) {
        this.dictionaryGroupCode = dictionaryGroupCode;
    }

    @Basic
    @Column(name = "dictionary_group_name")
    public String getDictionaryGroupName() {
        return dictionaryGroupName;
    }

    public void setDictionaryGroupName(String dictionaryGroupName) {
        this.dictionaryGroupName = dictionaryGroupName;
    }
}
