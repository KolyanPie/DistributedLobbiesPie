package ru.kolyanpie.dlp.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String name;

    @Override
    public String toString() {
        return String.format("User{name='%s', id='%s'}", name, id);
    }
}
