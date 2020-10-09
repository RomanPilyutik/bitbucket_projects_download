package org.example.dto;

import java.util.List;

public class RepoList {
    List<Repo> values;

    @Override
    public String toString() {
        return "RepoList{" +
                "values=" + values +
                '}';
    }

    public List<Repo> getValues() {
        return values;
    }

    public void setValues(final List<Repo> values) {
        this.values = values;
    }
}
