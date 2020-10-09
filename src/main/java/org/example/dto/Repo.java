package org.example.dto;

import java.util.List;

public class Repo {
    @Override
    public String toString() {
        return "Repo{" +
                "name='" + name + '\'' +
                ", links=" + links +
                '}';
    }

    private String name;
    private RepoLink links;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RepoLink getLinks() {
        return links;
    }

    public void setLinks(RepoLink links) {
        this.links = links;
    }
}
