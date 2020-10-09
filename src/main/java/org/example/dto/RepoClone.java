package org.example.dto;

public class RepoClone {
    @Override
    public String toString() {
        return "RepoClone{" +
                "name='" + name + '\'' +
                ", href='" + href + '\'' +
                '}';
    }

    private String name;
    private String href;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(final String href) {
        this.href = href;
    }
}
