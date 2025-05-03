package domain.person;

import java.util.Objects;

public class Person {
    private String name;
    private String mood; //alegre, triste, etc.
    private int attentionTime; //expresado en milisegundos



    public Person(String name, String mood, int attentionTime) {
        this.name = name;
        this.mood = mood;
        this.attentionTime = attentionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public int getAttentionTime() {
        return attentionTime;
    }

    public void setAttentionTime(int attentionTime) {
        this.attentionTime = attentionTime;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", mood='" + mood + '\'' +
                ", attentionTime=" + attentionTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(mood, person.mood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mood);
    }
}


