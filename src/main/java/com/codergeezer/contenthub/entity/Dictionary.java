package com.codergeezer.contenthub.entity;

import com.codergeezer.core.base.data.BaseEntity;

import javax.persistence.*;

/**
 * @author haidv
 * @version 1.0
 */
@Entity
@Table(name = "tbl_dictionary")
public class Dictionary extends BaseEntity {

    private Long dictionaryId;

    private String dictionaryCode;

    private String dictionaryName;

    private String dictionaryGroupCode;

    private Long dictionaryGroupId;

    private int sortOrder;

    private String value;

    private Boolean isDefault;

    private String description;

    @Id
    @Column(name = "dictionary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    @Basic
    @Column(name = "dictionary_code")
    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }

    @Basic
    @Column(name = "dictionary_name")
    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
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
    @Column(name = "dictionary_group_id")
    public Long getDictionaryGroupId() {
        return dictionaryGroupId;
    }

    public void setDictionaryGroupId(Long dictionaryGroupId) {
        this.dictionaryGroupId = dictionaryGroupId;
    }

    @Basic
    @Column(name = "sort_order")
    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "is_default")
    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
