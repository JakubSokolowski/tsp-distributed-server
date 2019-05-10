package com.tsp.bean;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "files")
public class File implements Serializable {

    @Id
    @Column(name = "file_name")
    private String file_name;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "file_data")
    private byte[] file_data;

    public File() {

    }

    public File(String file_name, byte[] file_data) {
        this.file_name = file_name;
        this.file_data = file_data;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public byte[] getFile_data() {
        return file_data;
    }

    public void setFile_data(byte[] file_data) {
        this.file_data = file_data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(file_name, file.file_name) &&
                Objects.equals(file_data, file.file_data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file_name, file_data);
    }

    @Override
    public String toString() {
        return "File{" +
                "file_name='" + file_name + '\'' +
                ", file_data='" + file_data + '\'' +
                '}';
    }
}
