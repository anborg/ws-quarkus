package api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Person{
    public final String id;
    public final String firstName;
    public final String lastName;
    public Person(String id, String firstName, String lastName){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
    }
    public static Person of(String id, String fn, String ln) {
        return new Person(id,fn,ln);
    }
}
