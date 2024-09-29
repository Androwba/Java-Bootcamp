package edu.school21.models;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

@OrmEntity(table = "all_types_table")
public class AllTypesTest {

    @OrmColumnId
    private Long id;

    @OrmColumn(name = "string_field", length = 50)
    private String stringField;

    @OrmColumn(name = "int_field")
    private Integer intField;

    @OrmColumn(name = "double_field")
    private Double doubleField;

    @OrmColumn(name = "boolean_field")
    private Boolean booleanField;

    @OrmColumn(name = "long_field")
    private Long longField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public Boolean getBooleanField() {
        return booleanField;
    }

    public void setBooleanField(Boolean booleanField) {
        this.booleanField = booleanField;
    }

    public Long getLongField() {
        return longField;
    }

    public void setLongField(Long longField) {
        this.longField = longField;
    }
}
