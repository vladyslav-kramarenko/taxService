package com.my.kramarenko.taxService.db.entity;

public class Type {
    private String id;
    private String name;
    private int isIndividual;
    public int getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(int isIndividual) {
        this.isIndividual = isIndividual;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        if (!id.equals(type.id)) return false;
        return name.equals(type.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }
}
