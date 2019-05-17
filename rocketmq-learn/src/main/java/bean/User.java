package bean;

public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private String title;

    public User(Integer id,String name, Integer age, String sex,String title) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
