package org.example.dto;

import java.util.List;

public class RepoLink {

    private List<RepoClone> clone;

    public List<RepoClone> getClone() {
        return clone;
    }

    public void setClone(final List<RepoClone> clone) {
        this.clone = clone;
    }

    @Override
    public String toString() {
        return "RepoLink{" +
                "clone=" + clone +
                '}';
    }
}
